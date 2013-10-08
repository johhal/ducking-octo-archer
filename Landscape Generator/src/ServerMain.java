import org.lwjgl.LWJGLException;

import Server.ServerGameManager;

public class ServerMain {
	public static void main(String[] args) throws LWJGLException {
		
		ServerGameManager server = new ServerGameManager();
		server.initialize(50, 50, 2, 12346);
	}
}
