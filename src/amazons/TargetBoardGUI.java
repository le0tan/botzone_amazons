package amazons;

// Simplify the imports below when necessary
import javax.swing.*;
import java.awt.*;

/**
 * TargetBoardGUI
 */
public class TargetBoardGUI extends BasicAmazonsGUI {
    private AmazonsAI randomAI = new RandomAI();

    public void initTargetBoard(AmazonsGUI motherGUI,int level) {
        String s;
        s="Level "+Integer.toString(level-3);
        setTitle(s);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setLocation(motherGUI.getLocation().x - preferredSize.width, motherGUI.getLocation().y);
        // add pieces here
        for(int i=0;i<level;i++) {
            cb.moveStep(randomAI.nextMove(cb));
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (cb.board[i][j] != null) {
                    placePiece(i, j, cb.board[i][j].color);
                }
            }
        }
    }

    public void clearBoard() {
        cb = new ChessBoard();
        for (int i = 0; i < NUM_OF_ROWS; i++) {
            for (int j = 0; j < NUM_OF_COLS; j++) {
                removePiece(i, j);
            }
        }
    }
}