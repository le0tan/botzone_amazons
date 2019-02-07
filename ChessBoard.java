/**
 * ChessBoard
 */
public class ChessBoard {

    ChessPiece[][] board = new ChessPiece[8][8];
    int turn;

    // Constructor

    ChessBoard() {
        turn = 1;
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
        if (!withinBoard(x, y)) {
            return false;
        } else {
            return board[x][y] instanceof ChessPiece;
        }
    }

    // Returns true if the chesspiece is put successfully.
    private boolean putPiece(ChessPiece p) {
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

    public int colorForTurn() {
        return turn%2 == 0 ? 1 : 0;
    }

    private boolean inDiagnol(int x, int y, int xx, int yy) {
        return Math.abs(x-xx) == Math.abs(y-yy);
    }

    public boolean isLegalMove(int src_x, int src_y, int tar_x, int tar_y, int obs_x, int obs_y) {
        //TODO: maybe I forget about certain illegal cases
        return hasPiece(src_x, src_y) 
            && board[src_x][src_y].color == colorForTurn() 
            && !hasPiece(tar_x, tar_y)
            && inDiagnol(tar_x, tar_y, src_x, src_y)
            && !(obs_x == tar_x && obs_y == tar_y)
            && inDiagnol(obs_x, obs_y, tar_x, tar_y)
            ;
    }

    public static boolean withinBoard(int x, int y) {
        return !(x > 7 || y > 7 || x < 0 || y < 0);
    }

    public boolean movePiece(int src_x, int src_y, int tar_x, int tar_y, int obs_x, int obs_y) {
        if (!isLegalMove(src_x, src_y, tar_x, tar_y, obs_x, obs_y)
            || !withinBoard(obs_x, obs_y)) {
            return false;
        } else {
            ChessPiece temp = board[src_x][src_y];
            board[src_x][src_y] = null;
            board[tar_x][tar_y] = temp;
            board[obs_x][obs_y] = new ChessPiece(obs_x, obs_y, 2, true);
            turn++;
            return true;
        }
    }

    public int declareResult() {
        // TODO:
        // -1 means the game continues
        // 0 means the black wins
        // 1 means the white wins
        return -1;
    }

    public void printBoard() {
        System.out.print("  ");
        for(int i=0;i<8;i++) System.out.printf("%d ", i);
        System.out.println();
        for (int i = 0; i < 8; i++) {
            for (int j = -1; j < 8; j++) {
                if(j == -1) {
                    System.out.printf("%d ",i);
                    continue;
                } else if (board[i][j] instanceof ChessPiece) {
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
        System.out.println();
    }
}
