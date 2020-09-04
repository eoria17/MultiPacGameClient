package clientConnection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;

import javax.swing.JFrame;

import game.Game;
import packets.RemoveConnectionPacket;

//(Theo) This class will be responsible for managing data/object and running the game logic for the client. It will be responsible for
//accepting and sending data to the server
public class Client implements Runnable{
	
	private String host;
	private int port;
	
	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	
	private boolean running = false;
	private EventListener listener;
	
	public Client(String host, int port) {
		this.host = host;
		this.port = port;
	}
	
	public void connect() {
		try {
			socket = new Socket(host,port);
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
			listener = new EventListener();
			new Thread(this).start();
		}catch(ConnectException e) {
			System.out.println("Unable to connect to the server");
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public Socket getSocket() {
		return socket;
	}

	public void close() {
		try {
			running = false;
			in.close();
			out.close();
			socket.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendObject(Object packet) {
		try {
			out.writeObject(packet);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		try {
			running = true;
			System.out.println("You are connected! Loading the game...");
			while(running) {
				try {
					Object data = in.readObject();
					listener.received(data, this);
					
					boolean allReady = true;
					
					System.out.println(ConnectionHandler.playersReady);
					//check if all players are ready
					for(boolean ready : ConnectionHandler.playersReady.values()) {
						if(!ready) {
							allReady = false;
							break;
						}
					}
					
//					//run the game
//					if(allReady) {
//						System.out.println("enter all ready");
//						System.out.println(ConnectionHandler.playersReady);
//						
//						try {
//							Game game = new Game();
//					        game.setTitle("Monster Game");
//					        game.setSize(700,700);
//					        game.setLocationRelativeTo(null);  // center the frame
//					        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//					        game.setVisible(true);
//					        game.play();
//						} catch (Exception e) {
//							e.printStackTrace();
//						}
//						
//					}
					
					
				}catch(ClassNotFoundException e) {
					e.printStackTrace();
				}catch(SocketException e) {
					e.printStackTrace();
				}
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

}
