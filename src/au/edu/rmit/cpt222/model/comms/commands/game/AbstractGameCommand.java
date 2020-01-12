package au.edu.rmit.cpt222.model.comms.commands.game;

import au.edu.rmit.cpt222.model.comms.RequestTask;

public abstract class AbstractGameCommand implements GameCommand {
	private static final long serialVersionUID = 6732226640617693299L;

	public abstract void execute(RequestTask task);
	
	@Override 
	public String toString() {
		return this.getClass().getName();
	}
}
