package Players;
import common.*;
import Gameboards.TicTacToeGame;

import java.util.Scanner;

public class HumanPlayer extends Player {


	/**
	 * play the tictoe with human
	 * @param game
	 */
	public  void play(TicTacToeGame game) {

		if(game.getLevel() == game.lines*game.columns){
			throw new IllegalArgumentException("Game is finished already!");
		}

		boolean success = false;

		while(!success) {
        	System.out.println(game);
        	System.out.print(game.nextCellValue() + " to play: ");

			Scanner in = new Scanner(System.in);

			String answer = in.nextLine();
        	int value;
        	try {
                value = Integer.parseInt(answer)-1;
            } catch (NumberFormatException e) {
            	value = -1;
            }
            if(value < 0 || value >= (game.lines*game.columns)){
            	System.out.println("Please check the value should be between 1 and " + (game.lines*game.columns));
            } else if(game.valueAt(value) != CellField.EMPTY) {
            	System.out.println("This cell field has already been played please choose another.");
            } else {
            	game.play(value);
            	success = true;
            }
        }

	}
}