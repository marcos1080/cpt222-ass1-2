package au.edu.rmit.cpt222.test.harness;

import java.util.Collection;

import au.edu.rmit.cpt222.model.GameEngineImpl;
import au.edu.rmit.cpt222.model.SimplePlayer;
import au.edu.rmit.cpt222.model.exceptions.InsufficientFundsException;
import au.edu.rmit.cpt222.model.interfaces.DicePair;
import au.edu.rmit.cpt222.model.interfaces.GameEngine;
import au.edu.rmit.cpt222.model.interfaces.GameEngine.GameStatus;
import au.edu.rmit.cpt222.model.interfaces.GameEngineCallback;
import au.edu.rmit.cpt222.model.interfaces.Player;

/**
 * Test Harness for SADI Assignment 1 (Dice Game)
 * 
 */
public class TestHarness {

	/**
	 * Sample Callback implementation to capture result callback calls for basic
	 * outcome/result testing.
	 */
	class ResultCallback implements GameEngineCallback {
		private int countGameResult = 0;
		private int countHouseRollOutcome = 0;
		private int countPlayerRollOutcome = 0;

		public void clearResults() {
			this.countGameResult = this.countPlayerRollOutcome = this.countHouseRollOutcome = 0;
		}

		@Override
		public void gameResult(Player player, GameStatus status,
				GameEngine engine) {
			System.out
			.println("CALLBACK: gameResult() called for test #"
					+ TestHarness.this.testNumber + ", game outcome ="
					+ status);
			this.countGameResult++;
		}

		@Override
		public void houseRoll(DicePair dicePair, GameEngine engine) {
			// TODO not used
		}

		@Override
		public void houseRollOutcome(DicePair result, GameEngine engine) {
			System.out.println("CALLBACK: houseRollOutcome() called for test #"
					+ TestHarness.this.testNumber + ", result=" + result);
			this.countHouseRollOutcome++;
		}

		@Override
		public void playerRoll(Player player, DicePair dicePair,
				GameEngine engine) {
			// TODO not used
		}

		@Override
		public void playerRollOutcome(Player player, DicePair result,
				GameEngine engine) {
			System.out
			.println("CALLBACK: playerRollOutcome() called for test #"
					+ TestHarness.this.testNumber + ", result="
					+ result);
			this.countPlayerRollOutcome++;
		}
	}

	public final static int DEFAULT_BET = 100;
	public final static int INITIAL_DELAY = 1;
	public final static int FINAL_DELAY = 100;
	public final static int DELAY_INCREMENT = 20;
	public final static int DEFAULT_CREDIT_POINTS1 = 1000;
	public final static int DEFAULT_CREDIT_POINTS2 = 700;
	public final static int DEFAULT_CREDIT_POINTS3 = 500;

	public static void main(String args[]) {
		new TestHarness();
	}

	private float score = 0.0f;
	private int testNumber = 0;
	private GameEngineImpl gameEngine = new GameEngineImpl();
	private Collection<Player> players;
	private ResultCallback resultCallBack = new ResultCallback();
	private Player theGambler = new SimplePlayer("1", "The Gambler",
			DEFAULT_CREDIT_POINTS1);
	private Player theHustler = new SimplePlayer("2", "The Hustler",
			DEFAULT_CREDIT_POINTS2);
	private Player theCasual = new SimplePlayer("3", "The Casual",
			DEFAULT_CREDIT_POINTS3);

	public TestHarness() {
		// basic tests
		testAddPlayers();
		testRemovePlayer();
		testInvalidBet();
		testValidBet();
		testPreCondition();

		// game execution tests
		this.gameEngine.addGameEngineCallback(this.resultCallBack);
		testFirstGame();
		testSecondGame();
		testCallbackCalls();

		System.out.println("\nThe test harness score is: " + this.score
				+ "/ 8.5 marks. NOTE: 1.5 additional mark will be"
				+ " allocated based on callback console outputs.");
	}

	// helper method for handling unexpected exceptions
	private void handleException(Exception e, int testNumber, float partialScore) {
		System.out.println("*EXCEPTION* in test #" + testNumber + ", "
				+ e.getMessage());
		System.out.println("Partial Score: "
				+ String.format("(%.1f marks)", partialScore));
	}

	// reset one of the players for testing purposes
	private void resetGambler() {
		this.gameEngine.removePlayer(this.theGambler.getPlayerId());
		this.theGambler = new SimplePlayer("1", "The Gambler",
				DEFAULT_CREDIT_POINTS1);
		this.gameEngine.addPlayer(this.theGambler);
	}

	/**
	 * Test the addition of 3 players to the system. The expected size of the
	 * Collection is 3.
	 */
	private void testAddPlayers() {
		try {
			this.testNumber = 1;
			System.out.println("Test 01 - Adding Players (1.0 mark)");

			this.gameEngine.addPlayer(this.theGambler);
			this.gameEngine.addPlayer(this.theHustler);
			this.gameEngine.addPlayer(this.theCasual);

			this.players = this.gameEngine.getAllPlayers();
			// test player.toString() implementation
			for (Player player : this.players)
				System.out.println(player);

			if (this.gameEngine.getAllPlayers().size() == 3) {
				System.out.println("You successfully added three "
						+ "players to the game (1.0 mark)");
				this.score += 1.0;
			} else
				System.out.println("One or more of your players is "
						+ "incorrectly missing from the game (0.0 marks)");
		} catch (Exception e) {
			handleException(e, this.testNumber, 0);
		}
	}

	/**
	 * Test the number of the outcome callback calls.
	 */
	private void testCallbackCalls() {
		float localScore = 0.0f;
		try {
			this.testNumber = 8;
			System.out
			.println("\nTest 08 - Test Outcome Callback Calls (1.5 mark)");
			this.resultCallBack.clearResults();

			this.theCasual.placeBet(DEFAULT_BET);
			try {
				this.gameEngine.placeBet(this.theCasual, DEFAULT_BET);
			} catch (Exception e) {
				// should not be thrown here
				handleException(e, this.testNumber, 0);
			}
			this.gameEngine.rollPlayer(this.theCasual, INITIAL_DELAY,
					FINAL_DELAY, DELAY_INCREMENT);
			this.gameEngine.calculateResult();

			// outcomes should be called only once
			if ((this.resultCallBack.countPlayerRollOutcome == 1)
					&& (this.resultCallBack.countHouseRollOutcome == 1)) {
				System.out
				.println("Roll outcomes called once each for player and house: (1.0 mark)");
				this.score += 1.0;
			}
			// outcomes should be called only once
			if (this.resultCallBack.countGameResult == 1) {
				System.out.println("Game outcome called once only: (0.5 mark)");
				this.score += 0.5;
			} else
				System.out.println("Incorrect number of results: " + "player="
						+ this.resultCallBack.countPlayerRollOutcome
						+ ", house="
						+ this.resultCallBack.countHouseRollOutcome
						+ " (0.0 marks)");
		} catch (Exception e) {
			handleException(e, this.testNumber, localScore);
		}
	}

	/**
	 * Execute game once and test the results.
	 */
	private void testFirstGame() {
		try {
			this.testNumber = 6;
			System.out
			.println("\nTest 06 - Test First Roll for The Casual (1.5 marks)");

			testRollImpl(this.theCasual, 1.5f);
		} catch (Exception e) {
			handleException(e, this.testNumber, 0);
		}
	}

	/**
	 * Test for a bet that exceeds a player's available points. The placeBet
	 * method should throw InsufficientFundsException.
	 */
	private void testInvalidBet() {
		try {
			this.testNumber = 3;
			System.out
			.println("\nTest 03 - Handling an Invalid Bet (1.0 mark)");

			// reset "gambler"
			this.resetGambler();

			// place invalid bet
			this.theGambler.placeBet(1100);

			// should not reach this line because bet exceeds player's balance
			System.out.println(this.theGambler.getPlayerName()
					+ "'s invalid bet was not handled (0.0 marks)");
		} catch (InsufficientFundsException ife) {
			System.out.println("You successfully detected an "
					+ "invalid bet for insufficent credit scenario (1 mark)");
			this.score += 1.0;
		} catch (Exception e) {
			handleException(e, this.testNumber, 0);
		}
	}

	/**
	 * Tests that there are assertions or IllegalArgumentException to capture
	 * illegal inputs: 1) negative bet; 2) initialDelay > finalDelay.
	 */
	private void testPreCondition() {
		this.testNumber = 5;
		System.out.println("\nTest 05 - Test roll() Precondition (0.5 marks)");

		try {
			// providing invalid bet input
			this.gameEngine.placeBet(this.theGambler, -1);
			// providing invalid delay input
			this.gameEngine.rollPlayer(this.theGambler, INITIAL_DELAY
					+ FINAL_DELAY, FINAL_DELAY, DELAY_INCREMENT);

		} catch (AssertionError e) {
			System.out
			.println("Succesfully detected illegal input with assertion (0.5 marks)");
			this.score += 0.5;
			return;
		} catch (IllegalArgumentException e) {
			System.out
			.println("Succesfully detected illegal input with IllegalArgumentException (0.5 marks)");
			this.score += 0.5;
			return;
		} catch (Exception e) {
			System.out
			.println("TO DO: Caught Unknown Exception for illegal argument .. Marker to check code");

			return;
		}
		System.out.println("Did not capture illegal input (0.0 marks)");
	}

	/**
	 * Test the removal of 1 player from the system. The removePlayer() method
	 * should return true and the size of the player collection should be equal
	 * to 2.
	 */
	private void testRemovePlayer() {

		try {
			this.testNumber = 2;
			System.out.println("\nTest 02 - Removing a Player (1.0 mark)");
			this.players = this.gameEngine.getAllPlayers();
			int numberOfPlayers = this.players.size();

			// remove the player
			boolean isRemoved = this.gameEngine.removePlayer(this.theHustler
					.getPlayerId());

			// display updated players
			this.players = this.gameEngine.getAllPlayers();
			System.out.println("Updated players:");
			for (Player player : this.players)
				System.out.println(player);

			if (isRemoved
					&& this.gameEngine.getAllPlayers().size() == --numberOfPlayers) {
				System.out.println("You successfully removed a player "
						+ "from the game (1 mark)");
				this.score += 1.0;
			} else
				System.out.println("Player was not removed from the game "
						+ "(0.0 marks)");
		} catch (Exception e) {
			handleException(e, this.testNumber, 0);
		}
	}

	// helper method for testing game outcome
	private void testRollImpl(Player player, float marks) {

		int currentPoints = player.getPoints();

		try {
			this.gameEngine.placeBet(player, DEFAULT_BET);
		} catch (Exception e) {
			// should not be thrown here
			handleException(e, this.testNumber, 0);
		}

		// for (Player player : this.gameEngine.getAllPlayers())
		this.gameEngine.rollPlayer(player, INITIAL_DELAY, FINAL_DELAY,
				DELAY_INCREMENT);

		this.gameEngine.calculateResult();

		// display updated player's info
		System.out.println(player);

		GameStatus outcome = player.getGameResult();

		if (player.getPoints() == (currentPoints + DEFAULT_BET)
				|| player.getPoints() == (currentPoints - DEFAULT_BET)
				|| (outcome == GameStatus.DREW && player.getPoints() == currentPoints)) {
			System.out.println("You completed a successful roll with "
					+ player.getPlayerName()
					+ "'s point balances updated correctly (" + marks
					+ " marks)");

			this.score += marks;
		} else
			System.out.println(player.getPlayerName()
					+ "'s balance not updated correctly (0.0 marks)");
	}

	/**
	 * Execute game a second time and test the results.
	 */
	private void testSecondGame() {
		try {
			this.testNumber = 7;
			System.out
			.println("\nTest 07 - Test Second Roll for the Gambler (1.5 marks)");

			testRollImpl(this.theGambler, 1.5f);
		} catch (Exception e) {
			handleException(e, this.testNumber, 0);
		}
	}

	/**
	 * Test for a valid bet. Checks if the game engine's placeBet() method
	 * forwards the call to the appropriate player.
	 */
	private void testValidBet() {
		try {
			this.testNumber = 4;
			System.out.println("\nTest 04 - Handling a Valid Bet (0.5 marks)");

			this.gameEngine.placeBet(this.theCasual, DEFAULT_BET);

			if (this.theCasual.getBet() == DEFAULT_BET) {
				System.out.println(this.theCasual.getPlayerName()
						+ " successfully made a bet (0.5 marks)");
				this.score += 0.5;
			} else
				System.out.println(this.theCasual.getPlayerName()
						+ "'s getBet() was incorrect (0.0 marks)");
		} catch (InsufficientFundsException ife) {
			System.out.println(this.theCasual.getPlayerName()
					+ "'s bet throws unnecessary exception (0.0 marks)");
		} catch (Exception e) {
			handleException(e, this.testNumber, 0);
		}
	}
}