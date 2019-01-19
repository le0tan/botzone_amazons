import java.util.List;

public class ChessPiece {

    // Properties

    int x;
    int y;
    int color;  // 0 stands for black, 1 for white
    boolean isObstacle;

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

    private List<ChessPiece> possiblePositions(boolean putObstacle) {
        // the arg specifies whether we're outputing a list of obstacles of normal chesses
        // although whether pieces are obstacles or not does not affect the game
        // it has something to do with GUI, so we'd better put it there
        // TODO: implement the queen-like possible position determination
    }

    // returns how many positions this piece can go
    private int freedom() {
        return this.possiblePositions(false).size();
    }

}