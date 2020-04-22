
public class BTreeNode implements Comparable<BTreeNode> {
    private int[] childPointers;
    // store values as numbers converted from binary
    // to convert back, convert value to binary, then fill in preceeding 0s based on k
    Long[] values;
    private int selfPointer;
    private int parentPointer;

    public BTreeNode(int k, int selfPointer, int parentPointer) {
        childPointers = new int[k];
        values = new Long[k];
        this.selfPointer = selfPointer;
        this.parentPointer = parentPointer;
        
    }

    public int compareTo(BTreeNode n) {
        return -1;
    }

    public Long[] getValues() {
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
            // what if stored value is 000000? use size k to infer values stored, but how to discern empty vs 000000?
            // default array filled with -1?
            if (values[i] != 0L)
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

    private String valueToString(Long input) {
        String output = "";
        String binary = Long.toBinaryString(input);
        for (int i = binary.length(); i < values.length; i++)
            output += "0";
        return output + binary;
    }
}