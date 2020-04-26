import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class PrintWrapper {
    public static void writeNode(BTreeNode node, String filePath) {
        Scanner scan;
        RandomAccessFile f;
        try {
            scan = new Scanner(new File(filePath));
            f = new RandomAccessFile(new File(filePath), "rw");
            for (int currentLine = 1; currentLine < node.getSelfPointer(); currentLine++) {
                if (!scan.hasNextLine() && currentLine + 1 < node.getSelfPointer()) {
                    throw new IllegalArgumentException("Line " + node.getSelfPointer() + " does not exist.");
                }
                f.readLine();
            }
            f.write(node.toString().getBytes());
            f.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}