package clientConnection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;

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
	
	private String errorMessage;
	
	public Client(String host, int port) {
		this.host = host;
		this.port = port;
		errorMessage = "";
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
	
	public String getErrorMessage() {
		return errorMessage;
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
			out.flush();
			out.reset();
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public synchronized void run(){
		try {
			running = true;
			System.out.println("You are connected! Loading the game...");
			System.out.println();
			
			while(running) {
				try {
					Object data = in.readObject();
					listener.received(data, this);
					
				}catch(ClassNotFoundException e) {
					e.printStackTrace();
				}catch(SocketException e) {
					close();
				} catch (PlayerLimitException e) {
					errorMessage = e.getMessage();
					System.out.println(errorMessage);
					close();
				}
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

}
