package au.edu.rmit.cpt222.model.comms;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import au.edu.rmit.cpt222.model.comms.commands.game.DisconnectCommand;
import au.edu.rmit.cpt222.model.comms.commands.game.GameCommand;

/**
 * Class to encapsulate all the socket communication and admin functionality.
 * 
 * @author Mark Stuart
 */
public class GameClientSocket {
	private Socket clientSocket;
	
	// create streams
	private ObjectOutputStream requestStream;
	private ObjectInputStream responseStream;
	
	/**
	 * Connects to the server using the details from the Config class.
	 */
	public void connect() {
		try {
			// open socket.
			clientSocket = new Socket(Config.getHostAddress(), Config.getHostPort());
			
			//Open streams.
			requestStream = new ObjectOutputStream(clientSocket.getOutputStream());
			responseStream = new ObjectInputStream(clientSocket.getInputStream());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Shuts down the connection to the server.
	 */
	public void disconnect() {
		System.out.println("Closing output/input streams and socket...!");
		
		try {
			// Inform server of disconnect.
			System.out.println("Asking server to close connection...!");
			requestStream.writeObject(new DisconnectCommand());
			
			/**
			 *  Stop the socket and streams from closing too quickly.
			 *  If this is not done an exception is thrown on the server as the socket is closed too soon.
			 */
			Thread.sleep(200);
						
			requestStream.close();
			responseStream.close();
			clientSocket.close();
			System.out.println("   success!");
		} catch (IOException | InterruptedException e) {
			System.out.println("   an error was encountered!");
			e.printStackTrace();
		}
	}
	
	/**
	 * Send a command to the server. No response expected.
	 * 
	 * @param command
	 */
	public void sendRequest(GameCommand command) {
		try {
			requestStream.writeObject(command);
			requestStream.reset();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Send a command to the server. A response object is expected.
	 * 
	 * @param command
	 * @return
	 */
	public Object sendRequestWithResponse(GameCommand command) {
		Object returnObject = null;
		
		try {
			requestStream.writeObject(command);
			requestStream.reset();
			returnObject = responseStream.readObject();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return returnObject;
	}
	
}
