package au.edu.rmit.cpt222.view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import au.edu.rmit.cpt222.model.interfaces.DicePair;
import au.edu.rmit.cpt222.view.player.DicePairView;

/**
 * A view to hold the dice for the house.
 * 
 * @author Mark Stuart
 */
public class HousePanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String NAME = "House";
	private static final Dimension NAME_SIZE = new Dimension(100, 10);
	
	private DicePairView diceView;
	
	public HousePanel() {
		setLayout(new BorderLayout());
		int borderWidth = MainView.DEF_BORDER;
		setBorder(BorderFactory.createEmptyBorder(
				borderWidth, borderWidth, borderWidth, borderWidth));
		
		// Name
		JLabel nameLabel = new JLabel(NAME);	
		nameLabel.setPreferredSize(NAME_SIZE);
		add(nameLabel, BorderLayout.WEST);
		
		// Dice
		diceView = new DicePairView();
		add(diceView, BorderLayout.EAST);
	}
	
	/**
	 * Set the faces for the dice.
	 * 
	 * @param dicePair
	 */
	public void updateDice(DicePair dicePair) {
		diceView.updateDice(dicePair);
	}
}
