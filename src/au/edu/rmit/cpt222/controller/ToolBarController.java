package au.edu.rmit.cpt222.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import au.edu.rmit.cpt222.view.ToolBar;
import au.edu.rmit.cpt222.view.dialogs.DialogManager;

/**
 * Action Listener class for the program tool bar.
 * 
 * @author Mark Stuart
 */
public class ToolBarController implements ActionListener {
	
	private MainController mainController;
	
	public ToolBarController(MainController mainController) {
		this.mainController = mainController;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
			case ToolBar.ROLL_COMMAND :
				mainController.rollPlayer();
				break;
			case ToolBar.PLACE_BET_COMMAND:
				mainController.showDialog(DialogManager.PLACE_BET);
				break;
			case ToolBar.ADD_CREDIT_COMMAND:
				mainController.showDialog(DialogManager.ADD_CREDIT);
				break;
		}
	}
}
