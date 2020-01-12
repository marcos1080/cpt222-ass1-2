package au.edu.rmit.cpt222.model;

import java.util.concurrent.ThreadLocalRandom;

import au.edu.rmit.cpt222.model.interfaces.Dice;

public class HelperMethods {
	public static int generateRandomFaceNumber() {
		return ThreadLocalRandom.current().nextInt(Dice.NUM_OF_FACES) + 1;
	}
	
	public static boolean isNumeric(String testString) {
	    try {
	        int testInt = Integer.parseInt(testString);
	    } catch (NumberFormatException | NullPointerException nfe) {
	        return false;
	    }
	    return true;
	}
}
