package au.edu.rmit.cpt222.view.player;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import au.edu.rmit.cpt222.controller.MainController;
import au.edu.rmit.cpt222.model.interfaces.DicePair;
import au.edu.rmit.cpt222.model.interfaces.Player;
import au.edu.rmit.cpt222.view.MainView;

/**
 * View to hold the details of a player.
 * Holds the name, credit, current bet and a dice pair representation.
 * 
 * @author Mark Stuart
 */
public class PlayerView extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private MainController mainController;
	
	private DicePairView diceView;
	private JLabel pointsLabel;
	private JLabel betLabel;
	
	public PlayerView(Player player, MainController mainController) {
		this.mainController = mainController;
		setLayout(new BorderLayout());
		int borderWidth = MainView.DEF_BORDER;
		setBorder(BorderFactory.createEmptyBorder(
				borderWidth, borderWidth, borderWidth, borderWidth));
		
		// Name
		JLabel nameLabel = new JLabel(player.getPlayerName());	
		nameLabel.setPreferredSize(new Dimension(100, nameLabel.getSize().height));
		add(nameLabel, BorderLayout.WEST);
		
		JPanel bettingView = new JPanel(new FlowLayout(FlowLayout.LEFT));
		add(bettingView, BorderLayout.CENTER);
		
		// Points
		pointsLabel = new JLabel(String.valueOf(player.getPoints()));
		pointsLabel.setPreferredSize(new Dimension(100, 30));
		bettingView.add(pointsLabel);
		
		// Bet
		betLabel = new JLabel(String.valueOf(0));
		bettingView.add(betLabel);
		
		// Dice
		diceView = new DicePairView();
		add(diceView, BorderLayout.EAST);
	}
	
	/**
	 * Update the dice faces.
	 * 
	 * @param dicePair
	 */
	public void updateDice(DicePair dicePair) {
		diceView.updateDice(dicePair);
	}
	
	/**
	 * Update the points and current bet of the player.
	 */
	public void updateValues() {
		// Get player object from the model.
		Player player = mainController.getPlayer();
		
		betLabel.setText(String.valueOf(player.getBet()));
		pointsLabel.setText(String.valueOf(player.getPoints()));
	}
}
