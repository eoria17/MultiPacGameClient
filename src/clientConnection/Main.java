package clientConnection;

import java.util.Scanner;

import packets.AddConnectionPacket;
import packets.ReadyPacket;
import packets.SettingPacket;

public class Main {

	public static void main(String[] args) {
		tempMenu();
	}

	//(Li//)
	//(Theo) This func will be a temporary menu, it will be used to connect to the server and set the game max player setting
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

			do {
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

					System.out.println("Connection to the server and creating Game room..");

					//(Theo) This will create a client object which is a thread object to connect to the server.
					//The client thread will be responsible in managing the connection to the server
					Client client = new Client("localhost", 8080);
					client.connect();

					//(Theo) This add connection packet will register the connection to the server, registering into the server's connected clients list.
					AddConnectionPacket packet = new AddConnectionPacket();
					client.sendObject(packet);

					//(Theo) This will set the limit of how many clients are able to connect to the server.
					SettingPacket settingPacket = new SettingPacket(playerLimit);
					client.sendObject(settingPacket);
					
					System.out.println("Are you ready to play the game? (y/n)");
					String ready = sc.next();
					if(ready.equalsIgnoreCase("y")) {
						ReadyPacket rpacket = new ReadyPacket(ConnectionHandler.id ,true);
						client.sendObject(rpacket);
					}
					break;
				} else if (opt == 2) {
					System.out.println("Joining game..");

					Client client = new Client("localhost", 8080);
					client.connect();

					AddConnectionPacket packet = new AddConnectionPacket();
					client.sendObject(packet);
					
					System.out.println("Are you ready to play the game? (y/n)");
					String ready = sc.next();
					if(ready.equalsIgnoreCase("y")) {
						ReadyPacket rpacket = new ReadyPacket(ConnectionHandler.id ,true);
						client.sendObject(rpacket);
					}
					break;
				}
				
			} while (true);
		} while (true);
	}
}
