package clientConnection;

import packets.*;
import java.util.HashMap;

//(Theo) used to check what kind of packet are being received
public class EventListener {

	private boolean gameRunningStatus = false;
	private GameThread newGame;

	public synchronized  void received(Object p, Client c) throws PlayerLimitException {

		// (Theo) if a new client connected to the server, the server will send the
		// necessary clients data here.
		// The data received will be registered in the connection handler.
		if (p instanceof AddConnectionPacket) {
			AddConnectionPacket packet = (AddConnectionPacket) p;
			ConnectionHandler.connections.put(packet.id, new Connection(packet.id));
			ConnectionHandler.allPlayersReadyStatus.put(packet.id, false);
			System.out.println("Player " + (packet.id + 1) + " has connected");

		} else if (p instanceof RemoveConnectionPacket) {
			RemoveConnectionPacket packet = (RemoveConnectionPacket) p;
			System.out.println("Connection: " + packet.id + " has disconnected");
			ConnectionHandler.connections.remove(packet.id);

		} else if (p instanceof RejectedPacket) {
			
				RejectedPacket packet = (RejectedPacket) p;
				throw new PlayerLimitException(packet.message);
//				RemoveConnectionPacket rp = new RemoveConnectionPacket();
//				rp.id = ConnectionHandler.id;
//				c.sendObject(rp);
				
			
		} else if (p instanceof ClientSettingPacket) {
			ClientSettingPacket packet = (ClientSettingPacket) p;
			ConnectionHandler.id = packet.id;

			ConnectionHandler.allPlayersReadyStatus.put(packet.id, false);
			// used for initialize positions when restart the game
			ConnectionHandler.allPlayersReadyPosition.put(packet.id,
					ConnectionHandler.allPlayersPosition.get(packet.id));

			System.out.println("You are player " + (ConnectionHandler.id + 1));

		} else if (p instanceof PlayersUpdatePacket) {
			PlayersUpdatePacket packet = (PlayersUpdatePacket) p;

			ConnectionHandler.allPlayersReadyStatus = packet.readyStatus;
			ConnectionHandler.allPlayersPosition = packet.clientsPosition;
			ConnectionHandler.allFoodPosition = packet.foodPositions;

			if (packet.deathStatus != null) {
				ConnectionHandler.deadPlayers = packet.deathStatus;
			}
		} else if (p instanceof EmptyPacket) {
			

		} else if (p instanceof StartGamePacket) {
			StartGamePacket packet = (StartGamePacket) p;

			// run the gamr for the first time
			if (newGame == null) {
				newGame = new GameThread(c);
				newGame.start(packet);
			} else { // restart the game
				ConnectionHandler.rematchPlayers = new HashMap<>();
				ConnectionHandler.monsterPosition = null;
				ConnectionHandler.deadPlayers = new HashMap<>();
				for (int i : ConnectionHandler.allPlayersReadyPosition.keySet()) {
					ConnectionHandler.allPlayersPosition.put(i,
							ConnectionHandler.allPlayersReadyPosition.get(i));
				}

				newGame.stop();
				newGame = new GameThread(c);
				newGame.start(packet);
			}

		} else if (p instanceof MonsterPositionPacket) {
			MonsterPositionPacket packet = (MonsterPositionPacket) p;
			ConnectionHandler.monsterPosition = packet.position;
		
		}else if(p instanceof FoodEatenPacket) {
			FoodEatenPacket packet = (FoodEatenPacket) p;
			
			ConnectionHandler.allFoodPosition.remove(packet.id);
		}else if(p instanceof RematchPlayersPacket) {
			RematchPlayersPacket packet = (RematchPlayersPacket) p;
			ConnectionHandler.rematchPlayers = packet.rematchPlayers;
		}

	}

}
