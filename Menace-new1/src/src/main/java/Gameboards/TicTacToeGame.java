/**
 * The class TicTacToeGame contains the grid and tracks its progress.
 * It automatically maintains the current state of
 * the game as players are making moves.
 *
 * @author Guy-Vincent Jourdan, University of Ottawa
 * @author Jacob Kszan
 * @author Sebastian Larrivee
 */

package Gameboards;

import common.CellField;
import common.GameState;
import common.TransformBoard;
import common.Utils;


public class TicTacToeGame {

	//public static Logger logger = LoggerFactory.getLogger(TicTacToeGame.class);
	//The game board, stored in an array
	/**
	 * The board of the game, stored as a single array.
	 */
	private CellField[] board;


	/**
	 * level records the number of rounds that have been
	 * played so far. Starts at 0.
	 */
	private int level;

	/**
	 * gameState records the current state of the game.
	 */
	private GameState gameState;


	/**
	 * lines is the number of lines in the grid
	 */
	public final int lines;

	/**
	 * columns is the number of columns in the grid
	 */
	public final int columns;


	/**
	 * sizeWin is the number of cell of the same type
	 * that must be aligned to win the game
	 */
	public final int sizeWin;


	/**
	 * the list of all possible symmetries of a nxn board
	 * can be found be iterating through the transformations
	 * listed in allTransformations
	 */
	private  static final TransformBoard[] allTransformationsSquare =
			{TransformBoard.ID, TransformBoard.ROT, TransformBoard.ROT,
					TransformBoard.ROT, TransformBoard.HSYM, TransformBoard.ROT,
					TransformBoard.ROT, TransformBoard.ROT};

	/**
	 * the list of all possible symmetries of a nxm board
	 * can be found be iterating through the transformations
	 * listed in allTransformations
	 */
	private  static final TransformBoard[] allTransformationsNonSquare =
			{TransformBoard.ID, TransformBoard.HSYM,
					TransformBoard.VSYM, TransformBoard.HSYM};

	/**
	 * instance variable to record the transformations for that particular board
	 */
	private  TransformBoard[] allTransformations;

	/**
	 * records how far we are in the list of transformations
	 * in allTransformations
	 */
	private int currentTransformation;

	/**
	 * transformedBoard is used to iterate through all
	 * game symetries. We use an indirection, so as to not
	 * modify the instance variable board. In the
	 * current symmetrythe cell at index i is accessed
	 * via board[transformedBoard[i]]
	 */
	public int[] transformedBoard;


	/**
	 * default constructor, for a game of 3x3, which must
	 * align 3 cells
	 */
	public TicTacToeGame(){
		this(3,3,3);
	}

	/**
	 * constructor allowing to specify the number of lines
	 * and the number of columns for the game. 3 cells must
	 * be aligned.
	 * @param lines
	 *  the number of lines in the game
	 * @param columns
	 *  the number of columns in the game
	 */
	public TicTacToeGame(int lines, int columns){
		this(lines, columns, 3);
	}

	/**
	 * constructor allowing to specify the number of lines
	 * and the number of columns for the game, as well as
	 * the number of cells that must be aligned to win.
	 * @param lines
	 *  the number of lines in the game
	 * @param columns
	 *  the number of columns in the game
	 * @param sizeWin
	 *  the number of cells that must be aligned to win.
	 */
	public TicTacToeGame(int lines, int columns, int sizeWin){
		this.lines = lines;
		this.columns = columns;
		this.sizeWin = sizeWin;
		board = new CellField[lines*columns];
		for(int i = 0; i < lines*columns ; i ++) {
			board[i] = CellField.EMPTY;
		}
		level = 0;
		gameState = GameState.PLAYING;
		if(lines == columns) {
			allTransformations = allTransformationsSquare;
		} else {
			allTransformations = allTransformationsNonSquare;
		}
		transformedBoard = new int[lines*columns];

		reset();

	}


	/**
	 * constructor allowing to create a new game based
	 * on an exisiting game, on which one move is added.
	 * The resulting new instance is a deep copy of
	 * the game reference passed as parameter.
	 * @param base
	 *  the TicTacToeGame instance on which this new game
	 *  is based
	 * @param next
	 *  the index of the next move.
	 */

	public TicTacToeGame(TicTacToeGame base, int next){

		lines = base.lines;
		columns = base.columns;
		sizeWin = base.sizeWin;

		if(next < 0 || next >= lines*columns){
			throw new IllegalArgumentException("Illegal position: " + next);
		}

		if(base == null){
			throw new IllegalArgumentException("Illegal base game: null value");
		}

		if(base.board[next] != CellField.EMPTY) {
			throw new IllegalArgumentException("CellField not empty: " + next + " in game " + base);
		}

		board = new CellField[lines*columns];
		for(int i = 0; i < lines*columns ; i ++) {
			board[i] = base.board[i];
		}

		// allTransformations doesn't change so we can share the reference here
		allTransformations = base.allTransformations;


		level = base.level+1;

		board[next] = base.nextCellValue();

		if(base.gameState != GameState.PLAYING) {
			System.out.println("hum, extending a finished game... keeping original winner");
			gameState = base.gameState;
		} else {
			setGameState(next);
		}

		reset();
	}


	/**
	 * Compares this instance of the game with the
	 * instance passed as parameter. Return true
	 * if and only if the two instance represent
	 * the same state of the game.
	 * @param o
	 *  the  instance to be compared with this one
	 */

	public boolean equals(Object o) {
		if(o == null) {
			return false;
		}
		if(getClass() != o.getClass()){
			return false;
		}

		TicTacToeGame other = (TicTacToeGame)o;

		if((level != other.level) 	||
				(lines != other.lines) 	||
				(columns != other.columns)||
				(sizeWin != other.sizeWin)){
			return false;
		}
		for(int i = 0; i < board.length ; i++ ) {
			if(board[i]!= other.board[i]) {
				return false;
			}
		}
		return true;
	}

	/**
	 * getter for the variable level
	 * @return
	 * 	the value of level
	 */
	public int getLevel(){
		return level;
	}


	/**
	 * getter for the variable gameState
	 * @return
	 * 	the value of gameState
	 */
	public GameState getGameState(){
		return gameState;
	}

	/**
	 * returns the cellValue that is expected next,
	 * in other word, which played (X or O) should
	 * play next.
	 * This method does not modify the state of the
	 * game.
	 * @return
	 *  the value of the enum CellField corresponding
	 * to the next expected value.
	 */
	public CellField nextCellValue(){
		return (level%2 == 0) ? CellField.X : CellField.O;
	}

	/**
	 * returns the value  of the cell at
	 * index i.
	 * If the index is invalid, an error message is
	 * printed out. The behaviour is then unspecified
	 * @param i
	 *  the index of the cell in the array board
	 * @return
	 *  the value at index i in the variable board.
	 */
	public CellField valueAt(int i) {

		if(i < 0 || i >= lines*columns){
			throw new IllegalArgumentException("Illegal position: " + i);
		}

		return board[i];
	}

	/**
	 * This method is call by the next player to play
	 * at the cell  at index i.
	 * If the index is invalid, an error message is
	 * printed out. The behaviour is then unspecified
	 * If the chosen cell is not empty, an error message is
	 * printed out. The behaviour is then unspecified
	 * If the move is valide, the board is updated, as well
	 * as the state of the game.
	 * To faciliate testing, is is acceptable to keep playing
	 * after a game is already won. If that is the case, the
	 * a message should be printed out and the move recorded.
	 * the  winner of the game is the player who won first
	 * @param i
	 *  the index of the cell in the array board that has been
	 * selected by the next player
	 */
	public void play(int i) {

		if(i < 0 || i >= lines*columns){
			throw new IllegalArgumentException("Illegal position: " + i);
		}
		if(board[i] != CellField.EMPTY) {
			throw new IllegalArgumentException("CellField not empty: " + i + " in game " + toString());
		}

		board[i] = nextCellValue();
		level++;
		if(gameState != GameState.PLAYING) {
			System.out.println("hum, extending a finished game... keeping original winner");
		} else {
			setGameState(i);
		}

	}


	/**
	 * A helper method which updates the gameState variable
	 * correctly after the cell at index i was just set.
	 * The method assumes that prior to setting the cell
	 * at index i, the gameState variable was correctly set.
	 * it also assumes that it is only called if the game was
	 * not already finished when the cell at index i was played
	 * (the the game was playing). Therefore, it only needs to
	 * check if playing at index i has concluded the game
	 *
	 * @param index
	 *  the index of the cell in the array board that has just
	 * been set
	 */
	private void setGameState(int index){

		int left = Math.min(sizeWin-1,index%columns);
		int right= Math.min(sizeWin-1,columns - (index%columns +1));
		if( (countConsecutive(index-1, left,-1,board[index]) +
				countConsecutive(index+1, right,1,board[index]))
				>= sizeWin-1 ) {
			setGameState(board[index]);
			return;
		}

		int up 	= Math.min(sizeWin-1,index/columns);
		int down= Math.min(sizeWin-1, lines - (index/columns +1));
		if( (countConsecutive(index-columns, up,-columns,board[index]) +
				countConsecutive(index+columns, down,columns,board[index]))
				>= sizeWin-1 ) {
			setGameState(board[index]);
			return;
		}

		int upLeft = Math.min(up, left);
		int downRight= Math.min(down, right);
		if( (countConsecutive(index-(columns+1), upLeft,-(columns+1),board[index]) +
				countConsecutive(index+(columns+1), downRight,columns+1,board[index]))
				>= sizeWin-1 ) {
			setGameState(board[index]);
			return;
		}

		int upRight= Math.min(up, right);
		int downLeft = Math.min(down, left);
		if( (countConsecutive(index-(columns-1), upRight,-(columns-1),board[index]) +
				countConsecutive(index+(columns-1), downLeft,columns-1,board[index]))
				>= sizeWin-1 ) {
			setGameState(board[index]);
			return;
		}



		if (level == lines*columns) {
			gameState = GameState.DRAW;
		} else {
			gameState = GameState.PLAYING;
		}

	}

	/**
	 * Get the state of the board to log
	 * @return String
	 */
	public String  getstateDetails(){
		String s="";
		for(int i=0;i<board.length;i++)
		{
			s=s+"Position:"+(i+1)+" State:"+board[i];
		}
		return s;
	}

	/**
	 * Get the count of the board
	 * @param startingPosition
	 * @param numberOfSteps
	 * @param stepGap
	 * @param value
	 * @return
	 */
	private int countConsecutive(int startingPosition, int numberOfSteps,
								 int stepGap, CellField value){

		int result= 0;
		for(int i = 0; i < numberOfSteps;i++){
			if(board[startingPosition + i*stepGap] != value)
				break;
			result++;
		}
		return result;

	}

	/**
	 * Set the game set to win or draw
	 * @param value
	 */
	private void setGameState(CellField value){
		switch(value){
			case X:
				gameState = GameState.XWIN;
				break;
			case O:
				gameState = GameState.OWIN;
				break;
			default:
				throw new IllegalArgumentException("cannot set Game State to value " + value);
		}
	}


	/**
	 * Returns a String for the result
	 *
	 * @return
	 *  String representation of the game
	 */

	public String toString(){
		String res = "";
		for(int i = 0; i < lines ; i++){
			if(i>0) {
				for(int j = 0; j < 4*columns - 1; j++){
					res+="-";
				}
				res+= Utils.NEW_LINE;
			}
			for(int j = 0; j < columns ; j++){
				switch(board[i*columns + j]){
					case X:
						res+= " X ";
						break;
					case O:
						res+= " O ";
						break;
					default:
						res+=  "   ";
				}
				if(j<columns - 1){
					res += "|";
				} else{
					res += Utils.NEW_LINE;
				}
			}
		}
		return res ;

	}


	/**
	 * restarts the list of symmetries
	 */
	public void reset(){
		currentTransformation = -1;
		transformedBoard = new int[lines*columns];
	}

	/**
	 * checks if there are more symmetries to go through
	 *
	 * @return
	 *   true iff there are additional symmetries
	 */
	public boolean hasNext(){
		return currentTransformation < (allTransformations.length-1);
	}

	/**
	 * computes the next symmetries and stores it in
	 * the array "transform".
	 * Requires that this.hasNext() == true
	 */
	public void next(){


		if(!hasNext()){
			throw new IllegalStateException("No next transformation");
		}
		currentTransformation++;
		transform(allTransformations[currentTransformation]);
	}

	/**
	 * Applies the transformation specified as parameter
	 * to transformedBoard
	 */
	private void transform(TransformBoard type){

		switch(type) {
			case ID :
				for(int i =0 ; i < board.length; i++) {
					transformedBoard[i]=i;
				}
				break;
			case ROT :
				Utils.rotate(lines, columns,transformedBoard);
				break;
			case VSYM :
				Utils.verticalFlip(lines, columns,transformedBoard);
				break;
			case HSYM :
				Utils.horizontalFlip(lines, columns,transformedBoard);
				break;
			default:
				System.out.println("Unknow type: " + type);
		}
	}



	/**
	 * Compares this instance of the game with the
	 * instance passed as parameter. Return true
	 * if and only if the two instance represent
	 * the same state of the game, up to symmetry.
	 * @param other
	 *  the TicTacToeGame instance to be compared with this one
	 */
	public boolean equalsWithSymmetry(TicTacToeGame other){

		if(other == null) {
			return false;
		}
		if((level != other.level) 	||
				(lines != other.lines) 	||
				(columns != other.columns)||
				(sizeWin != other.sizeWin)){
			return false;
		}

		reset();
		while(hasNext()){
			next();
			boolean different = false;
			for(int i = 0; i < transformedBoard.length ; i++ ) {
				if(board[transformedBoard[i]]!= other.board[i]) {
					different = true;
					break;
				}
			}
			if(!different)
				return true;
		}
		return false;
	}

	/**
	 * Returns a String representation of the game as currently trasnsformed
	 *
	 * @return
	 *  String representation of the game
	 */

	public String toStringTransformed(){
		if(transformedBoard == null) {
			throw new NullPointerException("transformedBoard not initialized");
		}

		String res = "";
		for(int i = 0; i < lines ; i++){
			if(i>0) {
				for(int j = 0; j < 4*columns - 1; j++){
					res+="-";
				}
				res+= Utils.NEW_LINE;
			}
			for(int j = 0; j < columns ; j++){
				switch(board[transformedBoard[i*columns + j]]){
					case X:
						res+= " X ";
						break;
					case O:
						res+= " O ";
						break;
					default:
						res+=  "   ";
				}
				if(j<columns - 1){
					res += "|";
				} else{
					res += Utils.NEW_LINE;
				}
			}
		}
		return res ;

	}

 }