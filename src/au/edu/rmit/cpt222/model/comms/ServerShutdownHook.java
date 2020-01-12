package au.edu.rmit.cpt222.model.comms;

/**
 * Handles the graceful shutdown of the server.
 * 
 * @author Mark Stuart
 */
public class ServerShutdownHook extends Thread {
	
	private GameEngineServerStub serverStub;
	
	public ServerShutdownHook (GameEngineServerStub serverStub) {
		this.serverStub = serverStub;
	}
	
	@Override
	public void run() {
		System.out.println("Client shutdown hook called!");
		serverStub.close();
	}
}
