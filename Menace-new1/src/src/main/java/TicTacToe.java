import Gameboards.TicTacToeGame;
import Players.*;
import common.CellField;
import common.GameState;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import java.util.Scanner;
import org.apache.log4j.BasicConfigurator;

public class TicTacToe {

    //logger object to create logs
    static Logger logger = LoggerFactory.getLogger(TicTacToe.class);

    /**
     * To train the menace
     * @param players
     * @return
     */
    public static boolean train(Player[] players) {

        int numberOfPlays = TRAINING_ROUNDS;

        while(numberOfPlays > 0) {
            TicTacToeGame game = new TicTacToeGame();
            //The menace plays first everytime
            int turn = 0;
            players[turn%2].startNewGame(CellField.X);
            players[(turn+1)%2].startNewGame(CellField.O);
            while(game.getGameState() == GameState.PLAYING) {
                players[turn%2].play(game);
                turn++;
            }

            //log game state details
            BasicConfigurator.configure();
            logger.info(game.getstateDetails());

            players[0].gameFinished(game.getGameState());
            players[1].gameFinished(game.getGameState());
            numberOfPlays--;
        }
        System.out.println("player 1: " + players[0]) ;
        System.out.println("player 2: " + players[1]) ;

       return true;

    }

    /**
     * Training rounds constant
     */
    public static final int TRAINING_ROUNDS = 500;
    /**3
     * <b>main</b> of the application.
     *
     * @param args
     *            command line parameters
     */

    public static void main(String[] args) {

       // To play again the menace
        ComputerRandomPlayer random = new ComputerRandomPlayer();
       //human manual play
        HumanPlayer human = new HumanPlayer();

        Player[] players = new Player[2];
        players[0] = new ComputerMenacePlayer();
        boolean stop = false;

        while(!stop) {
            System.out.println("CHOOSE 2 TO PLAY AND TRAIN THE MENANCE");
            System.out.println("(1) Play against MENACE (HUMAN) ");
            System.out.println("(2) Play against MENACE (RANDOM)");
            System.out.println("(q) Quit");

            Scanner in = new Scanner(System.in);

            // to quit the game
            String answer = in.nextLine();
            if(answer.equals("q")) {
                stop = true;

                //To play with random player
            } else if (answer.equals("2")) {
                players[1] = random;
                train(players);

                //To play with human
            } else if (answer.equals("1")) {
                players[1] = human;
                TicTacToeGame game;
                game = new TicTacToeGame();
                int turn = 0;
                players[turn%2].startNewGame(CellField.X);
                players[(turn+1)%2].startNewGame(CellField.O);
                while(game.getGameState() == GameState.PLAYING) {
                    players[turn%2].play(game);
                    turn++;
                }
                players[0].gameFinished(game.getGameState());
                players[1].gameFinished(game.getGameState());

                System.out.println(game);
                System.out.println("Result: " + game.getGameState());
                System.out.println(players[0]);

            }
        }


    }

}