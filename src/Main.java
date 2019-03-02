/**
 * Main
 */
public class Main {

    public static void main(String[] args) {
        AmazonsGUI GUI = new AmazonsGUI();
        GUI.initMainFrame();
        GUI.new StartingScreen().createWindow();
        // TargetBoardGUI bg = new TargetBoardGUI();
        // bg.initMainFrame();
        // ChessBoard cb = new ChessBoard();
        // AmazonsAI ai = new RandomAI();
        // for(int i=0;i<10;i++) {
        //     cb.moveStep(ai.nextMove(cb));
        // }
        // bg.initTargetBoard(cb);
    }
}