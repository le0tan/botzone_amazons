/**
 * AmazonsFileWatcher
 */
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class AmazonsFileWatcher extends FileWatcher
{
    private ChessBoard board = null;
    public static final String inputFileName = "input.txt";
    public static final String outputFileName = "output.txt";
    public AmazonsFileWatcher(String watchFile, ChessBoard board)
    {
        super(watchFile);
        this.board = board;
    }

    @Override
    public void onModified() throws Exception
    {
        File input = new File(getFilePath().toString());
        File output = new File(getFolderPath() + File.separator + outputFileName);
        Scanner sc = new Scanner(input);
        FileWriter writer = new FileWriter(output);
        Move move = AmazonsFileInput.moveFromInput(sc.nextLine());
        // testing code, will be replaced later
        if(true || move != null && board.moveStep(move)) {
            writer.write("0\n");
            writer.close();
        } else {
            writer.write("-1");
            writer.close();
        }
        sc.close();
    }
}