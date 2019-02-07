
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
        do {
            ok = true;
            clrscr();   // I dunno why this doesn' work...
            System.out.println("Put pieces with command like \"3 3 black t\"");
            int x = sc.nextInt();
            int y = sc.nextInt();
            String color = sc.next();
            boolean isObstacle = sc.next().equals("t") ? true : false;
            if (ChessPiece.sanityCheck(x, y, color, isObstacle)) {
                if (!cb.putPiece(new ChessPiece(x, y, color, isObstacle))) {
                    System.out.println("Illegal input. Please try again.");
                    ok = false;
                    continue;
                }
            } else {
                System.out.println("Illegal input. Please try again.");
                ok = false;
                continue;
            }
            cb.printBoard();
        } while (sc.hasNext() || !ok);
    }
}
