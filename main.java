package assign1;

import java.util.BitSet;

/**
 * Assignment 1
 * Submitted by: 
 * Student 1.מיכאל בביאב 	ID# 312956121
 * Student 2.אלכס גרוביצ'ב 	ID# 321765919
 */



public class main {

	public static void main(String[] args) {
		
		byte[] a;
		byte[] b;
		
		String in[]=new String[2];
		//in[0]="F:\\eclipse\\workspace\\Compress\\files\\london_in_polish_source.txt";
		//in[0]="F:\\eclipse\\workspace\\Compress\\files\\test.txt";
		//in[0]="F:\\eclipse\\workspace\\Compress\\files\\test2.txt";
		//in[0]="F:\\eclipse\\workspace\\Compress\\files\\test3.txt";
		//in[0]="F:\\eclipse\\workspace\\Compress\\files\\OnTheOrigin.txt";
		in[0]="F:\\eclipse\\workspace\\Compress\\files\\OnTheOrigin_C2.txt";
		//in[0]="F:\\eclipse\\workspace\\Compress\\files\\fibtest.txt";
		//in[0]="F:\\eclipse\\workspace\\Compress\\files\\fibtest2.txt";
		String out[]=new String[2];
		//out[0]="F:\\eclipse\\workspace\\Compress\\files\\london_in_polish_source_CODED.txt";
		//out[0]="F:\\eclipse\\workspace\\Compress\\files\\test_CODED.txt";
		//out[0]="F:\\eclipse\\workspace\\Compress\\files\\test2_CODED.txt";
		//out[0]="F:\\eclipse\\workspace\\Compress\\files\\test3_CODED.txt";
		//out[0]="F:\\eclipse\\workspace\\Compress\\files\\OnTheOrigin_CODED.txt";
		out[0]="F:\\eclipse\\workspace\\Compress\\files\\OnTheOrigin_C2_CODED.txt";
		//out[0]="F:\\eclipse\\workspace\\Compress\\files\\YouKnowThisSound_CODED";
		//out[0]="F:\\eclipse\\workspace\\Compress\\files\\5.mp3_CODED";
		
		String outNew[]=new String[2];
		//outNew[0]="F:\\eclipse\\workspace\\Compress\\files\\london_in_polish_source_New.txt";
		//outNew[0]="F:\\eclipse\\workspace\\Compress\\files\\test_New.txt";
		//outNew[0]="F:\\eclipse\\workspace\\Compress\\files\\test2_New.txt";
		//outNew[0]="F:\\eclipse\\workspace\\Compress\\files\\test3_New.txt";
		//outNew[0]="F:\\eclipse\\workspace\\Compress\\files\\OnTheOrigin_New.txt";
		outNew[0]="F:\\eclipse\\workspace\\Compress\\files\\OnTheOrigin_C2_New.txt";
		//outNew[0]="F:\\eclipse\\workspace\\Compress\\files\\YouKnowThisSound_New";
		//outNew[0]="F:\\eclipse\\workspace\\Compress\\files\\5.mp3_New";
		
		
		String outArray[]=new String[2];
		//outArray[0]="F:\\eclipse\\workspace\\assign1\\files\\london_in_polish_source_CODEDARRAY.txt";
		//outArray[0]="F:\\eclipse\\workspace\\assign1\\files\\test_CODEDARRAY.txt";
		//outArray[0]="F:\\eclipse\\workspace\\assign1\\files\\test2_CODEDARRAY.txt";
		//outArray[0]="F:\\eclipse\\workspace\\assign1\\files\\OnTheOrigin_CODEDARRAY.txt";
		//outArray[0]="F:\\eclipse\\workspace\\assign1\\files\\OnTheOrigin_C2_CODEDARRAY.txt";
		//outArray[0]="F:\\eclipse\\workspace\\assign1\\files\\YouKnowThisSound_CODEDARRAY";
		//String outNewArray[]=new String[2];
		//outNewArray[0]="F:\\eclipse\\workspace\\assign1\\files\\london_in_polish_source_NewArray.txt";
		//outNewArray[0]="F:\\eclipse\\workspace\\assign1\\files\\test_NewArray.txt";
		//outNewArray[0]="F:\\eclipse\\workspace\\assign1\\files\\test2_NewArray.txt";
		//outNewArray[0]="F:\\eclipse\\workspace\\assign1\\files\\OnTheOrigin_NewArray.txt";
		//outNewArray[0]="F:\\eclipse\\workspace\\assign1\\files\\OnTheOrigin_C2_NewArray.txt";
		//outNewArray[0]="F:\\eclipse\\workspace\\assign1\\files\\YouKnowThisSound_NewArray";
	
		HufmannEncoderDecoder mytest= new HufmannEncoderDecoder();
		mytest.Compress(in, out);
		mytest.Decompress(out, outNew);
		//a=mytest.CompressWithArray(in, outArray);
		//b=mytest.DecompressWithArray(outArray, outNewArray);

		
		
		String outEng[]=new String[2];
		//outEng[0]="F:\\eclipse\\workspace\\assign1\\files\\test_CodedEng.txt";
		//outEng[0]="F:\\eclipse\\workspace\\assign1\\files\\test2_CodedEng.txt";
		//outEng[0]="F:\\eclipse\\workspace\\assign1\\files\\test3_CodedEng.txt";
		outEng[0]="F:\\eclipse\\workspace\\Compress\\files\\OnTheOrigin_CodedEng.txt";
		//outEng[0]="F:\\eclipse\\workspace\\assign1\\files\\OnTheOrigin_C2_CodedEng.txt";
		
		
		String outNewEng[]=new String[2];
		//outNewEng[0]="F:\\eclipse\\workspace\\assign1\\files\\test_CodedEngNew.txt";
		//outNewEng[0]="F:\\eclipse\\workspace\\assign1\\files\\test2_CodedEngNew.txt";
		//outNewEng[0]="F:\\eclipse\\workspace\\assign1\\files\\test3_New.txt";
		outNewEng[0]="F:\\eclipse\\workspace\\Compress\\files\\OnTheOrigin_CodedEngNew.txt";
		//outNewEng[0]="F:\\eclipse\\workspace\\assign1\\files\\OnTheOrigin_C2_CodedEngNew.txt";
		
		String outArrayEng[]=new String[2];
		//outArrayEng[0]="F:\\eclipse\\workspace\\assign1\\files\\test_CodedEngArray.txt";
		//outArrayEng[0]="F:\\eclipse\\workspace\\assign1\\files\\test2_CodedEngArray.txt";
		outArrayEng[0]="F:\\eclipse\\workspace\\Compress\\files\\OnTheOrigin_CodedEngArray.txt";
		//outArrayEng[0]="F:\\eclipse\\workspace\\assign1\\files\\OnTheOrigin_C2_CodedEngArray.txt";
		
		
		String outNewArrayEng[]=new String[2];
		//outNewArrayEng[0]="F:\\eclipse\\workspace\\assign1\\files\\test_CodedEngNewArray.txt";
		//outNewArrayEng[0]="F:\\eclipse\\workspace\\assign1\\files\\test2_CodedEngNewArray.txt";
		outNewArrayEng[0]="F:\\eclipse\\workspace\\Compress\\files\\OnTheOrigin_CodedEngNewArray.txt";
		//outNewArrayEng[0]="F:\\eclipse\\workspace\\assign1\\files\\OnTheOrigin_C2_CodedEngNewArray.txt";
		
		//HufmannEnglishEnDe mytest2=new HufmannEnglishEnDe();
		//mytest2.CompressEng(in,outEng);
		//mytest2.DecompressEng(outEng,outNewEng);
		//a=mytest2.CompressWithArrayEng(in,outArrayEng);
		//b=mytest2.DecompressWithArrayEng(outArrayEng,outNewArrayEng);		
	}


}
