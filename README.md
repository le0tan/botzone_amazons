# botzone_amazons

This project is a JAVA re-implementation of [BOTZONE](cn.botzone.org) that's created and maintained by PKU.

## Dev Plan

- Design the classes and loading procedure ✔
- Implement the CLI interface using `ChessBoard`, `ChessPiece` and `Amazons` only ✔
- Implement the GUI (hard and unfamiliar)
- Implement the AI feature inside `BotAmazons`
- Implement LAN multiplayer (future)
- Implement online multiplayer (easy peasy after LAN)

## To-dos for Feburary 12

- Finish the command line interface (Starting screen, choose black/white, declare the results, and choose to play with AI or not **if** the third task is finished)
- Have test data for a complete game
- Implement random AI with classname "RandomAI.java". It receives two arguments: whether it's white or black and the ChessBoard instance. It may make use of any public method in ChessBoard and ChessPiece to help with its decision (It can't output illegal outputs anyways... XD)
- Come up with a way of representing the searching process (Maybe a class for tagged data?)
```java
class BFSResult {
    int layer;
    Pairs position;
}
BFSResult[] searchProcess = new ArrayList<BFSResult>();
Queue<BFSResult> q = new Queue<BFSResult>();
while(cond) {
    BFSResult t = q.poll();
    // extend states from t, but with layer++
    // add those extended states to q
}
```

## To-dos for Feburary 16

- Implement the first version of DrawChessPiece (in AmazonsGUI.java, the private class inside the public class). (Refer to Graphics2D for more information)
- Implement click to place chess piece; black pieces and white pieces should appear by turns
- Implement displayFreedom() method
