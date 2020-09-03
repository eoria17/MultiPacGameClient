package cilentConnection;

import game.Grid;
import game.Player;
import game.Position;
import game.Grid;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class getPosition {
    private List<Player> players = new ArrayList<>();//Store player
    private Player player1;
    private Player player2;
    private Player player3;
    private Player player4;

    public void player() {
        players.add(player1);
        players.add(player2);
        players.add(player3);
        players.add(player4);
    }

    public void getPosition(){
        Position player1Cell = player1.move();
        Position player2Cell = player2.move();
        Position player3Cell = player3.move();
        Position player4Cell = player4.move();
        Client client = new Client("localhost", 8080);
        client.connect();
        client.sendObject(player1Cell);
        client.sendObject(player2Cell);
        client.sendObject(player3Cell);
        client.sendObject(player4Cell);
    }
}
