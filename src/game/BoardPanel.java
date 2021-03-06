package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JPanel;

import clientConnection.ConnectionHandler;

/* This panel represents the game board (grid) 
 * It also responds to game related events
 * The overridden paintcomponent() is called whenever the board
 * or the pieces needs to be updated 
 */
public class BoardPanel extends JPanel implements KeyEventHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Player player;

	private HashMap<Integer, Player> players = new HashMap<Integer, Player>();
	private HashMap<Integer, Position> foods;

	private Monster[] monsters;
	private Grid grid;
	private Graphics gr;
	private final int CELLWIDTH = 40;
	private final int CELLHEIGHT = 40;
	private final int LMARGIN = 100;
	private final int TMARGIN = 100;

	public BoardPanel(Grid g, HashMap<Integer, Player> players, Monster[] m) {
		this.players = players;
		player = players.get(ConnectionHandler.id);
		grid = g;
		monsters = m;
		gr = this.getGraphics();

		this.foods = ConnectionHandler.allFoodPosition;
	}

	/* responds to various button clicked messages */
	public void actionPerformed(ActionEvent e) {
		if (((JButton) e.getSource()).getText().compareTo("start") == 0)
			player.setReady(true);
	}

	/* returns the x coordinate based on left margin and cell width */
	private int xCor(int col) {
		return LMARGIN + col * CELLWIDTH;
	}

	/* returns the y coordinate based on top margin and cell height */
	private int yCor(int row) {
		return TMARGIN + row * CELLHEIGHT;
	}

	/*
	 * Redraws the board and the pieces Called initially and in response to
	 * repaint()
	 */
	protected void paintComponent(Graphics gr) {
		super.paintComponent(gr);
		Position cells[] = grid.getAllCells();

		Position cell;
		for (int i = 0; i < cells.length; i++) {
			cell = cells[i];
			if (cell.col % 5 == 0 && cell.row % 5 == 0)
				gr.setColor(Color.cyan);
			else
				gr.setColor(Color.white);
			gr.fillRect(xCor(cell.col), yCor(cell.row), CELLWIDTH, CELLHEIGHT);
			gr.setColor(Color.black);
			gr.drawRect(xCor(cell.col), yCor(cell.row), CELLWIDTH, CELLHEIGHT);
		}

		for (int p : players.keySet()) {
			// the player who was eaten by the monster should not be updated
			if (ConnectionHandler.isPlayerDead(p)) {
				continue;
			}

			cell = players.get(p).getCell();
			gr.setColor(Color.red);
			gr.fillOval(xCor(cell.col) + CELLWIDTH / 8, yCor(cell.row) + CELLWIDTH / 8, CELLWIDTH * 3 / 4,
					CELLHEIGHT * 3 / 4);
			gr.setColor(Color.white);
			gr.drawString(((p + 1) + ""), xCor(cell.col) + CELLWIDTH / 3, yCor(cell.row) + 2 * CELLWIDTH / 3);
		}

		for (Position p : ConnectionHandler.allFoodPosition.values()) {
			cell = p;
			gr.setColor(Color.orange);
			gr.fillOval(xCor(cell.col) + CELLWIDTH / 4, yCor(cell.row) + CELLWIDTH / 4, CELLWIDTH * 2 / 4,
					CELLHEIGHT * 2 / 4);
			gr.setColor(Color.white);
		}

		for (Monster monster : monsters) {
			if (monster.viewable()) {
				cell = monster.getCell();
				gr.setColor(Color.black);
				gr.fillRect(xCor(cell.col), yCor(cell.row), CELLWIDTH, CELLHEIGHT);
				gr.setColor(Color.white);
				gr.drawString("M", xCor(cell.col) + CELLWIDTH / 3, yCor(cell.row) + 2 * CELLWIDTH / 3);
			}
		}
	}

//	public void updatePlayers() {
//		for (int p : players.keySet()) {
//			Position newPos = ConnectionHandler.allPlayersPosition.get(p);
//			players.get(p).setCell(newPos);
//		}
//	}

	@Override
	public void handleKeyEvent(String keyCode) {
		// if the current player is dead then do nothing
		if (ConnectionHandler.isPlayerDead(ConnectionHandler.id)) {
			return;
		}

		// one player win the game
		if (ConnectionHandler.allPlayersReadyStatus.size() > 1 &&
				(ConnectionHandler.allPlayersReadyStatus.size() - ConnectionHandler.deadPlayers.size() == 1)) {
			return;
		}

		if (keyCode.compareTo("up") == 0)
			player.setDirection('U');
		else if (keyCode.compareTo("down") == 0)
			player.setDirection('D');
		else if (keyCode.compareTo("left") == 0)
			player.setDirection('L');
		else if (keyCode.compareTo("right") == 0)
			player.setDirection('R');
		else if (keyCode.compareTo("start") == 0)
			player.setReady(true);
		else if (keyCode.compareTo("drop") == 0) { //set to key A
			if(player.hasFood()) {
				player.dropFood();
				foods.put(ConnectionHandler.id, player.getFoodPosition());
			}
		}
			

	}
}