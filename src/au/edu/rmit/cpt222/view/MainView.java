package au.edu.rmit.cpt222.view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import au.edu.rmit.cpt222.controller.MainController;
import au.edu.rmit.cpt222.model.interfaces.GameEngine;
import au.edu.rmit.cpt222.view.menu.GameMenuBar;

public class MainView extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// Window dimensions.
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	
	// Default border width
	public static final int DEF_BORDER = 10;
	
	private MainController mainController;
	
	// Attached panels.
	private ToolBar toolbar;
	private GamePanel gamePanel;
	private HistoryBox historyBox;
	private GameMenuBar menuBar;
		
	public MainView(String title, GameEngine model) {
		super(title);
		mainController = new MainController(this, model);
		this.initView();
	}
	
	/**
	 * Get the toolbar for the program
	 * 
	 * @return ToolBar
	 */
	public ToolBar getToolBar() {
		return toolbar;
	}
	/**
	 * Get the game panel for the program
	 * 
	 * @return GamePanel
	 */
	
	public GamePanel getGamePanel() {
		return gamePanel;
	}
	
	/**
	 * Get the History Box for the program.
	 * 
	 * @return HistoryBox
	 */
	public HistoryBox getHistoryBox() {
		return historyBox;
	}
	
	// Set the view up.
	private void initView() {
		setSize(WIDTH, HEIGHT);
		setLocationRelativeTo(null); // Sets the window to the center of the screen.
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		// Initialise sub-views.
		gamePanel = new GamePanel(mainController);
		historyBox = new HistoryBox();
		menuBar = new GameMenuBar(mainController);
		
		/**
		 *  Toolbar is disabled by default.
		 *  Only shows once a player is added.
		 */
		toolbar = new ToolBar(mainController);
		toolbar.setVisible(false);;
		
		// Add views
		add(gamePanel, BorderLayout.CENTER);
		add(historyBox, BorderLayout.EAST);
		add(toolbar, BorderLayout.NORTH);
		
		setJMenuBar(this.menuBar);
		
		setVisible(true);
	}
}
