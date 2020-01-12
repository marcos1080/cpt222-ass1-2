package au.edu.rmit.cpt222.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import au.edu.rmit.cpt222.model.HelperMethods;
import au.edu.rmit.cpt222.view.dialogs.PlaceBetDialog;

/**
 * Action Listener class for the Place Bet dialog.
 * 
 * @author Mark Stuart
 */
public class PlaceBetDialogController implements ActionListener {

	private MainController mainController;
	private PlaceBetDialog dialog;
	
	public PlaceBetDialogController(PlaceBetDialog dialog, MainController mainController) {
		this.mainController = mainController;
		this.dialog = dialog;
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getActionCommand() == PlaceBetDialog.ACCEPT) {
			String bet = dialog.getBet();
			
			// Check if valid amount entered.
			if (! HelperMethods.isNumeric(bet)) {
				mainController.showError("Invalid bet entered. Bet nust be a number.");
				return;
			}
			
			mainController.placeBet(Integer.parseInt(dialog.getBet()));
			dialog.dispose();
		} else {
			// Cancel pressed.
			dialog.dispose();
		}
	}
}
