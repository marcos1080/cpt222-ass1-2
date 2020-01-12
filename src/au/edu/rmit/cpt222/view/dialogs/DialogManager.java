package au.edu.rmit.cpt222.view.dialogs;

import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import au.edu.rmit.cpt222.controller.MainController;

/**
 * Manager for displaying dialogs for the program.
 * THis class is used to encapsulate all the dialog functionality in one place.
 * 
 * @author Mark Stuart
 */
public class DialogManager {
	public static final String ADD_PLAYER = "Add Player";
	public static final String PLACE_BET = "Place Bet";
	public static final String ADD_CREDIT = "Add Credit";
	public static final String NETWORK = "Network Settings";
	
	// Used to set the borders of dialog fields.
	public static final int CREDIT_TOP_BORDER = 8;
	public static final int CREDIT_BOTTOM_BORDER = 10;
	public static final Dimension CREDIT_SPINNER_SIZE = new Dimension(70, 25);
	
	private MainController mainController;
	
	// Dialogs
	private NewPlayerDialog newPlayer;
	private PlaceBetDialog placeBet;
	private AddCreditDialog addCredit;
	private NetworkDialog network;
	
	public DialogManager(MainController mainController) {
		this.mainController = mainController;
		
		// set dialogs.
		newPlayer = new NewPlayerDialog(mainController, ADD_PLAYER);
		placeBet = new PlaceBetDialog(mainController, PLACE_BET);
		addCredit = new AddCreditDialog(mainController, ADD_CREDIT);
		network = new NetworkDialog(mainController, NETWORK);
	}
	
	/**
	 * Display a dialog.
	 */
	public void showDialog(String dialogName) {
		switch (dialogName) {
			case ADD_PLAYER:
				display(newPlayer);
				break;
			case PLACE_BET:
				display(placeBet);
				break;
			case ADD_CREDIT:
				display(addCredit);
				break;
			case NETWORK:
				display(network);
				break;
		}
	}
	
	/**
	 * Display an error on screen.
	 * 
	 * @param message
	 */
	public void showError(String message) {
		JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * Show the dialog on screen.
	 * 
	 * @param dialog
	 * 			Dialog to show.
	 */
	private void display(JDialog dialog) {
		dialog.setLocationRelativeTo(mainController.getMainView());
		dialog.setVisible(true);
	}
}
