import Gameboards.MenaceTicTacToeGame;
import Gameboards.TicTacToeGame;
import Players.MenacePlayer;
import Players.RandomPPlayer;
import Players.HumanPlayer;
import Players.Player;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class TicTacToeTest {

    /**
     * to if the training round is sucessfull
     */
    @Test
    void trainingRoundTest()
    {
        TicTacToe game = new TicTacToe();
        assertEquals(500,game.TRAINING_ROUNDS);
    }

    /**
     * to check menace is train
     */
    @Test
    void trainTest()
    {
        TicTacToe game = new TicTacToe();
        Player[] players = new Player[2];
        players[0] = new MenacePlayer();
        players[1] =new RandomPPlayer();
        assertTrue(game.train(players));
    }
    /**
     * to check menace is train is not null
     */
    @Test
    void trainTheTicTacToeForNotNullTest()
    {
        TicTacToe game = new TicTacToe();
        Player[] players = new Player[2];
        players[0] = new MenacePlayer();
        players[1] =new RandomPPlayer();
        assertNotNull(game.train(players));
    }

    /**
     * to check if player passes to tic tac toe is not null
     */
    @Test
    void playerTest()
    {
        TicTacToeGame game = new TicTacToeGame();
        Player[] players = new Player[2];
       HumanPlayer p=new HumanPlayer();
        players[0] = new MenacePlayer();
        players[1] =new RandomPPlayer();
        assertNotNull(players);
    }

    /**
     * not null menance tic tac toe
     */
    @Test
    void compMenaceTest(){
        MenaceTicTacToeGame m = new MenaceTicTacToeGame();
        Player[] players = new Player[2];
        players[0] = new MenacePlayer();
        players[1] =new RandomPPlayer();
        assertNotNull(m);
    }

    /**
     * To check symmetry
     */
    @Test
    void checkEqualSymetryTest(){
        TicTacToeGame game = new TicTacToeGame();
        assertEquals(false,game.equalsWithSymmetry(null));
    }

    /**
     * To check game level
     */
    @Test
    void checkGameLevelTest(){
        TicTacToeGame game = new TicTacToeGame();
        assertEquals(0,game.getLevel());
    }

}