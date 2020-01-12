package au.edu.rmit.cpt222.model;

import java.io.Serializable;

import au.edu.rmit.cpt222.model.interfaces.Dice;

public class DiceImpl implements Dice, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8774599135029084765L;
	private int face;
	
	public DiceImpl() {
		// Set a random number from 1-6 as the initial face number.
		this.setFace(HelperMethods.generateRandomFaceNumber());
	}

	@Override
	public int getFace() {
		return this.face;
	}

	@Override
	public void setFace(int currentFace) {
		if(currentFace < 1 || currentFace > Dice.NUM_OF_FACES) 
			throw new IllegalArgumentException("ERROR: dice face value is out of range");
		
		this.face = currentFace;
	}

	@Override
	public String toString() {
		return String.valueOf(this.face);
	}
}
