package clientConnection;

import javax.swing.JFrame;

import game.Game;
import packets.StartGamePacket;

import static java.lang.Thread.sleep;

public class GameThread implements Runnable {
	
	Game game;
	Client c;
	boolean running;
	
	
	public GameThread(Client c) {
		this.c = c;
		running = true;
	}

	public void start(StartGamePacket packet) {
		
		try {
			ConnectionHandler.gridObstacles = packet.gridObstacles;

			game = new Game(packet.clientsPosition, c);
			game.setTitle("Monster Game");
			game.setSize(700, 700);
			game.setLocationRelativeTo(null); // center the frame
			game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			game.setVisible(true);

		} catch (Exception e) {
			e.printStackTrace();
		}

		new Thread(this).start();
	}

	public void stop() {
		running = false;
		game.dispose();
	}
	
	@Override
	public void run() {
		
		while(running) {
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			game.updatePlayers();
			game.updateMonster();
			game.updateFoodPosition();

			String message = game.play(c);

			if (message.equalsIgnoreCase("")) {
				// update the text of the timer
				game.updateTextTime();
			} else {
				if (ConnectionHandler.rematchPlayers != null &&
						ConnectionHandler.rematchPlayers.size() > 0) {
					game.updateText(ConnectionHandler.rematchPlayers.size() + " players wish to play again.");
				}
			}
		}
		
		
	}

}
