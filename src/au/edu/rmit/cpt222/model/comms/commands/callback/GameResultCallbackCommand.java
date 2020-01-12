package au.edu.rmit.cpt222.model.comms.commands.callback;

import au.edu.rmit.cpt222.model.comms.GameEngineClientStub;
import au.edu.rmit.cpt222.model.interfaces.Player;
import au.edu.rmit.cpt222.model.interfaces.GameEngine.GameStatus;

public class GameResultCallbackCommand extends AbstractCallbackCommand {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7537869509591695903L;
	private Player player;
	private GameStatus result;
	
	public GameResultCallbackCommand(Player player, GameStatus result) {
		this.player = player;
		this.result = result;
	}

	@Override
	public void execute(GameEngineClientStub clientStub) {
		// Check if this callback is targeted for this client
		if (player.getPlayerId().equals(clientStub.getPlayerId())) {
			// Yes: display the game result in the client view.
			clientStub.getCallback().gameResult(player, result, clientStub);
		} else {
			// No: display to the console.
			StringBuilder message = new StringBuilder();
			message.append(player.getPlayerName() + " has ");
			message.append(result + " the game.");
			System.out.println(message);
		}	
	}

}
