package clientConnection;

import java.util.HashMap;
import java.util.Scanner;

import game.Position;
import packets.AddConnectionPacket;
import packets.ReadyPacket;
import packets.SettingPacket;
import packets.StartingPositionPacket;

public class Main {

	public static void main(String[] args) {
		tempMenu();
	}

	public static boolean setStartingPosition(Client c, int opt, HashMap<Integer, Position> startingPositions) {
		if(ConnectionHandler.allPlayersPosition.containsValue(startingPositions.get(opt))) {
			return false;
		}else {
			ConnectionHandler.allPlayersPosition.put(ConnectionHandler.id, startingPositions.get(opt));
			StartingPositionPacket sPacket = new StartingPositionPacket(startingPositions.get(opt));
			c.sendObject(sPacket);
			return true;
		}
	}

	public static void setStartingPosition(Client c) {
		Scanner sc = new Scanner(System.in);
		
		Position topLeft = new Position(0,0);
		Position topRight = new Position(0,10);
		Position botLeft = new Position(10,0);
		Position botRight = new Position(10,10);

		HashMap<Integer, Position> startingPositions = new HashMap<Integer, Position>();
		startingPositions.put(1, topLeft);
		startingPositions.put(2, topRight);
		startingPositions.put(3, botLeft);
		startingPositions.put(4, botRight);

		do {
			System.out.println("---------------------------------------");
			System.out.println("Select a starting position: ");
			System.out.println("1. Top left");
			System.out.println("2. Top right");
			System.out.println("3. Bottom left");
			System.out.println("4. Bottom Right");

			if (!sc.hasNextInt()) {
				System.out.println("Error: Menu option enterred is not a number. Please enter a menu number.");
				System.out.println();
				sc.next();
				continue;
			}
			int opt = sc.nextInt();

			if(ConnectionHandler.allPlayersPosition.containsValue(startingPositions.get(opt))) {
				System.out.println("Starting position already taken");
				continue;
			}else {
				ConnectionHandler.allPlayersPosition.put(ConnectionHandler.id, startingPositions.get(opt));
				StartingPositionPacket sPacket = new StartingPositionPacket(startingPositions.get(opt));
				c.sendObject(sPacket);
				System.out.println("Starting position confirmed.");
				break;
			}
		} while (true);

	}

	// (Theo) This func will be a temporary menu, it will be used to connect to the
	// server and set the game max player setting
	public static void tempMenu() {
		Scanner sc = new Scanner(System.in);

		do {
			System.out.println("Welcome to the Multiplayer pacman game!");
			System.out.println("---------------------------------------");
			System.out.println("Please select a menu option:");
			System.out.println("1. Create a game room");
			System.out.println("2. Join a game");
			System.out.println("---------------------------------------");
			System.out.print("Enter a menu number: ");

			if (!sc.hasNextInt()) {
				System.out.println("Error: Menu option enterred is not a number. Please enter a menu number.");
				System.out.println();
				sc.next();
				continue;
			}
			int opt = sc.nextInt();

			if (opt == 1) {
				System.out.print("Please enter the player limit for this game:");
				if (!sc.hasNextInt()) {
					System.out.println("Error: Please enter a number for the player limit (From 1-4).");
					System.out.println();
					sc.next();
					continue;
				}
				int playerLimit = sc.nextInt();
				if (playerLimit > 4 || playerLimit < 1) {
					System.out.println("Please enter the correct player limit from 1-4 players");
					continue;
				}

				System.out.println("Connectiong to the server and creating Game room..");

				// (Theo) This will create a client object which is a thread object to connect
				// to the server.
				// The client thread will be responsible in managing the connection to the
				// server
				Client client = new Client(Settings.host, Settings.port);
				client.connect();
				
				try {
					Thread.sleep(1000);
					
					if (!client.getSocket().isClosed()) {
						// (Theo) This will set the limit of how many clients are able to connect to the
						// server.
						SettingPacket settingPacket = new SettingPacket(playerLimit);
						client.sendObject(settingPacket);
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
				

				try {
					Thread.sleep(2000);
					
					if (!client.getSocket().isClosed()) {
						// (Theo) This add connection packet will register the connection to the server,
						// registering into the server's connected clients list.
						AddConnectionPacket packet = new AddConnectionPacket();
						client.sendObject(packet);
						
						setStartingPosition(client);
						
						System.out.println("Are you ready to play the game? (y/n)");
						String ready = sc.next();
						if (ready.equalsIgnoreCase("y")) {
							ConnectionHandler.allPlayersReadyStatus.put(ConnectionHandler.id, true);
							ReadyPacket rpacket = new ReadyPacket(ConnectionHandler.id, true);
							client.sendObject(rpacket);
							break;
						}

						// select starting position
					}
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				

				break;

			} else if (opt == 2) {
				
				System.out.println("Connecting to the server..");

				Client client = new Client(Settings.host, Settings.port);
				client.connect();

				
				
				try {
					Thread.sleep(3000);
					
					if (!client.getSocket().isClosed()) {
						AddConnectionPacket packet = new AddConnectionPacket();
						client.sendObject(packet);
						
						setStartingPosition(client);
						
						System.out.println("Are you ready to play the game? (y/n)");
						String ready = sc.next();

						if (ready.equalsIgnoreCase("y")) {
							ConnectionHandler.allPlayersReadyStatus.put(ConnectionHandler.id, true);
							ReadyPacket rpacket = new ReadyPacket(ConnectionHandler.id, true);
							client.sendObject(rpacket);
							break;
						}else {
							System.out.println("wrong input. game is closed.");
							client.close();
						}
					}
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			break;
		} while (true);
	}
}
