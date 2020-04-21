
public class BTreeNode implements Comparable<BTreeNode> {
    private int[] childPointers;
    String[] values;
    private int selfPointer;
    private int parentPointer;

    public BTreeNode(int k, int selfPointer, int parentPointer) {
        childPointers = new int[k];
        values = new String[k];
        this.selfPointer = selfPointer;
        this.parentPointer = parentPointer;
        
    }

    public int compareTo(BTreeNode n) {
        return -1;
    }

    public String[] getValues() {
        return values;
    }

    public int[] getChildren() {
        return childPointers;
    }

    public int getSelfPointer() {
        return selfPointer;
    }

    public int getTotalObjects() {
        int totalObjects = 0;
        for (int i = 0; i < values.length; i++) {
            if (!values[i].equals(""))
                totalObjects++;
        }
        return totalObjects;
    }

    public int getTotalChildren() {
        int totalChildren = 0;
        for (int i = 0; i < childPointers.length; i++) {
            if (childPointers[i] != 0)
                totalChildren++;
        }
        return totalChildren;
    }

    @Override
    public String toString() {
        String output = "";
        output += selfPointer + "\n";
        if (getTotalObjects() != 0)
            output += "0\n";
        else
            output += "1\n";
        output += parentPointer + "\n";
        output += getTotalObjects() + "\n";
        return output;
    }
}