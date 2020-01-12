package au.edu.rmit.cpt222.model.comms;

import java.io.IOException;
import java.net.ServerSocket;

import au.edu.rmit.cpt222.model.GameEngineImpl;
import au.edu.rmit.cpt222.model.interfaces.GameEngine;

/**
 * Class that receives incoming client connections.
 * Once a connection has been made a separate thread is created
 * in a RequestTask object to handle communication between the
 * client and model.
 * 
 * @author Mark Stuart
 */
public class GameEngineServerStub {
	
	private GameEngine model;
	private ServerSocket serverSocket;
	
	public GameEngineServerStub(int portNumber) {
		
		model = new GameEngineImpl();
		
		// OPen server side socket.
		try {
			serverSocket = new ServerSocket(portNumber);
			System.out.println("Server on port " + serverSocket.getLocalPort() + " is waiting for client connections");
			
			// Close streams and socket ( when client exits);
			Runtime.getRuntime().addShutdownHook(new ServerShutdownHook(this));
			
			while (!serverSocket.isClosed()) {
				// Receive incoming clients. - create a new thread for each connection.
				new RequestTask(serverSocket.accept(), this).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public GameEngine getModel() {
		return model;
	}
	
	protected void close() {
		try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
