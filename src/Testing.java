import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class Testing {
    public static void main(String[] args) throws IOException, InterruptedException {
        // RandomAccessFile f = new RandomAccessFile(new File("haha.txt"), "rw");
        // f.readLine();
        // //f.write("00004        1000101".getBytes());
        // f.close();
        // Scanner input = new Scanner(new File("haha.txt"));
        // input.nextLine();
        // System.out.println(input.nextInt());
        // System.out.println(input.nextLong());
        // input.close();

        BTreeNode node = new BTreeNode(6, 3, 13, -1);
        node.add(17);
        node.add(3);
        node.add(17);
        node.add(24);
        PrintWrapper.writeNode(node, "haha.txt");
        node.add(17);
        PrintWrapper.writeNode(node, "haha.txt");
    }
}