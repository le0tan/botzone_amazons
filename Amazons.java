
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

    // public static void readStep(int x,int y,int xx,int yy,int xxx,int yyy) {
    // Scanner sc = new Scanner(System.in);
    // do {
    // try {
    // x = sc.nextInt();
    // y = sc.nextInt();
    // xx = sc.nextInt();
    // yy = sc.nextInt();
    // xxx = sc.nextInt();
    // yyy = sc.nextInt();
    // // if(cb.isLegalMove(x, y, xx, yy, xxx, yyy)) {
    // // List<Pair> possible = cb.getPiece(x, y).possiblePositions(cb);
    // // System.out.println("Possible positions:");
    // // }
    // // for (Pair p : possible) {
    // // System.out.printf("(%d, %d) ", p.x, p.y);
    // // }
    // } catch (InputMismatchException e) {
    // sc.next();
    // System.out.println("Illegal input. Please try again.");
    // continue;
    // }
    // sc.close();
    // break;
    // }while(0==0);

    // }

    public static boolean checkResult(ChessBoard cb) {
        int checkResult = cb.declareResult();
        if (checkResult == 2) {
            System.out.println("Game over, it is a tie.");
            return true;
        }
        if (checkResult == 1) {
            System.out.println("Game over, the white wins.");
            return true;
        }
        if (checkResult == 0) {
            System.out.println("Game over, the black wins.");
            return true;
        }
        return false;
    }

    public static boolean moveStep(int x, int y, int xx, int yy, int xxx, int yyy, ChessBoard cb) {
        Scanner sca = new Scanner(System.in);
        do {
            try {
                x = sca.nextInt();
                y = sca.nextInt();
                xx = sca.nextInt();
                yy = sca.nextInt();
                xxx = sca.nextInt();
                yyy = sca.nextInt();
                // if(cb.isLegalMove(x, y, xx, yy, xxx, yyy)) {
                // List<Pair> possible = cb.getPiece(x, y).possiblePositions(cb);
                // System.out.println("Possible positions:");
                // }
                // for (Pair p : possible) {
                // System.out.printf("(%d, %d) ", p.x, p.y);
                // }
            } catch (InputMismatchException e) {
                sca.next();
                System.out.println("Illegal input. Please try again.");
                continue;
            }
            // sca.close();
            break;
        } while (true);
        if (!cb.movePiece(x, y, xx, yy, xxx, yyy)) {
            System.out.println("Illegal input. Please try again.");
            return false;
        }
        return true;
    }

    public static void main(String args[]) {
        // AmazonsGUI gui = new AmazonsGUI();
        ChessBoard cb = new ChessBoard();
        Scanner sc = new Scanner(System.in);
        boolean ok = false;

        while (!ok) {

            // choose the number of players
            System.out.println("Please choose the number of players.");
            System.out.println("0: No player mode(let the computer play with itself.");
            System.out.println("1: One player mode (play with the computer).");
            System.out.println("2: Two player mode.");
            int playerNumber;
            try {
                playerNumber = sc.nextInt();
            } catch (InputMismatchException e) {
                sc.next();
                System.out.println("Illegal input. Please try again.");
                continue;
            }

            // 0 player mode
            if (playerNumber == 0) {
                // choose difficulty
                System.out.println("Please choose difficulty for the black.");
                System.out.println("1: easy");
                System.out.println("2: hard");
                int diffBlack;
                try {
                    diffBlack = sc.nextInt();
                } catch (InputMismatchException e) {
                    sc.next();
                    System.out.println("Illegal input. Please try again.");
                    continue;
                }
                System.out.println("Please choose difficulty for the white.");
                System.out.println("1: easy");
                System.out.println("2: hard");
                int diffWhite;
                try {
                    diffWhite = sc.nextInt();
                } catch (InputMismatchException e) {
                    sc.next();
                    System.out.println("Illegal input. Please try again.");
                    continue;
                }
                RandomAI aib = new RandomAI();
                RandomAI aiw = new RandomAI();
                // TODO:implement calculating AI

                do {
                    if (cb.colorForTurn() == 0) {
                        System.out.println("It is turn for the black.");
                        cb = aib.randomAi(cb);
                    } else if (cb.colorForTurn() == 1) {
                        System.out.println("It is turn for the white.");
                        cb = aiw.randomAi(cb);
                    }
                    cb.printBoard();
                    boolean checkres = checkResult(cb);
                    if (checkres) {
                        ok = true;
                        break;
                    }
                } while (true);
                // I don't know what is the proper expression here,
                // now it just work like this but it doesn't look right...
            }

            // 1 player mode
            else if (playerNumber == 1) {

                // choose difficulty
                System.out.println("Please choose difficulty.");
                System.out.println("1: easy");
                System.out.println("2: hard");
                int difficulty;
                try {
                    difficulty = sc.nextInt();
                } catch (InputMismatchException e) {
                    sc.next();
                    System.out.println("Illegal input. Please try again.");
                    continue;
                }
                RandomAI ai = new RandomAI();
                // TODO:implement calculating AI
                // if(difficulty==1) {
                // RandomAI ai=new RandomAI();
                // } else if(difficulty==2) {
                // CalculatingAI ai=new CalculatingAI();
                // }

                // choose color
                System.out.println("Please choose your color.");
                System.out.println("+: black");
                System.out.println("-: white");
                String color;
                do {
                    color = sc.next();
                    if (!(color.equals("+") || color.equals("-"))) {
                        System.out.println("Illegal input. Please try again.");
                        continue;
                    } else
                        break;
                } while (true);

                cb.printBoard();
                System.out.println(
                        "0 2 3 3 3 4 means \nmove the chess on (0,2) to (3,3) \nand place an obstacle on (3,4)\n");
                if (color.equals("+")) {
                    do {
                        if (cb.colorForTurn() == 0) {
                            System.out.println("It is your turn.");
                            int x = 0, y = 0, xx = 0, yy = 0, xxx = 0, yyy = 0;
                            ok = moveStep(x, y, xx, yy, xxx, yyy, cb);
                            if (!ok)
                                continue;
                        } else if (cb.colorForTurn() == 1) {
                            System.out.println("It is turn for the computer.");
                            cb = ai.randomAi(cb);
                        }
                        cb.printBoard();
                        boolean checkres = checkResult(cb);
                        if (checkres)
                            break;
                    } while (true);
                } else if (color.equals("-")) {
                    do {
                        if (cb.colorForTurn() == 0) {
                            System.out.println("It is turn for the computer.");
                            cb = ai.randomAi(cb);
                        } else if (cb.colorForTurn() == 1) {
                            System.out.println("It is your turn.");
                            int x = 0, y = 0, xx = 0, yy = 0, xxx = 0, yyy = 0;
                            ok = moveStep(x, y, xx, yy, xxx, yyy, cb);
                            if (!ok)
                                continue;
                        }
                        cb.printBoard();
                        boolean checkres = checkResult(cb);
                        if (checkres)
                            break;
                    } while (true);
                }
            }

            // 2 player mode
            else if (playerNumber == 2) {
                cb.printBoard();
                System.out.println(
                        "0 2 3 3 3 4 means \nmove the chess on (0,2) to (3,3) \nand place an obstacle on (3,4)\n");
                System.out.printf("It is turn for %s.\n", cb.colorForTurn() == 0 ? "+" : "-");
                do {
                    int x = 0, y = 0, xx = 0, yy = 0, xxx = 0, yyy = 0;
                    ok = moveStep(x, y, xx, yy, xxx, yyy, cb);
                    if (!ok)
                        continue;
                    // try {
                    // // clrscr(); // I dunno why this doesn' work...
                    // x = sc.nextInt();
                    // y = sc.nextInt();
                    // xx = sc.nextInt();
                    // yy = sc.nextInt();
                    // xxx = sc.nextInt();
                    // yyy = sc.nextInt();
                    // // if(cb.isLegalMove(x, y, xx, yy, xxx, yyy)) {
                    // // List<Pair> possible = cb.getPiece(x, y).possiblePositions(cb);
                    // // System.out.println("Possible positions:");
                    // // }
                    // // for (Pair p : possible) {
                    // // System.out.printf("(%d, %d) ", p.x, p.y);
                    // // }
                    // } catch (InputMismatchException e) {
                    // sc.next();
                    // System.out.println("Illegal input. Please try again.");
                    // continue;
                    // }
                    // if (!cb.movePiece(x, y, xx, yy, xxx, yyy)) {
                    // System.out.println("Illegal input. Please try again.");
                    // ok = false;
                    // continue;
                    // }
                    cb.printBoard();
                    boolean checkres = checkResult(cb);
                    if (checkres)
                        break;
                    // int checkResult = cb.declareResult();
                    // if (checkResult == 1) {
                    // System.out.println("Game over, the white wins.");
                    // break;
                    // }
                    // if (checkResult == 0) {
                    // System.out.println("Game over, the black wins.");
                    // break;
                    // }
                    System.out.printf("It is turn for %s.\n", cb.colorForTurn() == 0 ? "+" : "-");
                } while (true);
            }
        }
        // sc.close();
    }

}
