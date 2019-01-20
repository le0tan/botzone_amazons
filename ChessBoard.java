/**
 * ChessBoard
 */
public class ChessBoard {

    ChessPiece[][] board = new ChessBoard[8][8];
    int turn;

    // Constructor

    ChessBoard() {
        turn = 0;
        // 0,2; 2,0; 5,0; 7,2
        int[][] coordBlack = {{0,2,5,7},{2,0,0,2}};
        for(int i=0;i<4;i++) {
            for(int j=0;j<4;j++) {
                this.board[i][j] = new ChessPiece(i,j,0,false);
            }
        }

        // 0,5; 2,7; 5,7; 7,5
        int[][] coordWhite = {{0,2,5,7},{5,7,7,5}};
        for(int i=0;i<4;i++) {
            for(int j=0;j<4;j++) {
                this.board[i][j] = new ChessPiece(i,j,1,false);
            }
        }
    }

    // Methods

    public boolean putPiece(ChessPiece p) {
        board[p.x][p.y] = p;
        // TODO:
    }

    public void printBoard() {
        // TODO: Print out the board
    }
}