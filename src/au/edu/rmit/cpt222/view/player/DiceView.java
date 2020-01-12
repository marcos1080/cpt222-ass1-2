package au.edu.rmit.cpt222.view.player;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import au.edu.rmit.cpt222.model.HelperMethods;
import au.edu.rmit.cpt222.model.interfaces.Dice;

/**
 * View for a dice.
 * 
 * @author Mark Stuart
 */
public class DiceView extends JLabel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String IMAGE_FOLDER_PATH = "assets/";
	
	// Current value of the dice.
	private int value;
	
	// Images for each face of the dice.
	private ImageIcon dice1;
	private ImageIcon dice2;
	private ImageIcon dice3;
	private ImageIcon dice4;
	private ImageIcon dice5;
	private ImageIcon dice6;
	
	public DiceView() {
		// Generate a random value.
		this(HelperMethods.generateRandomFaceNumber());
	}
	
	public DiceView(int value) {
		this.value = value;
		dice1 = createImageIcon("Dice-1.png");
		dice2 = createImageIcon("Dice-2.png");
		dice3 = createImageIcon("Dice-3.png");
		dice4 = createImageIcon("Dice-4.png");
		dice5 = createImageIcon("Dice-5.png");
		dice6 = createImageIcon("Dice-6.png");
		
		updateView();
	}
	
	/**
	 * Set a new value for the dice.
	 * 
	 * @param newValue
	 */
	public void setValue(int newValue) {
		assert newValue > 0 && newValue <= Dice.NUM_OF_FACES: "Invalid bet value, must be greater than 0";
		value = newValue;
		updateView();
	}
	
	/**
	 * Get the current value of the dice.
	 * 
	 * @return int
	 */
	public int getValue() {
		return value;
	}
	
	/**
	 * Update the image of the dice based on the value.
	 */
	private void updateView() {
		switch (value) {
			case 1:
				this.setIcon(dice1);
				break;
			case 2:
				this.setIcon(dice2);
				break;
			case 3:
				this.setIcon(dice3);
				break;
			case 4:
				this.setIcon(dice4);
				break;
			case 5:
				this.setIcon(dice5);
				break;
			case 6:
				this.setIcon(dice6);
				break;
		}
		
		revalidate();
	}
	
	/**
	 * Load the image for a dice face.
	 * 
	 * @param imageName
	 * @return ImageIcon
	 */
	private ImageIcon createImageIcon(String imageName) {
		java.net.URL imgURL = getClass().getResource(IMAGE_FOLDER_PATH + imageName);
		if (imgURL != null) {
			return new ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find file: " + imageName);
			return null;
		}
}
}
