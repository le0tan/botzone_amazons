import java.util.Scanner;
import java.util.InputMismatchException;;

/**
 * ChessBoard
 */
public class ChessBoard {

    ChessPiece[][] board = new ChessPiece[8][8];
    boolean[][] pressed=new boolean[8][8];
    int turn;
    ChessPiece[] black = new ChessPiece[4];
    ChessPiece[] white = new ChessPiece[4];
    int[][] coordBlack = { { 5, 7, 7, 5 }, { 0, 2, 5, 7 } };
    int[][] coordWhite = { { 2, 0, 0, 2 }, { 0, 2, 5, 7 } };

    // Constructor

    ChessBoard() {
        turn = 1;
        for(int i=0;i<8;i++)
         for(int j=0;j<8;j++)
          pressed[i][j]=false;
        for (int i = 0; i < 4; i++) {
            this.board[coordBlack[0][i]][coordBlack[1][i]] = new ChessPiece(coordBlack[0][i], coordBlack[1][i], 0,
                    false);
            this.black[i] = new ChessPiece(coordBlack[0][i], coordBlack[1][i], 0, false);
        }

        for (int i = 0; i < 4; i++) {
            this.board[coordWhite[0][i]][coordWhite[1][i]] = new ChessPiece(coordWhite[0][i], coordWhite[1][i], 1,
                    false);
            this.white[i] = new ChessPiece(coordWhite[0][i], coordWhite[1][i], 1, false);
        }
    }

    // Methods

    /** Methods on moving chess pieces around **/

    public boolean hasPiece(int x, int y) {
        if (!withinBoard(x, y)) {
            return false;
        } else {
            return board[x][y] instanceof ChessPiece;
        }
    }

    public ChessPiece getPiece(int x, int y) {
        return board[x][y];
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

    public void removePiece(int x, int y) {
        if (hasPiece(x, y))
            board[x][y] = null;
    }

    public boolean movePiece(Move move) {
        if (!isLegalMove(move.src_x, move.src_y, move.tar_x, move.tar_y, move.obs_x, move.obs_y) || !withinBoard(move.obs_x, move.obs_y)) {
            return false;
        } else {
            ChessPiece temp = board[move.src_x][move.src_y];
            board[move.tar_x][move.tar_y] = temp;
            temp.x = move.tar_x;
            temp.y = move.tar_y;
            if (colorForTurn() == 1) {
                for (int i = 0; i < 4; i++) {
                    if (black[i] == board[move.src_x][move.src_y]) {
                        black[i] = temp;
                        break;
                    }
                }
            } else {
                for (int i = 0; i < 4; i++) {
                    if (white[i] == board[move.src_x][move.src_y]) {
                        white[i] = temp;
                        break;
                    }
                }
            }
            removePiece(move.src_x, move.src_y);
            board[move.obs_x][move.obs_y] = new ChessPiece(move.obs_x, move.obs_y, 2, true);
            turn++;
            return true;
        }
    }

    /** End of methods on moving chess pieces around **/

    /** Auxiliary methods **/

    public int colorForTurn() {
        return turn % 2 == 0 ? 1 : 0;
    }

    private boolean inQueenPosition(int x, int y, int xx, int yy) {
        return Math.abs(x - xx) == Math.abs(y - yy) || x == xx || y == yy;
    }

    public boolean isLegalMove(int src_x, int src_y, int tar_x, int tar_y, int obs_x, int obs_y) {
        return hasPiece(src_x, src_y) && board[src_x][src_y].color == colorForTurn() && !hasPiece(tar_x, tar_y)
                && (!hasPiece(obs_x, obs_y) || obs_x == src_x && obs_y == src_y)
                && inQueenPosition(tar_x, tar_y, src_x, src_y)
                && inQueenPosition(obs_x, obs_y, tar_x, tar_y);
    }

    public static boolean withinBoard(int x, int y) {
        return x >= 0 && y >= 0 && x <= 7 && y <= 7;
    }

    /** End of auxiliary methods **/

    /** Methods for actual games **/

    public boolean moveStep(Move move) {
        if (!this.movePiece(move)) {
            System.out.println("Illegal input. Please try again.");
            return false;
        }
        return true;
    }

    public int declareResult() {
        // 0 stands for black wins
        // 1 stands for white wins
        // -1 stands for game continues
        int counterb = 0;
        for (int i = 0; i < 4; i++) {
            if (this.black[i].freedom(this) == 0)
                counterb++;
        }
        int counterw = 0;
        for (int i = 0; i < 4; i++) {
            if (this.white[i].freedom(this) == 0)
                counterw++;
        }
        if (counterb == 4 && counterw == 4)
            return 2;
        if (counterb == 4)
            return 1;
        if (counterw == 4)
            return 0;
        return -1;
    }

    /** End of methods for actual games **/

    /** CLI specific methods **/

    public void printBoard() {
        System.out.print("  ");
        for (int i = 0; i < 8; i++)
            System.out.printf("%d ", i);
        System.out.println();
        for (int i = 0; i < 8; i++) {
            for (int j = -1; j < 8; j++) {
                if (j == -1) {
                    System.out.printf("%d ", i);
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

    public boolean moveStepFromInput() {
        // This is bad. Try to consolidate to one single Scanner for a single file
        Scanner sca = new Scanner(System.in);
        Move move = null;
        do {
            try {
                move = new Move(sca.nextInt(), sca.nextInt(), sca.nextInt(), sca.nextInt(), sca.nextInt(), sca.nextInt());
            } catch (InputMismatchException e) {
                sca.next();
                System.out.println("Illegal input. Please try again.");
                continue;
            }
            // sca.close();
            break;
        } while (true);
        return this.moveStep(move);
    }

    /** End of CLI specific methods **/

    /** GUI related methods **/
    

    /** End of GUI related methods **/
}
