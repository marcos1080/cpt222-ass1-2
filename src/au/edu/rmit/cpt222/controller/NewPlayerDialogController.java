package au.edu.rmit.cpt222.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import au.edu.rmit.cpt222.model.HelperMethods;
import au.edu.rmit.cpt222.view.dialogs.NewPlayerDialog;

/**
 * Action Listener class for the Add Player dialog.
 * 
 * @author Mark Stuart
 */
public class NewPlayerDialogController implements ActionListener {
	
	private MainController mainController;
	private NewPlayerDialog dialog;
	
	public NewPlayerDialogController(NewPlayerDialog dialog, MainController mainController) {
		this.mainController = mainController;
		this.dialog = dialog;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		switch (event.getActionCommand()) {
			case NewPlayerDialog.CANCEL:
				dialog.dispose();
				break;
			case NewPlayerDialog.ACCEPT:
				String name = dialog.getNameField();
				String points = dialog.getPointsField();
				
				// If name field is empty then display an error.
				if (name.isEmpty()) {
					mainController.showError("Name cannot be empty!");
				} else if (! HelperMethods.isNumeric(points)) {
					mainController.showError("Invalid credit amount! Must be a number");
				} else {
					mainController.addPlayer(name, Integer.parseInt(points));
					
					// Reset the dialog.
					dialog.reset();
					dialog.dispose();
				}
				break;
		}
	}
}
