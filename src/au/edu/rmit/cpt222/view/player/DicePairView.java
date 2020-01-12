package au.edu.rmit.cpt222.view.player;

import javax.swing.JPanel;

import au.edu.rmit.cpt222.model.interfaces.DicePair;

/**
 * View to hold a pair of dice faces.
 * 
 * @author Mark Stuart
 */
public class DicePairView extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DiceView diceView1;
	private DiceView diceView2;
	
	public DicePairView() {
		diceView1 = new DiceView();
		add(diceView1);
		
		diceView2 = new DiceView();
		add(diceView2);
	}
	
	/**
	 * Set the faces of the dice.
	 * 
	 * @param dicePair
	 */
	public void updateDice(DicePair dicePair) {
		diceView1.setValue(dicePair.getDice1().getFace());
		diceView2.setValue(dicePair.getDice2().getFace());
	}
}
