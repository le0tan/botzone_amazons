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

    // ChessPiece(int x, int y, String color, boolean isObstacle) {
    //     if (!(color.equals("black")||color.equals("white")))
    //     {
    //         this.color = -1;
    //         return;
    //     }
    //     this.x = x;
    //     this.y = y;
    //     this.isObstacle = isObstacle;  
    //     if (color.equals("black")) {
    //         this.color = 0;
    //     } else {
    //         this.color = 1;
    //     }
    // }

    // Methods
    
    private List<Pairs> possiblePositions(ChessBoard board) {
        // @param boolean putObstacle specifies the property of the output ChessPiece -
        // whether they're obstacles or not
        List<Pairs> positions= new ArrayList<Pairs>();
        Pairs position=new Pairs();
        for(int i=-1;i<=1;i++)
         for(int j=-1;j<=1;j++) 
          if(i!=0||j!=0) {
             position.x=this.x+i;
             position.y=this.y+j;
             while(board.hasPiece(position.x,position.y)) {
                 positions.add(position);
                 position.x=position.x+i;
                 position.y=position.y+j;
             }
         }
        return positions;
    }

    // returns how many positions this piece can go
    private int freedom(ChessBoard board) {
        return this.possiblePositions(board).size();
    }

    // public static boolean sanityCheck(int x, int y, int color, boolean isObstacle) {
    //     if (ChessBoard.withinBoard(x, y)) {
    //         return false;
    //     } else {
    //         if (isObstacle) {
    //             // since obstacles don't have color...
    //             return true;
    //         } else {
    //             return color == 0 || color == 1;
    //         }
    //     }
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

    private class Pairs {
        int x;
        int y;
    }
}
