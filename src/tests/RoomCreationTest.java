package tests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import clientConnection.Client;
import clientConnection.Settings;
import packets.AddConnectionPacket;
import packets.SettingPacket;

public class RoomCreationTest {

	@Test
	public void test() {
		// create a client
		Client player1 = new Client(Settings.host, Settings.port);
		player1.connect();

		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
		}

		assertTrue(player1.getSocket().isConnected());

		// player 1 created a game room and set the player limit to 2
		SettingPacket settingPacket = new SettingPacket(2);
		player1.sendObject(settingPacket);

		// register connection packet to server
		AddConnectionPacket packet1 = new AddConnectionPacket();
		player1.sendObject(packet1);

		// create a client
		Client player2 = new Client(Settings.host, Settings.port);
		player2.connect();

		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
		}

		assertTrue(player2.getSocket().isConnected());

		// player 2 tried to creat a game room and set the player limit to 3
		SettingPacket settingPacket2 = new SettingPacket(3);
		player2.sendObject(settingPacket2);
		
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		assertTrue(player2.getSocket().isClosed());

	}

}
