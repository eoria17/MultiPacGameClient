package tests;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import clientConnection.Client;
import clientConnection.ConnectionHandler;
import clientConnection.Settings;
import game.Position;
import packets.AddConnectionPacket;
import packets.ReadyPacket;
import packets.SettingPacket;
import packets.StartingPositionPacket;

class readyTest {

	@Test
	void test() {
		// create a client
		Client player1 = new Client(Settings.host, Settings.port);
		player1.connect();

		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
		}

		assertTrue(player1.getSocket().isConnected());

		// player 1 created a game room and set the player limit to 1
		SettingPacket settingPacket = new SettingPacket(1);
		player1.sendObject(settingPacket);

		// register connection packet to server
		AddConnectionPacket packet1 = new AddConnectionPacket();
		player1.sendObject(packet1);
		
		//set starting position
		Position topLeft = new Position(0,0);
		
		StartingPositionPacket sPacket = new StartingPositionPacket(topLeft);
		player1.sendObject(sPacket);
		
		//player ready
		ReadyPacket rpacket = new ReadyPacket(ConnectionHandler.id, true);
		player1.sendObject(rpacket);
		
		try {
			Thread.sleep(5000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		player1.close();
	}

}
