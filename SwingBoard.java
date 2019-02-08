import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.Toolkit;
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
    public static JPanel squarePanel = new JPanel();
    public static JPanel squaresPanel = new JPanel();

    private static void createSquares() {
        LayoutManager layout = new GridLayout(NUMBER_OF_ROWS, NUMBER_OF_FILES);
        squaresPanel.setLayout(layout);
        for (int i = NUMBER_OF_ROWS; i > 0; i--) {
            for (int j = 1; j <= NUMBER_OF_FILES; j++) {
                squarePanel = new JPanel();
                square = new JLabel("r" + i + ":c" + j);
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
            return new Color(255, 255, 255);
        else
            return new Color(0, 0, 0);
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