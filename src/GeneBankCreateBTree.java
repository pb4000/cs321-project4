import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GeneBankCreateBTree {
    String[] args;
    boolean usingCache;
    CacheDriver<BTreeNode> cache;
    File gbkfile;
    int debugLevel;
    BTreeNode root;
    ScannerWrapper scan;
    int k;
    int degree;
    Scanner queryScan;

    public static void main(String[] args) {
        GeneBankCreateBTree g = new GeneBankCreateBTree(args);
        g.run();
    }

    public GeneBankCreateBTree(String[] args) {
        this.args = args;
        debugLevel = 0;
    }

    public void run() {
        parseArgs();
    }

    public void parseArgs() {
        // check for correct length
        if (args.length < 4 || args.length > 6) {
            printUsage();
            System.exit(1);
        }
        // with/without cache
        if (args[0].equals("1")) {
            // if cache size is not given, end
            if (args.length == 4) {
                printUsage();
                System.exit(1);
            }
            usingCache = true;
            cache = new CacheDriver<BTreeNode>(1, Integer.parseInt(args[3]));
            // set debug level if it exists
            if (args.length == 6) {
                debugLevel = Integer.parseInt(args[5]);
            }
        } else {
            // set debug level if it exists
            if (args.length == 5) {
                debugLevel = Integer.parseInt(args[4]);
            }
        }
        // initialize BTree file
        degree = Integer.parseInt(args[1]);
        k = Integer.parseInt(args[3]);
        root=null;
        // initialize query file
        gbkfile = new File(args[2]);
        if (!gbkfile.exists()) {
            FileNotFoundException e = new FileNotFoundException("Given gbk file does not exist.");
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void startPrint(){

    }

    public void readGBK(){

    }

    public static void printUsage() {
        System.out.println(
                "Usage:\njava GeneBankCreateBTree <0/1(no/with Cache)> <degree> <gbk file> <sequence length> [<cache size>] [<debug level>]");
    }
}