import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ScannerWrapper {
int k;
int degree;
Scanner fileScan;
File file;
int nodeDiskSize;
Scanner gbkScanner;

    public ScannerWrapper(File f) {
        try {
            if (!f.exists()) {
                throw new FileNotFoundException("Given file does not exist");
            }
            gbkScanner = new Scanner(f);
            file = f;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

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

    public void close() {
        gbkScanner.close();
    }

    public static int[] getMetadata(File f) {
        int[] output = new int[4];
        try {
            Scanner scan = new Scanner(f);
            for (int i = 0; i < 4; i++) {
                output[i] = scan.nextInt();
            }
            scan.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return output;
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
        return ++lineCount;
    }

    public BTreeNode getNode(int pointer) {
        // read file into a string
        String node = "";
        try {
            fileScan = new Scanner(file);
            for (int i = 1; i < pointer; i++) {
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
        fileScan.nextInt();
        fileScan.nextInt();
        int[] childPointers = new int[degree + 1];
        for (int i = 0; i < degree + 1; i++) {
            childPointers[i] = fileScan.nextInt();
        }
        long[] values = new long[degree];
        int[] frequency = new int[degree];
        String temp;
        /**
         * Added special case for -1
         */
        for (int i = 0; i < degree; i++) {
            frequency[i] = fileScan.nextInt();
            temp = fileScan.next();
            if (Long.parseLong(temp) == -1)
                values[i] = -1;
            else
                values[i] = Long.parseLong(temp, 2);
        }

        fileScan.close();
        return new BTreeNode(file, k, degree, selfPointer, parentPointer, values, frequency, childPointers);
    }
}