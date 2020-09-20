package clientConnection;

import packets.*;

//(Theo) used to check what kind of packet are being received
public class EventListener {

	private boolean gameRunningStatus = false;

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

			System.out.println("You are player " + (ConnectionHandler.id + 1));

		} else if (p instanceof PlayersUpdatePacket) {
			PlayersUpdatePacket packet = (PlayersUpdatePacket) p;

			ConnectionHandler.allPlayersReadyStatus = packet.readyStatus;
			ConnectionHandler.allPlayersPosition = packet.clientsPosition;

			if (packet.deathStatus != null) {
				ConnectionHandler.deadPlayers = packet.deathStatus;
			}
		} else if (p instanceof EmptyPacket) {
			

		} else if (p instanceof StartGamePacket) {
			StartGamePacket packet = (StartGamePacket) p;
			
			GameThread newGame = new GameThread(c);
			newGame.start(packet);

		} else if (p instanceof MonsterPositionPacket) {
			MonsterPositionPacket packet = (MonsterPositionPacket) p;
			ConnectionHandler.monsterPosition = packet.position;
		}

	}

}
