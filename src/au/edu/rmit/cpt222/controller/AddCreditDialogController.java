package au.edu.rmit.cpt222.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import au.edu.rmit.cpt222.view.dialogs.AddCreditDialog;
import au.edu.rmit.cpt222.view.dialogs.NewPlayerDialog;

/**
 * Action Listener for the Add Credit Dialog.
 * 
 * @author Mark Stuart
 *
 */
public class AddCreditDialogController implements ActionListener {
	
	private AddCreditDialog dialog;
	private MainController mainController;
	
	public AddCreditDialogController(AddCreditDialog dialog, MainController mainController) {
		this.dialog = dialog;
		this.mainController = mainController;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		// Both buttons will activate a dialog dispose.
		dialog.dispose();
		
		// Yes button will add the credit amount to the player.
		if (event.getActionCommand() == NewPlayerDialog.ACCEPT )
			mainController.addCredit(dialog.getCredit());
	}
}
