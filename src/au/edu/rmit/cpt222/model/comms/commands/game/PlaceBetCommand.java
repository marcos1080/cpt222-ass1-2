package au.edu.rmit.cpt222.model.comms.commands.game;

import java.io.IOException;
import java.io.ObjectOutputStream;

import au.edu.rmit.cpt222.model.comms.RequestTask;
import au.edu.rmit.cpt222.model.exceptions.InsufficientFundsException;
import au.edu.rmit.cpt222.model.interfaces.Player;

public class PlaceBetCommand extends AbstractGameCommand {
	private static final long serialVersionUID = 4447628100974356632L;
	private Player player;
	private int betPoints;
	
	public PlaceBetCommand (Player player, int betPoints) {
		this.player = player;
		this.betPoints = betPoints;
	}

	@Override
	public void execute(RequestTask task) {
		String returnMessage = null;
		
		// Place bet
		try {
			task.getModel().placeBet(player, betPoints);
		} catch (InsufficientFundsException e) {
			System.out.println("Blace bet command has thrown an isufficient funds error");
			returnMessage = e.getMessage();
		}
		
		// Client game engine needs to handle any error.
		try {
			ObjectOutputStream responseStream = task.getResponseStream();
			responseStream.writeObject(returnMessage);
			responseStream.reset();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}
