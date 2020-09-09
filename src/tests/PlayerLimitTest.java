package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import clientConnection.Client;
import clientConnection.Settings;
import packets.AddConnectionPacket;
import packets.SettingPacket;

public class PlayerLimitTest {
	
	@Test
	public void test() {
	    
		// create a client
		Client player1 = new Client(Settings.host, Settings.port);
		player1.connect();

		assertTrue(player1.getSocket().isConnected());
		assertEquals("", player1.getErrorMessage());

		// register connection packet to server
		AddConnectionPacket packet1 = new AddConnectionPacket();
		player1.sendObject(packet1);

		// player 1 created a game room and set the player limit to 2
		SettingPacket settingPacket = new SettingPacket(2);
		player1.sendObject(settingPacket);

		try {
			Thread.sleep(2000);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Player 2 connect
		Client player2 = new Client(Settings.host, Settings.port);
		player2.connect();

		assertTrue(player2.getSocket().isConnected());
		assertEquals("", player2.getErrorMessage());

		// register connection packet to server
		AddConnectionPacket packet2 = new AddConnectionPacket();
		player2.sendObject(packet2);
		
		try {
			Thread.sleep(2000);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Player 3 tried to connect and get an error
		Client player3 = new Client(Settings.host, Settings.port);
		player3.connect();
		
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		assertTrue(player3.getSocket().isClosed());
		
	}

}
