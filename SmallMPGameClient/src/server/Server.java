package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import Game.World;

public class Server {

	int portNumber = 22222;
	ArrayList<ClientInstance> clientList;
	World gameWorld;

	public Server() {
		gameWorld = new World(null);
		clientList = new ArrayList<ClientInstance>();

		try {
			ServerSocket ss = new ServerSocket(portNumber);

			while (!ss.isClosed()) {

				Socket sock = ss.accept();
				ClientInstance ci = new ClientInstance(sock, this, clientList.size(), gameWorld);
				clientList.add(ci);
				Thread t = new Thread(ci);

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public World getWorld(){
		return gameWorld;
	}
}
