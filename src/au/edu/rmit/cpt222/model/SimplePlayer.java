package au.edu.rmit.cpt222.model;

import java.io.Serializable;
import java.time.Instant;

import au.edu.rmit.cpt222.model.exceptions.InsufficientFundsException;
import au.edu.rmit.cpt222.model.interfaces.DicePair;
import au.edu.rmit.cpt222.model.interfaces.GameEngine.GameStatus;
import au.edu.rmit.cpt222.model.interfaces.Player;

public class SimplePlayer implements Player, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private int points; 
	private int bet;
	private GameStatus gameResult;
	private DicePair rollResult;
	
	public SimplePlayer(String name) {
		this(Instant.now().toString(), name);
	}
	
	public SimplePlayer(String id, String name) {
		this(id, name, Player.DEFAULT_NUM_POINTS);
	}
	
	public SimplePlayer(String name, int points) {
		this(Instant.now().toString(), name, points);
	}
	
	public SimplePlayer(String id, String name, int points) {
		this.id = id;
		this.name = name;
		this.points = points;
	}

	@Override
	public int getBet() {
		return this.bet;
	}

	@Override
	public GameStatus getGameResult() {
		return gameResult;
	}

	@Override
	public String getPlayerId() {
		return this.id;
	}

	@Override
	public String getPlayerName() {
		return this.name;
	}

	@Override
	public int getPoints() {
		return this.points;
	}

	@Override
	public DicePair getRollResult() {
		return this.rollResult;
	}

	@Override
	public void placeBet(int bet) throws InsufficientFundsException {
		assert bet >= 0 : "Invalid bet value, must not be negative";
		if(bet > this.points)
			throw new InsufficientFundsException("Player only has $" + this.points);
		
		this.bet = bet;
	}

	@Override
	public void resetBet() throws InsufficientFundsException {
		if(Player.DEFAULT_BET > this.points)
			throw new InsufficientFundsException("Player only has $" + this.points);
		
		this.bet = Player.DEFAULT_BET;
	}

	@Override
	public void setGameResult(GameStatus status) {
		this.gameResult = status;
	}

	@Override
	public void setPlayerName(String playerName) {
		assert ! playerName.isEmpty() : "Player name cannot be empty";
			this.name = playerName;
	}

	@Override
	public void setPoints(int points) {
		assert points >= 0 : "Invalid points value";
		this.points = points;
	}

	@Override
	public void setRollResult(DicePair rollResult) {
		this.rollResult = rollResult;
	}

	@Override
	public String toString() {
		StringBuilder message = new StringBuilder();
		message.append("\t\tid: " + id + "\n");
		message.append("\t\tname: " + name + "\n");
		message.append("\t\tpoints: " + points + "\n");
		message.append("\t\trollResult: " + rollResult + "\n");
		message.append("\t\tbet: " + bet);
		
		return message.toString();
	}
}
