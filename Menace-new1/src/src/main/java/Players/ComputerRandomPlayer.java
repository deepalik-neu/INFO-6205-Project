package Players;
import common.*;
import Gameboards.TicTacToeGame;

public class ComputerRandomPlayer extends Player {

	/**
	 * play with computer random player
	 * @param game
	 */
	public  void play(TicTacToeGame game) {

		// conclude game is finish
		if(game.getLevel() == game.lines*game.columns){
			throw new IllegalArgumentException("Game is finished already!");
		}

		int choice;
		do {
			choice = Utils.generator.nextInt(game.lines*game.columns);
		} while (game.valueAt(choice) != CellField.EMPTY);

		game.play(choice);
	}

}