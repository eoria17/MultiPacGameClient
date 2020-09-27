package tests;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.Socket;

import org.junit.Test;

import clientConnection.Client;
import clientConnection.Settings;

public class ConnectionTest {

	@Test
	public void test() {
		Client client = new Client(Settings.host, Settings.port);
		
		Socket cSocket = client.getSocket();
		assertEquals(null, cSocket);
		
		client.connect();
		
		cSocket = client.getSocket();
		assertTrue(cSocket.isConnected());
		
		client.close();
	}

}
