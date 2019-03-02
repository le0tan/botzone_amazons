package amazons;

/**
 * FileIOTester
 */
public class FileIOTester {
    public static void main(String[] args) throws Exception {
        String watchFile = "./" + AmazonsFileWatcher.inputFileName;
        try {
        } catch (Exception e) {
            //TODO: handle exception
        }
        FileWatcher fileWatcher = new AmazonsFileWatcher(watchFile, new ChessBoard());
        fileWatcher.watchFile();
    }
}