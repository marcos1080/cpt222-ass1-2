package au.edu.rmit.cpt222.model.comms;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import au.edu.rmit.cpt222.model.comms.commands.callback.CallbackCommand;
import au.edu.rmit.cpt222.model.comms.commands.callback.GameResultCallbackCommand;
import au.edu.rmit.cpt222.model.comms.commands.callback.HouseRollCallbackCommand;
import au.edu.rmit.cpt222.model.comms.commands.callback.PlayerRollCallbackCommand;
import au.edu.rmit.cpt222.model.interfaces.DicePair;
import au.edu.rmit.cpt222.model.interfaces.GameEngine;
import au.edu.rmit.cpt222.model.interfaces.GameEngine.GameStatus;
import au.edu.rmit.cpt222.model.interfaces.GameEngineCallback;
import au.edu.rmit.cpt222.model.interfaces.Player;

/**
 * Game engine callback that runs as a thread.
 * It uses a blocking queue to receive callback instructions from the model
 * 
 * 
 * @author Mark Stuart
 */
public class ServerStubGameEngineCallback extends Thread implements GameEngineCallback {
	
	// Communication objects.
	private Socket callbackSocket;
	private ObjectOutputStream callbackStream;
	private BlockingQueue<CallbackCommand> blockingQueue = new LinkedBlockingDeque<>();
		
	public ServerStubGameEngineCallback(String address, int port) {
		//Open streams.
		try {
			System.out.println(address);
			callbackSocket = new Socket(address, port);
			callbackStream = new ObjectOutputStream(callbackSocket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void gameResult(Player player, GameStatus result, GameEngine engine) {
		blockingQueue.add(new GameResultCallbackCommand(player, result));
	}

	@Override
	public void houseRoll(DicePair dicePair, GameEngine engine) {
		blockingQueue.add(new HouseRollCallbackCommand(dicePair, false));
	}

	@Override
	public void houseRollOutcome(DicePair result, GameEngine engine) {
		blockingQueue.add(new HouseRollCallbackCommand(result, false));
	}

	@Override
	public void playerRoll(Player player, DicePair dicePair, GameEngine engine) {
		System.out.println(player.getPlayerName() + ": " + dicePair);
		blockingQueue.add(new PlayerRollCallbackCommand(player, dicePair, false));
	}

	@Override
	public void playerRollOutcome(Player player, DicePair result, GameEngine engine) {
		blockingQueue.add(new PlayerRollCallbackCommand(player, result, true));
	}
	
	public void close() throws IOException {
		System.out.println("\tClosing game engine callback...");
		
		callbackStream.close();
		callbackSocket.close();
		
		System.out.println("\t\tsuccess!");
	}
	
	@Override
	public void run() {
		try {
			// Sends any command that gets put in the queue.
			while(!callbackSocket.isClosed()) {
				callbackStream.writeObject(blockingQueue.take());
				callbackStream.reset();
			}
			
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}
}
