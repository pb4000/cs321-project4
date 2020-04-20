// driver class
public class GeneBankCreateBTree {

}

// wrapper for Scanner 
public class ScannerWrapper {
	// checks cache for node with given line number first
	// if not there, then read from file
	public BTreeNode getNode(int lineNumber) 

	// checks if node is full
	public boolean isFull(int lineNumber)

	// returns the next line
	public String nextLine()
}

// wrapper for printWriter
public class PrintWrapper {
	// splits the node given by the line number
	public void split(int lineNumber)

	// adds a new node to the end of the file
	public void addNode(BTreeNode node)
}

// parses file to node and vice versa
public class Parser {
	// instantiates a ScannerWrapper and reads file into a BTreeNode object
	public BTreeNode fileToNode(int lineNumber)

	// calls BTreeNode.toString
	public String nodeToFile(BTreeNode node)
}

// btree node object
public class BTreeNode {
	int[] childPointers;	// stores line # of children
	int self pointer;	// pointer to self line number 
	Long[] values;	// stored DNA values in long form

	// converts self to a String suitable for writing into the file
	public String toString()
}

// cache class to store nodes
public class Cache {
	// already done
}