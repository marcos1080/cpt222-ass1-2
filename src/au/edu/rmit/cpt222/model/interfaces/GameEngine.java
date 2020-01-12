package au.edu.rmit.cpt222.model.interfaces;

import java.util.Collection;

import au.edu.rmit.cpt222.model.exceptions.InsufficientFundsException;

/**
 * Assignment interface (facade) for SADI providing main model functionality
 *
 * @author Mikhail Perepletchikov
 *
 */

public interface GameEngine {

	/**
	 * enum type representing a game outcome for a player
	 */
	public enum GameStatus {
		WON, LOST, DREW
	}

	/**
	 * Default number of dice used in the game
	 */
	public static final int NUM_OF_DICE = 2;

	/**
	 * Adds new GameEngineCallback to the GameEngine
	 * 
	 * @param gameEngineCallback
	 *            a client specific implementation of GameEngineCallback used to
	 *            perform display updates etc. You will use a different
	 *            implementation of the GameEngineCallback for GUI and console
	 *            versions
	 */
	public void addGameEngineCallback(GameEngineCallback gameEngineCallback);

	/**
	 * Adds a Player to the game.
	 * 
	 * @param player
	 *            Player object to add to the game
	 * @return player's ID (must return null if player cannot be added)
	 */
	public String addPlayer(Player player);

	/**
	 * This method goes through all players and applies win/loss/draw outcome to
	 * update game status and betting points values.
	 * GameEngineCallback.gameResult(Player, GameStatus, GameEngine) should also
	 * be called with final result for each player.
	 */
	public void calculateResult();

	/**
	 * Returns collection of all players participating in a game
	 */
	public Collection<Player> getAllPlayers();

	/**
	 * Retrieves a Player indicated by the provided id
	 * 
	 * @param playerId
	 *            id of the Player to retrieve (must return null if not found)
	 * @return Player object
	 */
	public Player getPlayer(String playerId);

	/**
	 * The implementation should forward the call to the Player entity
	 * 
	 * @param player
	 *            betting Player
	 * @param betPoints
	 *            the bet in points
	 * @throws au.edu.rmit.cpt222.model.exceptions.InsufficientFundsException
	 *             if the player has insufficient points and the bet cannot be
	 *             placed
	 */
	public void placeBet(Player player, int betPoints)
			throws InsufficientFundsException;

	/**
	 * Removes a callback associated with the player (when a player quits the
	 * game) to remove no longer needed UI updates
	 * 
	 * TODO (Required in Assignment 2. Do not implement in Ass1.)
	 * 
	 * @param gameEngineCallback
	 *            a client specific implementation of GameEngineCallback to be
	 *            removed from the game. NOTE: to be used in Assignment 2. You
	 *            don't need to implement this in Assignment 1.
	 * 
	 */
	public void removeGameEngineCallback(GameEngineCallback gameEngineCallback);

	/**
	 * Removes a given Player from the game
	 * 
	 * @param playerId
	 *            id of the Player to be removed
	 * @return true if the player exists and was removed successfully; false
	 *         otherwise.
	 */
	public boolean removePlayer(String playerId);

	/**
	 * Same as rollPlayer(), but rolls for the house and calls the house
	 * versions of the callback methods on GameEngineCallback. No player
	 * parameter is required
	 * 
	 * @param initialDelay
	 *            the starting delay in ms between updates (based on how fast
	 *            dice are rolling).
	 * @param finalDelay
	 *            the final delay in ms between updates when the dice stop
	 *            rolling.
	 * @param delayIncrement
	 *            how much the dice slow down (delay gets longer) after each
	 *            roll/tumble.
	 * 
	 */
	public void rollHouse(int initialDelay, int finalDelay, int delayIncrement);

	/**
	 * Roll both dice progressing from the initialDelay to the finalDelay in
	 * increments of delayIncrement. Delays are in milliseconds (ms)
	 * 
	 * 1. start at initialDelay then increment by delayIncrement each time a new
	 * number is shown on the dice faces; 2. call
	 * GameEngineCallback.playerRoll(...) or houseRoll(...) each time a pair of
	 * new dice faces are shown until delay is equal or greater than finalDelay;
	 * 3. call GameEngineCallback.playerRollOutcome(...) or
	 * houseRollOutcome(...) with final result for player or house; 4. update
	 * the player with final result so it can be retrieved later
	 * 
	 * @param player
	 *            the player who is rolling and will have their rollResult set
	 *            at the end of the roll
	 * @param initialDelay
	 *            the starting delay in ms between updates (based on how fast
	 *            dice are rolling). default value should be set to 1
	 * @param finalDelay
	 *            the final delay in ms between updates when the dice stop
	 *            rolling. default value should be set to 100
	 * @param delayIncrement
	 *            how much the dice slow down (delay gets longer) after each
	 *            roll/tumble. default value should be set to 20
	 */
	public void rollPlayer(Player player, int initialDelay, int finalDelay,
			int delayIncrement);

	/**
	 * 
	 * Sets player's (credit) points to the provided value
	 * 
	 * @param player
	 *            id of the Player to be updated
	 * @param totalPoints
	 *            sets player points balance (total points)
	 */
	public void setPlayerPoints(String playerId, int totalPoints);

}
