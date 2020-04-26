/**
 * Organize values and add methods within BTreeNode? Or create BTree driver
 * class to create and manage nodes? Probably within BTreeNode. Consult Wesley.
 * 
 * NOTE: In all cases, a value of -1 should be treated as EMPTY or NULL.
 * 
 * Any time there is a value inserted into or removed from a BTreeNode, the
 * array will be reordered and sorted.
 * 
 * Add a split method which splits arrays in half when the node is full and
 * returns 2 new nodes.
 */
public class BTreeNode implements Comparable<BTreeNode> {
    private int[] childPointers;
    // store values as numbers converted from binary
    // to convert back, convert value to binary, then fill in preceeding 0s based on k
    long[] values;
    int[] frequency;
    private int selfPointer;
    private int parentPointer;
    private int k;
    private int degree;
    // total number of lines each node takes up on the disk
    // 5 lines of metadata, degree + 1 lines of child pointers, degree lines of values stored
    private int nodeDiskSize;

    public BTreeNode(int k, int degree, int selfPointer, int parentPointer) {
        this.k = k;
        this.degree = degree;
        this.selfPointer = selfPointer;
        this.parentPointer = parentPointer;
        childPointers = new int[degree + 1];
        values = new long[degree];
        frequency = new int[degree];
        initEmptyValues(childPointers);
        initEmptyValues(values);
        initEmptyValues(frequency);
        nodeDiskSize = 5 + (2 * degree) + 1;
    }

    public BTreeNode(int k, int degree, int selfPointer, int parentPointer, long[] values, int[] frequency, int[] childPointers) {
        this.k = k;
        this.degree = degree;
        this.selfPointer = selfPointer;
        this.parentPointer = parentPointer;
        this.childPointers = childPointers;
        this.values = values;
        this.frequency = frequency;
        nodeDiskSize = 5 + (2 * degree) + 1;
    }

    public BTreeNode(int k, int degree) {
        this.k = k;
        this.degree = degree;
        selfPointer = parentPointer = -1;
        childPointers = new int[degree + 1];
        values = new long[degree];
        frequency = new int[degree];
        initEmptyValues(childPointers);
        initEmptyValues(values);
        initEmptyValues(frequency);
        nodeDiskSize = 5 + (2 * degree) + 1;
    }

    // if array is full, returns false
    // inserts a new value at the end of each array
    // sorts the arrays
    
    // add option to search for where added value belongs
    // i.e. return child pointer of where to add, or placxes value in array
    public boolean add(long newValue) {
        for (int i = 0; i < degree; i++) {
            if (values[i] == newValue) {
                frequency[i]++;
                return true;
            }
        }
        if (isFull())
            return false;
        values[values.length - 1] = newValue;
        frequency[frequency.length - 1] = 1;
        sort();
        return true;
    }

    // returns true if the node is full
    public boolean isFull() {
        return getTotalObjects() == degree;
    }

    /**
     * If node is not full, return null.
     * If degree is 1, return null.
     * If degree is 2, keep lesser value in this node and return a right child node with the greater value.
     * Moves middle and right children to new child node. 
     * If degree >= 3
     * Split values in half, keeping one for this node 
     * Make 2 new child nodes with the other values
     * Return the child nodes in an array 
     */
    public BTreeNode[] split() {
        BTreeNode[] arrayOut;
        if (!isFull() || degree == 1)
            return null;
        if (degree == 2) {
            // 0. initialize variables
            long[] childValues = new long[degree];
            int[] childFrequency = new int[degree];
            int[] childChildPointers = new int[degree + 1];
            initEmptyValues(childValues);
            initEmptyValues(childFrequency);
            initEmptyValues(childChildPointers);
            arrayOut = new BTreeNode[1];
            // 1. assign values and frequency
            childValues[0] = values[1];
            values[1] = -1L;
            childFrequency[0] = frequency[1];
            frequency[1] = -1;
            // 2. assign child pointers
            childChildPointers[0] = childPointers[1];
            childChildPointers[1] = childPointers[2];
            childPointers[1] = ScannerWrapper.getNextPointer();
            childPointers[2] = -1;
            // 3. instantiate BTreeNode
            arrayOut[0] = new BTreeNode(k, degree, childPointers[1], selfPointer, childValues, childFrequency, childChildPointers);
        } else {
            // 0. initialize variables
            long[] leftValues = new long[degree];
            long[] rightValues = new long[degree];
            int[] leftFrequency = new int[degree];
            int[] rightFrequency = new int[degree];
            int[] leftChildPointers = new int[degree + 1];
            int[] rightChildPointers = new int[degree + 1];
            initEmptyValues(leftValues);
            initEmptyValues(rightValues);
            initEmptyValues(leftFrequency);
            initEmptyValues(rightFrequency);
            initEmptyValues(leftChildPointers);
            initEmptyValues(rightChildPointers);
            arrayOut = new BTreeNode[2];
            // 1. assign values and frequency, as well as child pointers if possible
            int middle = values.length / 2;
            for (int i = 0; i < degree; i++) {
                if (i < middle) {
                    leftValues[i] = values[i];
                    values[i] = -1L;
                    leftFrequency[i] = frequency[i];
                    frequency[i] = -1;
                    if (childPointers[i] != -1) {
                        leftChildPointers[i] = childPointers[i];
                        childPointers[i] = -1;
                    }
                } else if (i > middle) {
                    rightValues[i - middle - 1] = values[i];
                    values[i] = -1L;
                    rightFrequency[i - middle - 1] = frequency[i];
                    frequency[i] = -1;
                    if (childPointers[i] != -1) {
                        rightChildPointers[i - middle - 1] = childPointers[i];
                        childPointers[i] = -1;
                    }
                } else if (i == middle && childPointers[i] != -1) {
                    // if the middle childPointer exists, that means the subtree is less than the mdiddle value
                    // therefore, must be assign to left (smaller) subtree
                    leftChildPointers[i] = childPointers[i];
                    childPointers[i] = -1;
                }
            }
            // move last value to beginning
            values[0] = values[middle];
            values[middle] = -1L;
            frequency[0] = frequency[middle];
            frequency[middle] = -1;
            // assign next childPointer
            childPointers[0] = ScannerWrapper.getNextPointer();
            childPointers[1] = childPointers[0] + nodeDiskSize;
            // 2. instantiate BTreeNodes
            arrayOut[0] = new BTreeNode(k, degree, childPointers[0], selfPointer, leftValues, leftFrequency, leftChildPointers);
            arrayOut[1] = new BTreeNode(k, degree, childPointers[1], selfPointer, rightValues, rightFrequency, rightChildPointers);
        }
        return arrayOut;
    }

    public int compareTo(BTreeNode n) {
        if (values[0] - n.getValues()[0] < 0) {
            return 1;
        } else if (values[0] - n.getValues()[0] > 0) {
            return -1;
        } else {
            return 0;
        }
    }

    public long[] getValues() {
        return values;
    }

    public int[] getChildren() {
        return childPointers;
    }

    public int getSelfPointer() {
        return selfPointer;
    }

    public void setSelfPointer(int selfPointer) {
        this.selfPointer = selfPointer;
    }

    public int getParentpointer() {
        return parentPointer;
    }

    public void setParentPointer(int parentPointer) {
        this.parentPointer = parentPointer;
    }

    public int[] getFrequency() {
        return frequency;
    }

    public int getTotalObjects() {
        int totalObjects = 0;
        for (int i = 0; i < degree; i++) {
            // what if stored value is 000000? use size k to infer values stored, but how to discern empty vs 000000?
            // default array filled with -1?
            if (values[i] != -1L)
                totalObjects++;
        }
        return totalObjects;
    }

    public int getTotalChildren() {
        int totalChildren = 0;
        for (int i = 0; i < childPointers.length; i++) {
            if (childPointers[i] != -1)
                totalChildren++;
        }
        return totalChildren;
    }

    private String add10Spaces(int input) {
        String output = "";
        for (int i = 0; i + Integer.toString(input).length() < 10; i++) {
            output += " ";
        }
        return output + input;
    }

    private String add62Spaces(String input) {
        String output = "";
        for (int i = 0; i + input.length() < 62; i++) {
            output += " ";
        }
        return output + input;
    }

    @Override
    public String toString() {
        String output = "";
        output += add10Spaces(selfPointer) + "\n";
        if (getTotalChildren() == 0)
            output += "1\n";
        else
            output += "0\n";
        output += add10Spaces(parentPointer) + "\n";
        output += add10Spaces(getTotalObjects()) + "\n";
        output += add10Spaces(getTotalChildren()) + "\n";
        for (int i = 0; i < degree + 1; i++) {
            // if (childPointers[i] != -1) {
            //     output += addZeros(childPointers[i]) + "\n";
            // } else {
            //     output += addZeros(-1) + "\n";
            // }
            output += add10Spaces(childPointers[i]) + "\n";
        }
        for (int i = 0; i < degree; i++) {
            // if (values[i] != -1L) {
            //     output += addZeros(frequency[i]) + " " + addSpaces(valueToString(values[i])) + "\n";
            // } else {
            //     output += addZeros(-1) + " " + addSpaces("-1") + "\n";
            // }
            output += add10Spaces(frequency[i]) + " " + add62Spaces(valueToString(values[i])) + "\n";
        }
        return output;
    }

    private String valueToString(long input) {
        String output = "";
        String binary = Long.toBinaryString(input);
        for (int i = binary.length(); i < k * 2; i++)
            output += "0";
        return output + binary;
    }

    // initializes given int array to -1
    private static void initEmptyValues(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = -1;
        }
    }

    // initializes given int array to -1
    private static void initEmptyValues(long[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = -1L;
        }
    }

    // sort values and frequency based on ordering of values
    // afterwards, sort childPointers based on ordered values
    private void sort() {
        long templong = values[degree - 1];
        int tempint = frequency[degree - 1];
        for (int i = 0; i < degree; i++) {
            if (values[i] == -1L) {
                values[i] = templong;
                frequency[i] = tempint;
                values[degree - 1] = -1L;
                frequency[degree - 1] = -1;
                return;
            }
            if (values[i] > templong) {
                shift(i);
                values[i] = templong;
                frequency[i] = tempint;
                return;
            }
        }
        sortChildren();
    }

    private void sortChildren() {
        int[] temp = childPointers; // used to store pointers and iterate through them
        BTreeNode tempNode;
        for (int i = 0; i < degree + 1; i++) {
            childPointers[i] = -1;
        }
        for (int i = 0; i < degree + 1; i++) {  // iterate through childPointers
            if (temp[i] != -1) {   // if a pointer is stored here
                tempNode = ScannerWrapper.getNode(temp[i]);
                if (i == degree) {  // if we are at the end
                    if (tempNode.getValues()[0] < values[i - 1]) {  // if the last child pointer does not belong there
                        for (int k = 0; k < degree + 1; k++) {  // iterate until you find where it belongs
                            if (k == degree - 1) {
                                childPointers[degree] = tempNode.getSelfPointer();
                                break;
                            } else if (k == 0) {
                                if (tempNode.getValues()[0] < values[k]) {
                                    childPointers[k] = tempNode.getSelfPointer();
                                    break;
                                }
                            } else if (tempNode.getValues()[0] < values[k] && tempNode.getValues()[0] > values[k - 1]) {
                                childPointers[k] = tempNode.getSelfPointer();
                                break;
                            }
                        }
                        childPointers[i] = -1;  // reset the value of that child pointer to empty

                    }
                } else if (tempNode.getValues()[0] > values[i] || tempNode.getValues()[0] < values[i - 1]) {  // if that pointer does not belong there
                    for (int k = 0; k < degree + 1; k++) {  // iterate until you find where it belongs
                        if (k == degree - 1) {
                            childPointers[degree] = tempNode.getSelfPointer();
                            break;
                        } else if (k == 0) {
                            if (tempNode.getValues()[0] < values[k]) {
                                childPointers[k] = tempNode.getSelfPointer();
                                break;
                            }
                        } else if (tempNode.getValues()[0] < values[k] && tempNode.getValues()[0] > values[k - 1]) {
                            childPointers[k] = tempNode.getSelfPointer();
                            break;
                        }
                    }
                    childPointers[i] = -1;  // reset the value of that child pointer to empty
                }
            }
        }
    }

    // shifts values and frequency to make space for new entries
    private void shift(int position) {
        for (int i = degree - 2; i >= position; i--) {
            values[i + 1] = values[i];
            frequency[i + 1] = frequency[i];
        }
    }
}