package au.edu.rmit.cpt222.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;

import au.edu.rmit.cpt222.model.interfaces.GameEngine.GameStatus;
import au.edu.rmit.cpt222.model.interfaces.Player;

/**
 * Display a history of the previous games.
 * 
 * @author Mark Stuart
 */
public class HistoryBox extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static int GAME_NUMBER = 1;
	private static int MAX_GAMES_DISPLAYED = 5;
	private static int START_LINE = 15;
	private static int END_LINE = 17;
	private static final String TITLE = "History";
	private static final int WIDTH = 300;
	
	private JTextArea history;
	
	public HistoryBox() {
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(WIDTH, getSize().height));
		
		// Title
		JLabel title = new JLabel(TITLE);
		int borderWidth = MainView.DEF_BORDER;
		title.setBorder(BorderFactory.createEmptyBorder(
				borderWidth, borderWidth, borderWidth, borderWidth));
		add(title, BorderLayout.NORTH);
		
		// History area
		history = new JTextArea();
		history.setBorder(BorderFactory.createEmptyBorder(
				borderWidth, borderWidth, 0, borderWidth));
		history.setEnabled(false);
		history.setForeground(Color.BLACK);
		add(history, BorderLayout.CENTER);
	}
	
	/**
	 * Display the results of the last game.
	 * 
	 * @param player
	 * @param result
	 */
	public void addGameResult(Player player, GameStatus result) {
		StringBuffer resultText = new StringBuffer();
		resultText.append("Game #").append(GAME_NUMBER);
		resultText.append(": ").append(player.getPlayerName()).append(" ");
		
		switch(result) {
			case WON:
				resultText.append("Won");
				break;
			case LOST:
				resultText.append("Lost");
				break;
			default:
				resultText.append("Drew");
		}
		
		resultText.append("\n\tBet: ").append(player.getBet());
		resultText.append(", Credit: ").append(player.getPoints()).append("\n\n");
		
		history.insert(resultText.toString(), 0);
		
		// If game number > 5 then delete oldest entry.
		if (GAME_NUMBER > MAX_GAMES_DISPLAYED) {
			try {
				int start = history.getLineStartOffset(START_LINE);
				int end = history.getLineEndOffset(END_LINE);
				
				history.replaceRange("", start, end);
			} catch (BadLocationException e) {
				System.out.println("Bad range values");
			}
		}
		
		GAME_NUMBER ++;
	}
}
