package au.edu.rmit.cpt222.model.comms.commands.game;

import java.io.IOException;
import java.io.ObjectOutputStream;

import au.edu.rmit.cpt222.model.comms.RequestTask;
import au.edu.rmit.cpt222.model.interfaces.GameEngine;
import au.edu.rmit.cpt222.model.interfaces.Player;

public class AddPlayerCommand extends AbstractGameCommand {
	private static final long serialVersionUID = 2095155802819431432L;
	private Player player;
	private GameEngine gameEngine;
	
	public AddPlayerCommand(Player player) {
		this.player = player;
	}

	@Override
	public void execute(RequestTask task) {
		this.gameEngine = task.getModel();
		ObjectOutputStream responseStream = task.getResponseStream();
		
		// Add player to the model.
		String playerId = gameEngine.addPlayer(player);
		
		// Return the playerId to the client
		try {
			responseStream.writeObject(playerId);
			responseStream.reset();
		} catch (IOException e) {
			// Cannot return the playerId to the client. Remove from game engine.
			gameEngine.removePlayer(playerId);
			e.printStackTrace();
		}	
	}
}
