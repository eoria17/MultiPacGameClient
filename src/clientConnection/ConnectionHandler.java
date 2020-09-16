package clientConnection;

import java.util.HashMap;

import game.Player;
import game.Position;

public class ConnectionHandler {
	
	public static int id;
	public static HashMap<Integer,Connection> connections = new HashMap<Integer,Connection>();
	public static HashMap<Integer, Boolean> allPlayersReadyStatus = new HashMap<Integer, Boolean>();
	public static HashMap<Integer, Position> allPlayersPosition = new HashMap<Integer, Position>();
	public static Position[] gridObstacles;
	public static Position monsterPosition;
}
