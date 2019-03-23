package amazons;

/**
 * RandomAI
 */

import java.util.*;

public class RandomAI extends AmazonsAI {

    public Move nextMove(ChessBoard inputBoard) {
        ChessBoard board = inputBoard.clone();
        Move move = new Move(0, 0, 0, 0, 0, 0);
        ChessPiece[] chesses = new ChessPiece[4];
        if (board.colorForTurn() == 0)
            chesses = board.black;
        else if (board.colorForTurn() == 1)
            chesses = board.white;
        Random ra = new Random();
        // choose a chess
        int chessNumber;
        do {
            chessNumber = ra.nextInt(4);
        } while (chesses[chessNumber].freedom(board) == 0);
        // System.out.printf("%d %d ", chesses[chessNumber].x, chesses[chessNumber].y);
        move.src_x = chesses[chessNumber].x;
        move.src_y = chesses[chessNumber].y;
        // choose a target position
        int fd = chesses[chessNumber].freedom(board);
        int planNumber = ra.nextInt(fd);
        Pair tar = chesses[chessNumber].possiblePositions(board).get(planNumber);
        // System.out.printf("%d %d ", tar.x, tar.y);
        move.tar_x = tar.x;
        move.tar_y = tar.y;
        ChessPiece target = new ChessPiece(tar.x, tar.y, board.colorForTurn(), false);
        // move the chess to the target
        board.board[tar.x][tar.y] = target;
        board.removePiece(chesses[chessNumber].x, chesses[chessNumber].y);
        chesses[chessNumber] = target;
        // choose an obstacle position
        fd = target.freedom(board);
        planNumber = ra.nextInt(fd);
        Pair obs = target.possiblePositions(board).get(planNumber);
        // System.out.printf("%d %d\n", obs.x, obs.y);
        move.obs_x = obs.x;
        move.obs_y = obs.y;
        // create the obstacle
        ChessPiece obstacle = new ChessPiece(obs.x, obs.y, 2, true);
        board.board[obs.x][obs.y] = obstacle;
        if (board.colorForTurn() == 0)
            board.black = chesses;
        else if (board.colorForTurn() == 1)
            board.white = chesses;
        // board.turn++;
        // board.history.add(new Move(move.src_x, move.src_y, move.tar_x, move.tar_y,
        // move.obs_x, move.obs_y));
        return move;
    }
}