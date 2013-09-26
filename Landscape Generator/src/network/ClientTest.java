package network;

import java.io.IOException;
import java.net.UnknownHostException;

import org.lwjgl.LWJGLException;

import Client.ClientGameManager;
import Client.LibraryLoader;

public class ClientTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try
		{
			LibraryLoader.loadNativeLibraries();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			System.out.println("sry bro..");
			System.exit(0);
		}
		
		ClientGameManager client = new ClientGameManager("localhost", 12345);
		try {
			client.init();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		client.run();
	}

}
