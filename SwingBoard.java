import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
// import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;
import javax.swing.border.LineBorder;

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
    private static Dimension preferredSize = new Dimension();

    private void createSquares() {
        final JPanel board = new JPanel();
        getContentPane().add(board);
        board.setLayout(new GridLayout(8, 8));

        final JPanel glass = (JPanel) getGlassPane();
        glass.setVisible(false);
        glass.setLayout(new GridLayout(8, 8));
        int count = 0;
        for(int i=0;i<8;i++) {
            for(int j=0;j<8;j++) {
                JPanel jp = new JPanel();
                jp.setBackground(new Color(count*4,count*4,count*4));
                count++;
                glass.add(jp);
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
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Swing Chess Board");
        // setIconImage(new ImageIcon("board.jpg").getImage());
        getContentPane().add(squaresPanel, BorderLayout.CENTER);
        setVisible(true);
        preferredSize.width = boardSize;
        preferredSize.height = boardSize;
        // System.out.printf("height=%d\n",preferredSize.height);
        setPreferredSize(preferredSize);
        createSquares();
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