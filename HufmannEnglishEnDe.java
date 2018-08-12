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
import assign1.HuffmanNode;
import java.util.HashMap;


public class HufmannEnglishEnDe extends HufmannEncoderDecoder
{
	private int occurrence[];//an array that for the number of times each word appears in the file
	private String[] commenWords;//the common Words in the text
	private byte[] huffmanCompressedByteArrayEng; //only for the CompressWithArray method 
	private byte[] huffmanDecompressedByteArrayEng; //only for the DecompressWithArray method 

	public HufmannEnglishEnDe()
	{
		super(261);// call the parameter constructor of HufmannEncoderDecoder. its 256 regular characters with 5 additional symbols  
		occurrence=new int[5];//represent the number of occurrence of the strings: int[0] is the occurrence of commenWords[0] and so on..
		commenWords=new String[5];//initialize
		commenWords[0]= "the";//will be at FrequencyArray[256]
		commenWords[1]= "and";//will be at FrequencyArray[257]
		commenWords[2]= "be";//will be at FrequencyArray[258]
		commenWords[3]= "ea";//will be at FrequencyArray[259]
		commenWords[4]= "is";//will be at FrequencyArray[260]
	}
	public void CompressEng(String[] input_names, String[] output_names)
	{
		//input_names and output_names contain the path to the files					
		File originalFileInputEng = new File(input_names[0]);//the path to the input file
		File originalfileOutEng = new File(output_names[0]);//the path to the output file
		this.frequencyArrayBulid(originalFileInputEng);//build the frequencyArray
		this.HuffmanTreeBulid();//create the Huffman tree
		HuffmanNode root =tree; //save the root of the tree in node root and use it in the next function
        this.huffmanCodeGenerator(root, "");//create the hushmap
        this.writeHuffmanCode(originalFileInputEng,originalfileOutEng,false);  
		
	}
	public void DecompressEng(String[] input_names, String[] output_names)
	{
		File compressedFileInputEng = new File(input_names[0]);//the path to the input file
		File compressedfileOutEng = new File(output_names[0]);//the path to the output file
		this.writeOriginalCode(compressedFileInputEng,compressedfileOutEng, false);
		
	}
	public byte[] CompressWithArrayEng(String[] input_names, String[] output_names)
	{
		File originalFileInputEng = new File(input_names[0]);//the path to the input file
		File originalfileOutEng = new File(output_names[0]);//the path to the output file
		if(huffmanCodesString.isEmpty())//to check if the regular Compress method was used before this one,if it is then
			//the FrequencyArray HuffmanNode tree and the HashMap are already made so no need to make them again  
			{
				this.frequencyArrayBulid(originalFileInputEng);//build the frequencyArray
				this.HuffmanTreeBulid();//create the Huffman tree
				HuffmanNode root =tree; //save the root of the tree in node root and use it in the next function
		        this.huffmanCodeGenerator(root, "");//create the hushmap
			}
		this.writeHuffmanCode(originalFileInputEng,originalfileOutEng,true);
		return huffmanCompressedByteArrayEng;
	}
	public byte[] DecompressWithArrayEng(String[] input_names, String[] output_names)
	{
		File compressedFileInputEng = new File(input_names[0]);//the path to the input file
		File compressedfileOutEng = new File(output_names[0]);//the path to the output file
		this.writeOriginalCode(compressedFileInputEng,compressedfileOutEng, true);
		return huffmanDecompressedByteArrayEng;
	}
	@SuppressWarnings("unused")//for Suppressing Warnings of not used 
	public void frequencyArrayBulid(File file)//make the FrequencyArray 
	{
		try {
        	Path path = Paths.get(file.getAbsolutePath());
        	byte[] inputData = Files.readAllBytes(path);//reading the file as byte and saving it in inputData
        	char currentchar;//a char that represent the current char in inputData
        	for (byte b : inputData)//for each byte in inputData
        	{
        		FrequencyArray[b&0xFF]++;//add 1 to the index which byte0xFF(0xFF is because we want 0-255 and not -127 to 128) represent each time we encounters the character(byte b)
            }
        	
        	String inputDataAsString=new String(inputData,"US-ASCII");//save the text file as string for counting the occurrence for each word
        	for(int i=0;i<commenWords.length;i++)
        	{
        		String temp=inputDataAsString;//we save the string because we will change it
        		int index = temp.indexOf(commenWords[i]);//save the index of the first occurrence of each word
        		int count = 0;//an int that represent the times the word appears
        		while (index != -1) {//if index is -1 then the words doesn't exist in the string so while the word is in the string
        		    count++;//count it
        		    temp = temp.substring(index + 1);//make a new string that begins after the deleted word for counting again
        		    index = temp.indexOf(commenWords[i]);//save the index of the first occurrence of each word in the new string
        		}
        		inputDataAsString=inputDataAsString.replaceAll(commenWords[i], "");//when its done replace the searched word with noting for not confusing words such at bea(its be but counted as ea also)
        		occurrence[i]=count;//put the times at the right index in occurrence
        	}
        	for(int i=0;i<occurrence.length;i++)//add the number of occurrence of the additional symbols and subtract the occurrence of each char its made of accordingly  
        	{
        		FrequencyArray[i+256]=occurrence[i];//the additional symbols start on 256 and so on
        		for(char b:commenWords[i].toCharArray())//for ecah char in the word
        		{
        			int f=occurrence[i];//save the number of occurrence
        			while(f>0)//while there still occurrence
        			{
        				FrequencyArray[b]--;//subtract the char
        				f--;//for loop
        			}
        		}
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
				priorityHuffmanNode.add(new HuffmanNode(String.valueOf(i),FrequencyArray[i]));//create a HuffmanNode with its constructor and add it to the PriorityQueue    
	      }
		while (priorityHuffmanNode.size() > 1) 
		{
			HuffmanNode newleft = priorityHuffmanNode.poll();//the first is the left node
			HuffmanNode newright = priorityHuffmanNode.poll();//the second is the right node
			HuffmanNode parentNode = new HuffmanNode("0",0);//new node which is the parent of the above two
			parentNode.setFreq(newleft.getFrequency()+newright.getFrequency());//the parent Frequency is the sum of its children
			parentNode.setLeft(newleft);//put newleft as the lest child
			parentNode.setRight(newright);//put newright as the right child
			tree = parentNode;//the parentNode is the root of the tree
			// add this node to the priority-queue.
			priorityHuffmanNode.add(parentNode);// add it to the PriorityQueue for further tree making
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
				huffmanCodesString.put(node.getSymbol(), codes); // if the node is leaf then the code is done and now we put it in the hushmap
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
				huffmanToReqString.put(codes, node.getSymbol()); //if the node is leaf then the code is done and now we put it in the HashMap
			}
		}	
	}	
	public void writeHuffmanCode(File fileInput,File fileout,boolean arrayFlag)//write huffman to file(the arrayFlag is for the 2 method with array)
	{
		try 
        {
        	FileOutputStream out = new FileOutputStream(fileout);//for the new file
        	BufferedOutputStream bitout = new BufferedOutputStream(out);//for output in byte form
        	if(!fileout.exists())//if new file exits then replace it if not create it 
        		fileout.createNewFile();
        	else
        	{
        		fileout.delete();
        		fileout.createNewFile();
        	}
        	Path path = Paths.get(fileInput.getAbsolutePath());
        	byte[] content = Files.readAllBytes(path);//reading the file as byte and saving it in content
        	StringBuilder all = new StringBuilder();//a StringBuilder which represent the huffman code of the file in binary String format 
        	//a StringBuilder instant of regular string because we keep changing it(with string the algorithm it takes more time)        	
        	this.HuffmanTreeToString(tree);//the string to represent our huffman tree.('1' is leaf flowed by the data(9 bits) and 0 is not leaf)
        	short length=(short) treeString.length();//the length of the tree string in short format		
			byte[] size=ByteBuffer.allocate(2).putShort(length).array();//a byte[] that will have our size
			bitout.write(size);//write the size first
        	all.append(treeString);//append the tree string first 
        	char currentchar;//a char to represent the characters in content
        	StringBuilder tempChar = new StringBuilder();//a string that will represent our word
        	boolean foundFirstLetter=false;//a flag for knowing if we have a first letter for a word in commonwords
        	boolean foundSecondLetter=false;//a flag for knowing if we have a second letter for a word in commonwords
        	for (byte b : content)//for each byte in content
        	{
        		currentchar=(char)(b&0xFF);//cast the byte to char and save the characters ( b&0xFF because we want 0-255 and not -127 to 128)
				if(((currentchar=='t')||(currentchar=='a')||(currentchar=='b')||(currentchar=='e')||(currentchar=='i'))&&(foundFirstLetter==false))
				{//check if it one of the first letters in one of the words in commonwords
					tempChar.append(currentchar);//save the first letter
					foundFirstLetter=true;//we have a first letter
				}
				else if((foundFirstLetter==true)&&(foundSecondLetter==false))//if we have a first and we dont have a second
				{
					tempChar.append(currentchar);//save the second letter 
					switch (String.valueOf(tempChar))//see what word we have now
					{
					    case "be":
					    	all=all.append(huffmanCodesString.get(String.valueOf(258)));
					    	tempChar.setLength(0);//reset the word for next letter
					    	foundFirstLetter=false;//reset the flag for next letter			    	
					        break;
					    case "ea":
					    	all=all.append(huffmanCodesString.get(String.valueOf(259)));
					    	tempChar.setLength(0);//reset the word for next letter
					    	foundFirstLetter=false;//reset the flag for next letter
					        break;
					    case "is":
					    	all=all.append(huffmanCodesString.get(String.valueOf(260)));
					    	tempChar.setLength(0);//reset the word for next letter
					    	foundFirstLetter=false;//reset the flag for next letter
					        break;
					    //if it one of the above then we have found a word and we will write the huffman code for it in all  
						//if it one of the below then we have found a second letter   
					    case "th":
					    case "an":
					    	foundSecondLetter=true;//reset the flag for next letter
					        break;
					    default://if the word we have is not part of the commonwords word then that's means the second letter is not what we looking for so we write it to all
					    	all=all.append(huffmanCodesString.get(String.valueOf((int)tempChar.charAt(0))));
					    	all=all.append(huffmanCodesString.get(String.valueOf((int)tempChar.charAt(1))));
					    	tempChar.setLength(0);//reset the word for next letter
					    	foundFirstLetter=false;//reset the flag for next letter
					}	
				}
				else if((foundFirstLetter==true)&&(foundSecondLetter==true))//if we have the first and second letters
				{
					tempChar.append(currentchar);//save the third letter
					switch (String.valueOf(tempChar))
					{
					    case "the":
					    	all=all.append(huffmanCodesString.get(String.valueOf(256)));
					    	tempChar.setLength(0);//reset the word for next letter
					    	foundFirstLetter=false;//reset the flag for next letter	
					    	foundSecondLetter=false;//reset the flag for next letter
					        break;
					    case "and":
					    	all=all.append(huffmanCodesString.get(String.valueOf(257)));
					    	tempChar.setLength(0);//reset the word for next letter
					    	foundFirstLetter=false;//reset the flag for next letter	
					    	foundSecondLetter=false;//reset the flag for next letter
					        break;
					    default:
					    	all=all.append(huffmanCodesString.get(String.valueOf((int)tempChar.charAt(0))));
					    	all=all.append(huffmanCodesString.get(String.valueOf((int)tempChar.charAt(1))));
					    	all=all.append(huffmanCodesString.get(String.valueOf((int)tempChar.charAt(2))));
					    	tempChar.setLength(0);//reset the word for next letter
					    	foundFirstLetter=false;//reset the flag for next letter	
					    	foundSecondLetter=false;//reset the flag for next letter
					}	
				} 
				else//if the letter is not in any of the words
				{
					all=all.append(huffmanCodesString.get(String.valueOf((int)currentchar)));
				}
        	}
        	if(tempChar.length()!=0)//if we reach the end of content but the tempChar is not empthy then send write it to all  
        	{
        		switch (tempChar.length())
        		{
			    case 1:
			    	all=all.append(huffmanCodesString.get(String.valueOf((int)tempChar.charAt(0))));
			        break;
			    case 2:
			    	all=all.append(huffmanCodesString.get(String.valueOf((int)tempChar.charAt(0))));
			    	all=all.append(huffmanCodesString.get(String.valueOf((int)tempChar.charAt(1))));
			        break;
			    case 3:
			    	all=all.append(huffmanCodesString.get(String.valueOf((int)tempChar.charAt(0))));
			    	all=all.append(huffmanCodesString.get(String.valueOf((int)tempChar.charAt(1))));
			    	all=all.append(huffmanCodesString.get(String.valueOf((int)tempChar.charAt(2))));
			        break;
        		}
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
	public void writeOriginalCode(File fileInput,File fileout,boolean arrayFlag)//write original form to file(the arrayFlag is for the 2 method with array)
	{
		try {
        	if(!fileout.exists())//if new file exits then replace it if not create it 
        		fileout.createNewFile();
        	else
        	{
        		fileout.delete();
        		fileout.createNewFile();
        	}
			Path path = Paths.get(fileInput.getAbsolutePath());
        	byte[] inputData = Files.readAllBytes(path);//reading the file as byte and saving it in inputData
        	FileOutputStream out = new FileOutputStream(fileout);//for the new file
        	BitSet inputDataBits=BitSet.valueOf(inputData);//the inputData in bit format       	
        	ByteBuffer treeSizeBytes=ByteBuffer.wrap(inputData, 0, 2);//the first 2 bytes is the size of the tree binary String
        	short treeSizeInt = treeSizeBytes.getShort();//get the size in short format	
        	String treeBinaryString=new String();//the string to represent our huffman tree.('1' is leaf flowed by the data(9 bits) and 0 is not leaf)
        	for(int i=16;i<16+treeSizeInt;i++)//16 because short is 16 bits
        	{
        		if(inputDataBits.get(i)==true)
        			treeBinaryString=treeBinaryString+'1';
    			else
    				treeBinaryString=treeBinaryString+'0';
        	}       	
        	decoTree=this.HuffmanStringToTree(treeBinaryString, 0, (new HuffmanNode(0,0)));//make the tree from the file 
        	this.originalCodeGeneratorU(decoTree, "");//make the hush map from the new tree         	
        	char currentchar;//the cahr to write to the file
        	StringBuilder bits = new StringBuilder();//a string which will represent the huffman code
        	StringBuilder decompressedChars = new StringBuilder();//a string for the  method with buye[] array
        	
        	for (int i=16+treeSizeInt;i<inputDataBits.length()+1;i++)//for each byte in inputData
        	///i=0
        	{
        		if(inputDataBits.get(i)==true)//if its true then append '1' to bits
        		{
        			bits.append('1');
        		}
        		else
        		{
        			bits.append('0');
        		}
        		if(huffmanToReqString.containsKey(bits.toString()))//if the bits we have right now is in the hushmap then
        		{
        			int huffmanIndex=Integer.parseInt(huffmanToReqString.get(bits.toString()));//get its key in integer form which will represent its index in frequencyArray so we will know what words it is         			
        			if(huffmanIndex<256)//if its smaller then 256 which means it not the additional symbols
        			{
        				currentchar=(char)huffmanIndex;//get it char form
        				out.write(currentchar);//write it
            			bits.setLength(0);//clear bits for the new huffman code
            			if(arrayFlag)//if we want the byte[] array then
            			{
            				decompressedChars.append(currentchar);//bulid it
            			}
        			}
        			else//if it bigger then 256 or equal then its the additional symbols
        			{
        				int index=huffmanIndex-256;//calculate the index of the additional symbol in the commenWords   
        				for(char b: commenWords[index].toCharArray())//for each char in the additional symbol
        				{
        					out.write(b);//write it
        					if(arrayFlag)//if we want the byte[] array then
                			{
                				decompressedChars.append(b);//bulid it
                			}
        				}
        				bits.setLength(0);//clear bits for the new huffman code
        			}            
        		}
        	}
        	if(arrayFlag)//if we want the byte[] array then 
			{
        		huffmanDecompressedByteArray=decompressedChars.toString().getBytes();//get the original code in byte[] format
			}
        	out.close();
		} 
	
		catch (IOException e) 
		{
				e.printStackTrace();
		}	
	}
	public void mapPrintTestENG(HashMap<String, String> map)//a test function for future use
	{
		for (String key : map.keySet()) 
		{
		    System.out.println(key + " " + map.get(key));
		}
	}
	public void mapPrintTest2ENG(HashMap<String, String> map)//a test function for future use
	{
		for (String key : map.keySet()) 
		{
		    System.out.println(key + " " + map.get(key));
		}
	}
}
