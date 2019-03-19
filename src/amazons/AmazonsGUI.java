package amazons;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * AmazonsGUI
 */
public class AmazonsGUI extends BasicAmazonsGUI {

    private int playMode = 0;
    private int declaredResult = 0;
    public int level = 0;
    private static JLabel resultLabel;
    private ResultWindow resultWindow = new ResultWindow();
    private ControlPanel controlPanel;
    private TargetBoardGUI tbg = new TargetBoardGUI();
    private ControlPanelForPuzzle controlPanelForPuzzle = new ControlPanelForPuzzle();
    private AmazonsGUI highestLevelReferenceToThis = this;
    RandomAI rai = new RandomAI();

    private MouseAdapter adaptor = new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent me) {
            JPanel temp = (JPanel) me.getSource();
            temp.setBackground(new Color(150, 150, 150));
        }

        @Override
        public void mouseExited(MouseEvent me) {
            JPanel temp = (JPanel) me.getSource();
            Pair p = new Pair();
            boolean ct = cb.choosingTarget();
            boolean co = cb.choosingObstacle();
            boolean legal = false;
            if (ct || co) {
                for (int i = 0; i < 64; i++) {
                    if (temp.equals(squaresPanel.getComponent(i))) {
                        p.x = i / 8;
                        p.y = i % 8;
                        break;
                    }
                }
            }
            if (ct) {
                for (int i = 0; i < cb.chessToMove.freedom(cb); i++) {
                    if (cb.chessToMove.possiblePositions(cb).get(i).x == p.x
                            && cb.chessToMove.possiblePositions(cb).get(i).y == p.y) {
                        legal = true;
                        break;
                    }
                }
            }
            if (co) {
                for (int i = 0; i < cb.movedChess.freedom(cb); i++) {
                    if (cb.movedChess.possiblePositions(cb).get(i).x == p.x
                            && cb.movedChess.possiblePositions(cb).get(i).y == p.y) {
                        legal = true;
                        break;
                    }
                }
            }
            if (legal)
                temp.setBackground(new Color(100, 100, 100));
            else
                temp.setBackground(new Color(200, 200, 200));
        }

        @Override
        public void mousePressed(MouseEvent me) {
            JPanel temp = (JPanel) me.getSource();
            temp.setBackground(new Color(100, 100, 100));
            int x = 0, y = 0;
            for (int i = 0; i < 64; i++) {
                if (temp.equals(squaresPanel.getComponent(i))) {
                    x = i / 8;
                    y = i % 8;
                    break;
                }
            }
            reactToMouseClick(x, y);
        }
        // @Override
        // public void mouseReleased(MouseEvent me) {
        // JPanel temp = (JPanel) me.getSource();
        // temp.setBackground(new Color(200,200,200));
        // }
    };

    private void reactToMouseClick(int x, int y) {
        if (declaredResult == 1) {
            return;
        }
        if (playMode == 3) {
            runAI();
            checkResult();
            return;
        }
        Pair p = new Pair(x, y);
        boolean ct = cb.choosingTarget();
        boolean co = cb.choosingObstacle();
        boolean legal = false;
        if (ct) {
            for (int i = 0; i < cb.chessToMove.freedom(cb); i++) {
                if (cb.chessToMove.possiblePositions(cb).get(i).x == p.x
                        && cb.chessToMove.possiblePositions(cb).get(i).y == p.y) {
                    legal = true;
                    break;
                }
            }
        }
        if (co) {
            for (int i = 0; i < cb.movedChess.freedom(cb); i++) {
                if (cb.movedChess.possiblePositions(cb).get(i).x == p.x
                        && cb.movedChess.possiblePositions(cb).get(i).y == p.y) {
                    legal = true;
                    break;
                }
            }
        }

        if (cb.choosingTarget() && cb.hasPiece(x, y) && cb.board[x][y].x == cb.chessToMove.x
                && cb.board[x][y].y == cb.chessToMove.y) {
            cb.pressed[x][y] = false;
            displayFreedom(x, y);
            cb.chessToMove = null;
        }
        if (ct) {
            if (legal) {
                for (int i = 0; i < 64; i++) {
                    squaresPanel.getComponent(i).setBackground(new Color(200, 200, 200));
                }
                placePiece(x, y, cb.chessToMove.color);
                removePiece(cb.chessToMove.x, cb.chessToMove.y);
                cb.moveChess(x, y);
                displayFreedom(x, y);
            }
        } else if (co) {
            if (legal) {
                for (int i = 0; i < 64; i++) {
                    squaresPanel.getComponent(i).setBackground(new Color(200, 200, 200));
                }
                placePiece(x, y, 2);
                cb.putObstacle(x, y);
            }
        } else if (cb.hasPiece(x, y) && (cb.board[x][y].color == cb.colorForTurn())) {
            cb.pressed[x][y] = true;
            displayFreedom(x, y);
            cb.setChessToMove();
        }

        if (co && legal) {
            if (playMode == 1 || playMode == 2) {
                runAI();
            }
        }

        if (playMode != 4) {
            checkResult();
        }
        if (playMode == 4) {
            checkResultForPuzzle();
        }
    }

    private void checkResult() {
        int result = cb.declareResult();
        switch (result) {
        case 0:
            resultWindow.createWindow("The black wins!");
            break;
        case 1:
            resultWindow.createWindow("The white wins!");
            break;
        case 2:
            resultWindow.createWindow("This is a tie");
            break;
        default:
            break;
        }
        if (result != -1)
            declaredResult = 1;
    }

    private void checkResultForPuzzle() {
        boolean check = true;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ((this.cb.board[i][j] != null && tbg.cb.board[i][j] != null
                        && !this.cb.board[i][j].equals(tbg.cb.board[i][j]))
                        || (this.cb.board[i][j] != null && tbg.cb.board[i][j] == null)
                        || (this.cb.board[i][j] == null && tbg.cb.board[i][j] != null))
                    check = false;
            }
        }
        if (check) {
            resultWindow.createWindow("You win!");
            declaredResult = 1;
        }
    }

    public void initMainFrame() {
        // Get the default size of our windows
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int boardSize = 0;
        int width = dim.width;
        int height = dim.height;
        if (width >= height) {
            boardSize = height / 2;
        } else {
            boardSize = width / 2;
        }

        // init window
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Amazons");

        addWindowListener();

        getContentPane().add(squaresPanel, BorderLayout.CENTER);
        setVisible(false);
        preferredSize.width = boardSize;
        preferredSize.height = boardSize;
        setPreferredSize(preferredSize);
        setResizable(false);
        createSquares();
        createGlass();
        for (int i = 0; i < 4; i++) {
            placePiece(cb.coordBlack[0][i], cb.coordBlack[1][i], 0);
            placePiece(cb.coordWhite[0][i], cb.coordWhite[1][i], 1);
        }
        setBounds(boardSize / 4, boardSize / 4, boardSize, boardSize);
        centreWindow(this);
        pack();
    }

    private void addWindowListener() {
        // window action listener
        JFrame currentMainframe = this;
        controlPanel = new ControlPanel();
        controlPanel.setVisible(false);
        WindowAdapter wa1 = new WindowAdapter() {
            @Override
            public void windowStateChanged(WindowEvent e) {
                controlPanel.setState(e.getNewState());
            }
        };
        WindowAdapter wa2 = new WindowAdapter() {
            @Override
            public void windowStateChanged(WindowEvent e) {
                tbg.setState(e.getNewState());
            }
        };
        WindowAdapter wa3 = new WindowAdapter() {
            @Override
            public void windowStateChanged(WindowEvent e) {
                controlPanelForPuzzle.setState(e.getNewState());
            }
        };
        WindowAdapter wa4 = new WindowAdapter() {
            @Override
            public void windowStateChanged(WindowEvent e) {
                currentMainframe.setState(e.getNewState());
            }
        };
        this.addWindowStateListener(wa1);
        this.addWindowStateListener(wa2);
        this.addWindowStateListener(wa3);
        controlPanel.addWindowStateListener(wa4);
        controlPanelForPuzzle.addWindowStateListener(wa4);
        controlPanelForPuzzle.addWindowStateListener(wa2);
        tbg.addWindowStateListener(wa4);
        tbg.addWindowStateListener(wa3);
        

        // window movement listener
        ComponentAdapter ca1 = new ComponentAdapter() {
            @Override
            public void componentMoved(ComponentEvent e) {
                controlPanel.setLocationRelativeTo(currentMainframe);
                Point p = currentMainframe.getLocation();
                controlPanel.setLocation(new Point(p.x, p.y + currentMainframe.getHeight()));
            }
        };
        ComponentAdapter ca2 = new ComponentAdapter() {
            @Override
            public void componentMoved(ComponentEvent e) {
                tbg.setLocationRelativeTo(currentMainframe);
                currentMainframe.setLocationRelativeTo(tbg);
                Point p = currentMainframe.getLocation();
                tbg.setLocation(new Point(p.x - currentMainframe.getWidth(), p.y));
            }
        };
        ComponentAdapter ca3 = new ComponentAdapter() {
            @Override
            public void componentMoved(ComponentEvent e) {
                controlPanelForPuzzle.setLocationRelativeTo(currentMainframe);
                Point p = currentMainframe.getLocation();
                controlPanelForPuzzle
                        .setLocation(new Point(p.x - currentMainframe.getWidth(), p.y + currentMainframe.getHeight()));
            }
        };
        // ComponentAdapter ca4 = new ComponentAdapter() {
        //     @Override
        //     public void componentMoved(ComponentEvent e) {
        //         controlPanelForPuzzle.setLocationRelativeTo(tbg);
        //         Point p = currentMainframe.getLocation();
        //         controlPanelForPuzzle
        //                 .setLocation(new Point(p.x - currentMainframe.getWidth(), p.y + currentMainframe.getHeight()));
        //     }
        // };
        // ComponentAdapter ca5 = new ComponentAdapter() {
        //     @Override
        //     public void componentMoved(ComponentEvent e) {
        //         currentMainframe.setLocationRelativeTo(tbg);
        //         Point p = tbg.getLocation();
        //         currentMainframe.setLocation(new Point(p.x + tbg.getWidth(), p.y));
        //     }
        // };
        this.addComponentListener(ca1);
        this.addComponentListener(ca2);
        this.addComponentListener(ca3);
    }

    public void showChessBoard() {
        if (playMode == 2) {
            runAI();
        }
        setVisible(true);
    }

    public void closeChessBoard() {
        resetBoard();
        setVisible(false);
    }

    public static void centreWindow(Window frame) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
    }

    @Override
    protected void createSquares() {
        squaresPanel.setLayout(new GridLayout(NUM_OF_ROWS, NUM_OF_COLS));
        for (int i = 0; i < NUM_OF_ROWS; i++) {
            for (int j = 0; j < NUM_OF_COLS; j++) {
                final int a = i, b = j;
                JPanel square = new JPanel();
                square.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(2)));
                square.setForeground(new Color(0, 0, 0));
                square.setBackground(new Color(200, 200, 200));
                square.addMouseListener(adaptor);
                squaresPanel.add(square);
                this.pack();
                // We don't know why the width changes here.
                SQUARE_COL = square.getSize().height;
            }
        }
    }

    private void runAI() {
        int colorForTurn = 0;
        Move move = rai.nextMove(cb);
        cb.moveStep(move);
        if (cb.colorForTurn() == 0)
            colorForTurn = 1;
        else
            colorForTurn = 0;
        removePiece(move.src_x, move.src_y);
        placePiece(move.tar_x, move.tar_y, colorForTurn);
        placePiece(move.obs_x, move.obs_y, 2);
    }

    private void undo() {
        if (cb.history.size() == 0)
            return;
        Move move = cb.undo();
        removePiece(move.tar_x, move.tar_y);
        removePiece(move.obs_x, move.obs_y);
        placePiece(move.src_x, move.src_y, cb.colorForTurn());
    }

    public class StartingScreen extends JFrame implements ActionListener {
        public void createWindow() {
            StartingScreen ss = new StartingScreen();
            ss.init();
        }

        private void init() {
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            setTitle("Starting Screen");
            setPreferredSize(new Dimension(200, 400));
            this.setSize(200, 400);
            centreWindow(this);
            // setUndecorated(true);
            setLayout(new GridLayout(5, 1));
            setVisible(true);
            int x = getContentPane().getLocation().x;
            int y = getContentPane().getLocation().y;

            JButton button1 = new JButton("Start PvP locally");
            button1.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(2)));
            button1.setActionCommand("1");
            button1.addActionListener(this);

            JButton button2 = new JButton("Start PvC as black");
            button2.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(2)));
            button2.setActionCommand("2");
            button2.addActionListener(this);

            JButton button3 = new JButton("Start PvC as white");
            button3.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(2)));
            button3.setActionCommand("3");
            button3.addActionListener(this);

            JButton button4 = new JButton("Let AI play with itself");
            button4.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(2)));
            button4.setActionCommand("4");
            button4.addActionListener(this);

            JButton button5 = new JButton("Play a puzzle solving game");
            button5.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(2)));
            button5.setActionCommand("5");
            button5.addActionListener(this);

            getContentPane().add(button1);
            getContentPane().add(button2);
            getContentPane().add(button3);
            getContentPane().add(button4);
            getContentPane().add(button5);
            button1.repaint();
            button2.repaint();
            button3.repaint();
            button4.repaint();
            button5.repaint();
            pack();
        }

        public void actionPerformed(ActionEvent event) {
            String cmd = event.getActionCommand();
            switch (cmd) {
            case "1":
                playMode = 0;
                centreWindow(highestLevelReferenceToThis);
                showChessBoard();
                controlPanel.createWindow();
                controlPanel.setVisible(true);
                dispose();
                break;
            case "2":
                playMode = 1;
                centreWindow(highestLevelReferenceToThis);
                showChessBoard();
                controlPanel.createWindow();
                controlPanel.setVisible(true);
                dispose();
                break;
            case "3":
                playMode = 2;
                centreWindow(highestLevelReferenceToThis);
                showChessBoard();
                controlPanel.createWindow();
                controlPanel.setVisible(true);
                dispose();
                break;
            case "4":
                playMode = 3;
                centreWindow(highestLevelReferenceToThis);
                showChessBoard();
                controlPanel.createWindow();
                controlPanel.setVisible(true);
                dispose();
                break;
            case "5":
                playMode = 4;
                new Levels().createWindow();
                dispose();
                break;
            default:
                break;
            }
        }
    }

    public class Levels extends JFrame implements ActionListener {
        public Levels createWindow() {
            this.init();
            return this;
        }

        private void init() {
            setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
            setTitle("Choose level");
            setPreferredSize(new Dimension(320, 400));
            this.setSize(320, 400);
            centreWindow(this);
            setVisible(true);
            setLayout(new GridLayout(5, 4));
            JButton[] buttons = new JButton[20];
            for (int i = 0; i < 20; i++) {
                buttons[i] = new JButton(Integer.toString(i + 1));
                buttons[i].setBorder(BorderFactory.createStrokeBorder(new BasicStroke(2)));
                buttons[i].setActionCommand(Integer.toString(i + 4));
                buttons[i].addActionListener(this);
                getContentPane().add(buttons[i]);
                buttons[i].repaint();
            }
            pack();
        }

        public void actionPerformed(ActionEvent event) {
            String cmd = event.getActionCommand();
            level = Integer.parseInt(cmd);
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            highestLevelReferenceToThis.setLocation(dim.width / 2, highestLevelReferenceToThis.getLocation().y);
            showChessBoard();
            tbg = new TargetBoardGUI();
            tbg.initMainFrame();
            tbg.initTargetBoard(highestLevelReferenceToThis, level);
            controlPanelForPuzzle.createWindow(tbg);
            dispose();
        }
    }

    public class ControlPanel extends JFrame implements ActionListener {
        private boolean isCreated = false;

        public ControlPanel createWindow() {
            if (isCreated) {
                return this;
            } else {
                this.init();
                isCreated = true;
                return this;
            }
        }

        private void close() {
            this.setVisible(false);
        }

        private void init() {
            setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            setTitle("Command Center");
            setPreferredSize(new Dimension(preferredSize.width, 70));
            setUndecorated(true);
            setLayout(new GridLayout(1, 3));
            setVisible(true);

            JButton button1 = new JButton("Reset Game");
            button1.setActionCommand("RESET");
            button1.addActionListener(this);

            JButton button2 = new JButton("Back to Menu");
            button2.setActionCommand("BACK");
            button2.addActionListener(this);

            JButton button3 = new JButton("Undo");
            button3.setActionCommand("UNDO");
            button3.addActionListener(this);

            getContentPane().add(button3);
            getContentPane().add(button1);
            getContentPane().add(button2);

            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            setBounds((dim.width - preferredSize.width) / 2, (dim.height + preferredSize.height) / 2,
                    preferredSize.width, 400);
            pack();
        }

        public void actionPerformed(ActionEvent event) {
            String cmd = event.getActionCommand();
            switch (cmd) {
            case "RESET":
                resetBoard();
                declaredResult = 0;
                if (resultWindow != null) {
                    resultWindow.closeWindow();
                }
                break;
            case "BACK":
                new StartingScreen().createWindow();
                closeChessBoard();
                dispose();
                break;
            case "UNDO":
                undo();
                if (playMode == 1 || playMode == 2) {
                    undo();
                }
                if (playMode == 2 && cb.history.size() == 0) {
                    runAI();
                }
                break;
            default:
                break;
            }
        }
    }

    public class ControlPanelForPuzzle extends JFrame implements ActionListener {
        private boolean isCreated = false;
        private TargetBoardGUI targetGUI;

        public ControlPanelForPuzzle createWindow(TargetBoardGUI targetGUI) {
            this.targetGUI = targetGUI;
            if (isCreated) {
                return this;
            } else {
                this.init();
                isCreated = true;
                return this;
            }
        }

        private void init() {
            setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            setTitle("Command Center");
            setPreferredSize(new Dimension(highestLevelReferenceToThis.getWidth() * 2, 70));
            setLocation(highestLevelReferenceToThis.getLocation().x - highestLevelReferenceToThis.getWidth(),
                    highestLevelReferenceToThis.getLocation().y + highestLevelReferenceToThis.getHeight());
            // setSize(motherGUI.getWidth(),70);
            setUndecorated(true);
            setLayout(new GridLayout(1, 6));
            setVisible(true);

            JButton button1 = new JButton("Undo");
            button1.setActionCommand("UNDO");
            button1.addActionListener(this);

            JButton button2 = new JButton("Reset");
            button2.setActionCommand("RESET");
            button2.addActionListener(this);

            JButton button3 = new JButton("Next Level");
            button3.setActionCommand("NEXT");
            button3.addActionListener(this);

            JButton button4 = new JButton("Last Level");
            button4.setActionCommand("LAST");
            button4.addActionListener(this);

            JButton button5 = new JButton("Choose Level");
            button5.setActionCommand("CHOOSE");
            button5.addActionListener(this);

            JButton button6 = new JButton("Back to Menu");
            button6.setActionCommand("BACK");
            button6.addActionListener(this);

            getContentPane().add(button6);
            getContentPane().add(button5);
            getContentPane().add(button4);
            getContentPane().add(button3);
            getContentPane().add(button1);
            getContentPane().add(button2);

            pack();
        }

        public void actionPerformed(ActionEvent event) {
            String cmd = event.getActionCommand();
            switch (cmd) {
            case "BACK":
                closeChessBoard();
                targetGUI.closeChessBoard();
                new StartingScreen().createWindow();
                declaredResult = 0;
                if (resultWindow != null) {
                    resultWindow.closeWindow();
                }
                dispose();
                break;
            case "CHOOSE":
                closeChessBoard();
                targetGUI.closeChessBoard();
                new Levels().createWindow();
                declaredResult = 0;
                if (resultWindow != null) {
                    resultWindow.closeWindow();
                }
                dispose();
                break;
            case "LAST":
                if (level > 4) {
                    level--;
                    resetBoard();
                    tbg.clearBoard();
                    tbg.initTargetBoard(highestLevelReferenceToThis, level);
                    declaredResult = 0;
                    if (resultWindow != null) {
                        resultWindow.closeWindow();
                    }
                }
                break;
            case "NEXT":
                if (level < 23) {
                    level++;
                    resetBoard();
                    tbg.clearBoard();
                    tbg.initTargetBoard(highestLevelReferenceToThis, level);
                    declaredResult = 0;
                    if (resultWindow != null) {
                        resultWindow.closeWindow();
                    }
                }
                break;
            case "RESET":
                resetBoard();
                declaredResult = 0;
                if (resultWindow != null) {
                    resultWindow.closeWindow();
                }
                break;
            case "UNDO":
                if (declaredResult == 0) {
                    undo();
                }
                break;
            default:
                break;
            }
        }
    }

    private static class ResultWindow extends JFrame {
        public void createWindow(String content) {
            this.init(content);
            centreWindow(this);
        }

        private void init(String content) {
            setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
            setTitle("Result");
            setVisible(true);
            setPreferredSize(new Dimension(200, 100));
            // setResizable(false);
            resultLabel = new JLabel(content, SwingConstants.CENTER);
            getContentPane().add(resultLabel);
            pack();
        }

        public void closeWindow() {
            dispose();
        }
    }
}