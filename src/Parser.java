public class Parser {

    public Parser(String X){
        //changechange
    }

        // instantiates a ScannerWrapper and reads file into a BTreeNode object
        public static BTreeNode fileToNode(int lineNumber){

        }
    
        // calls BTreeNode.toString
        public static String nodeToFile(BTreeNode node){

        }
    
        // converts binary long to String of DNA
        public static String decimalToDNA(long bin,int k){
            String binary = Long.toBinaryString(bin);
            String retVal="";
            String holder="";

            for(int i=0;i<k-binary.length();i++){
                retVal= "0"+retVal;
            }
            
            for(int i=0;i<k;){
                holder+= binary.charAt(i) +binary.charAt(i+1);

                if(holder=="00"){
                    retVal+="A";
                }else if(holder=="01"){
                    retVal+="C";
                }else if(holder=="10"){
                    retVal+="G";
                }else if(holder=="11"){
                    retVal+="T";
                }
                i+=2;
            }
            return retVal;
        }
    
        // converts String DNA to a binary long
        public static long dnaToDecimal(String dna){
            String bin = "";
            dna = dna.toUpperCase();
            for(int i=0;i<dna.length();i++){
                if(dna.charAt(i)=='A'){
                    bin+="00";
                } else if(dna.charAt(i)=='T'){
                    bin+="11";
                } else if(dna.charAt(i)=='C'){
                    bin+="01";
                } else if(dna.charAt(i)=='G'){
                    bin+="10";
                } else {
                    return -1L;
                }
            }
            return Long.parseLong(bin, 2);
        }
}
