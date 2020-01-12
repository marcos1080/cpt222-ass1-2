package au.edu.rmit.cpt222.model.interfaces;

/**
 * Assignment interface for SADI representing pair of Dice values
 * 
 * @author Mikhail Perepletchikov and Caspar Ryan
 * 
 */

public interface DicePair {
	/**
	 * @return rolled dice 1
	 */
	public Dice getDice1();

	/**
	 * @return rolled dice 2
	 */
	public Dice getDice2();

	/**
	 * @return combined score of dice faces
	 */
	public int getTotalScore();
}