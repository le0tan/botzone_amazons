import java.util.List;

public class ChessPiece {

    // Properties

    int x;
    int y;
    int color;  // 0 stands for black, 1 for white
    boolean isObstacle;
    // although whether pieces are obstacles or not does not affect the game
    // it has something to do with GUI, so we'd better put it there

    // Constructors

    ChessPiece(int x, int y, int color, boolean isObstacle) {
        this.x = x; this.y = y; this.color = color; this.isObstacle = isObstacle;
    }
    ChessPiece(int x, int y, String[] color, boolean isObstacle){
        this.x = x; this.y = y; this.isObstacle = isObstacle;
        // TODO: Handle illegal string input here
        if(color.equals("black")) {
            this.color = 0;
        } else {
            this.color = 1;
        }
    }

    // Methods

    private List<ChessPiece> possiblePositions(ChessBoard board, boolean putObstacle) {
        // @param boolean putObstacle specifies the property of the output ChessPiece - whether they're obstacles or not
        // TODO: implement the queen-like possible position determination
    }

    // returns how many positions this piece can go
    private int freedom(ChessBoard board) {
        return this.possiblePositions(board, false).size();
    }

}