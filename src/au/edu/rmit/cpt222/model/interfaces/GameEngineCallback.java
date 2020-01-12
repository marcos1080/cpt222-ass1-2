package au.edu.rmit.cpt222.model.interfaces;

import au.edu.rmit.cpt222.model.interfaces.GameEngine.GameStatus;

/**
 * Assignment interface for SADI to notify client of GameEngine events.
 * 
 * @author Mikhail Perepletchikov and Caspar Ryan
 * 
 */
public interface GameEngineCallback {

	/**
	 * 
	 * Called for each Player to indicate the outcome of the current game. Use
	 * this to update your GUI display or log to console.
	 * 
	 * @param player
	 *            the Player who is playing the game
	 * @param result
	 *            indicates game outcome - won/lost/drew
	 * @param engine
	 *            a convenience reference to the engine so the receiver can call
	 *            methods if necessary
	 */
	public void gameResult(Player player, GameStatus result, GameEngine engine);

	/**
	 * 
	 * Called as the HOUSE dice are rolling. Use this to update your GUI display
	 * or log to console
	 * 
	 * @param dicePair
	 *            the current (upfacing) values of the rolling dice
	 * @param engine
	 *            a convenience reference to the engine so the receiver can call
	 *            methods if necessary
	 */
	public void houseRoll(DicePair dicePair, GameEngine engine);

	/**
	 * 
	 * Called when HOUSE dice have stopped rolling. Use this to update your GUI
	 * display or log to console
	 * 
	 * @param result
	 *            the final resting state of the rolled dice
	 * @param engine
	 *            a convenience reference to the engine so the receiver can call
	 *            methods if necessary
	 */
	public void houseRollOutcome(DicePair result, GameEngine engine);

	/**
	 * 
	 * Called as the dice are rolling for a Player. Use this to update your GUI
	 * display or log to console
	 * 
	 * @param player
	 *            the Player who rolled
	 * @param dicePair
	 *            the current (upfacing) values of the rolling dice
	 * @param engine
	 *            a convenience reference to the engine so the receiver can call
	 *            methods if necessary
	 */
	public void playerRoll(Player player, DicePair dicePair, GameEngine engine);

	/**
	 * 
	 * Called when the dice have stopped rolling. Use this to update your GUI
	 * display or log to console
	 * 
	 * @param player
	 *            the Player who rolled
	 * @param result
	 *            the final resting state of the rolled dice
	 * @param engine
	 *            a convenience reference to the engine so the receiver can call
	 *            methods if necessary
	 */
	public void playerRollOutcome(Player player, DicePair result,
			GameEngine engine);
}
