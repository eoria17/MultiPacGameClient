package tests;

import clientConnection.ConnectionHandler;
import game.Grid;
import game.Player;
import game.Position;
import org.junit.*;
import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;


public class TestOtherPosition {
    Player player;
    Position c;
    Grid grid;

    @Test
    public void TestOtehrPosition() {
        HashMap<Integer, Position> clientsPosition = new HashMap<>();
        HashMap<Integer, Position> allPlayersPosition = new HashMap<>();
        HashMap<Integer, Player> players = new HashMap<Integer, Player>();
        for (int i : players.keySet()) {
            try {
                Player p = new Player(grid, ConnectionHandler.allPlayersPosition.get(i).getRow(), ConnectionHandler.allPlayersPosition.get(i).getCol());

                players.put(i, p);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        clientsPosition = allPlayersPosition;
        for (Map.Entry<Integer, Position> entry : allPlayersPosition.entrySet()) {
            assertEquals(allPlayersPosition, clientsPosition);
        }

    }
}
