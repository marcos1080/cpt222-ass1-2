package au.edu.rmit.cpt222.model.interfaces;

import au.edu.rmit.cpt222.model.exceptions.InsufficientFundsException;
import au.edu.rmit.cpt222.model.interfaces.GameEngine.GameStatus;

/**
 * Assignment interface for SADI representing the player.
 * 
 * @author Mikhail Perepletchikov and Caspar Ryan
 * 
 */

public interface Player {

	public static final int DEFAULT_BET = 10;
	public static final int DEFAULT_NUM_POINTS = 100;

	/**
	 * 
	 * @return the bet as was set with placeBet()
	 */
	public int getBet();

	/**
	 * 
	 @return outcome of the last game played by the player.
	 */
	public GameStatus getGameResult();

	/**
	 * 
	 * @return the player ID
	 */
	public String getPlayerId();

	/**
	 * 
	 * @return human readable player name
	 */
	public String getPlayerName();

	/**
	 * 
	 * @return number of available betting points
	 */
	public int getPoints();

	/**
	 * 
	 * @return a DicePair containing both dice values of the last game
	 */
	public DicePair getRollResult();

	/**
	 * 
	 * @param bet
	 *            the bet in points.
	 * @throws au.edu.rmit.cpt222.model.exceptions.InsufficientFundsException
	 *             if the player has insufficient points and the bet cannot be
	 *             placed
	 */
	public void placeBet(int bet) throws InsufficientFundsException;

	/**
	 * 
	 * reset the bet to default bet value for next round (in case the player
	 * does not explicitly bet). Assignment 2 related only (can ignore in
	 * Assignment 1) * @throws
	 * au.edu.rmit.cpt222.model.exceptions.InsufficientFundsException if the
	 * player has insufficient points and the bet cannot be reset
	 */
	public void resetBet() throws InsufficientFundsException;

	/**
	 * 
	 * @param status
	 *            status (win/loss/draw) of the last game played by the player
	 */
	public void setGameResult(GameStatus status);

	/**
	 * 
	 * @param playerName
	 *            human readable player name
	 */
	public void setPlayerName(String playerName);

	/**
	 * 
	 * @param points
	 *            set total betting points (updated with each win or loss)
	 */
	public void setPoints(int points);

	/**
	 * 
	 * @param rollResult
	 *            a DicePair containing both dice values for the last game
	 */
	public void setRollResult(DicePair rollResult);

	/**
	 * 
	 * @return a human readable String that lists the values of this Player
	 *         instance for debugging or console display
	 */
	@Override
	public String toString();
}
