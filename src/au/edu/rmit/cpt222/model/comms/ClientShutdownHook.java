package au.edu.rmit.cpt222.model.comms;

public class ClientShutdownHook extends Thread {
	
	private GameEngineClientStub clientStub;
	
	public ClientShutdownHook (GameEngineClientStub clientStub) {
		this.clientStub = clientStub;
	}
	
	@Override
	public void run() {
		System.out.println("Client shutdown hook called!");
		clientStub.close();
	}
}
