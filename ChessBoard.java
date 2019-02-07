/**
 * ChessBoard
 */
public class ChessBoard {

    ChessPiece[][] board = new ChessPiece[8][8];
    int turn;

    // Constructor

    ChessBoard() {
        turn = 0;
        // 0,2; 2,0; 5,0; 7,2
        int[][] coordBlack = { { 0, 2, 5, 7 }, { 2, 0, 0, 2 } };
        for (int i = 0; i < 4; i++) {
            this.board[coordBlack[0][i]][coordBlack[1][i]] = new ChessPiece(coordBlack[0][i], coordBlack[1][i], 1,
                    false);
        }

        // 0,5; 2,7; 5,7; 7,5
        int[][] coordWhite = { { 0, 2, 5, 7 }, { 5, 7, 7, 5 } };
        for (int i = 0; i < 4; i++) {
            this.board[coordWhite[0][i]][coordWhite[1][i]] = new ChessPiece(coordWhite[0][i], coordWhite[1][i], 0,
                    false);
        }
    }

    // Methods

    public boolean putPiece(ChessPiece p) {
        board[p.x][p.y] = p;
        // TODO:
        return true;
    }

    public void printBoard() {
        // TODO: Print out the board
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] instanceof ChessPiece) {
                    if (board[i][j].color == 0)
                        System.out.print("-");
                    else
                        System.out.print("+");
                } else {
                    System.out.print("o");
                }
                if (j < 7)
                    System.out.print(" ");
            }
            System.out.println();
        }
    }
}
