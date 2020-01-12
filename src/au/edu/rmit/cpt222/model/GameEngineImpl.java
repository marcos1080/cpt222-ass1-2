package au.edu.rmit.cpt222.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import au.edu.rmit.cpt222.model.exceptions.InsufficientFundsException;
import au.edu.rmit.cpt222.model.interfaces.GameEngine;
import au.edu.rmit.cpt222.model.interfaces.GameEngineCallback;
import au.edu.rmit.cpt222.model.interfaces.Player;
import au.edu.rmit.cpt222.model.interfaces.DicePair;

public class GameEngineImpl implements GameEngine {
	public final static int INITIAL_DELAY = 1;
	public final static int FINAL_DELAY = 300;
	public final static int DELAY_INCREMENT = 30;
	
	private Set<GameEngineCallback> gameEngineCallbacks = Collections.newSetFromMap(new ConcurrentHashMap<GameEngineCallback, Boolean>());
	private ConcurrentHashMap<String, Player> players = new ConcurrentHashMap<String, Player>();
	private DicePair houseDice = new DicePairImpl();
	private Object gameLock = new Object();;

	@Override
	public void addGameEngineCallback(GameEngineCallback gameEngineCallback) {
		this.gameEngineCallbacks.add(gameEngineCallback);
	}

	@Override
	public String addPlayer(Player player) {
		String playerId = player.getPlayerId();
		
		// Cannot have duplicate id's
		if(players.containsKey(playerId)) {
			return null;
		}
		
		// Should not have the same name - just confusing.
		for(Player inspectedPlayer : getAllPlayers()) {
			if(inspectedPlayer.getPlayerName() == player.getPlayerName()) {
				return null;
			}
		}
		
		// Add to player map and return the id.
		this.players.put(playerId, player);
		
		return playerId;
	}

	@Override
	public void calculateResult() {
		
		/**
		 * No destructive actions on the players and gameEngineCallback collections
		 * should be performed while the results of the round are being calculated.
		 */
		synchronized (gameLock) {
			// Get a list of players that will be in this round
			List<Player> validPlayers = processBets();
			
			// Roll the house and get the dice results.
			this.rollHouse(INITIAL_DELAY, FINAL_DELAY, DELAY_INCREMENT);
			int houseTotal = this.houseDice.getTotalScore();
			
			// Cycle through each player and calculate results.
			for (Player player: validPlayers) {
				
				playGame(player, houseTotal);
			}
		}
	}

	@Override
	public Collection<Player> getAllPlayers() {
		return Collections.unmodifiableCollection(new ArrayList<Player>(players.values()));
	}

	@Override
	public Player getPlayer(String playerId) {
		return players.get(playerId);
	}

	@Override
	public void placeBet(Player player, int betPoints) throws InsufficientFundsException {
		
		/**
		 * A player shouldn't be able to place a bet once calculating 
		 * the result of round has begun.
		 */
		synchronized (gameLock) {
			assert players.containsKey(player.getPlayerId()) : "ERROR: Player doesn't exist!";
			assert betPoints > 0 : "ERROR: The bet value must be greater than 0!";
			getPlayer(player.getPlayerId()).placeBet(betPoints);
		}
	}

	@Override
	public void removeGameEngineCallback(GameEngineCallback gameEngineCallback) {
		/**
		 * A game engine callback should not be removed during a round.
		 */
		synchronized (gameLock) {
			this.gameEngineCallbacks.remove(gameEngineCallback);
		}
	}

	@Override
	public boolean removePlayer(String playerId) {
		if(this.players.containsKey(playerId)) {
			
			/**
			 * Should not be able to remove a player while a round is being processed.
			 * 
			 * A case could happen where a player is placed in the validPlayer variable
			 * during the calculateResult method but removed from the players map
			 * just after. This could be a problem.
			 */
			synchronized (gameLock) {
				this.players.remove(playerId);
				return true;
			}
		}
		
		return false;
	}

	@Override
	public void rollHouse(int initialDelay, int finalDelay, int delayIncrement) {
		assert houseDice != null: "ERROR: house dice have not been initialised!";
		
		// Roll the dice.
		houseDice = this.rollProgression(initialDelay, finalDelay, delayIncrement, null);
		
		// Display the final dice faces to the client.
		for(GameEngineCallback gameEngineCallback: gameEngineCallbacks) {
			gameEngineCallback.houseRollOutcome(houseDice, this);
		}
	}

	@Override
	public void rollPlayer(Player player, int initialDelay, int finalDelay, int delayIncrement) {
		player = getPlayer(player.getPlayerId());
		
		// Roll the dice
		DicePair dicePair = this.rollProgression(initialDelay, finalDelay, delayIncrement, player);
		
		// Save the final dice faces to the player object.
		player.setRollResult(dicePair);
		
		// Display the final dice faces to the client.
		for(GameEngineCallback gameEngineCallback: this.gameEngineCallbacks) {
			gameEngineCallback.playerRollOutcome(player, dicePair, this);
		}
		
	}

	@Override
	public void setPlayerPoints(String playerId, int totalPoints) {
		Player player = getPlayer(playerId);
		if (player != null )
			getPlayer(playerId).setPoints(totalPoints);
	}
	
	/**
	 * Helper method to emulate a dice roll. It takes an initial delay and increments to a final delay generating a 
	 * random dice pair at each increment.
	 * 
	 * If the player parameter is set to null it indicates the progression is for the house.
	 * 
	 * @param dicePair
	 * 				Dice pair object that holds 2 dice.
	 * @param initialDelay
	 * 				The initial delay increment amount in milliseconds.
	 * @param finalDelay
	 * 				The final delay amount in milliseconds
	 * @param delayIncrement
	 * 				Amount to increase the delay in milliseconds
	 * @param player
	 * 				If set to null the player is the house.
	 */
	private DicePair rollProgression(int initialDelay, int finalDelay, int delayIncrement, Player player) {
		// Slow down delay increment loop.
		int delay = initialDelay;
		while(delay < finalDelay) {
			// Generate dice faces.
			DicePair dicePair = new DicePairImpl();
			
			// Send result to clients.
			for(GameEngineCallback gameEngineCallback: gameEngineCallbacks) {
				// Test if roll is for house or player.
				if (player == null) {
					gameEngineCallback.houseRoll(dicePair, this);
				} else {
					gameEngineCallback.playerRoll(player, dicePair, this);
				}
			}
			
			try {
				// Delay.
			    Thread.sleep(delay);
			} catch(InterruptedException e) {
			    Thread.currentThread().interrupt();
			}
			
			delay += delayIncrement;
		}
		
		// Final roll.
		return new DicePairImpl();
	}
	
	/**
	 * Loops through all the players in the game.
	 * If they haven't made a bet the default one is made for them.
	 * If they don't have enough for the minimum bet they will sit out of the round.
	 * 
	 * @return
	 */
	private List<Player> processBets() {
		List<Player> validPlayers = new ArrayList<Player>();
		
		getAllPlayers().forEach(player -> {
			boolean add = true;
			
			if (player.getBet() == 0) {
				try {
					player.resetBet();
				} catch (InsufficientFundsException e) {
					System.out.println("\t\t" + player.getPlayerName() + "does not have enough credit to play in this round");
					add = false;
				}
			}
			
			if (add) {
				validPlayers.add(player);
			}
		});
		
		return validPlayers;
	}
	
	private void playGame(Player player, int houseTotal) {
		// Roll the player.
		rollPlayer(player, GameEngineImpl.INITIAL_DELAY, GameEngineImpl.FINAL_DELAY, GameEngineImpl.DELAY_INCREMENT);
					
		int playerTotal = player.getRollResult().getTotalScore();
		
		GameStatus result;
		
		// Check for a draw.
		if(houseTotal == playerTotal) {
			result = GameStatus.DREW;
			// Return bet to credit points.
			player.setPoints(player.getPoints());
			
		} else if(houseTotal > playerTotal) {
			result = GameStatus.LOST;
			player.setPoints(player.getPoints() - player.getBet());
		} else {
			result = GameStatus.WON;
			player.setPoints(player.getPoints() + player.getBet());
		}
		
		player.setGameResult(result);
		
		// Send results to all player clients.
		for(GameEngineCallback gameEngineCallback: this.gameEngineCallbacks) {
			gameEngineCallback.gameResult(player, result, this);
		}
		
		// Reset the player bet.
		try {
			player.placeBet(0);
		} catch (InsufficientFundsException e) {
			e.printStackTrace();
		}
	}
}
