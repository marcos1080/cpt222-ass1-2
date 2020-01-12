package au.edu.rmit.cpt222.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import au.edu.rmit.cpt222.controller.MainController;
import au.edu.rmit.cpt222.model.interfaces.Player;
import au.edu.rmit.cpt222.view.player.PlayerView;

/**
 * A View class that contains the player information and house dice.
 * 
 * @author Mark Stuart
 */
public class GamePanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Dimension LABEL_SIZE = new Dimension(100, 10);
	private static final Dimension DICE_SIZE = new Dimension(70, 10);
	
	// Holds player views. Will allow for many player views do be displayed.
	private HashMap<String, PlayerView> playerViews;
	
	// Container used to contain the player views.
	private JPanel playerList;
	
	// Holds the house dice.
	private HousePanel housePanel;
	
	private MainController mainController;

	public GamePanel(MainController mainController) {
		this.mainController = mainController;
		setLayout(new BorderLayout());
		
		// Add column titles.
		JPanel columnTitles = new JPanel(new BorderLayout());
		int borderWidth = MainView.DEF_BORDER;
		columnTitles.setBorder(BorderFactory.createEmptyBorder(
				borderWidth, borderWidth, borderWidth, borderWidth));
		JLabel nameTitle = new JLabel("Player");
		JLabel creditTitle = new JLabel("Credit");
		JLabel betTitle = new JLabel("Current Bet");
		JLabel diceTitle = new JLabel("Dice");
		
		// Set sizes
		nameTitle.setPreferredSize(LABEL_SIZE);
		creditTitle.setPreferredSize(LABEL_SIZE);
		diceTitle.setPreferredSize(DICE_SIZE);
		
		// Add titles
		columnTitles.add(nameTitle, BorderLayout.WEST);
		JPanel bettingTitleLabel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		bettingTitleLabel.add(creditTitle);
		bettingTitleLabel.add(betTitle);
		columnTitles.add(bettingTitleLabel, BorderLayout.CENTER);
		columnTitles.add(diceTitle, BorderLayout.EAST);
		add(columnTitles, BorderLayout.NORTH);
		
		playerViews = new HashMap<String, PlayerView>();
		playerList = new JPanel(new BorderLayout());
		add(playerList, BorderLayout.CENTER);
		
		// Add house "player"
		housePanel = new HousePanel();
		add(housePanel, BorderLayout.SOUTH);
	}
	
	/**
	 * Create a new player view and add it to the screen.
	 * 
	 * @param player
	 */
	public void addPlayer(Player player) {
		PlayerView newPlayerView = new PlayerView(player, mainController);
		playerList.add(newPlayerView, BorderLayout.NORTH);
		playerViews.put(player.getPlayerId(), newPlayerView);
	}
	
	/**
	 * Get a player view
	 * 
	 * @param playerId
	 * @return PlayerVIew
	 */
	public PlayerView getPlayerView(String playerId) {
		return playerViews.get(playerId);
	}
	
	/**
	 * Get the view for the house.
	 * 
	 * @return HousePanel
	 */
	public HousePanel getHousePanel() {
		return housePanel;
	}
	
	/**
	 * Removes a player view
	 * 
	 * @param playerId
	 */
	public void removePlayer(String playerId) {
		playerList.remove(playerViews.get(playerId));
		playerViews.remove(playerId);
		revalidate();
		repaint();
	}
}
