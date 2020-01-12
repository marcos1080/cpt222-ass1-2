package au.edu.rmit.cpt222.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import au.edu.rmit.cpt222.model.HelperMethods;
import au.edu.rmit.cpt222.model.comms.Config;
import au.edu.rmit.cpt222.view.dialogs.NetworkDialog;

/**
 * Controller for the network settings dialog.
 * 
 * @author Mark Stuart
 */
public class NetworkDialogController implements ActionListener {

	private MainController mainController;
	private NetworkDialog dialog;
	
	public NetworkDialogController(NetworkDialog networkSettings, MainController mainController) {
		this.mainController = mainController;
		dialog = networkSettings;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getActionCommand() == NetworkDialog.ACCEPT) {
			String address = dialog.getAddress();
			String port = dialog.getPort();
			
			// Check if valid port entered.
			if ( !portIsValid(port) ) {
				mainController.showError("Invalid port entered. Must be between 1000 - 65535.");
				return;
			}
			
			// Check if valid address entered.
			if ( !addressIsValid(address) ) {
				mainController.showError("Invalid adddress entered. Must be a valid IP address.");
				return;
			}
			
			Config.setNetworkSettings(address, port);
			dialog.dispose();
		} else {
			// Cancel pressed.
			dialog.dispose();
		}
	}

	private boolean portIsValid(String port) {
		if (! HelperMethods.isNumeric(port))
			return false;
		
		int portNumber = Integer.parseInt(port);
		
		// Only accept this port range.
		if ( portNumber < 1000 || portNumber > 65535)
			return false;
		
		return true;
	}
	
	private boolean addressIsValid(String address) {
		Pattern ptn = Pattern.compile("^(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})$");
        Matcher match = ptn.matcher(address);
        return match.find();
	}
}
