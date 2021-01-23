
public class HuffmanNode implements Comparable
{
	public String letter;
	public Integer frequency;
	public HuffmanNode left, right;
	
	
	// constructor with null left and right nodes
	public HuffmanNode(String letter, Integer frequency)
	{
		this.letter = letter;
		this.frequency = frequency; 
		this.right = null;
		this.left = null;
	}
	
	
	// constructor with existing left and right nodes
	public HuffmanNode(HuffmanNode left, HuffmanNode right)
	{
		this.letter = left.letter + right.letter; 
		this.frequency = left.frequency + right.frequency;
		this.left = left;
		this.right = right;
	}
	
	
	// compare "frequency" of nodes to determine position in final BinaryHeap
	public int compareTo(Object o)
	{
		HuffmanNode huff = (HuffmanNode) o;
		return this.frequency.compareTo(huff.frequency);
	}
	
	
	// convert node to string
	public String toString()
	{
		String r = "<"+this.letter+", "+this.frequency+">";
		return r;
	}
}