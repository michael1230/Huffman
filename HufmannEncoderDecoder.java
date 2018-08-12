package assign1;

/**
 * Assignment 1
 * Submitted by: 
 * Student 1.מיכאל בביאב 	ID# 312956121
 * Student 2.אלכס גרוביצ'ב 	ID# 321765919
 */

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.BufferedOutputStream;
import java.io.File;
import java.util.BitSet;
import java.util.Comparator;
import java.util.PriorityQueue;
import assign1.HuffmanNode;
import java.util.HashMap;
import base.compressor;



public class HufmannEncoderDecoder implements compressor
{//its all protected for HufmannEnglishEnDe class
	protected int[] FrequencyArray;//the array which will be our frequency table
	protected HuffmanNode tree;//the tree which will be our huffman tree
	protected PriorityQueue<HuffmanNode> priorityHuffmanNode;//PriorityQueue for creating the tree
	protected Comparator<HuffmanNode> FrequencyComparator;//a Comparator for the PriorityQueue
	protected HashMap<Character, String> huffmanCodes;//HashMap for easy access to the char(Character) and its huffman code(String)
	protected HashMap<String, Character> huffmanToReq;//HashMap for easy access to the code(String)  and its huffman char(Character)
	protected byte[] huffmanCompressedByteArray; //only for the CompressWithArray method 
	protected byte[] huffmanDecompressedByteArray; //only for the DecompressWithArray method 	
	//the two other HashMap its for the HufmannEnglishEnDe class because there we will have symbols that are bigger then one char like "the"  
	protected HashMap<String, String> huffmanCodesString;//HashMap for easy access to the char(first String) and its huffman code(second String) only for HufmannEnglishEnDe
	protected HashMap<String, String> huffmanToReqString;//HashMap for easy access to the code(first String) and its huffman char(second String) only for HufmannEnglishEnDe
	protected StringBuilder treeString;// a string which represent our huffman tree
	protected HuffmanNode decoTree;// a huffman tree from the Decompressed file

   public HufmannEncoderDecoder()//a constructor
	{
		FrequencyComparator = Comparator.comparing(HuffmanNode::getFrequency);//make the Comparator based on the Frequency in the HuffmanNode
		FrequencyArray = new int[256];//because there are 256 possible bytes
		tree=null;//first the tree is null
		priorityHuffmanNode=new PriorityQueue<HuffmanNode>(256,FrequencyComparator);//PriorityQueue of 256 HuffmanNode(maybe not all the 256 is full) and the Comparator
		huffmanCodes= new HashMap<Character, String>();//initialize the map
		huffmanToReq= new HashMap<String, Character>();//initialize the map
		treeString= new StringBuilder();//initialize the string
		decoTree=null;//first the tree is null	
	}		
	public HufmannEncoderDecoder(int size)//a parameter constructor for HufmannEnglishEnDe class use
	{
		FrequencyComparator = Comparator.comparing(HuffmanNode::getFrequency);//make the Comparator based on the Frequency in the HuffmanNode
		FrequencyArray = new int[size];//because there are 256 possible bytes
		tree=null;//first the tree is null
		priorityHuffmanNode=new PriorityQueue<HuffmanNode>(size,FrequencyComparator);//PriorityQueue of 256 HuffmanNode(maybe not all the 256 is full) and the Comparator
		huffmanCodesString= new HashMap<String, String>();//initialize the map
		huffmanToReqString= new HashMap<String, String>();//initialize the map
		treeString= new StringBuilder();//initialize the string
		decoTree=null;//first the tree is null	
	}
	public void Compress(String[] input_names, String[] output_names)
	{	//input_names and output_names contain the path to the files					
		File originalFileInput = new File(input_names[0]);//the path to the input file
		File originalfileOut = new File(output_names[0]);//the path to the output file
		this.frequencyArrayBulid(originalFileInput);//build the frequencyArray
	    this.HuffmanTreeBulid();//create the Huffman tree
        HuffmanNode root =tree; //save the root of the tree in node root and use it in the next function
        this.huffmanCodeGenerator(root, "");//create the HashMap
        this.writeHuffmanCode(originalFileInput,originalfileOut,false);              
	}
	@Override
	public void Decompress(String[] input_names, String[] output_names)
	{
		File compressedFileInput = new File(input_names[0]);//the path to the input file
		File compressedfileOut = new File(output_names[0]);//the path to the output file
		this.writeOriginalCode(compressedFileInput, compressedfileOut, false);	
	}
	@Override
	public byte[] CompressWithArray(String[] input_names, String[] output_names)
	{//input_names and output_names contain the path to the files
		File originalFileInput = new File(input_names[0]);//the path to the input file
		File originalfileOut = new File(output_names[0]);//the path to the output file
		if(huffmanCodes.isEmpty())//to check if the regular Compress method was used before this one,if it is then
		//the FrequencyArray HuffmanNode tree and the HashMap are already made so no need to make them again  
		{
			this.frequencyArrayBulid(originalFileInput);//build the frequencyArray
		    this.HuffmanTreeBulid();////create the Huffman tree
	        HuffmanNode root =tree; //create the Huffman tree
	        this.huffmanCodeGenerator(root, "");//create the HashMap
		}
		
		this.writeHuffmanCode(originalFileInput, originalfileOut, true);//now send with true for the byte array
		return this.huffmanCompressedByteArray;
	}
	@Override
	public byte[] DecompressWithArray(String[] input_names, String[] output_names)
	{
		File compressedFileInput = new File(input_names[0]);//the path to the input file
		File compressedfileOut = new File(output_names[0]);//the path to the output file
		this.writeOriginalCode(compressedFileInput, compressedfileOut, true);//now send with true for the byte array
		return this.huffmanDecompressedByteArray;
	}
	public void FrequencyPrintTese(int [] a)//a test function for future use
	{
		for(int i=0;i<a.length;i++)
		{
			char c =(char)i;
			System.out.println(i+" : "+c+" : "+FrequencyArray[i]);
		}
	}	
	public void frequencyArrayBulid(File file)//make the FrequencyArray 
	{
		try {
        	Path path = Paths.get(file.getAbsolutePath());
        	byte[] inputData = Files.readAllBytes(path);//reading the file as byte and saving it in inputData
        	
        	for (byte b : inputData)//for each byte in inputData
        	{
        		FrequencyArray[b&0xFF]++;//add 1 to the index which byte0xFF(because we want 0-255 and not -127 to 128) represent each time we encounters the character(byte b)
            }	        		        	
		} 
		catch (IOException e) 
		{
				e.printStackTrace();
		}    
	}
	public void HuffmanTreeBulid()//bulid the PriorityQueue and the huffman tree from it
	  {
		for(int i=0;i<FrequencyArray.length;i++) //with this loop we build our PriorityQueue
	      {
	        if(FrequencyArray[i]>0)//if its 0 then that's means that there wasn't an occurrence of that character 
	          priorityHuffmanNode.add(new HuffmanNode(i,FrequencyArray[i]));//create a HuffmanNode with its constructor and add it to the PriorityQueue
	      }
		
	    while (priorityHuffmanNode.size() > 1) 
	     {
			HuffmanNode newleft = priorityHuffmanNode.poll();//the first is the left node
			HuffmanNode newright = priorityHuffmanNode.poll();//the second is the right node
			HuffmanNode parentNode = new HuffmanNode(0,0);//new node which is the parent of the above two
			parentNode.setFreq(newleft.getFrequency()+newright.getFrequency());//the parent Frequency is the sum of its children
			parentNode.setLeft(newleft);//put newleft as the lest child
			parentNode.setRight(newright);//put newright as the right child
			tree = parentNode;//the parentNode is the root of the tree
			// add this node to the priority-queue.
			priorityHuffmanNode.add(parentNode);// add it to the PriorityQueue for further tree making
		 }
	  }  
	public void mapPrintTest(HashMap<Character, String> map)//a test function for future use
	{
		for (char key : map.keySet()) {
		    System.out.println(key + " " + map.get(key));
		}
	}
	public void mapPrintTest2(HashMap<String, Character> map)//a test function for future use
	{
		for (String key : map.keySet()) {
		    System.out.println(key + " " + map.get(key));
		}
	}
	public void huffmanCodeGenerator(HuffmanNode node,String codes)//the huffman code Generator
	{
		if (node != null)
		{
			if (!node.isleaf())//if not node then keep going
			{
				huffmanCodeGenerator(node.getLeft(), codes + "0");//when we go left on the tree we add 0 to the code
				huffmanCodeGenerator(node.getRight(), codes + "1");//when we go right on the tree we add 1 to the code
			}
			else
			{ 
				huffmanCodes.put(node.getChar(), codes); // if the node is leaf then the code is done and now we put it in the HashMap
			}
		}	
	}
	public void originalCodeGeneratorU(HuffmanNode node,String codes)//the original code Generator(opposite of huffmanCodeGenerator)
	{
		if (node != null)
		{
			if (!node.isleaf())//if not node then keep going
			{
				originalCodeGeneratorU(node.getLeft(), codes + "0");//when we go left on the tree we add 0 to the code
				originalCodeGeneratorU(node.getRight(), codes + "1");//when we go right on the tree we add 1 to the code
			}
			else
			{ 
				huffmanToReq.put(codes, node.getChar()); //if the node is leaf then the code is done and now we put it in the HashMap
			}
		}	
	}
	public void writeOriginalCode(File fileInput,File fileout,boolean arrayFlag)//write original form to file(the arrayFlag is for the 2 method with array)
	{
		try {
        	Path path = Paths.get(fileInput.getAbsolutePath());
        	byte[] inputData = Files.readAllBytes(path);//reading the file as byte and saving it in inputData
        	FileOutputStream out = new FileOutputStream(fileout);//for the new file
        	ByteBuffer treeSizeBytes=ByteBuffer.wrap(inputData, 0, 2);//the first 2 bytes is the size of the tree binary String
        	short treeSizeInt = treeSizeBytes.getShort();//get the short from the byte to know the size of the tree string		
        	BitSet inputDataBits=BitSet.valueOf(inputData);//the inputData in bit format
        	StringBuilder treeBinaryString=new StringBuilder();//the string to represent our huffman tree.('1' is leaf flowed by the data(9 bits) and 0 is not leaf)
        	for(int i=16;i<16+treeSizeInt;i++)//16 because short is 16 bits
        	{
        		if(inputDataBits.get(i)==true)
        			treeBinaryString.append('1');
    			else
    				treeBinaryString.append('0');
        	}       	
        	decoTree=this.HuffmanStringToTree(treeBinaryString.toString(), 0, (new HuffmanNode(0,0)));//make the tree from the file 
        	this.originalCodeGeneratorU(decoTree, "");//make the hush map from the new tree
        	char currentchar;//the char to write to the file
        	StringBuilder bits = new StringBuilder();//a string which will represent the huffman code
        	StringBuilder decompressedChars = new StringBuilder();//a string for the  method with buye[] array
        	if(!fileout.exists())//if new file exits then replace it if not create it 
        		fileout.createNewFile();
        	else
        	{
        		fileout.delete();
        		fileout.createNewFile();
        	}
        	
        	for (int i=16+treeSizeInt;i<inputDataBits.length()+1;i++)//for each byte in inputData(its 16+treeSizeInt because before that there's the tree data and after it its the compressed data
        	{
        		if(inputDataBits.get(i)==true)//if its true then append '1' to bits
        		{
        			bits.append('1');
        		}
        		else
        		{
        			bits.append('0');
        		}
        		if(huffmanToReq.containsKey(bits.toString()))//check if the bits we have are a huffman code, if not Continue until it is
        		{
        			currentchar=huffmanToReq.get(bits.toString());//if it is then get the char that he represent
        			if(arrayFlag)//if we want the byte[] array then
        			{        	        				
        				decompressedChars.append(currentchar);//bulid it        				
        			}
        			
        			out.write(currentchar);
        			bits.setLength(0);//clear bits for the new huffman code
        		}
            }
        	if(arrayFlag)//if we want the byte[] array then 
			{
        		huffmanDecompressedByteArray=decompressedChars.toString().getBytes(); //.getBytes("UTF-8") ;//get the original code in byte[] format
        	
			}
        	out.close();
		} 
	
		catch (IOException e) 
		{
				e.printStackTrace();
		}	
	}
	public void writeHuffmanCode(File fileInput,File fileout,boolean arrayFlag)//write huffman to file(the arrayFlag is for the 2 method with array)
	{
		try 
        {
			if(!fileout.exists())//if new file exits then replace it if not create it 
        		fileout.createNewFile();
        	else
        	{
        		fileout.delete();
        		fileout.createNewFile();
        	}
			FileOutputStream out = new FileOutputStream(fileout,true);//for the new file. true is because we write twice to the file
        	BufferedOutputStream bitout = new BufferedOutputStream(out);//for output in byte form        	       	
        	Path path = Paths.get(fileInput.getAbsolutePath());
        	byte[] inputData = Files.readAllBytes(path);//reading the file as byte and saving it in inputData
        	StringBuilder all = new StringBuilder();//a StringBuilder which represent the huffman code of the file in binary String format 
        	//a StringBuilder instant of regular string because we keep changing it(with string the algorithm it takes more time)      	
        	this.HuffmanTreeToString(tree);//the string to represent our huffman tree.('1' is leaf flowed by the data(9 bits) and 0 is not leaf)
        	short length=(short) treeString.length();//the length of the tree string in short format		
			byte[] size=ByteBuffer.allocate(2).putShort(length).array();//a byte[] that will have our size
			bitout.write(size);//write the size first
        	all.append(treeString);//append the tree string first       	
        	char currentchar;//a char to represent the characters in inputData
        	for (byte b : inputData)//for each byte in inputData
        	{
        		currentchar=(char)(b&0xFF);//cast the byte to char and save the characters ( b&0xFF because we want 0-255 and not -127 to 128)
				String binaryString=huffmanCodes.get(currentchar);//get the code of that characters from the HashMap
				all=all.append(binaryString);//append all the binaryStrings together
        	}      	
        	BitSet huffmanCodeBit = new BitSet(all.length());//	BitSet for all the bits on the binary String	
			for (int i = 0; i < all.length(); i++) 
			{
			    if(all.charAt(i) == '1')//if the char at the string is '1' then put 1 on in the Bitset..all the other bits are '0' by default
			        huffmanCodeBit.set(i);
			}
			bitout.write(huffmanCodeBit.toByteArray());	//get the Bitset in char array format and then write it	
			if(arrayFlag)//if we want the byte[] array then 
			{
				huffmanCompressedByteArray=huffmanCodeBit.toByteArray();//put the huffman code in byte[] array format
			}
			bitout.close();
			out.close();
		} 
        catch (IOException e) 
		{
				e.printStackTrace();
		}	
	}
	public void HuffmanTreeToString(HuffmanNode tree)//make the string to represent our huffman tree('1' is leaf flowed by the data(9 bits) and 0 is not leaf)
	{
		if (tree.isleaf()) //if its a leaf
		{
			treeString=treeString.append('1');//put '1' on the string
			treeString.append(tree.getBinaryString());//call the getBinaryString function and append it to the string.
	    }
	    else //if its not
	    {
	    	treeString=treeString.append('0');//put '0' to the string
	    	HuffmanTreeToString(tree.getLeft());
	    	HuffmanTreeToString(tree.getRight());
	    }
	}
	public HuffmanNode HuffmanStringToTree(String treeString,int i,HuffmanNode decoTree)//make the tree from the binary string
	{//we need to iterator on the string while making a tree that means we need to somehow save the index while returning the node
	 //and we do it by putting the index in the frequency of the node and updating the i index
	if (treeString.charAt(i)=='1')//if its 1 then that a leaf
	{
		StringBuilder symbol = new StringBuilder(9);//a string of the symbol
		for(int j=i+1;j<i+10;j++)//the i is in '1' so we need 9 bits from it so until i+10
		{
			symbol.append(treeString.charAt(j));
		}
		i=i+9;//the new index 
		int parseInt = Integer.parseInt(symbol.toString(), 2);//the int to represent the char in the node
		HuffmanNode leaf= new HuffmanNode(parseInt,i);//create a new node that have the char and the index as the frequency
		leaf.setSymbol(String.valueOf(parseInt));//also put the string to the node
		//the char and the string are in the node because the 2 class need to use this function(English text class and the regular class)
		return leaf;
    }
    else if(treeString.charAt(i)=='0')//if its 0 then that Internal node
    {
    	decoTree.setLeft(HuffmanStringToTree(treeString,decoTree.getFrequency()+1,new HuffmanNode(0,i+1)));//call the function with frequency+1 as i and save it the the node
    	i=decoTree.getLeft().getFrequency()+1;//after we done with left side the i is the frequency+1 because we saved it
    	decoTree.setRight(HuffmanStringToTree(treeString,i,new HuffmanNode(0,i)));
    	decoTree.setFreq(decoTree.getRight().getFrequency());//after we done with right side we need to save the i to the frequency for future nodes 
		//and the last index we passed is in frequency of the right node
    }
	return decoTree;
	}
}
