package au.edu.rmit.cpt222.model.comms;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;

/**
 * Class that accepts connections from a callback client and creates
 * a new threaded CommandTask object.
 * 
 * @author Mark Stuart
 */
public class ClientGameEngineCallbackServer extends Thread {
	
	private ServerSocket serverSocket;
	private CallbackCommandQueue callbackQueue;
	
	public ClientGameEngineCallbackServer(GameEngineClientStub clientStub) {
		callbackQueue = new CallbackCommandQueue(clientStub);
		callbackQueue.start();
	}
	
	@Override
	public void run() {
		// Open server side socket.
		try {
			/**
			 *  Using empty port number here as there will be many clients running from 
			 *  this machine and they can't all use the same port.
			 */
			serverSocket = new ServerSocket(0);
			System.out.println("Callback Server on port " + serverSocket.getLocalPort() + 
					" is waiting for client connections");
			
			while (!serverSocket.isClosed()) {
				// Receive incoming clients. - create a new thread for each connection.
				new CommandTask(serverSocket.accept(), callbackQueue).start();
			}
		} catch (SocketException e) {
			System.out.println("   socket accept listener stopped");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("   socket closed");
	}
	
	public int getPort() {
		return serverSocket.getLocalPort();
	}
	
	public void close() {
		System.out.println("Closing the callback server...");
		try {
			serverSocket.close();
		} catch (IOException e) {
			System.out.println("   failed!");
			e.printStackTrace();
		}
	}
}
