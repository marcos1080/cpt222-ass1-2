package au.edu.rmit.cpt222.model.comms;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import au.edu.rmit.cpt222.model.comms.commands.callback.AbstractCallbackCommand;

/**
 * Class that handles incoming callbacks.
 * Each callback is sent to the callbackQueue that is running in another thread.
 * THis means this thread will return immediately to receive the next callback ASAP.
 * 
 * @author Mark Stuart
 */
public class CommandTask extends Thread {

	private Socket socket;
	private CallbackCommandQueue callbackQueue;
	private ObjectInputStream incomingCallbackStream;
	
	public CommandTask(Socket accept, CallbackCommandQueue callbackQueue) {
		socket = accept;
		this.callbackQueue = callbackQueue;
		
		System.out.println("Callback server has created a socket on port: " + accept.getPort());
	}
	
	@Override
	public void run() {
		try {
			incomingCallbackStream = new ObjectInputStream(socket.getInputStream());
			
			// Create main loop to receive commands.
			while (!socket.isClosed()) {
				AbstractCallbackCommand command = (AbstractCallbackCommand) incomingCallbackStream.readObject();
				callbackQueue.add(command);
			}
			
			// The other end of the socket has closed. Close this side.
			System.out.println("Callback on server has been closed. Closing local connection");
		} catch (EOFException e) {
			System.out.println("   connection has been closed by the server");
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void close() {
		try {
			incomingCallbackStream.close();
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
