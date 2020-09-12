package tests;

import clientConnection.Client;
import clientConnection.ConnectionHandler;
import game.Player;
import game.Position;
import org.junit.*;
import packets.ReadyPacket;
import clientConnection.ConnectionHandler;
import clientConnection.Client;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class TestReady {
    private Client client;
    @Test
    public void TestReady() throws Exception{
        HashMap<Integer, Boolean> allPlayersReadyStatus = new HashMap<Integer, Boolean>();
        String ready = "y";
        if(ready.equals("y")){
            ConnectionHandler.allPlayersReadyStatus.put(ConnectionHandler.id,true);
            ReadyPacket readyPacket = new ReadyPacket(ConnectionHandler.id, true);
        }
        for(Map.Entry<Integer,Boolean> entry : allPlayersReadyStatus.entrySet()){
            System.out.println(entry.getKey()+" "+entry.getValue());
        }
    }



}
