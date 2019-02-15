import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * A simple chess board using Swing
 *
 * @author Ashik Uzzaman
 * @link https://ashikuzzaman.wordpress.com
 */
public class SwingBoard extends JFrame {

    public static final int NUMBER_OF_ROWS = 8;
    public static final int NUMBER_OF_FILES = 8;
    public static JLabel square;
    public static JPanel squarePanel = new JPanel();    // This is just a pointer variable that is used temporarily
    public static JPanel squaresPanel = new JPanel();   // This is where the squares are stored
    public static Color darkSquareColor = new Color(178, 151, 89);
    public static Color lightSquareColor = new Color(255, 255, 255);

    private static void createSquares() {
        LayoutManager layout = new GridLayout(NUMBER_OF_ROWS, NUMBER_OF_FILES);
        squaresPanel.setLayout(layout);
        for (int i = 0; i < NUMBER_OF_ROWS; i++) {
            for (int j = 0; j < NUMBER_OF_FILES; j++) {
                squarePanel = new JPanel();
                square = new JLabel();
                final int a = i; final int b = j;
                squarePanel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent me) {
                        JPanel l = (JPanel) me.getSource();
                        l.setBackground(new Color(233,233,233));
                        System.out.printf("Mouse over tile (%d, %d)\n", a, b);
                    }
                    @Override
                    public void mouseExited(MouseEvent me) {
                        JPanel l = (JPanel) me.getSource();
                        l.setBackground(getColor(a, b));
                        // System.out.printf("Mouse over tile (%d, %d)\n", a, b);
                    }
                    @Override
                    public void mousePressed(MouseEvent me) {
                        JPanel l = (JPanel) me.getSource();
                        l.setBackground(new Color(0,0,255));
                    }
                    @Override
                    public void mouseReleased(MouseEvent me) {
                        JPanel l = (JPanel) me.getSource();
                        l.setBackground(getColor(a, b));
                    }
                });
                squarePanel.add(square);
                squarePanel.setBackground(getColor(i, j));
                square.setForeground(new Color(0, 0, 250));
                squarePanel.setToolTipText("Row " + i + " and Column " + j);
                squaresPanel.add(squarePanel);
            }
        }
    }

    private static Color getColor(int x, int y) {
        if ((x + y) % 2 == 0)
            return darkSquareColor;
        else
            return lightSquareColor;
    }

    public SwingBoard() {
        initComponents();
    }

    private void initComponents() {

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int boardSize = 0;
        int width = dim.width;
        int height = dim.height;
        if (width >= height) {
            boardSize = height / 2;
        } else {
            boardSize = width / 2;
        }
        createSquares();
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Swing Chess Board");
        // setIconImage(new ImageIcon("board.jpg").getImage());
        getContentPane().add(squaresPanel, BorderLayout.CENTER);
        setVisible(true);
        Dimension preferredSize = new Dimension();
        preferredSize.width = boardSize;
        preferredSize.height = boardSize;
        setPreferredSize(preferredSize);
        setBounds(boardSize / 4, boardSize / 4, boardSize, boardSize);
        pack();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SwingBoard();
            }
        });
    }
}