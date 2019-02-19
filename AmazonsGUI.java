import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.StrokeBorder;

import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import java.util.List;

/**
 * AmazonsGUI
 */
public class AmazonsGUI extends JFrame {

    private static JPanel squaresPanel = new JPanel();
    // private static JPanel glass=new JPanel();
    JPanel glass = (JPanel) getGlassPane();
    private static Dimension preferredSize = new Dimension();
    private static int NUM_OF_ROWS = 8;
    private static int NUM_OF_COLS = 8;
    private static int SQUARE_ROW;
    private static int SQUARE_COL;
    private static ChessBoard cb = new ChessBoard();
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

    public static void main(String[] args) {
        AmazonsGUI ag = new AmazonsGUI();
        ag.initComponents();
    }

    private void initComponents() {
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
        setTitle("Swing Chess Board");

        getContentPane().add(squaresPanel, BorderLayout.CENTER);
        setVisible(true);
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
        // removePiece(cb.coordBlack[0][0], cb.coordBlack[1][0]);
        // placePieces();
        // placePiece(2,3);
        // I don't understand
        setBounds(boardSize / 4, boardSize / 4, boardSize, boardSize);
        pack();
    }

    private void createSquares() {
        squaresPanel.setLayout(new GridLayout(NUM_OF_ROWS, NUM_OF_COLS));
        for (int i = 0; i < NUM_OF_ROWS; i++) {
            for (int j = 0; j < NUM_OF_COLS; j++) {
                final int a = i, b = j;
                JPanel square = new JPanel();
                square.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(2)));
                square.setForeground(new Color(0, 0, 0));
                square.setBackground(new Color(200, 200, 200));
                // square.add(new JLabel(String.format("(%d, %d)", i,j)));
                // square.add(new DrawChessPiece());
                // squaresPanel.add(new DrawChessPiece());
                square.addMouseListener(adaptor);
                squaresPanel.add(square);
                this.pack();
                SQUARE_ROW = square.getSize().width;
                // We don't know why the width changes here.
                SQUARE_COL = square.getSize().height;
            }
        }
    }

    private void createGlass() {
        // setGlassPane(glass);
        glass.setLayout(new GridLayout(NUM_OF_ROWS, NUM_OF_COLS));
        glass.setVisible(true);
        for (int i = 0; i < NUM_OF_ROWS; i++) {
            for (int j = 0; j < NUM_OF_COLS; j++) {
                JPanel glasses = new JPanel();
                glasses.setVisible(false);
                glass.add(glasses);
            }
        }
    }

    private void placePiece(int x, int y, int c) {
        glass.remove(x * 8 + y);
        glass.add(new DrawChessPiece(c), x * 8 + y);
        glass.revalidate();
    }

    private void removePiece(int x, int y) {
        JPanel glasses = new JPanel();
        glasses.setVisible(false);
        glass.remove(x * 8 + y);
        glass.add(glasses, x * 8 + y);
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

    private void displayFreedom(int x, int y) {
        // change the color on the base pane (the chessboard) to display available
        // positions for chesspiece on (x, y)
        // basically you can use squaresPanel
        // ((JPanel)squaresPanel.getComponent(0)).setBackground(new Color(0, 0, 1));
        List<Pair> positions = cb.board[x][y].possiblePositions(cb);
        int c = 100;
        if (cb.pressed[x][y])
            c = 100;
        else
            c = 200;
        for (int i = 0; i < cb.board[x][y].freedom(cb); i++) {
            ((JPanel) squaresPanel.getComponent(positions.get(i).x * 8 + positions.get(i).y))
                    .setBackground(new Color(c, c, c));
        }
    }

    private void reactToMouseClick(int x, int y) {
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

        if(cb.choosingTarget() &&cb.hasPiece(x, y)&& cb.board[x][y].x == cb.chessToMove.x && cb.board[x][y].y == cb.chessToMove.y) {
            cb.pressed[x][y] = false;
            displayFreedom(x, y);
            cb.chessToMove=null;
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
        }else if (cb.hasPiece(x, y) && (cb.board[x][y].color == cb.colorForTurn())) {
            cb.pressed[x][y] = true;
            displayFreedom(x, y);
            cb.setChessToMove();
        }
        
    }
}