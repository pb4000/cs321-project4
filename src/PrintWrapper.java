import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class PrintWrapper {

    /**
     * Navigates to the selfPointer of the given node in the file
     * and writes the node over anything that may have previously been there.
     * @param node
     * @param filePath
     */
    public static void writeNode(BTreeNode node, File file) {
        Scanner scan;
        RandomAccessFile f;
        try {
            scan = new Scanner(file);
            f = new RandomAccessFile(file, "rw");
            for (int currentLine = 1; currentLine < node.getSelfPointer(); currentLine++) {
                if (!scan.hasNextLine() && currentLine + 1 < node.getSelfPointer()) {
                    scan.close();
                    f.close();
                    throw new IllegalArgumentException("Line " + node.getSelfPointer() + " does not exist.");
                }
                f.readLine();
            }
            f.write(node.toString().getBytes());
            f.close();
            scan.close();
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
            f.write(new String(Parser.add10Spaces(rootNode.getSelfPointer()) + "\n").getBytes());
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