
/**
 * Amazons
 */

import java.util.*;
import java.lang.Runtime;
import java.io.IOException;

public class Amazons {

    public static void clrscr() {
        // Clears Screen in java
        try {
            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                Runtime.getRuntime().exec("clear");
        } catch (IOException | InterruptedException ex) {

        }
    }

    public static void main(String args[]) {
        // AmazonsGUI gui = new AmazonsGUI();
        // TODO: implement the proof-of-concept CLI interface
        ChessBoard cb = new ChessBoard();
        Scanner sc = new Scanner(System.in);
        boolean ok = false;

        // choose the number of players
        System.out.println("Please choose the number of players.");
        System.out.println("1:One player mode(play with the computer).");
        System.out.println("2:Two player mode.");
        int playerNumber = sc.nextInt();

        // TODO:one player mode

        // two player mode
        if (playerNumber == 2) {
            cb.printBoard();
            System.out.println("0 2 3 3 3 4 means \nmove the chess on (0,2) to (3,3) \nand place an obstacle on (3,4)\n");
            System.out.printf("It is turn for %s.", cb.colorForTurn() == 0 ? "+" : "-");
            do {
                ok = true;
                // clrscr(); // I dunno why this doesn' work...
                int x = sc.nextInt();
                int y = sc.nextInt();
                int xx = sc.nextInt();
                int yy = sc.nextInt();
                int xxx = sc.nextInt();
                int yyy = sc.nextInt();
                // if(cb.isLegalMove(x, y, xx, yy, xxx, yyy)) {
                // List<Pair> possible = cb.getPiece(x, y).possiblePositions(cb);
                // System.out.println("Possible positions:");
                // }
                // for (Pair p : possible) {
                // System.out.printf("(%d, %d) ", p.x, p.y);
                // }
                System.out.println();
                if (!cb.movePiece(x, y, xx, yy, xxx, yyy)) {
                    System.out.println("Illegal input. Please try again.");
                    ok = false;
                    continue;
                }
                cb.printBoard();
                int checkResult=cb.declareResult();
                if(checkResult==1) {
                    System.out.println("Game over, the white wins.");
                    break;
                }
                if(checkResult==0) {
                    System.out.println("Game over, the black wins.");
                    break;
                }
                System.out.printf("It is turn for %s.", cb.colorForTurn() == 0 ? "+" : "-");
            } while (sc.hasNext() || !ok);
        }
    }
}
