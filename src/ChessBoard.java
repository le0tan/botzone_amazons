import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.List;
import java.util.ArrayList;

/**
 * ChessBoard
 */
public class ChessBoard {

    public ChessPiece[][] board = new ChessPiece[8][8];
    private int turn;
    public ChessPiece[] black = new ChessPiece[4];
    public ChessPiece[] white = new ChessPiece[4];
    public static final int[][] coordBlack = { { 5, 7, 7, 5 }, { 0, 2, 5, 7 } };
    public static final int[][] coordWhite = { { 2, 0, 0, 2 }, { 0, 2, 5, 7 } };
    public List<Move> history = new ArrayList<Move>();
    private Move move = new Move(0, 0, 0, 0, 0, 0);

    boolean[][] pressed = new boolean[8][8];
    ChessPiece chessToMove = null;
    boolean chessmoved;
    ChessPiece movedChess = null;

    // Constructor

    ChessBoard() {
        turn = 1;
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                pressed[i][j] = false;
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

    ChessBoard(boolean isClone) {

    }

    protected ChessBoard clone() {
        ChessBoard res = new ChessBoard(true);
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                pressed[i][j] = false;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                res.board[i][j] = null;
                if (board[i][j] != null) {
                    res.board[i][j] = board[i][j].clone();
                }
            }
        }
        for (int i = 0; i < 4; i++) {
            res.black[i] = black[i].clone();
            res.white[i] = white[i].clone();
        }
        for (Move m : history) {
            res.history.add(m.clone());
        }
        res.turn = turn;
        return res;
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
        if (!isLegalMove(move)
                || !withinBoard(move.obs_x, move.obs_y)) {
            return false;
        } else {
            ChessPiece temp = board[move.src_x][move.src_y].clone();
            temp.x = move.tar_x;
            temp.y = move.tar_y;
            board[move.tar_x][move.tar_y] = temp;
            if (colorForTurn() == 0) {
                for (int i = 0; i < 4; i++) {
                    if (black[i].equals(board[move.src_x][move.src_y])) {
                        black[i] = temp;
                        break;
                    }
                }
            } else {
                for (int i = 0; i < 4; i++) {
                    if (white[i].equals(board[move.src_x][move.src_y])) {
                        white[i] = temp;
                        break;
                    }
                }
            }
            removePiece(move.src_x, move.src_y);
            board[move.obs_x][move.obs_y] = new ChessPiece(move.obs_x, move.obs_y, 2, true);
            System.out.println("Turn " + turn);
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

    public boolean isLegalMove(Move move) {
        int src_x = move.src_x, src_y = move.src_y, 
            tar_x = move.tar_x, tar_y = move.tar_y, 
            obs_x = move.obs_x, obs_y = move.obs_y;
        return hasPiece(src_x, src_y) && board[src_x][src_y].color == colorForTurn() && !hasPiece(tar_x, tar_y)
                && (!hasPiece(obs_x, obs_y) || obs_x == src_x && obs_y == src_y)
                && inQueenPosition(tar_x, tar_y, src_x, src_y) && inQueenPosition(obs_x, obs_y, tar_x, tar_y);
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
        history.add(move);
        return true;
    }

    public Move undo() {
        Move move = history.get(history.size() - 1);
        history.remove(history.size() - 1);
        turn--;
        ChessPiece temp = new ChessPiece(move.src_x, move.src_y, colorForTurn(), false);
        if (colorForTurn() == 0) {
            for (int i = 0; i < 4; i++) {
                if (black[i] == board[move.tar_x][move.tar_y]) {
                    black[i] = temp;
                    break;
                }
            }
        } else {
            for (int i = 0; i < 4; i++) {
                if (white[i] == board[move.tar_x][move.tar_y]) {
                    white[i] = temp;
                    break;
                }
            }
        }
        removePiece(move.obs_x, move.obs_y);
        removePiece(move.tar_x, move.tar_y);
        putPiece(temp);
        return move;
    }

    public int declareResult() {
        // 0 stands for black wins
        // 1 stands for white wins
        // 2 stands for a tie
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
            return 2; // ????
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
                move = new Move(sca.nextInt(), sca.nextInt(), sca.nextInt(), sca.nextInt(), sca.nextInt(),
                        sca.nextInt());
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

    public void setChessToMove() {
        if (colorForTurn() == 0) {
            for (int i = 0; i < 4; i++) {
                if (this.pressed[black[i].x][black[i].y]) {
                    chessToMove = black[i];
                }
            }
        } else {
            for (int i = 0; i < 4; i++) {
                if (this.pressed[white[i].x][white[i].y]) {
                    chessToMove = white[i];
                }
            }
        }
    }

    public boolean choosingTarget() {
        if (chessToMove != null)
            return true;
        else
            return false;
    }

    public boolean choosingObstacle() {
        if (movedChess != null)
            return true;
        else
            return false;
    }

    public void moveChess(int tar_x, int tar_y) {
        move.src_x = chessToMove.x;
        move.src_y = chessToMove.y;
        move.tar_x = tar_x;
        move.tar_y = tar_y;
        movedChess = new ChessPiece(tar_x, tar_y, chessToMove.color, false);
        board[tar_x][tar_y] = movedChess;
        pressed[tar_x][tar_y] = true;
        if (colorForTurn() == 0) {
            for (int i = 0; i < 4; i++) {
                if (black[i] == chessToMove) {
                    black[i] = movedChess;
                    break;
                }
            }
        } else {
            for (int i = 0; i < 4; i++) {
                if (white[i] == chessToMove) {
                    white[i] = movedChess;
                    break;
                }
            }
        }
        removePiece(chessToMove.x, chessToMove.y);
        pressed[chessToMove.x][chessToMove.y] = false;
        chessToMove = null;
    }

    public void putObstacle(int x, int y) {
        pressed[movedChess.x][movedChess.y] = false;
        board[x][y] = new ChessPiece(x, y, 2, true);
        movedChess = null;
        turn++;
        move.obs_x = x;
        move.obs_y = y;
        history.add(new Move(move.src_x, move.src_y, move.tar_x, move.tar_y, move.obs_x, move.obs_y));
    }

    /** End of GUI related methods **/
}
