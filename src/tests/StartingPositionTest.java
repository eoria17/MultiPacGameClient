package tests;

import clientConnection.Client;
import clientConnection.ConnectionHandler;
import clientConnection.Settings;
import game.Position;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import packets.AddConnectionPacket;
import packets.EmptyPacket;
import packets.SettingPacket;
import packets.StartingPositionPacket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import static clientConnection.Main.setStartingPosition;
import static java.lang.Thread.sleep;

public class StartingPositionTest {
    String host = "localhost";
    int port = 2000;
    TestServer server;

    @Before
    public void setUp() throws IOException, InterruptedException {
//        server = new TestServer(port);
//        server.start();

    }

    @Test
    public void testSetStartingPosition() {
        Client c = new Client(host, port);
        c.connect();
        Assert.assertEquals(0, ConnectionHandler.allPlayersPosition.size());

        Position topLeft = new Position(0,0);
        Position topRight = new Position(0,10);
        Position botLeft = new Position(10,0);
        Position botRight = new Position(10,10);
        HashMap<Integer, Position> startingPositions = new HashMap<Integer, Position>();
        startingPositions.put(1, topLeft);
        startingPositions.put(2, topRight);
        startingPositions.put(3, botLeft);
        startingPositions.put(4, botRight);

        Assert.assertEquals(0, ConnectionHandler.allPlayersPosition.size());
        setStartingPosition(c, 0, startingPositions);
        Assert.assertEquals(1, ConnectionHandler.allPlayersPosition.size());

        Assert.assertFalse(setStartingPosition(c, 0, startingPositions));
        Assert.assertTrue(setStartingPosition(c, 1, startingPositions));
        Assert.assertTrue(setStartingPosition(c, 2, startingPositions));
        Assert.assertTrue(setStartingPosition(c, 3, startingPositions));
    }

    @Test
    public void testSendPosition() throws InterruptedException {
        int playerLimit = 4;

        Client client = new Client(Settings.host, Settings.port);
        client.connect();

        AddConnectionPacket packet = new AddConnectionPacket();
        client.sendObject(packet);

        SettingPacket settingPacket = new SettingPacket(playerLimit);
        client.sendObject(settingPacket);

        Assert.assertTrue(client.getSocket().isConnected());

        int readySize = ConnectionHandler.allPlayersReadyStatus.size();
        int statusSize = ConnectionHandler.allPlayersReadyStatus.size();

        StartingPositionPacket sPacket = new StartingPositionPacket(new Position(0, 0));
        client.sendObject(sPacket);

        Assert.assertEquals(readySize, ConnectionHandler.allPlayersReadyStatus.size());
        Assert.assertEquals(statusSize, ConnectionHandler.allPlayersReadyStatus.size());

        // client 2
        Client client2 = new Client(Settings.host, Settings.port);
        client2.connect();

        packet = new AddConnectionPacket();
        client2.sendObject(packet);

        Assert.assertTrue(client2.getSocket().isConnected());

        readySize = ConnectionHandler.allPlayersReadyStatus.size();
        statusSize = ConnectionHandler.allPlayersReadyStatus.size();

        sPacket = new StartingPositionPacket(new Position(0, 1));
        client2.sendObject(sPacket);

        Assert.assertEquals(readySize, ConnectionHandler.allPlayersReadyStatus.size());
        Assert.assertEquals(statusSize, ConnectionHandler.allPlayersReadyStatus.size());

        // client 3
        Client client3 = new Client(Settings.host, Settings.port);
        client3.connect();

        packet = new AddConnectionPacket();
        client3.sendObject(packet);

        Assert.assertTrue(client3.getSocket().isConnected());

        readySize = ConnectionHandler.allPlayersReadyStatus.size();
        statusSize = ConnectionHandler.allPlayersReadyStatus.size();

        sPacket = new StartingPositionPacket(new Position(0, 1));
        client3.sendObject(sPacket);

        Assert.assertEquals(readySize, ConnectionHandler.allPlayersReadyStatus.size());
        Assert.assertEquals(statusSize, ConnectionHandler.allPlayersReadyStatus.size());

        // client 4
        Client client4 = new Client(Settings.host, Settings.port);
        client4.connect();

        packet = new AddConnectionPacket();
        client4.sendObject(packet);

        Assert.assertTrue(client4.getSocket().isConnected());

        readySize = ConnectionHandler.allPlayersReadyStatus.size();
        statusSize = ConnectionHandler.allPlayersReadyStatus.size();

        sPacket = new StartingPositionPacket(new Position(0, 1));
        client4.sendObject(sPacket);

        Assert.assertEquals(readySize, ConnectionHandler.allPlayersReadyStatus.size());
        Assert.assertEquals(statusSize, ConnectionHandler.allPlayersReadyStatus.size());
    }

    class TestServer implements Runnable {
        int port;
        ServerSocket serverSocket;

        public TestServer (int port) throws IOException {
            this.port = port;
            serverSocket = new ServerSocket(port);
        }

        public void start() {
            new Thread(this).start();
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                    ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                    Object data = in.readObject();

                    out.writeObject(new EmptyPacket());
                    out.flush();
                    out.reset();
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
