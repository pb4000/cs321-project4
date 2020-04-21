import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Test {
    private CacheDriver<String> c;
    private Scanner s;

    public static void main(String[] args) {
        Test t = new Test();
        t.start(args);
    }

    public Test() {

    }

    public void start(String[] args) {
        init(args);
        printCache();
        cycle();
        hitRatios();
    }

    public void printCache() {
        System.out.println("First level cache with " + c.getCache1().maxSize + " entries has been created");
        if (c.getLevel() == 2) {
            System.out.println("Second level cache with " + c.getCache2().maxSize + " entries has been created");
        }
        System.out.println("*******************************************");
    }

    public void hitRatios() {
        /**
         * Global
         */
        if (c.getLevel() == 2) {
            int globalRefs = c.getC1Refs() + c.getC2Refs();
            int globalHits = c.getC1Hits() + c.getC2Hits();
            double globalRatio = (double) globalHits / c.getC1Refs();
            System.out.println("The number of global references: " + c.getC1Refs());
            System.out.println("The number of global cache hits: " + globalHits);
            System.out.println("The global hit ratio: " + globalRatio + "\n");
        }
        /**
         * Cache 1
         */
        double c1Ratio = (double) c.getC1Hits() / c.getC1Refs();
        System.out.println("The number of 1st-level references: " + c.getC1Refs());
        System.out.println("The number of 1st-level cache hits: " + c.getC1Hits());
        System.out.println("The 1st-level cache hit ratio: " + c1Ratio + "\n");
        /**
         * Cache 2
         */
        if (c.getLevel() == 2) {
            double c2Ratio = (double) c.getC2Hits() / c.getC2Refs();
            System.out.println("The number of 2nd-level references: " + c.getC2Refs());
            System.out.println("The number of 2nd-level cache hits: " + c.getC2Hits());
            System.out.println("The 2nd-level cache hit ratio: " + c2Ratio + "\n");
        }
    }

    public void init(String[] args) {
        /**
         * Initializes CacheDriver with args
         */
        if (args.length == 3) {
            try {
                c = new CacheDriver(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
            } catch (IllegalArgumentException e) {
                printUsage();
                System.exit(1);
            }
        } else if (args.length == 4) {
            try {
                c = new CacheDriver(Integer.parseInt(args[0]), Integer.parseInt(args[1]),
                        Integer.parseInt(args[2]));
            } catch (IllegalArgumentException e) {
                printUsage();
                System.exit(1);
            }
        } else {
            printUsage();
            System.exit(1);
        }
        /**
         * Get input file
         */
        try {
            s = new Scanner(new File(args[args.length - 1]));
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            printUsage();
            System.exit(1);
        }
    }

    public void cycle() {
        String temp;
        while (s.hasNext()) {
            temp = s.next();
            c.search(temp);
        }
    }

    /**public void printCache() {
        System.out.println("\nCache 1:\n" + c.getCache1().toString() +
                "\nWith " + c.getC1Hits() + " hits.");
        if (c.getLevel() == 2) {
            System.out.println("\nCache 2:\n" + c.getCache2().toString() +
                    "\nWith " + c.getC2Hits() + " hits.");
        }
    }
     */

    private static void printUsage() {
        System.out.println("How to use this program:\n" +
                "java <path-to-Test> <1/2-cache-levels> <cache1-max-length>" +
                " <cache2-max-length-(optional)> <path-to-file>");
    }
}
