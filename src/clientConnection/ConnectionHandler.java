package clientConnection;

import java.util.HashMap;

import game.Position;

public class ConnectionHandler {
	
	public static int id;
	public static HashMap<Integer,Connection> connections = new HashMap<Integer,Connection>();
	public static HashMap<Integer, Boolean> allPlayersReadyStatus = new HashMap<Integer, Boolean>();
	public static HashMap<Integer, Position> allPlayersStartingPosition = new HashMap<Integer, Position>();
}
