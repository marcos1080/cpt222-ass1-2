package au.edu.rmit.cpt222.model;

import java.io.Serializable;

import au.edu.rmit.cpt222.model.interfaces.Dice;
import au.edu.rmit.cpt222.model.interfaces.DicePair;

public class DicePairImpl implements DicePair, Serializable {
	private static int PREV_DICE1_FACE = 0;
	private static int PREV_DICE2_FACE = 0;
	private static final long serialVersionUID = -7185543904650945172L;
	
	private Dice dice1;
	private Dice dice2;

	public DicePairImpl() {
		this(new DiceImpl(), new DiceImpl());
		
		// Ensure dice values that are not the same as the previous roll.
		PREV_DICE1_FACE = nonRepeatedRoll(dice1, PREV_DICE1_FACE);
		PREV_DICE2_FACE = nonRepeatedRoll(dice2, PREV_DICE2_FACE);
	}
	
	public DicePairImpl(Dice dice1, Dice dice2) {
		this.dice1 = dice1;
		this.dice2 = dice2;
	}

	@Override
	public Dice getDice1() {
		return this.dice1;
	}

	@Override
	public Dice getDice2() {
		return this.dice2;
	}

	@Override
	public int getTotalScore() {
		return this.dice1.getFace() + this.dice2.getFace();
	}

	@Override
	public String toString() {
		StringBuilder resultString = new StringBuilder();
		resultString.append("Dice 1: ");
		resultString.append(this.dice1);
		resultString.append(", Dice 2: ");
		resultString.append(this.dice2);
		resultString.append("... Total: ");
		resultString.append(this.getTotalScore());
		return resultString.toString();
	}
	
	// Makes sure that a dice cannot have the same value twice in a row.
	private int nonRepeatedRoll(Dice dice, int prevValue) {
		while (dice.getFace() == prevValue)
			dice.setFace(HelperMethods.generateRandomFaceNumber());
		
		return dice.getFace();
	}
}
