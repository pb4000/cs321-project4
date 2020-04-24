public class Testing {
    public static void main(String[] args) {
        BTreeNode node = new BTreeNode(3, 4);
        node.setSelfPointer(10);
        node.add(Parser.dnaToDecimal("ACG"));
        System.out.println(node.toString());
        node.add(Parser.dnaToDecimal("ATG"));
        System.out.println(node.toString());
        node.add(Parser.dnaToDecimal("ATG"));
        System.out.println(node.toString());
        node.add(Parser.dnaToDecimal("GGC"));
        System.out.println(node.toString());
        node.add(Parser.dnaToDecimal("ATG"));
        System.out.println(node.toString());
        node.add(Parser.dnaToDecimal("CGC"));
        System.out.println(node.toString());
        System.out.println(node.split()[1].toString());
        System.out.println(node.toString());
        System.out.println(node.getSelfPointer());
    }
}