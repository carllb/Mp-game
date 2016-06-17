package Packets;

import Game.Entity;
import Game.World;

public class PacketSendEntities extends Packet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;

	Entity[] entities;

	public PacketSendEntities(Entity[] es) {
		entities = es;
	}

	@Override
	public void onServer() {

	}

	@Override
	public void onClient(World world) {
		for (int i = 0; i < entities.length; i++) {
			world.addEntity(entities[i], i);
		}
	}
}
