
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
        ChessBoard cb = new ChessBoard();
        Scanner sc = new Scanner(System.in);
        boolean ok = false;

        while (!ok) {

            // choose the number of players
            System.out.println("Please choose the number of players.");
            System.out.println("1: One player mode (play with the computer).");
            System.out.println("2: Two player mode.");
            // TODO: This may also lead to illegal input, which can be resolved in similar
            // ways as the upcoming nextInt()'s
            int playerNumber = sc.nextInt();

            if (playerNumber == 1) {
                // TODO: One player mode
                ok = true;
            } else if (playerNumber == 2) {
                // Two player mode
                ok = true;
                cb.printBoard();
                System.out.println(
                        "0 2 3 3 3 4 means \nmove the chess on (0,2) to (3,3) \nand place an obstacle on (3,4)\n");
                System.out.printf("It is turn for %s.", cb.colorForTurn() == 0 ? "+" : "-");
                do {
                    int x, y, xx, yy, xxx, yyy;
                    try {
                        ok = true;
                        // clrscr(); // I dunno why this doesn' work...
                        x = sc.nextInt();
                        y = sc.nextInt();
                        xx = sc.nextInt();
                        yy = sc.nextInt();
                        xxx = sc.nextInt();
                        yyy = sc.nextInt();
                        // if(cb.isLegalMove(x, y, xx, yy, xxx, yyy)) {
                        // List<Pair> possible = cb.getPiece(x, y).possiblePositions(cb);
                        // System.out.println("Possible positions:");
                        // }
                        // for (Pair p : possible) {
                        // System.out.printf("(%d, %d) ", p.x, p.y);
                        // }
                    } catch (InputMismatchException e) {
                        sc.next();
                        System.out.println("Illegal input. Please try again.");
                        continue;
                    }
                    System.out.println();
                    if (!cb.movePiece(x, y, xx, yy, xxx, yyy)) {
                        System.out.println("Illegal input. Please try again.");
                        ok = false;
                        continue;
                    }
                    cb.printBoard();
                    int checkResult = cb.declareResult();
                    if (checkResult == 1) {
                        System.out.println("Game over, the white wins.");
                        break;
                    }
                    if (checkResult == 0) {
                        System.out.println("Game over, the black wins.");
                        break;
                    }
                    System.out.printf("It is turn for %s.\n", cb.colorForTurn() == 0 ? "+" : "-");
                } while (sc.hasNext() || !ok);
            }
        }
    }

}
