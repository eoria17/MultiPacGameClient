package tests;

import clientConnection.Client;
import clientConnection.ConnectionHandler;
import game.Position;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import packets.EmptyPacket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import static clientConnection.Main.setStartingPosition;

public class StartingPositionTest {
    Client c;
    String host = "localhost";
    int port = 8080;
    TestServer server;

    @Before
    public void setUp() throws IOException, InterruptedException {
        server = new TestServer(port);
        server.start();
        c = new Client(host, port);
        c.connect();
    }

    @Test
    public void testSetStartingPosition() {
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
