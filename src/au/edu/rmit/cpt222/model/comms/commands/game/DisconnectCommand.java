package au.edu.rmit.cpt222.model.comms.commands.game;

import au.edu.rmit.cpt222.model.comms.RequestTask;

public class DisconnectCommand extends AbstractGameCommand {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5370348930309625575L;
	
	@Override
	public void execute(RequestTask task) {
		task.close();
	}

}
