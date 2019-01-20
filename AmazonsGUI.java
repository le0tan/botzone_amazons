import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

/**
 * Amazons
 */

class Surface extends JPanel {
    private void doDrawing(Graphics g) {
        Graphics2D g2d  = (Graphics2D) g;
        g2d.drawString("Java 2D", 50, 50);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }
}

public class AmazonsGUI extends Frame {

    public AmazonsGUI() {
        initUI();
    }

    private void initUI() {

        add(new Surface());

        setTitle("Simple Java 2D example");
        setSize(300, 200);
        setLocationRelativeTo(null);
        // Don't know why the line below doesn't work, but made it work anyways...
        // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            // https://alvinalexander.com/blog/post/jfc-swing/closing-your-java-swing-application-when-user-presses-close-but
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                AmazonsGUI ex = new AmazonsGUI();
                ex.setVisible(true);
            }
        });
    }
}