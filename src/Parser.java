import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Parser {
    BTreeNode val;
    int degree=0;
    int k=1;
    File filename;
    public Parser(String file,int givenk,int givendegree){
        //why are we passing in a string to the parser constructor?
        //shouldnt this be taking in just the file name, the degree, and the K value?
        filename=new File(file);
    }

        // instantiates a ScannerWrapper and reads file into a BTreeNode object
        public BTreeNode fileToNode(int lineNumber) throws FileNotFoundException{
            ScannerWrapper X = new ScannerWrapper(filename,degree,k);
            return X.getNode(lineNumber);
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
