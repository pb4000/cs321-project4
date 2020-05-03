import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ScannerWrapper {
int k;
int degree;
Scanner fileScan;
File file;
int nodeDiskSize;

    public ScannerWrapper(File fileIn, int degree, int k) {
        if (!fileIn.exists()) {
            FileNotFoundException e = new FileNotFoundException("Given target does not exist.");
            e.printStackTrace();
            System.exit(1);
        }
        file = fileIn;
        this.degree = degree;
        this.k = k;
        nodeDiskSize = 5 + (2 * degree) + 1;
    }

    // returns next available node pointer. Used in split method in BTreeNode
    public int getNextPointer() {
        try {
            fileScan = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
        int lineCount = 0;
        while (fileScan.hasNextLine()) {
            fileScan.nextLine();
            lineCount++;
        }
        fileScan.close();
        return lineCount;
    }

    public BTreeNode getNode(int pointer) {
        // read file into a string
        String node = "";
        try {
            fileScan = new Scanner(file);
            for (int i = 0; i < pointer; i++) {
                if (fileScan.hasNextLine()) {
                    fileScan.nextLine();
                } else {
                    IndexOutOfBoundsException e = new IndexOutOfBoundsException("Specified node does not exist in file.");
                    e.printStackTrace();
                    System.exit(1);
                }
            }
            for (int i = 0; i < nodeDiskSize; i++) {
                if (fileScan.hasNextLine()) {
                    node += fileScan.nextLine() + "\n";
                } else {
                    IndexOutOfBoundsException e = new IndexOutOfBoundsException("Specified node does not exist in file.");
                    e.printStackTrace();
                    System.exit(1);
                }
            }
            fileScan.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }

        fileScan = new Scanner(node);
        // read string into variables
        int selfPointer = fileScan.nextInt();
        int leaf = fileScan.nextInt();
        boolean isLeaf;
        if (leaf == 1)
            isLeaf = true;
        else
            isLeaf = false;
        int parentPointer = fileScan.nextInt();
        fileScan.nextLine();
        fileScan.nextLine();
        int[] childPointers = new int[degree + 1];
        for (int i = 0; i < degree + 1; i++) {
            childPointers[i] = fileScan.nextInt();
        }
        long[] values = new long[degree];
        int[] frequency = new int[degree];
        for (int i = 0; i < degree; i++) {
            frequency[i] = fileScan.nextInt();
            values[i] = Long.parseLong(fileScan.next(), 2);
        }

        fileScan.close();
        return new BTreeNode(file, k, degree, selfPointer, parentPointer, values, frequency, childPointers);
    }
}