package au.edu.rmit.cpt222.model.comms.commands.game;

import java.io.IOException;

import au.edu.rmit.cpt222.model.comms.RequestTask;

public class RemovePlayerCommand extends AbstractGameCommand {
	private static final long serialVersionUID = -7490354185742740087L;
	private String playerId;
	
	public RemovePlayerCommand(String playerId) {
		this.playerId = playerId;
	}

	@Override
	public void execute(RequestTask task) {
		try {
			task.getResponseStream().writeObject(task.getModel().removePlayer(playerId));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
