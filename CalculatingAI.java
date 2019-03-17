/**
 * CalculatingAI
 */

public class CalculatingAI extends AmazonsAI{
    
    ChessPiece[] chesses= new ChessPiece[4];
    ChessBoard board= new ChessBoard();
    
    //TODO:implement calculating AI
    public Move nextMove(ChessBoard inputBoard) {
        ChessBoard board = inputBoard.clone();
        Move move=new Move(0, 0, 0, 0, 0, 0);
        ChessPiece[] chesses = new ChessPiece[4];
        if (board.colorForTurn() == 0)
            chesses = board.black;
        else if (board.colorForTurn() == 1)
            chesses = board.white;
        
        return move;
    }
}