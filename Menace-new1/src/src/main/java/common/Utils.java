package common;
import java.util.Random;


public class Utils {
	public static final Random generator = new Random();

	public static final String NEW_LINE = System.getProperty("line.separator");

	/**
	 * rotate
	 * @param lines
	 * @param columns
	 * @param transformedBoard
	 */
    public static void rotate(int lines, int columns, int[] transformedBoard){
    	if(lines != columns) {
    		throw new IllegalArgumentException("Cannot rotate a non square board");
    	}
    	if(transformedBoard == null) {
    		throw new NullPointerException("transformedBoard cannot be null");
    	}
    	if((lines < 1) || (columns < 1) || (transformedBoard.length != lines*columns)){
    		throw new IllegalArgumentException("Rotate called with incorrect arguments");
    	}

    	int[] tmp;
    	tmp = new int[transformedBoard.length];

    	for(int i =0 ; i < transformedBoard.length; i++) {
	                tmp[i] = transformedBoard[i];
	    }

    	for(int i =0 ; i < columns; i++) {
    		for(int j = 0; j < lines ; j++) {
    			transformedBoard[j*lines+i]=tmp[(columns-i-1)*lines+j];
    		}
	    }

    }

	/**
	 * horizontal Flip
	 * @param lines
	 * @param columns
	 * @param transformedBoard
	 */
    public static  void horizontalFlip(int lines, int columns, int[] transformedBoard){
    	if(transformedBoard == null) {
    		throw new NullPointerException("transformedBoard cannot be null");
    	}
    	if((lines < 1) || (columns < 1) || (transformedBoard.length != lines*columns)){
    		throw new IllegalArgumentException("horizontalFlip called with incorrect arguments");
    	}
    	int tmp;
	   	for(int i = 0; i < (lines/2); i++) {
    		for(int j=0 ; j< columns; j++) {
    			tmp = transformedBoard[(lines-i-1)*columns + j];
    			transformedBoard[(lines-i-1)*columns + j] = transformedBoard[i*columns + j];
    			transformedBoard[i*columns + j] = tmp;
    		}
    	}
    }

	/**
	 * vertical Flip
	 * @param lines
	 * @param columns
	 * @param transformedBoard
	 */
    public static  void verticalFlip(int lines, int columns, int[] transformedBoard){
    	if(transformedBoard == null) {
    		throw new NullPointerException("transformedBoard cannot be null");
    	}
    	if((lines < 1) || (columns < 1) || (transformedBoard.length != lines*columns)){
    		throw new IllegalArgumentException("verticalFlip called with incorrect arguments");
    	}
    	int tmp;
	   	for(int i = 0; i < (lines); i++) {
    		for(int j=0 ; j< (columns/2); j++) {
    			tmp = transformedBoard[(i+1)*columns -j-1];
    			transformedBoard[(i+1)*columns -j-1] = transformedBoard[i*columns + j];
    			transformedBoard[i*columns + j] = tmp;
    		}
    	}
    }

	//This is a simple method to test the function of HF, VF, and Rotate on various sizes of board and print the results into the console

	/**
	 * test the flips
	 * @param lines
	 * @param columns
	 */
	private static void test(int lines, int columns){
    	int[] test;
    	test = new int[lines*columns];
    	for(int i = 0 ; i < test.length; i++){
    		test[i] = i;
    	}
    	System.out.println("testing " + lines + " lines and " + columns + " columns.");
    	System.out.println(java.util.Arrays.toString(test));
    	horizontalFlip(lines,columns,test);
    	System.out.println("HF => " + java.util.Arrays.toString(test));
    	horizontalFlip(lines,columns,test);
    	System.out.println("HF => " + java.util.Arrays.toString(test));
    	verticalFlip(lines,columns,test);
    	System.out.println("VF => " + java.util.Arrays.toString(test));
    	verticalFlip(lines,columns,test);
    	System.out.println("VF => " + java.util.Arrays.toString(test));
    	if(lines == columns){
    		for(int i = 0; i < 4; i++) {
		    	rotate(lines,columns,test);
		    	System.out.println("ROT => " + java.util.Arrays.toString(test));    			
    		}
    	}
    }

    public static void main(String[] args){

    	test(2,2);
    	test(2,3);
    	test(3,3);
    	test(4,3);
    	test(4,4);


    }
}