public class Testing {
    public static void main(String[] args) {
        BTreeNode node = new BTreeNode(3, 4);
        node.setSelfPointer(10);
        node.add(Parser.dnaToDecimal("ACG"));
        System.out.println(node.toString());
        System.out.println(node.getTotalChildren());
        node.add(Parser.dnaToDecimal("ATG"));
        System.out.println(node.toString());
    }
}