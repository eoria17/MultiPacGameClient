package clientConnection;

import packets.AddConnectionPacket;
import packets.ClientSettingPacket;
import packets.RejectedPacket;
import packets.RemoveConnectionPacket;

//(Theo) used to check what kind of packet are being received
public class EventListener {
	
	public void received(Object p, Client c) {

		//(Theo) if a new client connected to the server, the server will send the necessary clients data here.
		//The data received will be registered in the connection handler.
		if(p instanceof AddConnectionPacket) {
			AddConnectionPacket packet = (AddConnectionPacket)p;
			ConnectionHandler.connections.put(packet.id,new Connection(packet.id));
			System.out.println(packet.id + " has connected");
		
		}else if(p instanceof RemoveConnectionPacket) {
			RemoveConnectionPacket packet = (RemoveConnectionPacket)p;
			System.out.println("Connection: " + packet.id + " has disconnected");
			ConnectionHandler.connections.remove(packet.id);
		
		}else if(p instanceof RejectedPacket) {
			RejectedPacket packet = (RejectedPacket) p;
			System.out.println(packet.message);
			
			RemoveConnectionPacket rp = new RemoveConnectionPacket();
			rp.id = ConnectionHandler.id;
			c.sendObject(rp);
			c.close();
			System.out.println("Game is closed.");
			
		}else if(p instanceof ClientSettingPacket) {
			ClientSettingPacket packet = (ClientSettingPacket) p;
			ConnectionHandler.id = packet.id;
		}
	}
	
}
