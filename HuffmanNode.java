package assign1;
/**
 * Assignment 1
 * Submitted by: 
 * Student 1.מיכאל בביאב 	ID# 312956121
 * Student 2.אלכס גרוביצ'ב 	ID# 321765919
 */

public class HuffmanNode implements Comparable<HuffmanNode> {
	private char c;//the character 
	private int frequency;//the character frequency
	private HuffmanNode left;//its left child
	private HuffmanNode right;//its right child
	private String Symbol;

	public HuffmanNode(int id,int frequency){//a constructor
		this.c=(char)id;
		this.frequency=frequency;
		this.right=null;
		this.left=null;
		this.Symbol=null;
	}
	public HuffmanNode(String id,int frequency){//a constructor
		this.Symbol=id;
		this.frequency=frequency;
		this.right=null;
		this.left=null;
	}
	
	public String getSymbol()//a getter for the character
	{
		return this.Symbol;
	}
	public char getChar()//a getter for the character
	{
		return this.c;
	}	
	public int getFrequency()//a getter for the Frequency
	{
	    return this.frequency;
	}
	public HuffmanNode getLeft()//a getter for the left child
	{
		return this.left;
	}
	public HuffmanNode getRight()//a getter for the right child
	{
		return this.right;
	}
	public void setFreq(int frequency)//a setter for the Frequency
	{
	    this.frequency = frequency;
	}
	public void setSymbol(String Symbol)//a setter for the Frequency
	{
	    this.Symbol = Symbol;
	}		
	public void setLeft(HuffmanNode o)//a setter for the left child
	{
		this.left=o;
	}
	public void setRight(HuffmanNode o)//a setter for the right child
	{
		this.right=o;
	}	
	@Override
	public int compareTo(HuffmanNode o) // the compare function for the PriorityQueue
	{
		return this.frequency-o.frequency;
	}	
	public boolean isleaf()//check if leaf
	{
	    if(this.left == null && this.right == null)
	        return true;
	    else
	        return false;    
	  }
	public String getBinaryString()//make the string to represent our node data,we use it for making the huffman tree string  
	{//9 bits not 8 because we want to use it in the HufmannEnglishEnDe class, there's the max value of 
	// a leaf data is 260 which need 9 bits to represent it.
		int binaryInt=0;//initialize 
		if(this.Symbol==null)//check if its for the English text class or the regular class
			 binaryInt=(int)this.c;//if regular class then just cast the char to int		
		else
			 binaryInt=Integer.parseInt(this.Symbol);//if for the English text class then parseInt(which is cast from string..) to int
		String binaryString=Integer.toBinaryString(binaryInt);//make a binary string that represent our int which represent our Symbol/char
		if(binaryString.length()<9)//if its length smaller then 9 then pad 0 in the end of it 
		{
			int zeroPad=9-binaryString.length();//how many zeros to pad
			while(zeroPad!=0)//pad them
			{
				binaryString='0'+binaryString;
				zeroPad--;
			}				
		}
		return binaryString;
	}
}

