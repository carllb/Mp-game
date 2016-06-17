package Packets;

import java.io.Serializable;

import Game.Game;
import Game.World;

public abstract class Packet implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public abstract void onServer();			
	public abstract void onClient(World world);
	
}