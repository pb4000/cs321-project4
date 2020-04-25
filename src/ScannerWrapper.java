import java.io.File;

public class ScannerWrapper {
int k;
int degree;
Scanner filescan= new Scanner();

    public ScannerWrapper(int newk, int newdegree, File f){
        k=newk;
        degree=newdegree;
        filescan = new Scanner(f);
    }
    //should create a scanner to be used to read through the file
    // returns next available node pointer. Used in split method in BTreeNode
    public static int getNextPointer() {
        //this should just take in all of the values of a stored node from the file and move into a usable BTreeNode
        return -1;
    }

    // returns the given node from the file
    public static BTreeNode getNode(int lineNumber) {
        return null;
    }
}