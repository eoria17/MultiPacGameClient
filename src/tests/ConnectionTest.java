package tests;

import static org.junit.Assert.assertTrue;

import java.net.Socket;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import clientConnection.Client;
import clientConnection.Settings;

public class ConnectionTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		Client client = new Client(Settings.host, Settings.port);
		client.connect();
		
		Socket socket = client.getSocket();
		
		assertTrue(socket.isConnected());
	}

}
