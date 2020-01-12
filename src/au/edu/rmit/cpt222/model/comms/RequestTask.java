package au.edu.rmit.cpt222.model.comms;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

import au.edu.rmit.cpt222.model.comms.commands.game.AbstractGameCommand;
import au.edu.rmit.cpt222.model.interfaces.GameEngine;

/**
 * Main workhorse for the server.
 * This class handles communication from the client. It receives GameCommands
 * and executes them.
 * 
 * @author Mark Stuart
 */
public class RequestTask extends Thread {

	private GameEngine model;
	private Socket clientSocket;
	private ObjectOutputStream responseStream;
	private ObjectInputStream requestStream;
	
	// Used for callback commands.
	private ServerStubGameEngineCallback callback;
	
	public RequestTask(Socket accept, GameEngineServerStub serverStub) {
		model = serverStub.getModel();
		clientSocket = accept;
	}

	@Override
	public void run() {
		//Open streams.
		try {
			responseStream = new ObjectOutputStream(clientSocket.getOutputStream());
			requestStream = new ObjectInputStream(clientSocket.getInputStream());
			
			// Create main loop to receive commands.
			while (! clientSocket.isClosed()) {
				AbstractGameCommand command = (AbstractGameCommand) requestStream.readObject();
				
				// Build CLI message
				StringBuilder message = new StringBuilder(this.toString() + "\n");
				String[] nameParts = command.getClass().getName().split("\\.");
				message.append("\tCommand: " + nameParts[nameParts.length - 1] );
				System.out.println(message.toString());
				
				command.execute(this);
			}
			
		} catch (SocketException e) { 
			/**
			 * This would be called if the connection was lost or when the
			 * client shuts down. There is a sleep method called on the 
			 * close method for the client stub that waits a bit for the
			 * connection to be closed on this side first.
			 */
			System.out.println("Connection to the client has been lost!");
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public GameEngine getModel() {
		return model;
	}
	
	public String getClientAddress() {
		return clientSocket.getInetAddress().getHostAddress();
	}
	
	public ObjectOutputStream getResponseStream() {
		return responseStream;
	}
	
	public ServerStubGameEngineCallback getCallback() {
		return callback;
	}
	
	public void setCallback(ServerStubGameEngineCallback callback) {
		this.callback = callback;
	}
	
	public void close() {
		try {
			System.out.println("\tClosing RequestTask...");
			
			requestStream.close();
			responseStream.close();
			clientSocket.close();
			
			System.out.println("\t\tsuccess!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String toString() {
		StringBuffer details = new StringBuffer("\nRequest from: ");
		details.append(clientSocket.getRemoteSocketAddress());
		
		return details.toString();
	}
}
