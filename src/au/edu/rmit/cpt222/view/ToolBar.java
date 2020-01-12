package au.edu.rmit.cpt222.view;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JPanel;

import au.edu.rmit.cpt222.controller.MainController;
import au.edu.rmit.cpt222.controller.ToolBarController;

/**
 * Tool bar view for the program.
 * 
 * @author Mark Stuart
 */
public class ToolBar extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String ROLL_COMMAND = "Roll";
	public static final String PLACE_BET_COMMAND = "Change Bet";
	public static final String ADD_CREDIT_COMMAND = "Add Credit";
	
	private JButton roll;
	private ToolBarController controller;
	
	public ToolBar(MainController mainController) {
		setBackground(Color.WHITE);
		controller = new ToolBarController(mainController);
		
		// Add buttons
		roll = new JButton();
		addButton(roll, ROLL_COMMAND);	
		addButton(new JButton(), PLACE_BET_COMMAND);
		addButton(new JButton(), ADD_CREDIT_COMMAND);
	}
	
	/**
	 * Disable the roll button. 
	 */
	public void disableRollButton() {
		roll.setEnabled(false);
	}
	
	/**
	 * Enable the roll button.
	 */
	public void enableRollButton() {
		roll.setEnabled(true);
	}
	
	/**
	 * Used to reduce duplicate code. Add a button to the toolbar.
	 * 
	 * @param button
	 * @param command
	 */
	private void addButton(JButton button, String command) {
		button.setText(command);
		button.setActionCommand(command);
		add(button);
		button.addActionListener(controller);
	}
}
