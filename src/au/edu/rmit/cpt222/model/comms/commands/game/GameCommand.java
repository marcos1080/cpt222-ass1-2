package au.edu.rmit.cpt222.model.comms.commands.game;

import java.io.Serializable;

import au.edu.rmit.cpt222.model.comms.RequestTask;

public interface GameCommand extends Serializable {
	public void execute(RequestTask task);
}
