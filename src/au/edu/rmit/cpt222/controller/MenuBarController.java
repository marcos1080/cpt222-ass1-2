package au.edu.rmit.cpt222.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import au.edu.rmit.cpt222.view.dialogs.DialogManager;
import au.edu.rmit.cpt222.view.menu.MainMenu;

/**
 * Action Listener class for the programs menu bar.
 * 
 * @author Mark Stuart
 */
public class MenuBarController implements ActionListener {
	
	private MainController mainController;
	
	public MenuBarController (MainController mainController) {
		this.mainController = mainController;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case MainMenu.NEW_GAME:
			mainController.showDialog(DialogManager.ADD_PLAYER);
			break;
		case MainMenu.NETWORK:
			mainController.showDialog(DialogManager.NETWORK);
			break;
		case MainMenu.CLOSE:
			System.exit(0);
			break;
		}
	}
}
