import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class HuffmanConverter
{

	// The # of chars in the ASCII table dictates
	// the size of the count[] & code[] arrays.
	public static final int NUMBER_OF_CHARACTERS = 256;

	// the contents of our message...
	private String contents;

	// the tree created from the msg
	private HuffmanTree huffmanTree;

	// tracks how often each character occurs
	private int count[];

	// the huffman code for each character
	private String code[];

	// stores the # of unique chars in contents
	private int uniqueChars = 0; //(optional)

	/** Constructor taking input String to be converted */
	public HuffmanConverter(String input) {
		this.contents = input;
		this.count = new int[NUMBER_OF_CHARACTERS];
		this.code = new String[NUMBER_OF_CHARACTERS];
	}

	/**
	 * Records the frequencies that each character of our
	* message occurs...
	* I.e., we use 'contents' to fill up the count[] list...
	*/
	public void recordFrequencies() {
		for(int i= 0; i < contents.length(); i++) {
			char c = contents.charAt(i);
			int ascii = (int) c;
			count[ascii]++;
		}	
	}
	
	/**
	* Converts our frequency list into a Huffman Tree. We do this by
	* taking our count[] list of frequencies, and creating a binary
	* heap in a manner similar to how a heap was made in HuffmanTree's
	* fileToHeap method. Then, we print the heap, and make a call to
	* HuffmanTree.heapToTree() method to get our much desired
	* HuffmanTree object, which we store as huffmanTree.
	*/
	public void frequenciesToTree() {
	
		// break file into ArrayList by letter
		ArrayList fileArrayList = new ArrayList();
		for(int i = 0; i < count.length; i++) {
			int freq = count[i];
			char letter = (char) i;
			if(freq>0) {
				String str = letter+"_"+freq;
				fileArrayList.add(str);
			}
		}
		
		// break ArrayList into Array by ;etter
		String[] fileArray = new String[fileArrayList.size()];
		for(int i = 0; i < fileArrayList.size(); i++) {
			fileArray[i] = (String) fileArrayList.get(i);
		}
		
		// create BinaryHeap and then HuffmanTree
		BinaryHeap<String> bheap = new BinaryHeap<String>(fileArray);
		this.huffmanTree = HuffmanTree.createFromHeap(bheap);
	}
	
	/**
	* Iterates over the huffmanTree to get the code for each letter.
	* The code for letter i gets stored as code[i]... This method
	* behaves similarly to HuffmanTree's printLegend() method...
	* Warning: Don't forget to initialize each code[i] to ""
	* BEFORE calling the recursive version of treeToCode...
	*/
	public void treeToCode() {
		treeToCode(huffmanTree.root, "");
	}
	
	
		/**
		* A private method to iterate over a HuffmanNode t using s, which
		* contains what we know of the HuffmanCode up to node t. This is
		* called by treeToCode(), and resembles the recursive printLegend
		* method in the HuffmanTree class. Note that when t is a leaf node,
		* t's letter tells us which index i to access in code[], and tells
		* us what to set code[i] to...
		*/
		private void treeToCode(HuffmanNode t, String s) {
			if(t.letter.length() > 1) {
				treeToCode(t.left, s + "0");
				treeToCode(t.right, s + "1");
			} else {
				code[(int) t.letter.charAt(0)]= s;
				System.out.println("'"+t.letter+"'="+s);
			}	
		}
	
	/**
	* Using the message stored in contents, and the huffman conversions
	* stored in code[], we create the Huffman encoding for our message
	* (a String of 0's and 1's), and return it...
	*/
	public String encodeMessage() {
		String output = "";
		for(int i = 0; i < contents.length(); i++) {
			char c = contents.charAt(i);
			output = output + code[(int) c];
		}
		return output;
	}

	/**
	* Reads in the contents of the file named filename and returns
	* it as a String. The main method calls this method on args[0]...
	 * @throws FileNotFoundException 
	*/
	public static String readContents(String filename) throws FileNotFoundException {
		Scanner input = new Scanner(new File(filename));
		
		String str = "";
		while(input.hasNextLine()) {
			str = str + input.nextLine() + "\n";
		}
		
		return str;
	
	}

	/**
	* Using the encoded String argument, and the huffman codings,
	* re-create the original message from our
	* huffman encoding and return it...
	*/
	public String decodeMessage(String encodedStr) {
		String decoded = "";	
		String sub = "";
		for(int i = 0; i< encodedStr.length(); i++) {
			String c = Character.toString(encodedStr.charAt(i));
			sub += c;
			if(Arrays.asList(code).contains(sub)) {
				decoded += (char) Arrays.asList(code).indexOf(sub);
				sub = "";
			}
		}
		return decoded;
	}
	
	/**
	* Uses args[0] as the filename, and reads in its contents. Then
	* instantiates a HuffmanConverter object, using its methods to
	* obtain our results and print the necessary output. Finally,
	* decode the message and compare it to the input file.<p>
	* NOTE: Example method provided below...
	 * @throws IOException 
	*/
	public static void main(String[] args) throws IOException
	{
		String contents = readContents(args[0]);
		HuffmanConverter a = new HuffmanConverter(contents);
		a.recordFrequencies();
		a.frequenciesToTree();
		System.out.println();
		a.treeToCode();
		System.out.println();
		System.out.println("Huffman Encoding:");
		String encodedStr = a.encodeMessage();
		System.out.println(encodedStr);
		System.out.println();
		System.out.println("Message size in ASCII encoding: "+contents.length()*8);
		System.out.println("Message size in Huffman coding: "+encodedStr.length());
		System.out.println();	
		String decoded = a.decodeMessage(encodedStr);
		System.out.println(decoded);
	}
}