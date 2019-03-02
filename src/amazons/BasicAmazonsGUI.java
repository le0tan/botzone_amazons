package amazons;

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
public class BasicAmazonsGUI extends JFrame {

    protected JPanel squaresPanel = new JPanel();
    // protected static JPanel glass=new JPanel();
    protected JPanel glass = (JPanel) getGlassPane();
    protected static Dimension preferredSize = new Dimension();
    protected static int NUM_OF_ROWS = 8;
    protected static int NUM_OF_COLS = 8;
    // protected static int SQUARE_ROW;
    protected static int SQUARE_COL;
    protected ChessBoard cb = new ChessBoard();

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

        getContentPane().add(squaresPanel, BorderLayout.CENTER);
        setVisible(true);
        preferredSize.width = boardSize;
        preferredSize.height = boardSize;
        setPreferredSize(preferredSize);
        setResizable(false);
        createSquares();
        createGlass();

        // // add pieces here!!!!!!!!!!!
        // for (int i = 0; i < 4; i++) {
        //     placePiece(cb.coordBlack[0][i], cb.coordBlack[1][i], 0);
        //     placePiece(cb.coordWhite[0][i], cb.coordWhite[1][i], 1);
        // }

        setBounds(boardSize / 4, boardSize / 4, boardSize, boardSize);
        centreWindow(this);
        pack();
    }

    public void initTargetBoard(ChessBoard targetBoard) {
        // add pieces here!!!!!!!!!!!
        for(int i=0;i<8;i++) {
            for(int j=0;j<8;j++) {
                if(targetBoard.board[i][j]!=null) {
                    placePiece(i, j, targetBoard.board[i][j].color);
                }
            }
        }
    }

    public void showChessBoard() {
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

    protected void resetBoard() {
        cb = new ChessBoard();
        for (int i = 0; i < NUM_OF_ROWS; i++) {
            for (int j = 0; j < NUM_OF_COLS; j++) {
                removePiece(i, j);
            }
        }
        for (int i = 0; i < 4; i++) {
            placePiece(cb.coordBlack[0][i], cb.coordBlack[1][i], 0);
            placePiece(cb.coordWhite[0][i], cb.coordWhite[1][i], 1);
        }
    }

    protected void createSquares() {
        squaresPanel.setLayout(new GridLayout(NUM_OF_ROWS, NUM_OF_COLS));
        for (int i = 0; i < NUM_OF_ROWS; i++) {
            for (int j = 0; j < NUM_OF_COLS; j++) {
                final int a = i, b = j;
                JPanel square = new JPanel();
                square.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(2)));
                square.setForeground(new Color(0, 0, 0));
                square.setBackground(new Color(200, 200, 200));
                squaresPanel.add(square);
                this.pack();
                // We don't know why the width changes here.
                SQUARE_COL = square.getSize().height;
            }
        }
    }

    protected void createGlass() {
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

    protected void placePiece(int x, int y, int c) {
        glass.remove(x * 8 + y);
        glass.add(new DrawChessPiece(c), x * 8 + y);
        glass.revalidate();
        glass.repaint();
    }

    protected void removePiece(int x, int y) {
        JPanel glasses = new JPanel();
        glasses.setVisible(false);
        glass.remove(x * 8 + y);
        glass.add(glasses, x * 8 + y);
        glass.revalidate();
        glass.repaint();
    }

    protected class DrawChessPiece extends JComponent {
        protected int color;

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

    protected void displayFreedom(int x, int y) {
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
}