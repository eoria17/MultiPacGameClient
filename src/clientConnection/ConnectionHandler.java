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
	public static HashMap<Integer, Boolean> deadPlayers = new HashMap<Integer, Boolean>();

	public static boolean isPlayerDead(int playerId) {
		if (deadPlayers.get(playerId) != null) {
			return true;
		} else {
			return false;
		}
	}
}
