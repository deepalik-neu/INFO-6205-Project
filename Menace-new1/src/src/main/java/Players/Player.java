//Abstract class player is an interface for all player types
package Players;
import common.*;
import Gameboards.TicTacToeGame;
public abstract class Player {

	//Game data of the player for the number of wins, loss, draw
	private int numberOfWin;
	private int numberOfLoss;
	private int numberOfDraw;
	private int numberOfGame;

	//to collect data to show statistics for few games
	private char[] slidingWindow;
	private int currentSlidingIndex;
	protected CellField myMove;

	public static final int WINDOWSIZE = 50;
	public static final char IWIN = 'w';
	public static final char ILOSE = 'l';
	public static final char IDRAW = 'd';

	/**
	 * Player Constructor
	 */
	public Player(){
		numberOfWin = 0;
		numberOfLoss= 0;
		numberOfDraw= 0;
		numberOfGame= 0;
		slidingWindow = new char[WINDOWSIZE];
		currentSlidingIndex= 0;
	}

	/**
	 * Abstract method
	 * @param game
	 */
	public abstract void play(TicTacToeGame game);

	/**
	 * Start new game and initiate the move
	 * @param myMove
	 */
	public void startNewGame(CellField myMove){
		this.myMove = myMove;
	}

	/**
	 * Once game is over get results
	 * @param result
	 */
	public void gameFinished(GameState result){
		if(result == GameState.DRAW) {
			numberOfDraw++;
			slidingWindow[currentSlidingIndex] = IDRAW;
		} else if (result == GameState.XWIN) {
			if(myMove == CellField.X) {
				numberOfWin++;
				slidingWindow[currentSlidingIndex] = IWIN;
			} else {
				numberOfLoss++;
				slidingWindow[currentSlidingIndex] = ILOSE;
			}
		} else if (result == GameState.OWIN) {
			if(myMove == CellField.O) {
				numberOfWin++;
				slidingWindow[currentSlidingIndex] = IWIN;
			} else {
				numberOfLoss++;
				slidingWindow[currentSlidingIndex] = ILOSE;
			}
		} else {
			throw new IllegalArgumentException("Result can't be " + result);
		}
		numberOfGame++;
		currentSlidingIndex = (currentSlidingIndex+1)%WINDOWSIZE;
	}

	/**
	 *
	 * @return
	 */
	public String toString(){
		String result;

		result = "This player won the following number of  " + numberOfWin + " games, lost following number of"
				+ numberOfLoss + " games and following number of " + numberOfDraw + " were draws.";

		if(numberOfGame >= WINDOWSIZE) {
			int w = 0;
			int l = 0;
			int d = 0;
			for(char c: slidingWindow) {
				switch(c){
					case IWIN:
						w++;
						break;
					case ILOSE:
						l++;
						break;
					case IDRAW:
						d++;
						break;
					default:
						System.out.println("Unknown value: " + c);
				}
			}
			result += " Over the last few " + WINDOWSIZE + " games the player  won " + w + " games, lost "
					+ l + " games and " + d + " were draws.";
		}
		return result;
	}

}