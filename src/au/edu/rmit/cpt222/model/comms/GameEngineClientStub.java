package au.edu.rmit.cpt222.model.comms;

import java.util.Collection;

import au.edu.rmit.cpt222.model.comms.commands.game.AddCallbackCommand;
import au.edu.rmit.cpt222.model.comms.commands.game.AddPlayerCommand;
import au.edu.rmit.cpt222.model.comms.commands.game.CalculateResultCommand;
import au.edu.rmit.cpt222.model.comms.commands.game.GetPlayerCommand;
import au.edu.rmit.cpt222.model.comms.commands.game.PlaceBetCommand;
import au.edu.rmit.cpt222.model.comms.commands.game.RemoveGameEngineCallbackCommand;
import au.edu.rmit.cpt222.model.comms.commands.game.RemovePlayerCommand;
import au.edu.rmit.cpt222.model.comms.commands.game.RollPlayerCommand;
import au.edu.rmit.cpt222.model.comms.commands.game.SetPlayerPointsCommand;
import au.edu.rmit.cpt222.model.exceptions.InsufficientFundsException;
import au.edu.rmit.cpt222.model.interfaces.GameEngine;
import au.edu.rmit.cpt222.model.interfaces.GameEngineCallback;
import au.edu.rmit.cpt222.model.interfaces.Player;

/**
 * Class that acts as a proxy for the client to the server.
 * It forwards any requests from the client to the server and handles
 * any responses.
 * 
 * @author Mark Stuart
 */
public class GameEngineClientStub implements GameEngine {
	
	private GameClientSocket clientSocket;
	
	// Callback variables.
	private GameEngineCallback callback;
	private ClientGameEngineCallbackServer callbackServer;
	
	// Store the latest player ID. Used to remove from server properly.
	private String currentPlayerId;
	
	public GameEngineClientStub() {
		// Set up socket to communicate to server.
		clientSocket = new GameClientSocket();
		
		// Set up callback server
		callbackServer = new ClientGameEngineCallbackServer(this);
		callbackServer.start();
		
		// Close streams and socket ( when client exits);
		Runtime.getRuntime().addShutdownHook(new ClientShutdownHook(this));
	}

	@Override
	public void addGameEngineCallback(GameEngineCallback gameEngineCallback) {
		clientSocket.connect(); // Connect to server.
		callback = gameEngineCallback;
		clientSocket.sendRequest(new AddCallbackCommand(callbackServer.getPort()));
	}

	@Override
	public String addPlayer(Player player) {		
		currentPlayerId = (String) clientSocket.sendRequestWithResponse(new AddPlayerCommand(player));;
		
		return currentPlayerId;
	}

	@Override
	public void calculateResult() {
		clientSocket.sendRequest(new CalculateResultCommand());
	}

	@Override
	public Collection<Player> getAllPlayers() {
		return null;
	}

	@Override
	public Player getPlayer(String playerId) {
		return (Player) clientSocket.sendRequestWithResponse(new GetPlayerCommand(playerId));
	}

	@Override
	public void placeBet(Player player, int betPoints) throws InsufficientFundsException {
		
		String exception = (String) clientSocket.sendRequestWithResponse(new PlaceBetCommand(player, betPoints));

		// If an exception was raised by the server create one here.
		if (exception != null)
			throw new InsufficientFundsException(exception);
	}

	@Override
	public void removeGameEngineCallback(GameEngineCallback gameEngineCallback) {
		System.out.println("Removing GameEngineCallback from server...");
		
		if ((Boolean) clientSocket.sendRequestWithResponse(new RemoveGameEngineCallbackCommand())) {
			System.out.println("   success!");
			clientSocket.disconnect(); // Shut down the connection to the server.
		} else
			System.out.println("   an error was encountered by the server!");
	}

	@Override
	public boolean removePlayer(String playerId) {
		
		System.out.println("Removing player from server...");
		
		Boolean result = (Boolean) clientSocket.sendRequestWithResponse(new RemovePlayerCommand(playerId));
		
		if (result) {
			System.out.println("   success!");
			
			// Reset local playerID.
			currentPlayerId = null;
			
			// Remove Game Engine Callback.
			removeGameEngineCallback(callback);
		} else
			System.out.println("   failed!");
			
		return result;
	}

	@Override
	public void rollHouse(int initialDelay, int finalDelay, int delayIncrement) {
	}

	@Override
	public void rollPlayer(Player player, int initialDelay, int finalDelay, int delayIncrement) {
		clientSocket.sendRequest(new RollPlayerCommand(player, initialDelay, finalDelay, delayIncrement));
	}

	@Override
	public void setPlayerPoints(String playerId, int totalPoints) {
		clientSocket.sendRequest(new SetPlayerPointsCommand(playerId, totalPoints));
	}

	public GameEngineCallback getCallback() {
		return callback;
	}
	
	public String getPlayerId() {
		return currentPlayerId;
	}
	
	public void close() {
		// If playerId is null then no connection has been made.
		if (currentPlayerId != null) {
			/**
			 *  Remove Player from client.
			 *  This will trigger the removecallback method which will
			 *  trigger the disconnect method.
			 */
			removePlayer(currentPlayerId);
		}
		
		callbackServer.close();
	}
}
