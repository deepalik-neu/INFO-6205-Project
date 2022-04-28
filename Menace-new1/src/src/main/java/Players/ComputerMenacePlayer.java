package Players;


import Gameboards.MenaceTicTacToeGame;
import Gameboards.TicTacToeGame;
import common.CellField;
import common.GameState;

import java.util.Hashtable;
import java.util.LinkedList;

public class ComputerMenacePlayer extends Player {

	//To store various match boxes
	Hashtable<Integer, String> ht1 = new Hashtable<Integer, String>();


	// Instance variable to store the list of all symmetrical games
	private LinkedList<LinkedList<MenaceTicTacToeGame>> allGames;

	// Instance variable to store the list of all matchboxes for the X player
	private LinkedList<LinkedList<MenaceTicTacToeGame>> matchboxesForX;

	// Instance variable to store the list of all matchboxes for the O player
	private LinkedList<LinkedList<MenaceTicTacToeGame>> matchboxesForO;

	// Instance variable to store the list of all matchboxes depending on the player
	private LinkedList<LinkedList<MenaceTicTacToeGame>> matchboxes;

	// Instance variable to store the list of all played games
	private LinkedList<MenaceTicTacToeGame> playedGames;

	/**
	 * computer menace player
	 */
	public ComputerMenacePlayer(){
		super();

		// Build the list of all unique games up to symmetry.
		// This part of the constructor is very similar to the ComputerPerfectPlayer
		allGames = new LinkedList<LinkedList<MenaceTicTacToeGame>>();
		allGames.add(new LinkedList<MenaceTicTacToeGame>());
		MenaceTicTacToeGame firstGame = new MenaceTicTacToeGame();
		allGames.get(0).add(firstGame);
		for(int i=1; i<= 9; i++) {
			LinkedList<MenaceTicTacToeGame> newList = new LinkedList<MenaceTicTacToeGame>();
			allGames.add(newList);
			for(MenaceTicTacToeGame game: allGames.get(i-1)){
				if(game.getGameState() == GameState.PLAYING) {
					for(int j = 0;
						j < 9;
						j++) {
						if(game.valueAt(j) == CellField.EMPTY) {
							MenaceTicTacToeGame newGame = new MenaceTicTacToeGame(game,j);
							boolean isNew = true;
							for(MenaceTicTacToeGame existingGame: allGames.get(i)) {
								if(newGame.equalsWithSymmetry(existingGame)) {
									isNew = false;
									break;
								}
							}
							if(isNew) {
								newList.add(newGame);
							}
						}
					}
				}
			}
		}

		matchboxesForX = new LinkedList<LinkedList<MenaceTicTacToeGame>>();
		matchboxesForO = new LinkedList<LinkedList<MenaceTicTacToeGame>>();

		// Build the list of all unique games (matchboxes) for X to choose from
		for (LinkedList<MenaceTicTacToeGame> list: allGames) {
			LinkedList<MenaceTicTacToeGame> newList = new LinkedList<MenaceTicTacToeGame>();
			for (MenaceTicTacToeGame matchbox: list) {
				// We want to add only the games that are not finished,
				// the games that X can play (even level games),
				// and the games that have at least two possible moves (so we exclude level 8)
				if (matchbox.getLevel() % 2 == 0  && matchbox.getGameState() == GameState.PLAYING && matchbox.getLevel() < 8) {
					newList.add(matchbox);
				}
			}
			if (!newList.isEmpty()) {
				matchboxesForX.add(newList);
			}
		}

		// Add beads to the list of all unique games (matchboxes) for X to choose from
		for (LinkedList<MenaceTicTacToeGame> list: matchboxesForX) {
			for (MenaceTicTacToeGame matchboxGame: list) {
				// This instance variable is used to store a list of potentially symmetrical games based on
				// the current game. It is used in order to avoid giving beads to moves that are symmetrically
				// identical to the moves which already hold beads
				LinkedList<MenaceTicTacToeGame> potentiallySymmetricalGames = new LinkedList<MenaceTicTacToeGame>();
				for (int cell = 0; cell < 9; cell++) {
					if (matchboxGame.valueAt(cell) == CellField.EMPTY) {
						// First we create the next game based on this one
						MenaceTicTacToeGame nextGame = new MenaceTicTacToeGame(matchboxGame, cell);
						// Then we give beads only to the games which are unique up to symmetry
						boolean same = false;
						int i = 0;
						while (!same && i < potentiallySymmetricalGames.size()) {
							if (potentiallySymmetricalGames.get(i++).equalsWithSymmetry(nextGame)) {
								same = true;
							}
						}
						if (!same) {
							matchboxGame.giveBeads(cell);
						}
						potentiallySymmetricalGames.add(nextGame);
					}
				}
			}
		}

		// Build the list of all unique games (matchboxes) for O to choose from
		for (LinkedList<MenaceTicTacToeGame> list: allGames) {
			LinkedList<MenaceTicTacToeGame> newList = new LinkedList<MenaceTicTacToeGame>();
			for (MenaceTicTacToeGame matchbox: list) {
				// We want to add only the games that are not finished,
				// the games that O can play (odd level games),
				// and the games that have at least two possible moves (so we exclude level 8)
				if (matchbox.getLevel() % 2 == 1  && matchbox.getGameState() == GameState.PLAYING && matchbox.getLevel() < 8) {
					newList.add(matchbox);
				}
			}
			if (!newList.isEmpty()) {
				matchboxesForO.add(newList);
			}
		}

		// Add beads to the list of all unique games (matchboxes) for O to choose from
		for (LinkedList<MenaceTicTacToeGame> list: matchboxesForO) {
			for (MenaceTicTacToeGame matchboxGame: list) {
				// This instance variable is used to store a list of potentially symmetrical games based on
				// the current game. It is used in order to avoid giving beads to moves that are symmetrically
				// identical to the moves which already hold beads
				LinkedList<MenaceTicTacToeGame> potentiallySymmetricalGames = new LinkedList<MenaceTicTacToeGame>();
				for (int cell = 0; cell < 9; cell++) {
					if (matchboxGame.valueAt(cell) == CellField.EMPTY) {
						// First we create the next game based on this one
						MenaceTicTacToeGame nextGame = new MenaceTicTacToeGame(matchboxGame, cell);
						// Then we give beads only to the games which are unique up to symmetry
						boolean same = false;
						int i = 0;
						while (!same && i < potentiallySymmetricalGames.size()) {
							if (potentiallySymmetricalGames.get(i++).equalsWithSymmetry(nextGame)) {
								same = true;
							}
						}
						if (!same) {
							matchboxGame.giveBeads(cell);
						}
						potentiallySymmetricalGames.add(nextGame);
					}
				}
			}
		}
	}

	// Override the startNewGame method from the superclass Player
	@Override
	public void startNewGame(CellField myMove) {
		// Call the superclass method startNewGame and pass as parameter the
		// variable myMove in order to initialize the game
		super.startNewGame(myMove);

		// Choose which list of matchboxes to play from depending on myMove (X or O)
		if (myMove == CellField.X) {
			matchboxes = matchboxesForX;
		}
		else if (myMove == CellField.O) {
			matchboxes = matchboxesForO;
		}
		// Initialize the list of played games for this game. This ensures that it is
		// re-initialized at every play
		playedGames = new LinkedList<MenaceTicTacToeGame>();
	}

	/**
	 * play with menace
	 * @param game
	 */
	public void play(TicTacToeGame game) {
		if(game.getLevel() == game.lines*game.columns){
			throw new IllegalArgumentException("Game is finished already!");
		}

		// If the game level is 7 or below, then we have more than one choice to make for the
		// move to play. So, we have to look through all the matchboxes and pick a random
		// bead from the matchbox that matches the current game, and then to play that move
		if (game.getLevel() < 8) {
			for (LinkedList<MenaceTicTacToeGame> list: matchboxes) {
				for (MenaceTicTacToeGame matchbox: list) {
					if(game.equalsWithSymmetry(matchbox)) {
						// Pick the move to play. We have to take into account the transformation.
						// Since this game is identical up to symmetry, then we have to transform
						// the game a few times to match the current one; so we choose the move
						// to play based on that transformation, so we have to take into account
						// the transformedBoard of the game that has been transformed
						game.play(game.transformedBoard[matchbox.chooseMove()]);
						playedGames.add(matchbox);
						return;
					}
				}
			}
		}
		// If the game level is 8, then we are at the last round of the game. Since the matchboxes
		// corresponding to level 8 were not added during the initialization of the game, then
		// we manually go through the game cell by cell and play the only empty cell remaining
		else {
			for (int cell = 0; cell < 9; cell++) {
				if (game.valueAt(cell) == CellField.EMPTY) {
					// Play the last empty cell
					game.play(cell);
					return;
				}
			}
		}
		// Should never reach here
		throw new IllegalStateException("Game not found: " + game);
	}

	// Override the gameFinished method. The goal of this method is to go through every game
	// that was played in the playedGames list and to give or remove beads depending on the
	// outcome of the game. We add 3 beads for a win, we add 1 bead for a draw, and we
	// remove 1 bead for a loss
	@Override
	public void gameFinished(GameState result) {
		super.gameFinished(result);
		if (result == GameState.DRAW) {
			for (MenaceTicTacToeGame playedGame: playedGames) {
				playedGame.updateNumberOfBeads(1);
			}
		}
		else if (result == GameState.XWIN) {
			if (myMove == CellField.X) {
				for (MenaceTicTacToeGame playedGame: playedGames) {
					playedGame.updateNumberOfBeads(3);
				}
			}
			else {
				for (MenaceTicTacToeGame playedGame: playedGames) {
					playedGame.updateNumberOfBeads(-1);
				}
			}
		}
		else if (result == GameState.OWIN) {
			if (myMove == CellField.O) {
				for (MenaceTicTacToeGame playedGame: playedGames) {
					playedGame.updateNumberOfBeads(3);
				}
			}
			else {
				for (MenaceTicTacToeGame playedGame: playedGames) {
					playedGame.updateNumberOfBeads(-1);
				}
			}
		}
	}
}