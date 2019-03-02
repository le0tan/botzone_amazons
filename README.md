# Amazons

This project is a JAVA re-implementation of [BOTZONE](cn.botzone.org) that's created and maintained by PKU.

## Dev Plan

- Design the classes and loading procedure ✔
- Implement the CLI interface using `ChessBoard`, `ChessPiece` and `Amazons` only ✔
- Implement the GUI (hard and unfamiliar) ✔
- Refine local playing experience ✔
- Implement the AI feature inside `BotAmazons` ✔
- Implement LAN multiplayer (future)
- Implement online multiplayer (easy peasy after LAN)

## Update on 3/3/2019

If you're using VSCode, you should enter `src` as the root directory now.

Compile files with `javac -d . ./amazons/*.java`, if nothing's changed in `Main`, you don't need to compile it again.

When adding new files, please add it inside `amazons` folder and include `package amazons` at the beginning of `.java` file.