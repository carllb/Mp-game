package Game;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JOptionPane;

import Packets.Packet;
import Packets.PacketUpdatePlayerVelocity;

public class Game {

	public static final int TARGET_FPS = 60;

	Display display;
	World world;
	Input input;
	EntityPlayer player;
	Socket server;
	ObjectInputStream ois;
	ObjectOutputStream oos;
	public static boolean running = true;

	public Game() {
		
		init();
		loop();

		// closes the streams
		if (ois != null) {
			try {
				ois.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (oos != null) {
			try {
				oos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void init() {
		try {
			String host = (String) JOptionPane.showInputDialog("Enter host");
			int port = Integer.parseInt(
					(String) JOptionPane.showInputDialog("Enter port number"));
			server = new Socket(host, port);
			ois = new ObjectInputStream(server.getInputStream());
			oos = new ObjectOutputStream(server.getOutputStream());
			input = new Input();
			player = new EntityPlayer(input, oos);
			world = new World(player);
			display = new Display(world);
			display.addKeyListener(input);
			Thread playerControl = new Thread(player);
			playerControl.start();
		} catch (Exception e) {
			e.printStackTrace();
			init();
		}
		
	}

	private void loop() {
		long startTime = 0L;
		long endTime = 0L;
		while (display.isVisible() && running) {
			startTime = System.currentTimeMillis();
			proccesPacket();
			display.repaint();
			endTime = System.currentTimeMillis();
			double mspf = (1f / TARGET_FPS) * 1000;
			if (endTime - startTime < mspf) {
				try {
					Thread.sleep((long) (mspf - (endTime - startTime)));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		running = false;
	}

	private void proccesPacket() {
		Packet p;
		try {
			p = (Packet) ois.readObject();
			p.onClient(world);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
