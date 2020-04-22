public class Testing {
    public static void main(String[] args) {
        int[] childPointers = { 1, 2, 3, 4, 5, 6 };
        Long[] values = { 001100L, 101001L, 000001L, -1L, 000000L };
        int[] frequency = { 2, 4, 1, 20, 5 };
        BTreeNode node = new BTreeNode(3, 5);
        System.out.println(node.toString() + "\nEOF\n\n\n\n");
    }
}