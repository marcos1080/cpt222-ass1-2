package au.edu.rmit.cpt222.model.comms.commands.game;

import au.edu.rmit.cpt222.model.comms.RequestTask;
import au.edu.rmit.cpt222.model.comms.ServerStubGameEngineCallback;

/**
 * This command creates a new callback and adds it to the requestTask object.
 * It then adds it to the model.
 * 
 * @author Mark Stuart
 */
public class AddCallbackCommand extends AbstractGameCommand {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3032414262915149374L;
	private int port;
	
	public AddCallbackCommand(int port) {
		this.port = port;
	}

	@Override
	public void execute(RequestTask task) {
		ServerStubGameEngineCallback callback = new ServerStubGameEngineCallback(
				task.getClientAddress(), port);
		
		task.setCallback(callback);
		task.getModel().addGameEngineCallback(callback);
		
		// ServerStubGameEngineCallback implements Thread.
		callback.start();
	}
}
