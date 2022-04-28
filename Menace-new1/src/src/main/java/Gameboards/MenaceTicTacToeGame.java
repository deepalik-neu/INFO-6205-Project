package Gameboards;

import common.GameState;
import common.Utils;

public class MenaceTicTacToeGame extends TicTacToeGame {

	// Instance variable to store the list of beads that
	// will be held by each position of the board
	private int[] beads;

	// Instance variable that will record the total number
	// of beads for each game. This variable is important
	// when generating a random bead based on the number
	// of beads
	private int totalNumberOfBeads;

	// Instance variable to record the cell that was played
	// during a game. This variable is important when we
	// go back and add/remove beads according to the game
	// outcome
	private int cellPlayed;

	/**
	 * constructor MenaceTicTacToeGame
	 */
	public MenaceTicTacToeGame() {
		super(3,3,3);

		// First we start with 0 beads for each move (each cell)
		beads = new int[9];
		for (int cell = 0; cell < 9; cell++) {
			beads[cell] = 0;
		}
		totalNumberOfBeads = 0;
	}

	/**
	 * constructor MenaceTicTacToeGame
	 */
	public MenaceTicTacToeGame(MenaceTicTacToeGame base, int next) {
		super(base, next);

		// First we start with 0 beads for each move (each cell)
		beads = new int[9];
		for (int cell = 0; cell < 9; cell++) {
			beads[cell] = 0;
		}
		totalNumberOfBeads = 0;
	}



	/**
	 * This method is used to give beads to each move in the game
	 * 	(each cell) when building the list of matchboxes to play from.
	 * 	Each level will record a different number of beads
	 * @param cell
	 */
	public void giveBeads(int cell) {
		if (cell < 0 || cell > 8) {
			throw new IllegalArgumentException("Illegal argument inputted");
		}
		if ((getLevel() == 0 || getLevel() == 1)) {
			beads[cell] += 8;
			totalNumberOfBeads += 8;
		}
		else if ((getLevel() == 2 || getLevel() == 3)) {
			beads[cell] += 4;
			totalNumberOfBeads += 4;
		}
		else if ((getLevel() == 4 || getLevel() == 5)) {
			beads[cell] += 2;
			totalNumberOfBeads += 2;
		}
		else if ((getLevel() == 6 || getLevel() == 7 || getLevel() == 8)) {
			beads[cell] += 1;
			totalNumberOfBeads += 1;
		}
	}

	// This method is used to update the number of beads once the game is finished.
	// We can pass it a negative number to remove a bead

	/**
	 * update Number Of Beads
	 * @param numberOfBeads
	 */
	public void updateNumberOfBeads(int numberOfBeads) {
		// We only remove a bead if that is not the last one in that game. So, once
		// we reach a point where we only have 1 bead left, we don't remove that bead
		if (totalNumberOfBeads > 1) {
			beads[cellPlayed] += numberOfBeads;
			totalNumberOfBeads += numberOfBeads;
		}
	}

	// This method is used to generate the random position to play from during a game

	/**
	 * choose move
	 * @return
	 */
	public int chooseMove() {
		if(getGameState() != GameState.PLAYING){
			throw new IllegalStateException("Game already finished");
		}
		// Generate a random number that is equal to or smaller than the total
		// number of beads in this board
		int random = Utils.generator.nextInt(totalNumberOfBeads) + 1;
		// Use a sum and go through the list of beads in each cell
		int sum = 0;
		int cell = -1;
		do {
			cell++;
			// Keep adding the number of beads
			sum += beads[cell];
		} while (sum < random);
		// Once we reach a sum that is larger than the random number generated, we
		// can chose a cell and return it. If a particular cell has a very large
		// relative number of beads, then the sum that is added will have a higher
		// chance of exceeding the random number that was generated, thus making
		// that cell more likely to be played
		cellPlayed = cell;
		return cell;
	}
}