package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import Game.World;
import Packets.Packet;

public class Server {

	int portNumber = 25566;
	ArrayList<ClientInstance> clientList;
	public World gameWorld;
	boolean isRunning = true;

	public Server() {
		gameWorld = new World(null);
		clientList = new ArrayList<ClientInstance>();
		Game game = new Game(this);
		Thread gameThread = new Thread(game);
		gameThread.start();

		try {
			ServerSocket ss = new ServerSocket(portNumber);
			
			while (!ss.isClosed()) {
				System.out.println("wathing for client");
				Socket sock = ss.accept();
				System.out.println("connected");
				ClientInstance ci = new ClientInstance(sock, this, clientList.size(), gameWorld);
				synchronized (clientList) {
					clientList.add(ci);
				}
				Thread t = new Thread(ci);
				t.start();
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			isRunning = false;
		}
	}

	public World getWorld() {
		return gameWorld;
	}

	public void broadCastPacket(Packet p) {
		synchronized (clientList) {
			for (int i = 0; i < clientList.size(); i++) {
				clientList.get(i).sendPacket(p);
			}
		}
	}
	
	public void broadCastPacketToAllBut(Packet p,int cid) {
		synchronized (clientList) {
			for (int i = 0; i < clientList.size(); i++) {
				if(i != cid){
					clientList.get(i).sendPacket(p);
				}
			}
		}
	}

	public static void main(String[] args) {
		new Server();
	}
}
