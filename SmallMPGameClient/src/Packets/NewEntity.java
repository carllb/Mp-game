package Packets;

import Game.Entity;
import Game.World;

public class NewEntity extends Packet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3L;

	Entity e;
	int index;
	
	public NewEntity(Entity e, int index) {
		this.e = e;
		this.index = index;
	}
	
	@Override
	public void onServer() {		

	}

	@Override
	public void onClient(World world) {
		world.addEntity(e,index);
	}

}
