package clientConnection;

import javax.swing.JFrame;

import game.Game;
import packets.StartGamePacket;

import static java.lang.Thread.sleep;

public class GameThread implements Runnable {
	
	Game game;
	Client c;
	
	
	
	public GameThread(Client c) {
		this.c = c;
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
	
	@Override
	public void run() {
		
		while(true) {
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			game.updatePlayers();
			game.updateMonster();
			String message = game.play(c);
			
			if (!message.equalsIgnoreCase("")) {
				break;
			}
		}
		
		
	}

}
