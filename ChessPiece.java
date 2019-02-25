import java.util.List;
import java.util.ArrayList;

public class ChessPiece {

    // Properties

    int x;
    int y;
    int color; // 0 stands for black, 1 for white, 2 for obstacle
    boolean isObstacle;
    // although whether pieces are obstacles or not does not affect the game
    // it has something to do with GUI, so we'd better put it there

    // Constructors

    ChessPiece(int x, int y, int color, boolean isObstacle) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.isObstacle = isObstacle;
    }

    protected ChessPiece clone() {
        return new ChessPiece(x, y, color, isObstacle);
    }

    public boolean equals(ChessPiece obj) {
        return x == obj.x && y == obj.y && color == obj.color && isObstacle == obj.isObstacle;
    }

    // Methods

    public List<Pair> possiblePositions(ChessBoard board) {
        // @param boolean putObstacle specifies the property of the output ChessPiece -
        // whether they're obstacles or not
        List<Pair> positions = new ArrayList<Pair>();
        Pair position = new Pair();
        for (int i = -1; i <= 1; i++)
            for (int j = -1; j <= 1; j++)
                if (i != 0 || j != 0) {
                    position.x = this.x + i;
                    position.y = this.y + j;
                    while (ChessBoard.withinBoard(position.x, position.y) && !board.hasPiece(position.x, position.y)) {
                        positions.add(new Pair(position.x, position.y));
                        position.x = position.x + i;
                        position.y = position.y + j;
                        // System.out.printf("%d %d\n", position.x, position.y);
                    }
                }
        return positions;
    }

    // returns how many positions this piece can go
    public int freedom(ChessBoard board) {
        return this.possiblePositions(board).size();
    }

    // public static boolean sanityCheck(int x, int y, int color, boolean
    // isObstacle) {
    // if (ChessBoard.withinBoard(x, y)) {
    // return false;
    // } else {
    // if (isObstacle) {
    // // since obstacles don't have color...
    // return true;
    // } else {
    // return color == 0 || color == 1;
    // }
    // }
    // }

    public static boolean sanityCheck(int x, int y, String color, boolean isObstacle) {
        if (x > 7 || y > 7 || x < 0 || y < 0) {
            return false;
        } else {
            if (isObstacle) {
                // since obstacles don't have color...
                return true;
            } else {
                return color.equals("black") || color.equals("white");
            }
        }
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", x, y);
    }
}
