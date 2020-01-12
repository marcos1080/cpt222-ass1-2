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
import au.edu.rmit.cpt222.controller.NetworkDialogController;
import au.edu.rmit.cpt222.model.comms.Config;
import au.edu.rmit.cpt222.view.MainView;

public class NetworkDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7710632440997572059L;
	private static final Dimension ADDRESS_FIELD_SIZE = new Dimension(200, 25);
	public static final String CANCEL = "Cancel";
	public static final String ACCEPT = "Save";
	
	private JTextField addressField;
	private JTextField portField;
    
    private NetworkDialogController actionListener;
	
	public NetworkDialog(MainController mainController, String title) {
		super(mainController.getMainView(), title);
		addressField = new JTextField(Config.getHostAddress());
		portField = new JTextField(Integer.toString(Config.getHostPort()));
	    
	    // Create dialogs action listener.
		actionListener = new NetworkDialogController(this, mainController);
		
		setupView();
	}
	
	private void setupView() {
		JPanel content = new JPanel();
		int borderWidth = MainView.DEF_BORDER;
		content.setBorder(BorderFactory.createEmptyBorder(
				borderWidth, borderWidth, borderWidth, borderWidth));
		content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		// Address
		JPanel addressPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		addressPanel.add(new JLabel("Enter Host IP Address..."));
		content.add(addressPanel);
		addressField.setPreferredSize(ADDRESS_FIELD_SIZE);
		content.add(addressField);
		
		// Port
		JPanel portPanel = new JPanel(new BorderLayout());
		portPanel.setBorder(BorderFactory.createEmptyBorder(
				DialogManager.CREDIT_TOP_BORDER, 0, DialogManager.CREDIT_BOTTOM_BORDER, 0));
		JLabel pointLabel = new JLabel("Enter Host Port... ");
		portPanel.add(pointLabel, BorderLayout.WEST);
		portField.setPreferredSize(DialogManager.CREDIT_SPINNER_SIZE);
		portPanel.add(portField);
	    content.add(portPanel);
		
	    // Buttons.
	    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	    JButton close = new JButton(CANCEL);
	    close.setMnemonic(KeyEvent.VK_C);
	    buttonPanel.add(close);
	    JButton accept = new JButton(ACCEPT);
	    accept.setMnemonic(KeyEvent.VK_A);
	    buttonPanel.add(accept);
	    content.add(buttonPanel);
	    
	    this.add(content);
	    pack();
	    
	    // Add listeners.
	    close.addActionListener(actionListener);
	    accept.addActionListener(actionListener);
	    
	    // Set enter to trigger accept.
	    getRootPane().setDefaultButton(accept);
	}
	
	public String getAddress() {
		return addressField.getText();
	}
	
	public String getPort() {
		return portField.getText();
	}
}
