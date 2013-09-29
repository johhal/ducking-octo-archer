import java.io.IOException;
import java.net.UnknownHostException;

import org.lwjgl.LWJGLException;

import Client.ClientGameManager;
import Client.LibraryLoader;
import Server.ServerGameManager;

public class ServerMain {
	public static void main(String[] args) throws LWJGLException {
//		try
//		{
//			LibraryLoader.loadNativeLibraries();
//		}
//		catch(Exception ex)
//		{
//			ex.printStackTrace();
//			System.out.println("sry bro..");
//			System.exit(0);
//		}
//		ServerGameManager gm = new ServerGameManager();
//		try{
//			gm.initialize(50, 50, 2);
//		}
//		catch (Exception ex)
//		{
//			System.out.println("initsiering misslyckades");
//			System.exit(0);
//			
//		}
//		gm.run();
		
		ServerGameManager server = new ServerGameManager();
		server.initialize(50, 50, 2);
		
		
	}
}
