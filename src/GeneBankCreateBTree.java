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
    String btreeFile="";
    Scanner gbkreader;
    File outFile;

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
        readGBK();
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
        // initialize degree,k, and root vars
        degree = Integer.parseInt(args[1]);
        k = Integer.parseInt(args[3]);
        root=null;
        // initialize gck file
        gbkfile = new File(args[2]);
        if (!gbkfile.exists()) {
            FileNotFoundException e = new FileNotFoundException("Given gbk file does not exist.");
            e.printStackTrace();
            System.exit(1);
        }
        btreeFile = args[2] + ".btree.data." + k + "." +degree;
        outFile = new File(btreeFile);
        root = new BTreeNode(outFile, k, degree);
        PrintWrapper.createFile(k, degree, root, outFile);
    }

    public void readGBK() throws FileNotFoundException{
        String holdsValue = "";
        gbkreader = new Scanner(gbkfile);
        String gbkLine = gbkreader.nextLine();
        boolean start=false;
        while(!start){
            gbkLine = gbkreader.nextLine();
            if(gbkLine.trim()=="ORIGIN"){
                start=true;
            }
        }

        while(gbkreader.hasNextLine()&&gbkLine.trim()=="//"){
            Scanner linereader = new Scanner(gbkLine);

            while(linereader.hasNext()){
                String next = linereader.next();
                if(!next.matches("-?(0|[1-9]\\d*)")){
                    holdsValue+=next.trim().toUpperCase();
                }
            }         

            //this should run through the holdsValue until there are not enough locations
        //this will be moved up
        while(holdsValue.length()>=k){
            //this will create new nodes or populate old
            String valToNode="";
            for(int i=0;i<k;i++){
                if(holdsValue.charAt(0)=='N'){
                    i--;
                }else{
                    valToNode+=holdsValue.charAt(0);
                }
                holdsValue = holdsValue.substring(1);
            }
            int addtype = root.add(Parser.dnaToDecimal(valToNode));
            while(addtype!=0){
                if(addtype==-1){
                    //split
                    //create BTree[]
                    //write the parent to current parent location
                    //wirte the left child to the next availible possition
                    //write the right child to the next availible possition
                    //write to disk all three nodes TODO CACHE
                    //then add again
                }else{
                    // return the locaiton of the child to check next
                }
            }
            //write to disk TODO CACHE
            //write to the last returned location
            


        }
            linereader.close();
            gbkLine=gbkreader.nextLine();
        }
        

    }

    public static void printUsage() {
        System.out.println(
                "Usage:\njava GeneBankCreateBTree <0/1(no/with Cache)> <degree> <gbk file> <sequence length> [<cache size>] [<debug level>]");
    }
}