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

    private boolean hasPiece(int x, int y) {
        if (x > 7 || y > 7 || x < 0 || y < 0) {
            return false;
        } else {
            return board[x][y] instanceof ChessPiece;
        }
    }

    // Returns true if the chesspiece is put successfully.
    public boolean putPiece(ChessPiece p) {
        if (hasPiece(p.x, p.y)) {
            // When the place (p.x, p.y) is already occupied with a piece
            return false;
        } else {
            board[p.x][p.y] = p;
            return true;
        }
    }

    private void removePiece(int x, int y) {
        if (hasPiece(x, y))
            board[x][y] = null;
    }

    public boolean movePiece(int src_x, int src_y, int tar_x, int tar_y) {
        if (!hasPiece(src_x, src_y) || hasPiece(tar_x, tar_y)) {
            return false;
        } else {
            ChessPiece temp = board[src_x][src_y];
            board[src_x][src_y] = null;
            board[tar_x][tar_y] = temp;
            return true;
        }
    }

    public void printBoard() {
        // TODO: Print out the board
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] instanceof ChessPiece) {
                    if (board[i][j].isObstacle)
                        System.out.print("*");
                    else if (board[i][j].color == 0)
                        System.out.print("+");
                    else
                        System.out.print("-");
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
