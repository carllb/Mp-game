package Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.ObjectOutputStream;

import Packets.PacketUpdatePlayerVelocity;

public class EntityPlayer extends Entity implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 11L;
	Input input;
	ObjectOutputStream oos;
	int cID;

	public EntityPlayer(Input input, ObjectOutputStream oos) {
		r = 60;
		x = 100;
		y = 100;
		this.input = input;
		this.oos = oos;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.YELLOW);
		g.fillOval(x, y, r / 2, r / 2);
	}

	@Override
	public void tick() {
		super.tick();
	}

	@Override
	public void run() {
		while (Game.running) {
			vx = 0;
			vy = 0;
			if (input.isKeyDown(KeyEvent.VK_W)) {
				vy -= 5;
			}
			if (input.isKeyDown(KeyEvent.VK_S)) {
				vy += 5;
			}
			if (input.isKeyDown(KeyEvent.VK_A)) {
				vx -= 5;
			}
			if (input.isKeyDown(KeyEvent.VK_D)) {
				vx += 5;
			}
			PacketUpdatePlayerVelocity pup = new PacketUpdatePlayerVelocity( vx, vy, cID);
			try {
				oos.writeObject(pup);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void setID(int cID) {
		this.cID = cID;
	}

	public EntityOtherPlayer toOtherPlayer() {
		EntityOtherPlayer eop = new EntityOtherPlayer();
		eop.r = r;
		eop.x = x;
		eop.y = y;
		eop.vy = vy;
		eop.vx = vx;
		return eop;
	}
}
