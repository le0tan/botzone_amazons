import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
/**
 * AmazonsGUI
 */
public class AmazonsGUI extends JFrame{

    private static JPanel squaresPanel = new JPanel();
    private static Dimension preferredSize = new Dimension();
    private static int NUM_OF_ROWS = 8;
    private static int NUM_OF_COLS = 8;
    private static MouseAdapter adaptor = new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent me) {
            JPanel temp = (JPanel) me.getSource();
            temp.setBackground(new Color(150,150,150));
        }
        @Override
        public void mouseExited(MouseEvent me) {
            JPanel temp = (JPanel) me.getSource();
            temp.setBackground(new Color(200,200,200));
        }
        @Override
        public void mousePressed(MouseEvent me) {
            JPanel temp = (JPanel) me.getSource();
            temp.setBackground(new Color(0,0,200));
        }
        @Override
        public void mouseReleased(MouseEvent me) {
            JPanel temp = (JPanel) me.getSource();
            temp.setBackground(new Color(200,200,200));
        }
    };

    public static void main(String[] args) {
        AmazonsGUI ag = new AmazonsGUI();
        ag.initComponents();
    }

    private void createSquares() {
        squaresPanel.setLayout(new GridLayout(NUM_OF_ROWS, NUM_OF_COLS));
        for(int i=0;i<NUM_OF_ROWS;i++) {
            for(int j=0;j<NUM_OF_COLS;j++) {
                final int a=i, b=j;
                JPanel square = new JPanel();
                square.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(2)));
                square.setForeground(new Color(0, 0, 0));
                square.setBackground(new Color(200, 200, 200));
                // square.add(new JLabel(String.format("(%d, %d)", i,j)));
                // square.add(new DrawChessPiece());
                // squaresPanel.add(new DrawChessPiece());
                squaresPanel.add(square);
            }
        }
    }

    private void placePieces() {
        JPanel glass = (JPanel) getGlassPane();
        glass.setLayout(new GridLayout(NUM_OF_ROWS, NUM_OF_COLS));
        glass.setVisible(true);
        for(int i=0;i<NUM_OF_ROWS;i++) {
            for(int j=0;j<NUM_OF_COLS;j++) {
                glass.add(new DrawChessPiece());
            }
        }
    }

    private void displayFreedom(int x, int y) {
        // change the color on the base pane (the chessboard) to display available positions for chesspiece on (x, y)
        // basically you can use squaresPanel
        // ((JPanel)squaresPanel.getComponent(0)).setBackground(new Color(0, 0, 1));
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
        createSquares();
        placePieces();
        // I don't understand
        setBounds(boardSize / 4, boardSize / 4, boardSize, boardSize);
        pack();
    }

    private class DrawChessPiece extends JComponent{

        @Override
        protected void paintComponent(Graphics g){
            super.paintComponent(g);
            Graphics2D graph2 = (Graphics2D) g;
            graph2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            graph2.setColor(Color.BLUE);
            Shape circle = new Ellipse2D.Double(30, 30, 10, 10);
            graph2.draw(circle);
            // circle.setColor(Color.blue);
        }
    }

}