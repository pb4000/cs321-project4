import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GeneBankCreateBTree {
    String[] args;
    boolean usingCache;
    CacheDriver<BTreeNode> cache;
    File btreeFile;
    File queryFile;
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
        initVars();
    }


    public void initVars() {
        // get metadata for the overall tree
        int[] meta = ScannerWrapper.getMetadata(btreeFile);
        k = meta[0];
        degree = meta[1];
        // init root node
        scan = new ScannerWrapper(btreeFile, degree, k);
        root = scan.getNode(meta[2]);
        if (usingCache) {
            cache.add(root);
        }
    }

    public void parseArgs() {
        // check for correct length
        if (args.length < 3 || args.length > 5) {
            printUsage();
            System.exit(1);
        }
        // with/without cache
        if (args[0].equals("1")) {
            // if cache size is not given, end
            if (args.length == 3) {
                printUsage();
                System.exit(1);
            }
            usingCache = true;
            cache = new CacheDriver<BTreeNode>(1, Integer.parseInt(args[3]));
            // set debug level if it exists
            if (args.length == 5) {
                debugLevel = Integer.parseInt(args[4]);
            }
        } else {
            // set debug level if it exists
            if (args.length == 4) {
                debugLevel = Integer.parseInt(args[3]);
            }
        }
        // initialize BTree file
        btreeFile = new File(args[1]);
        if (!btreeFile.exists()) {
            FileNotFoundException e = new FileNotFoundException("Given BTree file does not exist.");
            e.printStackTrace();
            System.exit(1);
        }
        // initialize query file
        queryFile = new File(args[2]);
        if (!queryFile.exists()) {
            FileNotFoundException e = new FileNotFoundException("Given query file does not exist.");
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void printUsage() {
        System.out.println(
                "Usage:\njava GeneBankSearch <0/1(no/with Cache)> <btree file> <query file> [<cache size>] [<debug level>]");
    }
}