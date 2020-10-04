package game;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.*;

import clientConnection.Client;
import clientConnection.ConnectionHandler;
import packets.FoodPositionPacket;
import packets.PlayerPositionPacket;
import packets.RematchPacket;

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

	private JLabel mLabel = new JLabel("Time Remaining : " + TIMEALLOWED);
	private JButton restart = new JButton("play again");

	private Grid grid;
	private Player player;
	private Monster[] monsters;
	private BoardPanel bp;
	private KeyBoard keyBoard;

	private HashMap<Integer, Player> players = new HashMap<Integer, Player>();
	private HashMap<Integer, Position> foods;

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
		
		foods = ConnectionHandler.allFoodPosition;

		monsters = new Monster[]{new Monster(grid, player, 5, 5),
				new Monster(grid, player, 5, 5)};
		bp = new BoardPanel(grid, players, monsters);
		keyBoard = new KeyBoard(bp);

		// Create a separate panel and add all the buttons
		JPanel panel = new JPanel();
		panel.add(mLabel);
		panel.add(restart);

		restart.setVisible(false);
		restart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				RematchPacket packet = new RematchPacket(ConnectionHandler.id);
				c.sendObject(packet);
				restart.setEnabled(false);
			}
		});

		// add panels to frame
		add(bp, BorderLayout.CENTER);
		add(panel, BorderLayout.SOUTH);

		keyBoard.keyEventRegister(bp);
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

	public synchronized void updatePlayers() {
		
		for(int i : players.keySet()) {
			try {
				Player p = new Player(grid, ConnectionHandler.allPlayersPosition.get(i).getRow(), ConnectionHandler.allPlayersPosition.get(i).getCol());
				
				players.put(i, p);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public synchronized void updateFoodPosition() {
		 foods = ConnectionHandler.allFoodPosition;
	}

	public synchronized void updateMonster() {
		if (ConnectionHandler.monsterPosition != null &&
				ConnectionHandler.monsterPosition.length == monsters.length) {
			for (int i = 0;i < monsters.length;i ++) {
				Monster monster = monsters[i];
				monster.setCell(ConnectionHandler.monsterPosition[i]);
			}
		}
	}

	public synchronized void updateTextTime() {
		int timeLeft = TIMEALLOWED - time;
		mLabel.setText("Time Remaining : " + timeLeft);
	}

	public synchronized void updateText(String text) {
		mLabel.setText(text);
	}

	/*
	 * This method waits until play is ready (until start button is pressed) after
	 * which it updates the moves in turn until time runs out (player won) or player
	 * is eaten up (player lost).
	 */
	public synchronized String play(Client c) {

		String message = "";

		bp.requestFocusInWindow();

		// if the new position is the same as the old position then no need to send position package
		Position oldPosition = player.getCell();
		Position newPlayerCell = player.move();

		// if the new position is the same as the old position then no need to send position package
		// except for the first time
		if (time == 0 || (oldPosition.getCol() != newPlayerCell.getCol() || oldPosition.getRow() != newPlayerCell.getRow())) {
			PlayerPositionPacket packet = new PlayerPositionPacket(ConnectionHandler.id, newPlayerCell);
			c.sendObject(packet);
		}

		player.setDirection(' ');
		
		//(Theo) food function
		if(!player.hasFood() && player.dropableFood()) {
			foods.put(ConnectionHandler.id, player.getFoodPosition());
			
			player.setNoMoreFood();
			
			FoodPositionPacket fPacket = new FoodPositionPacket(ConnectionHandler.id, player.getFoodPosition());
			c.sendObject(fPacket);
		}

		int timeLeft = TIMEALLOWED - time;
		if (timeLeft < 0) {
			timeLeft = 0;
		}
		time++;
		mLabel.setText("Time Remaining : " + timeLeft);
		bp.repaint();

		if (timeLeft == 0) {
			message = "Player Won";
			restart.setVisible(true);
		}

		if (ConnectionHandler.deadPlayers.size() == ConnectionHandler.allPlayersReadyStatus.size()) {
			message = "Player Lose";
			restart.setVisible(true);
		} else if (ConnectionHandler.allPlayersReadyStatus.size() > 1 &&
				(ConnectionHandler.allPlayersReadyStatus.size() - ConnectionHandler.deadPlayers.size() == 1)) {
			// only one player left
			message = "Player Won";
			restart.setVisible(true);
		}

		mLabel.setText(message);
		return message;
	}
}
