package au.edu.rmit.cpt222.view.dialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;
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
import au.edu.rmit.cpt222.controller.NewPlayerDialogController;
import au.edu.rmit.cpt222.view.MainView;

/**
 * Dialog box for the add player feature.
 * 
 * @author Mark Stuart
 */
public class NewPlayerDialog extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final Dimension NAME_FIELD_SIZE = new Dimension(200, 25);
	
	public static final String CANCEL = "Cancel";
	public static final String ACCEPT = "Add";
	
	private JTextField nameField;
	private JTextField pointsField;
    
    private NewPlayerDialogController actionListener;
	
	public NewPlayerDialog(MainController mainController, String title) {
		super(mainController.getMainView(), title);
		nameField = new JTextField();
		pointsField = new JTextField();
	    
	    // Create dialogs action listener.
		actionListener = new NewPlayerDialogController(this, mainController);
		
		setupView();
	}

	/**
	 * Set up the dialog view.
	 */
	private void setupView() {
		JPanel content = new JPanel();
		int borderWidth = MainView.DEF_BORDER;
		content.setBorder(BorderFactory.createEmptyBorder(
				borderWidth, borderWidth, borderWidth, borderWidth));
		content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		// Name
		JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		namePanel.add(new JLabel("Enter Player Name..."));
		content.add(namePanel);
		nameField.setPreferredSize(NAME_FIELD_SIZE);
		content.add(nameField);
		
		// Bet
		JPanel pointPanel = new JPanel(new BorderLayout());
		pointPanel.setBorder(BorderFactory.createEmptyBorder(
				DialogManager.CREDIT_TOP_BORDER, 0, DialogManager.CREDIT_BOTTOM_BORDER, 0));
		JLabel pointLabel = new JLabel("Enter Credit Amount... ");
		pointPanel.add(pointLabel, BorderLayout.WEST);
		pointsField.setPreferredSize(DialogManager.CREDIT_SPINNER_SIZE);
		pointPanel.add(pointsField);
	    content.add(pointPanel);
		
	    // Buttons.
	    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	    JButton close = new JButton(CANCEL);
	    close.setMnemonic(KeyEvent.VK_C);
	    buttonPanel.add(close);
	    JButton add = new JButton(ACCEPT);
	    add.setMnemonic(KeyEvent.VK_A);
	    buttonPanel.add(add);
	    content.add(buttonPanel);
	    
	    this.add(content);
	    pack();
	    
	    // Add listeners.
	    close.addActionListener(actionListener);
	    add.addActionListener(actionListener);
	    
	    // Set enter to trigger accept.
	    getRootPane().setDefaultButton(add);
	}
	
	/**
	 * Get the value of the name field.
	 * 
	 * @return String
	 * 			Value of the name field.
	 */
	public String getNameField() {
		return nameField.getText();
	}
	
	/**
	 * Get the value from the points field.
	 * 
	 * @return String
	 * 			Value of the points field.
	 */
	public String getPointsField() {
		return pointsField.getText();
	}
	
	/**
	 * Reset all dialog fields.
	 */
	public void reset() {
		nameField.setText("");
		pointsField.setText("");
	}
}
