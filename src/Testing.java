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
        RandomAccessFile f = new RandomAccessFile(new File("18k.txt"), "rw");
        long start = System.currentTimeMillis();
        for (int i = 0; i < 18800; i++) {
            f.write(("100010111010  01010101  010110101\n").getBytes());
        }
        f.close();
        System.out.println(System.currentTimeMillis() - start);
    }
}