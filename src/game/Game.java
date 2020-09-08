package game;

import java.awt.BorderLayout;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import clientConnection.Client;
import clientConnection.ConnectionHandler;
import packets.PlayerPositionPacket;

/* This class is the main System level class which creates all the objects
 * representing the game logic (model) and the panel for user interaction.
 * It also implements the main game loop
 */

public class Game extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final int TIMEALLOWED = 100;
	private int time = 0;

	private JButton start = new JButton("start");
	private JLabel mLabel = new JLabel("Time Remaining : " + TIMEALLOWED);

	private Grid grid;
	private Player player;
	private Monster monster;
	private BoardPanel bp;

	private HashMap<Integer, Player> players = new HashMap<Integer, Player>();

	/*
	 * This constructor creates the main model objects and the panel used for UI. It
	 * throws an exception if an attempt is made to place the player or the monster
	 * in an invalid location.
	 */
	public Game(HashMap<Integer, Position> startingPosition, Client c) throws Exception {
		grid = new Grid();

		for (Integer playerNumber : startingPosition.keySet()) {
			players.put(playerNumber, new Player(grid, startingPosition.get(playerNumber).getRow(),
					startingPosition.get(playerNumber).getCol()));
		}
		player = players.get(ConnectionHandler.id);

		monster = new Monster(grid, player, 5, 5);
		bp = new BoardPanel(grid, players, monster);

		// Create a separate panel and add all the buttons
		JPanel panel = new JPanel();
		panel.add(start);
		panel.add(mLabel);

		// add panels to frame
		add(bp, BorderLayout.CENTER);
		add(panel, BorderLayout.SOUTH);
		
		bp.addKeyListener(bp);
		bp.setFocusable(true);
		bp.setFocusTraversalKeysEnabled(false);

	}

	// method to delay by specified time in ms
	public void delay(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/*
	 * This method waits until play is ready (until start button is pressed) after
	 * which it updates the moves in turn until time runs out (player won) or player
	 * is eaten up (player lost).
	 */
	public String play(Client c) {
		
		String message = "";

		bp.requestFocusInWindow();
		Position newPlayerCell = player.move();
		
		PlayerPositionPacket packet = new PlayerPositionPacket(ConnectionHandler.id, newPlayerCell);
		c.sendObject(packet);
		
		for(int p : players.keySet()) {
			players.get(p).setCell(ConnectionHandler.allPlayersStartingPosition.get(p));
		}
		
		bp.repaint();

		player.setDirection(' ');

		int timeLeft = TIMEALLOWED - time;
		System.out.println(timeLeft);
		time++;
		mLabel.setText("Time Remaining : " + timeLeft);
		delay(1000);
		bp.repaint();

		if (timeLeft == 0) {
			message = "Player Won";
		}

		mLabel.setText(message);
		return message;
	}

	public String oldPlay() {
		int time = 0;
		String message;
		player.setDirection(' '); // set to no direction
		while (!player.isReady())
			delay(100);
		do {
			bp.requestFocusInWindow();
			Position newPlayerCell = player.move();
			// send object to server

			if (newPlayerCell == monster.getCell())
				break;
			player.setDirection(' '); // reset to no direction

			Position newMonsterCell = monster.move();
			if (newMonsterCell == player.getCell())
				break;

			// update time and repaint
			time++;
			mLabel.setText("Time Remaining : " + (TIMEALLOWED - time));
			delay(1000);
			bp.repaint();

		} while (time < TIMEALLOWED);

		if (time < TIMEALLOWED) // players has been eaten up
			message = "Player Lost";
		else
			message = "Player Won";

		mLabel.setText(message);
		return message;
	}

//    public static void main(String args[]) throws Exception
//    {
//        Game game = new Game();
//        game.setTitle("Monster Game");
//        game.setSize(700,700);
//        game.setLocationRelativeTo(null);  // center the frame
//        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        game.setVisible(true);
//        game.play();
//    }
}
