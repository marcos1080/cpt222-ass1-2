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
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import au.edu.rmit.cpt222.controller.AddCreditDialogController;
import au.edu.rmit.cpt222.controller.MainController;
import au.edu.rmit.cpt222.view.MainView;

/**
 * Dialog for adding credit to a player.
 * 
 * @author Mark Stuart
 */
public class AddCreditDialog extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int POINT_DEFAULT = 200;
	private static final int POINT_MIN = 10;
	private static final int POINT_MAX = 1000;
	private static final int POINT_STEP = 10;
	public static final String CANCEL = "Cancel";
	public static final String ACCEPT = "Add";
	
	private JSpinner pointSpinner;
    private SpinnerNumberModel pointSpinnerModel;
    
    // Controller
    private AddCreditDialogController actionListener;
	
	public AddCreditDialog(MainController mainController, String title) {
		super(mainController.getMainView(), title);
		pointSpinnerModel = new SpinnerNumberModel(POINT_DEFAULT, POINT_MIN, POINT_MAX, POINT_STEP);
	    pointSpinner = new JSpinner(this.pointSpinnerModel);
		actionListener = new AddCreditDialogController(this, mainController);
		
		setupView();
	}

	private void setupView() {
		JPanel content = new JPanel();
		int borderWidth = MainView.DEF_BORDER;
		content.setBorder(BorderFactory.createEmptyBorder(
				borderWidth, borderWidth, borderWidth, borderWidth));
		content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		// Bet
		JPanel pointPanel = new JPanel(new BorderLayout());
		pointPanel.setBorder(BorderFactory.createEmptyBorder(
				DialogManager.CREDIT_TOP_BORDER, 0, DialogManager.CREDIT_BOTTOM_BORDER, 0));
		JLabel pointLabel = new JLabel("Add Credit Amount... ");
		pointSpinner.setPreferredSize(DialogManager.CREDIT_SPINNER_SIZE);
		pointPanel.add(pointLabel, BorderLayout.WEST);
		pointPanel.add(pointSpinner, BorderLayout.EAST);
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
	 * Get the value of the add credit number spinner.
	 * 
	 * @return int
	 * 			Value of the credit spinner.
	 */
	public int getCredit() {
		return Integer.parseInt(pointSpinnerModel.getValue().toString());
	}
}
