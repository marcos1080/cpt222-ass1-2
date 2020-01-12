package au.edu.rmit.cpt222.model.comms.commands.callback;

import au.edu.rmit.cpt222.model.comms.GameEngineClientStub;
import au.edu.rmit.cpt222.model.interfaces.DicePair;

/**
 * This command handles both the houseRoll and houseOutcome
 * states.
 * The finalOutcome variable sets whether it is one or the other.
 *
 * @author Mark Stuart
 */
public class HouseRollCallbackCommand extends AbstractCallbackCommand {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7591925907370703259L;
	private DicePair dicePair;
	private Boolean finalOutcome;
	
	public HouseRollCallbackCommand(DicePair dicePair, Boolean finalOutcome) {
		this.dicePair = dicePair;
		this.finalOutcome = finalOutcome;
	}

	@Override
	public void execute(GameEngineClientStub clientStub) {
		if (finalOutcome) {
			clientStub.getCallback().houseRollOutcome(dicePair, clientStub);
		} else {
			clientStub.getCallback().houseRoll(dicePair, clientStub);
		}
	}

}
