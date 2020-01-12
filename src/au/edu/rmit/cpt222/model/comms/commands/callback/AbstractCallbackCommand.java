package au.edu.rmit.cpt222.model.comms.commands.callback;

import au.edu.rmit.cpt222.model.comms.GameEngineClientStub;

public abstract class AbstractCallbackCommand implements CallbackCommand {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3038591717696497052L;

	public abstract void execute(GameEngineClientStub clientStub);
	
	@Override 
	public String toString() {
		return this.getClass().getName();
	}
}
