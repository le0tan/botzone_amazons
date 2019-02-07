
/**
 * Amazons
 */

import java.util.*;
import java.lang.Runtime;
import java.io.IOException;

public class Amazons {

    public static void clrscr(){
        //Clears Screen in java
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
        cb.printBoard();
        System.out.println("0 2 3 3 3 4 means \nmove the chess on (0,2) to (3,3) \nand place an obstacle on (3,4)\n");
        do {
            ok = true;
            clrscr();   // I dunno why this doesn' work...
            System.out.printf("It is turn for %s.\n", cb.colorForTurn() == 0 ? "+" : "-");
            int x = sc.nextInt();
            int y = sc.nextInt();
            int xx = sc.nextInt();
            int yy = sc.nextInt();
            int xxx = sc.nextInt();
            int yyy = sc.nextInt();
            if(!cb.movePiece(x, y, xx, yy, xxx, yyy)) {
                System.out.println("Illegal input. Please try again.");
                ok = false;
                continue;
            }
            cb.printBoard();
        } while (sc.hasNext() || !ok);
    }
}
