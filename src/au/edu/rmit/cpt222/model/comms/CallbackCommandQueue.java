package au.edu.rmit.cpt222.model.comms;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import au.edu.rmit.cpt222.model.comms.commands.callback.CallbackCommand;

/**
 * Callbacks can be received quickly in the event of player and house
 * rolls. This thread uses a blocking queue to store the callbacks as 
 * they are received in the order they are received and fires them
 * one by one.
 * 
 * @author Mark Stuart
 */
public class CallbackCommandQueue extends Thread {
	private GameEngineClientStub clientStub;
	private BlockingQueue<CallbackCommand> blockingQueue;
	
	public CallbackCommandQueue(GameEngineClientStub clientStub) {
		this.clientStub = clientStub;
		blockingQueue = new LinkedBlockingDeque<>();
	}
	
	// Add a command to the queue.
	public boolean add(CallbackCommand command) {
		return blockingQueue.add(command);
	}
	
	@Override
	public void run() {
		while( true ) {
			try {
				// Waits for queue to have head then executes the command.
				blockingQueue.take().execute(clientStub);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
