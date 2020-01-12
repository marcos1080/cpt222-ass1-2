package au.edu.rmit.cpt222.view;

import java.util.logging.Level;
import java.util.logging.Logger;

import au.edu.rmit.cpt222.model.GameEngineCallbackImpl;
import au.edu.rmit.cpt222.model.GameEngineImpl;
import au.edu.rmit.cpt222.model.SimplePlayer;
import au.edu.rmit.cpt222.model.exceptions.InsufficientFundsException;
import au.edu.rmit.cpt222.model.interfaces.GameEngine;
import au.edu.rmit.cpt222.model.interfaces.Player;

/**
 * Simple console client for SADI Assignment 1
 * 
 * @author Mikhail Perepletchikov
 * 
 */
public class Client {
	private static Logger logger = Logger.getLogger("assignment1");
	public final static int INITIAL_DELAY = 1;
	public final static int FINAL_DELAY = 100;
	public final static int DELAY_INCREMENT = 20;
	public final static int SAMPLE_BET1 = 10;
	public final static int SAMPLE_BET2 = 20;
	public final static int SAMPLE_CREDIT_POINTS1 = 100;
	public final static int SAMPLE_CREDIT_POINTS2 = 150;

	public static void main(String args[]) {

		// initialise the Model (GameEngine)
		final GameEngine gameEngine = new GameEngineImpl();

		// create two sample players
		Player theShark = new SimplePlayer("1", "The Shark",
				SAMPLE_CREDIT_POINTS1);
		Player theRoller = new SimplePlayer("2", "The Roller",
				SAMPLE_CREDIT_POINTS2);

		// attach sample GameEngineCallbackImpl to the GameEngine
		gameEngine.addGameEngineCallback(new GameEngineCallbackImpl());

		// add players to the model
		gameEngine.addPlayer(theShark);
		gameEngine.addPlayer(theRoller);

		// set test bets
		try {
			theShark.placeBet(SAMPLE_BET1);
			// roll dice for test players
			gameEngine.rollPlayer(theShark, INITIAL_DELAY, FINAL_DELAY,
					DELAY_INCREMENT);
			theRoller.placeBet(SAMPLE_BET2);
			gameEngine.rollPlayer(theRoller, INITIAL_DELAY, FINAL_DELAY,
					DELAY_INCREMENT);
		}
		// test insufficient funds exception
		catch (InsufficientFundsException e) {
			logger.log(Level.INFO, e.getMessage());
		}

		// all players have "rolled" so now house rolls and results are
		// calculated
		gameEngine.calculateResult();

		// log final player data (including updated balance) so we can check
		// correctness
		for (Player player : gameEngine.getAllPlayers())
			logger.log(Level.INFO, player.toString());
	}
}
