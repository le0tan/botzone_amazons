/**
 * TargetBoardGUI
 */
public class TargetBoardGUI extends BasicAmazonsGUI{
    public void initTargetBoard(ChessBoard targetBoard) {
        setTitle("Target Board");
        // add pieces here!!!!!!!!!!!
        for(int i=0;i<8;i++) {
            for(int j=0;j<8;j++) {
                if(targetBoard.board[i][j]!=null) {
                    placePiece(i, j, targetBoard.board[i][j].color);
                }
            }
        }
    }
}