package au.edu.rmit.cpt222.view.dialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import au.edu.rmit.cpt222.controller.MainController;
import au.edu.rmit.cpt222.controller.PlaceBetDialogController;
import au.edu.rmit.cpt222.view.MainView;

/**
 * Dialog for placing a bet for a player.
 * 
 * @author Mark Stuart
 */
public class PlaceBetDialog extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String CANCEL = "Cancel";
	public static final String ACCEPT = "Place Bet";
	
	private JTextField betField;
    private PlaceBetDialogController actionListener;
	
	public PlaceBetDialog(MainController mainController, String title) {
		super(mainController.getMainView(), title);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(mainController.getMainView());
		
		// Panel to hold all other containers.
		JPanel contentPanel = new JPanel();
		int borderWidth = MainView.DEF_BORDER;
		contentPanel.setBorder(BorderFactory.createEmptyBorder(
				borderWidth, borderWidth, borderWidth, borderWidth));
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		add(contentPanel);
		
		// Betting field.
		JPanel betPanel = new JPanel(new BorderLayout());
		JLabel betLabel = new JLabel("Enter Bet Amount...");
		betPanel.add(betLabel, BorderLayout.WEST);
		betField = new JTextField();
		betField.setPreferredSize(DialogManager.CREDIT_SPINNER_SIZE);
		betPanel.add(betField, BorderLayout.EAST);
		betPanel.setBorder(BorderFactory.createEmptyBorder(
				0, 0, DialogManager.CREDIT_BOTTOM_BORDER, 0));
		contentPanel.add(betPanel);
				
		// Buttons.
	    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	    JButton close = new JButton(CANCEL);
	    close.setMnemonic(KeyEvent.VK_C);
	    buttonPanel.add(close);
	    JButton add = new JButton(ACCEPT);
	    add.setMnemonic(KeyEvent.VK_P);
	    buttonPanel.add(add);
	    contentPanel.add(buttonPanel);
	    
	    pack();
	    
	    // Add listeners.
	    actionListener = new PlaceBetDialogController(this, mainController);
	    close.addActionListener(actionListener);
	    add.addActionListener(actionListener);
	    
	    // Set enter to trigger accept.
	    getRootPane().setDefaultButton(add);
	}
	
	/**
	 * Get the value from the bet amount.
	 * 
	 * @return
	 */
	public String getBet() {
		return betField.getText();
	}
}
