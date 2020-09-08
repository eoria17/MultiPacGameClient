package clientConnection;

import javax.swing.JFrame;

import game.Game;
import packets.AddConnectionPacket;
import packets.ClientSettingPacket;
import packets.EmptyPacket;
import packets.PlayersUpdatePacket;
import packets.RejectedPacket;
import packets.RemoveConnectionPacket;
import packets.StartGamePacket;

//(Theo) used to check what kind of packet are being received
public class EventListener {
	
	private Game game;
	
	public void received(Object p, Client c) {

		//(Theo) if a new client connected to the server, the server will send the necessary clients data here.
		//The data received will be registered in the connection handler.
		if(p instanceof AddConnectionPacket) {
			AddConnectionPacket packet = (AddConnectionPacket)p;
			ConnectionHandler.connections.put(packet.id,new Connection(packet.id));
			ConnectionHandler.allPlayersReadyStatus.put(packet.id, false);
			System.out.println("Player " + (packet.id + 1) + " has connected");
		
		}else if(p instanceof RemoveConnectionPacket) {
			RemoveConnectionPacket packet = (RemoveConnectionPacket)p;
			System.out.println("Connection: " + packet.id + " has disconnected");
			ConnectionHandler.connections.remove(packet.id);
		
		}else if(p instanceof RejectedPacket) {
			RejectedPacket packet = (RejectedPacket) p;
			System.out.println(packet.message);
			
			RemoveConnectionPacket rp = new RemoveConnectionPacket();
			rp.id = ConnectionHandler.id;
			c.sendObject(rp);
			
			System.out.println("Game is closed.");
			c.close();
			
		}else if(p instanceof ClientSettingPacket) {
			ClientSettingPacket packet = (ClientSettingPacket) p;
			ConnectionHandler.id = packet.id;
			
			ConnectionHandler.allPlayersReadyStatus.put(packet.id, false);
			
			System.out.println("You are player " + (ConnectionHandler.id + 1));
		
		}else if(p instanceof PlayersUpdatePacket) {
			PlayersUpdatePacket packet = (PlayersUpdatePacket) p;
			
			System.out.println("From the server: " + packet.readyStatus);
			
			ConnectionHandler.allPlayersReadyStatus = packet.readyStatus;
			ConnectionHandler.allPlayersStartingPosition = packet.clientsPosition;
		
		}else if(p instanceof EmptyPacket) {
			
		
		}else if(p instanceof StartGamePacket) {
			StartGamePacket packet = (StartGamePacket) p;
			
			try {
				game = new Game(packet.clientsPosition);
		        game.setTitle("Monster Game");
		        game.setSize(700,700);
		        game.setLocationRelativeTo(null);  // center the frame
		        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		        game.setVisible(true);
		        
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}
	
}
