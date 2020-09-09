package packets;

import java.io.Serializable;
import java.util.HashMap;

import game.Grid;
import game.Player;
import game.Position;

public class PlayersUpdatePacket implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public HashMap<Integer,Boolean> readyStatus;
	public HashMap<Integer,Position> clientsPosition;
	
	public HashMap<Integer, Player> clientsPlayerInfo = new HashMap<Integer, Player>();

	public PlayersUpdatePacket(HashMap<Integer, Boolean> readyStatus, HashMap<Integer, Position> clientsPosition) {
		this.readyStatus = readyStatus;
		this.clientsPosition = clientsPosition;
		
	}
	
	public PlayersUpdatePacket(HashMap<Integer, Boolean> readyStatus, HashMap<Integer, Position> clientsPosition, Grid grid) {
		this.readyStatus = readyStatus;
		this.clientsPosition = clientsPosition;
		
		for(int i : this.clientsPosition.keySet()) {
			try {
				Player p = new Player(grid, this.clientsPosition.get(i).getRow(), this.clientsPosition.get(i).getCol());
				
				clientsPlayerInfo.put(i, p);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
	}
}
