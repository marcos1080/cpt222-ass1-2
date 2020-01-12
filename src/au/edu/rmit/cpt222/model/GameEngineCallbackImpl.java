package au.edu.rmit.cpt222.model;

import au.edu.rmit.cpt222.model.interfaces.DicePair;
import au.edu.rmit.cpt222.model.interfaces.GameEngine;
import au.edu.rmit.cpt222.model.interfaces.GameEngine.GameStatus;
import au.edu.rmit.cpt222.model.interfaces.GameEngineCallback;
import au.edu.rmit.cpt222.model.interfaces.Player;

public class GameEngineCallbackImpl implements GameEngineCallback {

	@Override
	public void gameResult(Player player, GameStatus result, GameEngine engine) {
	}

	@Override
	public void houseRoll(DicePair dicePair, GameEngine engine) {
	}

	@Override
	public void houseRollOutcome(DicePair result, GameEngine engine) {
	}

	@Override
	public void playerRoll(Player player, DicePair dicePair, GameEngine engine) {
	}

	@Override
	public void playerRollOutcome(Player player, DicePair result, GameEngine engine) {
	}

}
