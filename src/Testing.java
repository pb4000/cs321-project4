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
        // PrintWrapper.createFile(6, 7, new BTreeNode(new File("lol"), 6, 7, 5, -1), new File("lol"));
        // ScannerWrapper scan = new ScannerWrapper(new File("lol"), 7, 6);
        // System.out.println(scan.getNextPointer());

        System.out.println(Long.toBinaryString(-1));

        RandomAccessFile f = new RandomAccessFile(new File("jajaja"), "rw");
        BTreeNode node = new BTreeNode(new File("lol"), 6, 7, 5, -1);
        System.out.println(node.toString());
        f.write(node.toString().getBytes());
        node.add(10);
        System.out.println(node.toString());
        f.close();
        f = new RandomAccessFile(new File("jajaja"), "rw");
        f.write(node.toString().getBytes());
        f.close();
    }
}