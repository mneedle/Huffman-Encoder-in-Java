import java.util.ArrayList;

public class HuffmanTree
{
	HuffmanNode root;
	
	
	// constructor
	public HuffmanTree(HuffmanNode huff)
	{
		this.root = huff; 
	}
	
	
	// recursive tree traversal print function
	public void printLegend()
	{
		printLegend(root, "");
	}
	
		private void printLegend(HuffmanNode t, String s)
		{
			if(t.letter.length() > 1) {
				printLegend(t.left, s + "0");
				printLegend(t.right, s + "1");
			} else {
				System.out.println(t.letter+"="+s);
			}
		}
	
	
	// convert legend to BinaryHeap
	public static BinaryHeap legendToHeap(String legend)
	{
		// break legend into ArrayList by letter
		ArrayList legendArrayList = new ArrayList();
		String str = Character.toString(legend.charAt(0));
		for(int i = 1; i < legend.length(); i++) {
			char c = legend.charAt(i);
			if(Character.isLetter(c)) {
				legendArrayList.add(str);
				str = Character.toString(c);
			} else if(Character.isDigit(c)) {
				str = str + Character.toString(c);
			} else if(str.toCharArray().length==1) {
				str = str + "_";
			}
		}
		legendArrayList.add(str);
		
		//break ArrayList into Array by letter
		String[] legendArray = new String[legendArrayList.size()];
		for(int i = 0; i < legendArrayList.size(); i++) {
			legendArray[i] = (String) legendArrayList.get(i);
		}
		
		// create and return BinaryHeap
		BinaryHeap<String> bheap = new BinaryHeap<String>(legendArray);
		return bheap;
	}
	
	
	//apply The Algorithm to the input BinaryHeap to consolidate into a new BinaryHeap, from which to create and return a HuffmanTree
	public static HuffmanTree createFromHeap(BinaryHeap b)
	{
		
		int s = b.getSize();
		HuffmanNode[] huffArray = new HuffmanNode[s];
		for(int i = 0; i<s; i++) {
			String legendEntry = (String) b.deleteMin();
			String[] parts = legendEntry.split("_");
			String l = parts[0];
			Integer f = Integer.valueOf(parts[1]);
			HuffmanNode n = new HuffmanNode(l,f);
			huffArray[i]= n;
		}

		BinaryHeap<HuffmanNode> huffBinary = new BinaryHeap<HuffmanNode>(huffArray);
		huffBinary.printHeap();
		
		while(huffBinary.getSize()>1) {
			HuffmanNode min1 = huffBinary.deleteMin();
			HuffmanNode min2 = huffBinary.deleteMin();
			HuffmanNode n = new HuffmanNode(min2, min1);
			huffBinary.insert(n);
		}
		
		HuffmanTree t = new HuffmanTree(huffBinary.deleteMin());
		return t;	
	}
	
	
	// main method
	public static void main(String[] args)
	{
		String legend = "A 20 E 24 G 3 H 4 I 17 L 6 N 5 O 10 S 8 V 1 W 2";
		BinaryHeap bheap = legendToHeap(legend);
		bheap.printHeap();
		HuffmanTree htree = createFromHeap(bheap);
		htree.printLegend();
	}
}