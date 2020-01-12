package au.edu.rmit.cpt222.model.comms.commands.game;

import au.edu.rmit.cpt222.model.comms.RequestTask;
import au.edu.rmit.cpt222.model.interfaces.Player;

public class RollPlayerCommand extends AbstractGameCommand {
	private static final long serialVersionUID = 3854820145611360821L;
	private Player player;
	private int initialDelay;
	private int finalDelay;
	private int delayIncrement;
	
	public RollPlayerCommand (Player player, int initialDelay, int finalDelay, int delayIncrement) {
		this.player = player;
		this.initialDelay = initialDelay;
		this.finalDelay = finalDelay;
		this.delayIncrement = delayIncrement;
	}

	@Override
	public void execute(RequestTask task) {
		task.getModel().rollPlayer(player, initialDelay, finalDelay, delayIncrement);
		
		// Print the result.
		StringBuilder message = new StringBuilder("\tResult: ");
		message.append(task.getModel().getPlayer(player.getPlayerId()).getRollResult().toString());
		
		System.out.println(message.toString());
	}

}
