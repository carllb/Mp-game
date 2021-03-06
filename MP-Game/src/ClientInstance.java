import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientInstance implements Runnable{
	
	ObjectOutputStream oos;
	ObjectInputStream ois;
	
	Socket mySocket;

	int mycID;
	int x, y, vx, vy;
	
	public ClientInstance(Socket socket, int cID){
		mySocket = socket;
		mycID = cID;
	}
	
	@Override
	public void run() {
		try {
			oos = new ObjectOutputStream(mySocket.getOutputStream());
			ois = new ObjectInputStream(mySocket.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
