import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class PrintWrapper {

    /**
     * Navigates to the selfPointer of the given node in the file
     * and writes the node over anything that may have previously been there.
     * @param node
     * @param filePath
     */
    public static void writeNode(BTreeNode node, File file) {
        // each node takes up this many bytes. there is also 4 lines (40 bytes) of metadata for each file
        // DONT FORGET TO ACCOUNT FOR NEXT LINE MARKERS
        // long bytesPerNode = 48 + (11 * (node.getDegree() + 1)) + (74 * (node.getDegree()) - 1);
        // long bytePerNode = (4 * 11) + (2) + ((degree + 1) * 11) + ((10 + 1 + 62 + 1) * degree)

        ScannerWrapper wrap = new ScannerWrapper(file);
        // boolean endOfFile = false;
        // if (node.getSelfPointer() == wrap.getNextPointer()) {
        //     endOfFile = true;
        // }
        // RandomAccessFile f;
        try {
            RandomAccessFile f = new RandomAccessFile(file, "rw");
            // for (int currentLine = 1; currentLine < node.getSelfPointer(); currentLine++) {
            //     f.readLine();
            // }
            f.seek(node.getSelfPointer());
            /**
             * If writing to the end of the file, start on a new line
             */
            // if (node.getSelfPointer() == wrap.getNextPointer()) {
            //     f.write(" ".getBytes());
            // }
            f.write(new String(node.toString() + "\n").getBytes());
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

    /**
     * Creates a new, fully formatted file given the variables below.
     * @param k
     * @param degree
     * @param rootNode
     * @param totalNodes
     * @param file
     */
    public static void createFile(int k, int degree, BTreeNode rootNode, File file) {
        try {
            RandomAccessFile f = new RandomAccessFile(file, "rw");
            f.write(new String(Parser.add10Spaces(k) + "\n").getBytes());
            f.write(new String(Parser.add10Spaces(degree) + "\n").getBytes());
            f.write(new String(Parser.add10Spaces(Integer.valueOf(Long.toString(rootNode.getSelfPointer()))) + "\n").getBytes());
            f.write(new String(Parser.add10Spaces(1) + "\n").getBytes());
            f.write(rootNode.toString().getBytes());
            f.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Navigates to the correct line and overwrites the number of nodes.
     * @param nodes
     * @param file
     */
    public static void updateNumberOfNodes(int nodes, File file) {
        try {
            RandomAccessFile f = new RandomAccessFile(file, "rw");
            for (int i = 0; i < 4; i++) {
                f.readLine();
            }
            f.write(Parser.add10Spaces(nodes).getBytes());
            f.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Navigates to the correct line and overwrited the root pointer.
     * @param pointer
     * @param file
     */
    public static void updateRootPointer(int pointer, File file) {
        try {
            RandomAccessFile f = new RandomAccessFile(file, "rw");
            for (int i = 0; i < 3; i++) {
                f.readLine();
            }
            f.write(Parser.add10Spaces(pointer).getBytes());
            f.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}