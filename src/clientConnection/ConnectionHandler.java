package clientConnection;

import java.util.HashMap;

public class ConnectionHandler {
	
	public static int id;
	public static HashMap<Integer,Connection> connections = new HashMap<Integer,Connection>();
	public static HashMap<Integer, Boolean> playersReady = new HashMap<Integer, Boolean>();

}
