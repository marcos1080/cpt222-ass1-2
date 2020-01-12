package au.edu.rmit.cpt222.model.comms.commands.callback;

import au.edu.rmit.cpt222.model.comms.GameEngineClientStub;
import au.edu.rmit.cpt222.model.interfaces.DicePair;
import au.edu.rmit.cpt222.model.interfaces.GameEngineCallback;
import au.edu.rmit.cpt222.model.interfaces.Player;

/**
 * This command handles both the playerRoll and playerOutcome
 * states.
 * The finalOutcome variable sets whether it is one or the other.
 *
 * @author Mark Stuart
 */
public class PlayerRollCallbackCommand extends AbstractCallbackCommand {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Player player;
	private DicePair dicePair;
	private Boolean finalOutcome;
	
	public PlayerRollCallbackCommand(Player player, DicePair dicePair, Boolean finalOutcome) {
		this.player = player;
		this.dicePair = dicePair;
		this.finalOutcome = finalOutcome;
	}

	@Override
	public void execute(GameEngineClientStub clientStub) {
		GameEngineCallback callback = clientStub.getCallback();
		
		// Check if this callback is targeted for this client.
		if (player.getPlayerId().equals(clientStub.getPlayerId())) {
			// Yes: trigger the correct callback method.
			if (finalOutcome) {
				callback.playerRollOutcome(player, dicePair, clientStub);
			} else {
				callback.playerRoll(player, dicePair, clientStub);
			}
		} else {
			// No: if the callback is for a final outcome print to console.
			if (finalOutcome) {
				StringBuilder message = new StringBuilder();
				message.append(player.getPlayerName() + " has rolled: ");
				message.append(dicePair);
				System.out.println(message);
			}
		}	
	}
}
