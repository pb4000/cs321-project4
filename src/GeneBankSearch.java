public class GeneBankSearch {
    String[] args;
    boolean usingCache;
    Cache cache;
    
    public static void main(String[] args) {
        GeneBankSearch g = new GeneBankSearch(args);
        g.run();
    }

    public GeneBankSearch(String[] args) {
        this.args = args;
    }

    public void run() {
        parseArgs();
    }

    public void parseArgs() {

    }
}