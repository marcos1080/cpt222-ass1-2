package au.edu.rmit.cpt222.model.comms.commands.game;

import au.edu.rmit.cpt222.model.comms.RequestTask;
import au.edu.rmit.cpt222.model.interfaces.Player;

public class SetPlayerPointsCommand extends AbstractGameCommand implements GameCommand {
	private static final long serialVersionUID = 6074766299883683575L;
	private String playerId;
	private int totalPoints;

	public SetPlayerPointsCommand(String playerId, int totalPoints) {
		this.playerId = playerId;
		this.totalPoints = totalPoints;
	}

	@Override
	public void execute(RequestTask task) {
		Player player = task.getModel().getPlayer(playerId);
		
		// Get the before and after to calculate the winnings.
		int before = player.getPoints();
		task.getModel().setPlayerPoints(playerId, totalPoints);
		int after = player.getPoints();
		
		// Print the winnings.
		System.out.println("\tAmount added: " + (after - before));
	}

}
