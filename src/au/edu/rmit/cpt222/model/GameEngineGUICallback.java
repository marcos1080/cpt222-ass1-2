package au.edu.rmit.cpt222.model;

import au.edu.rmit.cpt222.controller.MainController;
import au.edu.rmit.cpt222.model.interfaces.DicePair;
import au.edu.rmit.cpt222.model.interfaces.GameEngine;
import au.edu.rmit.cpt222.model.interfaces.GameEngine.GameStatus;
import au.edu.rmit.cpt222.model.interfaces.GameEngineCallback;
import au.edu.rmit.cpt222.model.interfaces.Player;

public class GameEngineGUICallback extends GameEngineCallbackImpl implements GameEngineCallback {
	
	private MainController mainController;
	
	public GameEngineGUICallback(MainController mainController) {
		this.mainController = mainController;
	}

	@Override
	public void gameResult(Player player, GameStatus result, GameEngine engine) {
		mainController.displayResult(player, result);
		
		// Display the roll button again once the round ends.
		mainController.enableRollButton();
	}

	@Override
	public void houseRoll(DicePair dicePair, GameEngine engine) {
		// Once round starts disable the roll button so it can't be pressed again.
		mainController.disableRollButton();
		
		mainController.updateHouseDice(dicePair);
	}

	@Override
	public void houseRollOutcome(DicePair result, GameEngine engine) {
		mainController.updateHouseDice(result);
	}

	@Override
	public void playerRoll(Player player, DicePair dicePair, GameEngine engine) {
		mainController.getPlayerView(player.getPlayerId()).updateDice(dicePair);
	}

	@Override
	public void playerRollOutcome(Player player, DicePair result, GameEngine engine) {
		mainController.getPlayerView(player.getPlayerId()).updateDice(result);
	}
}
