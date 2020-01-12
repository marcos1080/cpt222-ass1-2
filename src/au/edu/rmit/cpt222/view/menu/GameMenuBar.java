package au.edu.rmit.cpt222.view.menu;

import java.awt.event.KeyEvent;

import javax.swing.JMenuBar;

import au.edu.rmit.cpt222.controller.MainController;

/**
 * Menu bar class for the program.
 * 
 * @author Mark Stuart
 */
public class GameMenuBar extends JMenuBar {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MainMenu mainMenu;
	
	public GameMenuBar(MainController mainController) {
		// Add Menu
		mainMenu = new MainMenu(mainController, "Menu");
		// Set keyboard shortcut. Alt + m
		mainMenu.setMnemonic(KeyEvent.VK_M);
		
		add(mainMenu);
	}
}
