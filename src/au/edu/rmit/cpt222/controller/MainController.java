package au.edu.rmit.cpt222.controller;

import au.edu.rmit.cpt222.model.GameEngineGUICallback;
import au.edu.rmit.cpt222.model.SimplePlayer;
import au.edu.rmit.cpt222.model.exceptions.InsufficientFundsException;
import au.edu.rmit.cpt222.model.interfaces.DicePair;
import au.edu.rmit.cpt222.model.interfaces.GameEngine;
import au.edu.rmit.cpt222.model.interfaces.GameEngine.GameStatus;
import au.edu.rmit.cpt222.model.interfaces.Player;
import au.edu.rmit.cpt222.view.MainView;
import au.edu.rmit.cpt222.view.dialogs.DialogManager;
import au.edu.rmit.cpt222.view.player.PlayerView;

/**
 * Main controller class. Handles all the communication logic between the model and view.
 * 
 * @author Mark Stuart
 */
public class MainController {
	
	private MainView mainView;
	private GameEngine model;
	
	// Used to refer to the model correctly.
	private String playerId;
	
	// Dialog Manager controls the display of dialogs. Used for encapsulation purposes.
	private DialogManager dialogManager;
	
	public MainController(MainView mainView, GameEngine model) {
		this.mainView = mainView;
		this.model = model;
		this.dialogManager = new DialogManager(this);
	}
	
	/**
	 * Get a reference to the model
	 * 
	 * @return GameEngine
	 */
	public GameEngine getModel() {
		return this.model;
	}
	
	/**
	 * Get the main view.
	 * 
	 * @return MainView
	 */
	public MainView getMainView() {
		return mainView;
	}
	
	/**
	 * Get the player object from the model.
	 * 
	 * @return Player
	 */
	public Player getPlayer() {
		assert playerId != null : "Player ID not set!";
		return model.getPlayer(playerId);
	}
	
	/**
	 * Get the view from the game panel containing player info.
	 * 	
	 * @param playerId
	 * @return PlayerView
	 */
	public PlayerView getPlayerView(String playerId) {
		return mainView.getGamePanel().getPlayerView(playerId);
	}
	
	/**
	 * Add a player to the model. Called from the new player dialog.
	 * 
	 * @param name
	 * @param points
	 */
	public void addPlayer(String name, int points) {
		// Check if there is a player already playing.
		if (playerId != null ) {
			model.removePlayer(playerId);
			mainView.getGamePanel().removePlayer(playerId);
		}
		
		// Callback to update the view from the model.
		model.addGameEngineCallback(new GameEngineGUICallback(this));
		
		// Add new player view to game panel.
		Player player = new SimplePlayer(name, points);
		playerId = model.addPlayer(player);
		mainView.getGamePanel().addPlayer(player);
		
		// Display toolbar for game functionality.
		mainView.getToolBar().setVisible(true);
	}
	
	/**
	 * Place a bet for the player to the model.
	 * 
	 * @param amount
	 */
	public void placeBet(int amount) {
		try {
			Player player = getPlayer();
			model.placeBet(player, amount);
			updatePlayerView();
		} catch (InsufficientFundsException error) {
			showError(error.getMessage());
		}
	}
	
	/**
	 * Add credit to a player object.
	 * 
	 * @param amount
	 */
	public void addCredit(int amount) {
		int newTotal = getPlayer().getPoints() + amount;
		model.setPlayerPoints(playerId, newTotal);
		updatePlayerView();
	}
	
	/**
	 * Allow the roll button to be pressed.
	 */
	public void enableRollButton() {
		mainView.getToolBar().enableRollButton();
	}
	
	/**
	 * Stop the roll button from being pressed.
	 */
	public void disableRollButton() {
		mainView.getToolBar().disableRollButton();
	}
	
	/**
	 * Roll the dice for the player, then calculate the game result.
	 */
	public void rollPlayer() {
		// Need to run in a separate thread otherwise the GUI locks up.
		new Thread(() -> {
			model.calculateResult();
		}).start();
	}
	
	/**
	 * Update the faces of the house dice view.
	 * 
	 * @param dicePair
	 */
	public void updateHouseDice(DicePair dicePair) {
		mainView.getGamePanel().getHousePanel().updateDice(dicePair);
	}
	
	/**
	 * Display a dialog.
	 * 
	 * @param dialogName
	 */
	public void showDialog(String dialogName) {
		dialogManager.showDialog(dialogName);
	}
	
	/**
	 * Show an error dialog.
	 * 
	 * @param message
	 */
	public void showError(String message) {
		dialogManager.showError(message);
	}
	
	/**
	 * Based on the result from the last game display the result to the history box
	 * then update the player info being displayed.
	 * 
	 * @param player
	 * @param result
	 */
	public void displayResult(Player player, GameStatus result) {
		mainView.getHistoryBox().addGameResult(player, result);
		
		// Update player view.
		updatePlayerView();
	}

	/**
	 * Update the player view information.
	 */
	private void updatePlayerView() {
		mainView.getGamePanel().getPlayerView(playerId).updateValues();
	}
}
