package au.edu.rmit.cpt222.model.comms.commands.game;

import au.edu.rmit.cpt222.model.comms.RequestTask;

public class CalculateResultCommand extends AbstractGameCommand {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2152571381991406851L;

	@Override
	public void execute(RequestTask task) {
		task.getModel().calculateResult();
	}

}
