package au.edu.rmit.cpt222.view.menu;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import au.edu.rmit.cpt222.controller.MainController;
import au.edu.rmit.cpt222.controller.MenuBarController;

/**
 * Main Menu for the program.
 * 
 * @author Mark Stuart
 */
public class MainMenu extends JMenu {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String NEW_GAME = "New Game";
	public static final String NETWORK = "Network";
	public static final String CLOSE = "Close";
	
	private JMenuItem newGame;
	private JMenuItem network;
	private JMenuItem close;
	private MenuBarController controller;
	
	public MainMenu(MainController mainController, String menuName) {
		super(menuName);
		controller = new MenuBarController(mainController);
		
		// Create menu items
		this.newGame = new JMenuItem(NEW_GAME);
		newGame.setMnemonic(KeyEvent.VK_G);
		newGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.CTRL_MASK));
		
		this.network = new JMenuItem(NETWORK);
		network.setMnemonic(KeyEvent.VK_N);
		network.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		
		this.close = new JMenuItem(CLOSE);
		close.setMnemonic(KeyEvent.VK_C);
		close.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
		
		// Add menu items to menu.
		this.add(this.newGame);
		this.add(this.network);
		this.add(this.close);
		
		// Add actions to menu items.
		this.newGame.addActionListener(controller);
		this.network.addActionListener(controller);
		this.close.addActionListener(controller);
	}
}
