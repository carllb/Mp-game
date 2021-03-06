package server;

public class Game implements Runnable{
	public static final int TARGET_FPS = 60;
	
	Server server;
	public Game(Server server) {
		this.server = server;
	}
	long startTime = 0;
	long endTime = 0;
	@Override
	public void run() {
		while(server.isRunning){
			startTime = System.currentTimeMillis();
			loop();
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
	}
	void loop(){
		server.gameWorld.tick();
		server.gameWorld.onServerTick(server);
		
	}
}
