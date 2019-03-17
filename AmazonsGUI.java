import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
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
import java.awt.event.WindowStateListener;
import java.awt.geom.Ellipse2D;

import java.util.List;

/**
 * AmazonsGUI
 */
public class AmazonsGUI {

    private static int SQUARE_COL;
    private static Dimension preferredSize = new Dimension();

    private static int playMode = 0;
    private static int declaredResult = 0;
    private static int initialized = 0;
    private static int level = 0;

    private static JLabel resultLabel;
    private static ResultWindow resultWindow;
    public static MainBoard mainBoard;
    private static ControlPanel controlPanel;
    private static Levels levels;
    private static TargetBoard targetBoard;

    public static void centreWindow(Window frame) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
    }

    // Class of windows
    public abstract class ChessBoardGUI extends JFrame {
        protected ChessBoard cb = new ChessBoard();
        RandomAI rai = new RandomAI();
        
        protected JPanel glass = (JPanel) getGlassPane();

        public abstract void init();

        public void createSquares(JPanel panel, MouseAdapter adaptor) {
            panel.setLayout(new GridLayout(8, 8));
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    JPanel square = new JPanel();
                    square.setVisible(true);
                    square.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(2)));
                    square.setForeground(new Color(0, 0, 0));
                    square.setBackground(new Color(200, 200, 200));
                    if(adaptor!=null)square.addMouseListener(adaptor);
                    panel.add(square);
                    SQUARE_COL = square.getSize().height;
                }
            }
            pack();
        }

        protected void createGlass() {
            //this.setGlassPane(glass);
            glass.setLayout(new GridLayout(8, 8));
            glass.setVisible(true);
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    JPanel glasses = new JPanel();
                    glasses.setVisible(false);
                    glass.add(glasses);
                }
            }
            pack();
        }

        protected void placePiece(int x, int y, int c) {
            glass.remove(x * 8 + y);
            glass.add(new DrawChessPiece(c), x * 8 + y);
            glass.revalidate();
            glass.repaint();
        }

        private class DrawChessPiece extends JComponent {
            private int color;

            DrawChessPiece(int color) {
                this.color = color;
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D graph2 = (Graphics2D) g;
                graph2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                graph2.setColor(Color.BLUE);
                // System.out.printf("%f \n",SQUARE_COL);
                Shape circle = new Ellipse2D.Double(SQUARE_COL / 10 * 1.7, SQUARE_COL / 10 * 1.5, SQUARE_COL / 10 * 8,
                        SQUARE_COL / 10 * 8);
                Color c = Color.WHITE;
                if (this.color == 0)
                    c = Color.BLACK;
                else if (this.color == 1)
                    c = Color.WHITE;
                else if (this.color == 2)
                    c = Color.BLUE;
                graph2.setPaint(new GradientPaint(0, 0, c, 100, 0, c));
                graph2.fill(circle);
                // graph2.draw(circle);
            }
        }

    }

    public class MainBoard extends ChessBoardGUI {
        // Methods for creating the whole chessboard
        private JPanel squaresPanel=new JPanel();

        public MainBoard createWindow() {
            this.init();
            return this;
        }

        public void init() {
            //this.add(squaresPanel);
            //this.add(glass);
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

            // window action listener
            controlPanel = new ControlPanel();
            controlPanel.setVisible(false);
            WindowAdapter wa1 = new WindowAdapter() {
                @Override
                public void windowStateChanged(WindowEvent e) {
                    controlPanel.setState(e.getNewState());
                }
            };
            JFrame currentMainframe = this;
            WindowAdapter wa2 = new WindowAdapter() {
                @Override
                public void windowStateChanged(WindowEvent e) {
                    currentMainframe.setState(e.getNewState());
                }
            };
            this.addWindowStateListener(wa1);
            controlPanel.addWindowStateListener(wa2);

            // window movement listener
            ComponentAdapter ca = new ComponentAdapter() {
                @Override
                public void componentMoved(ComponentEvent e) {
                    controlPanel.setLocationRelativeTo(currentMainframe);
                    Point p = currentMainframe.getLocation();
                    controlPanel.setLocation(new Point(p.x, p.y + currentMainframe.getHeight()));
                }
            };
            this.addComponentListener(ca);

            // getContentPane().add(squaresPanel, BorderLayout.CENTER);
            setVisible(true);
            preferredSize.width = boardSize;
            preferredSize.height = boardSize;
            setPreferredSize(preferredSize);
            setResizable(false);
            createSquares(this.squaresPanel,adaptor);
            createGlass();
            this.add(squaresPanel);
            this.setGlassPane(glass);
            //System.out.printf("%d\n",this.getComponentCount());
            //this.add(glass);
            for (int i = 0; i < 4; i++) {
                placePiece(cb.coordBlack[0][i], cb.coordBlack[1][i], 0);
                placePiece(cb.coordWhite[0][i], cb.coordWhite[1][i], 1);
            }
            setBounds(boardSize / 4, boardSize / 4, boardSize, boardSize);
            centreWindow(this);
            pack();
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

        private void resetBoard() {
            cb = new ChessBoard();
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    removePiece(i, j);
                }
            }
            for (int i = 0; i < 4; i++) {
                placePiece(cb.coordBlack[0][i], cb.coordBlack[1][i], 0);
                placePiece(cb.coordWhite[0][i], cb.coordWhite[1][i], 1);
            }
        }

        // Methods for moving chesses
        //JFrame frame=this;
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
                        if (temp.equals(glass.getComponent(i))) {
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
                    if (temp.equals(glass.getComponent(i))) {
                        x = i / 8;
                        y = i % 8;
                        System.out.printf("%d %d\n",x,y);
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
            if(playMode==4) {
                
            }
            if (playMode == 3) {
                mainBoard.runAI();
                int result = cb.declareResult();
                resultWindow = new ResultWindow();
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
                mainBoard.displayFreedom(x, y);
                cb.chessToMove = null;
            }
            if (ct) {
                if (legal) {
                    for (int i = 0; i < 64; i++) {
                        mainBoard.getComponent(i).setBackground(new Color(200, 200, 200));
                    }
                    mainBoard.placePiece(x, y, cb.chessToMove.color);
                    mainBoard.removePiece(cb.chessToMove.x, cb.chessToMove.y);
                    cb.moveChess(x, y);
                    mainBoard.displayFreedom(x, y);
                }
            } else if (co) {
                if (legal) {
                    for (int i = 0; i < 64; i++) {
                        mainBoard.getComponent(i).setBackground(new Color(200, 200, 200));
                    }
                    mainBoard.placePiece(x, y, 2);
                    cb.putObstacle(x, y);
                }
            } else if (cb.hasPiece(x, y) && (cb.board[x][y].color == cb.colorForTurn())) {
                cb.pressed[x][y] = true;
                mainBoard.displayFreedom(x, y);
                cb.setChessToMove();
            }

            if (co && legal) {
                if (playMode == 1 || playMode == 2) {
                    mainBoard.runAI();
                }
            }

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

        private void removePiece(int x, int y) {
            JPanel glasses = new JPanel();
            glasses.setVisible(false);
            glass.remove(x * 8 + y);
            glass.add(glasses, x * 8 + y);
            glass.revalidate();
            glass.repaint();
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

        private void displayFreedom(int x, int y) {
            // change the color on the base pane (the chessboard) to display available
            // positions for chesspiece on (x, y)
            // basically you can use squaresPanel
            // ((JPanel)mainBoard.getComponent(0)).setBackground(new Color(0, 0, 1));
            List<Pair> positions = cb.board[x][y].possiblePositions(cb);
            int c = 100;
            if (cb.pressed[x][y])
                c = 100;
            else
                c = 200;
            for (int i = 0; i < cb.board[x][y].freedom(cb); i++) {
                ((JPanel) mainBoard.getComponent(positions.get(i).x * 8 + positions.get(i).y))
                        .setBackground(new Color(c, c, c));
            }
        }
    }

    private class TargetBoard extends ChessBoardGUI {
        private JPanel squaresPanel=new JPanel();

        public TargetBoard createWindow() {
            this.init();
            return this;
        }

        public void init() {
            //this.add(squaresPanel);
            setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
            setTitle("Target Board");
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            int boardSize = 0;
            int width = dim.width;
            int height = dim.height;
            if (width >= height) {
                boardSize = height / 2;
            } else {
                boardSize = width / 2;
            }
            preferredSize.height=boardSize;
            preferredSize.width=boardSize;
            setPreferredSize(preferredSize);
            setSize(boardSize, boardSize);
            setResizable(false);
            setLocation(mainBoard.getLocation().x - mainBoard.getSize().width / 2,
                    mainBoard.getLocation().y);
            // getContentPane().add(squaresPanel, BorderLayout.CENTER);
            setVisible(true);

            createSquares(this.squaresPanel,null);
            createGlass();
            drawChessBoard();
            pack();
        }

        private void drawChessBoard() {
            for (int i = 0; i < level; i++) {
                Move move = this.rai.nextMove(this.cb);
                this.cb.moveStep(move);
            }
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (this.cb.board[i][j] != null) {
                        placePiece(i, j, this.cb.board[i][j].color);
                    }
                }
            }
        }
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
            if (initialized == 0) {
                initialized = 1;
                mainBoard = new MainBoard();
                mainBoard.init();
            }
            String cmd = event.getActionCommand();
            switch (cmd) {
            case "1":
                mainBoard.showChessBoard();
                controlPanel.createWindow();
                controlPanel.setVisible(true);
                dispose();
                break;
            case "2":
                playMode = 1;
                mainBoard.showChessBoard();
                controlPanel.createWindow();
                controlPanel.setVisible(true);
                dispose();
                break;
            case "3":
                playMode = 2;
                mainBoard.showChessBoard();
                controlPanel.createWindow();
                controlPanel.setVisible(true);
                dispose();
                break;
            case "4":
                playMode = 3;
                mainBoard.showChessBoard();
                controlPanel.createWindow();
                controlPanel.setVisible(true);
                dispose();
                break;
            case "5":
                playMode = 4;
                levels = new Levels();
                levels.createWindow();
                dispose();
                break;
            default:
                break;
            }
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
                mainBoard.resetBoard();
                declaredResult = 0;
                if (resultWindow != null) {
                    resultWindow.closeWindow();
                }
                break;
            case "BACK":
                new StartingScreen().createWindow();
                mainBoard.closeChessBoard();
                dispose();
                break;
            case "UNDO":
                mainBoard.undo();
                if (playMode == 1 || playMode == 2) {
                    mainBoard.undo();
                }
                if (playMode == 2 && mainBoard.cb.history.size() == 0) {
                    mainBoard.runAI();
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
            mainBoard.showChessBoard();
            controlPanel.createWindow();
            mainBoard.setLocation(mainBoard.getLocation().x + mainBoard.getSize().width / 2,
                    mainBoard.getLocation().y);
            targetBoard=new TargetBoard();
            targetBoard.createWindow();
            dispose();
        }
    }
}