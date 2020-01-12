package au.edu.rmit.cpt222.driver;

import javax.swing.SwingUtilities;

import au.edu.rmit.cpt222.model.comms.GameEngineClientStub;
import au.edu.rmit.cpt222.model.interfaces.GameEngine;
import au.edu.rmit.cpt222.view.MainView;

public class Ass2Driver {

	public static void main(String[] args) {
		// Initialise the Model.
		GameEngine model = new GameEngineClientStub();
		
		// Initialise view. Runs in different thread to avoid gui lockups.
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				MainView mainView = new MainView("Assignment 1 GUI", model);
			}		
		});	
	}
}
