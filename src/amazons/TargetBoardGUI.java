package amazons;


// Simplify the imports below when necessary
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * TargetBoardGUI
 */
public class TargetBoardGUI extends BasicAmazonsGUI {
    private ControlPanel controlPanel;
    
    public void initTargetBoard(ChessBoard targetBoard, AmazonsGUI motherGUI) {
        setTitle("Target Board");
        new ControlPanel().createWindow(motherGUI);
        // add pieces here!!!!!!!!!!!
        for(int i=0;i<8;i++) {
            for(int j=0;j<8;j++) {
                if(targetBoard.board[i][j]!=null) {
                    placePiece(i, j, targetBoard.board[i][j].color);
                }
            }
        }
    }
    
    public class ControlPanel extends JFrame implements ActionListener {
        private boolean isCreated = false;
        private AmazonsGUI motherGUI;

        public ControlPanel createWindow(AmazonsGUI motherGUI) {
            this.motherGUI = motherGUI;
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

            JButton button2 = new JButton("Back to Menu");
            button2.setActionCommand("BACK");
            button2.addActionListener(this);

            getContentPane().add(button2);

            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            setBounds((dim.width - preferredSize.width) / 2, (dim.height + preferredSize.height) / 2,
                    preferredSize.width, 400);
            pack();
        }

        public void actionPerformed(ActionEvent event) {
            String cmd = event.getActionCommand();
            switch (cmd) {
            case "BACK":
                motherGUI.new StartingScreen().createWindow();
                closeChessBoard();
                dispose();
                break;
            default:
                break;
            }
        }
    }
}