import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ScannerWrapper {
int k;
int degree;
Scanner filescan;

    public ScannerWrapper(File f, int d, int givenk)throws FileNotFoundException{
        filescan = new Scanner(f);
        degree=d;
        k=givenk;
    }
    // returns next available node pointer. Used in split method in BTreeNode
    public static int getNextPointer() {
        //this should just take in all of the values of a stored node from the file and move into a usable BTreeNode
        return -1;
    }

    // returns the given node from the file
    public static BTreeNode getNode(int lineNumber) {
        return null;
    }

    public BTreeNode readNode(){
        int selfPointer = filescan.nextInt();
        int isLeaf = filescan.nextInt();
        int parentPointer = filescan.nextInt();
        int[] childPointers  = new int[degree+1];
        int[] childValues = new int[degree+1];

        for(int i=0;i<degree+1;i++){

        }

        for(int i=0;i<degree+1;i++){

        }

        return null;
    }
}