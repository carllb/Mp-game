package server;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import Game.Entity;
import Game.EntityPlayer;
import Game.World;
import Packets.Packet;
import Packets.PacketSendEntitiesAndID;

public class ClientInstance implements Runnable{
	
	ObjectOutputStream oos;
	ObjectInputStream ois;
	
	Socket mySocket;

	int mycID;
	int x, y, vx, vy;
	World world;
	Server server;
	
	public ClientInstance(Socket socket, Server server,int cID, World world){
		mySocket = socket;
		mycID = cID;
		this.world = world;
		this.server = server;
		
	}
	
	public void sendPacket(Packet p){
		try {
			oos.writeObject(p);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		try {
			System.out.println("started server thread for client");
			oos = new ObjectOutputStream(mySocket.getOutputStream());
			ois = new ObjectInputStream(mySocket.getInputStream());
			// send all the entities
			Entity[] entities = world.getEntities();
			for(int i=0;i<entities.length;i++){
				if(entities[i] instanceof EntityPlayer){
					entities[i] = ((EntityPlayer)entities[i]).toOtherPlayer();
				}
			}
			PacketSendEntitiesAndID pseai = new PacketSendEntitiesAndID(entities, mycID);
			oos.writeObject(pseai);
			server.gameWorld.addEntity(new EntityPlayer(null, null), mycID);
			while(mySocket.isConnected()){
				try{
					Packet p = (Packet) ois.readObject();
					System.out.println("readgin client packets" + p.toString());
					p.onServer(server);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try{
			oos.close();
			ois.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}
