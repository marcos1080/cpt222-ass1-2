package au.edu.rmit.cpt222.model.comms.commands.game;

import java.io.IOException;
import java.io.ObjectOutputStream;

import au.edu.rmit.cpt222.model.comms.RequestTask;
import au.edu.rmit.cpt222.model.comms.ServerStubGameEngineCallback;

public class RemoveGameEngineCallbackCommand extends AbstractGameCommand {
	private static final long serialVersionUID = 6657093846476141507L;
	
	@Override
	public void execute(RequestTask task) {
		ServerStubGameEngineCallback callback = task.getCallback();
		task.getModel().removeGameEngineCallback(callback);
		
		// Set to null to remove reference.
		task.setCallback(null);
		
		// Close all connection variables.
		ObjectOutputStream responseStream = task.getResponseStream();
		try {
			callback.close();
			responseStream.writeObject(new Boolean(true));
			responseStream.reset();
		} catch (IOException e) {
			try {
				// An error was encountered. Try and inform the client.
				responseStream.writeObject(new Boolean(false));
				responseStream.reset();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			e.printStackTrace();
		}
	}
}
