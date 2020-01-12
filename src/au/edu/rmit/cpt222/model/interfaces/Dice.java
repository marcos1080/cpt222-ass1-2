package au.edu.rmit.cpt222.model.interfaces;

/**
 * Assignment interface for SADI representing the Dice.
 * 
 * @author Mikhail Perepletchikov and Caspar Ryan
 * 
 */

public interface Dice {
	public static final int NUM_OF_FACES = 6; // standard casino-type dice

	/**
	 * @return the numeric value of this dice's current face (upper surface
	 *         after roll)
	 */
	public int getFace();

	/**
	 * @param currentFace
	 *            current face of the dice
	 */
	public void setFace(int currentFace);
}