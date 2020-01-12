package au.edu.rmit.cpt222.model.comms.commands.game;

import java.io.IOException;
import java.io.ObjectOutputStream;

import au.edu.rmit.cpt222.model.comms.RequestTask;
import au.edu.rmit.cpt222.model.interfaces.Player;

public class GetPlayerCommand extends AbstractGameCommand {
	private static final long serialVersionUID = -6544213782387307052L;
	private String playerId;
	
	public GetPlayerCommand (String playerId) {
		this.playerId = playerId;
	}

	@Override
	public void execute(RequestTask task) {
		Player player = task.getModel().getPlayer(playerId);
		
		// Print the player info to the console.
		StringBuilder message = new StringBuilder("\tPlayer:\n");
		message.append(player.toString());
		System.out.println(message.toString());
		
		try {
			ObjectOutputStream responseStream = task.getResponseStream();
			responseStream.writeObject(player);
			responseStream.reset();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
