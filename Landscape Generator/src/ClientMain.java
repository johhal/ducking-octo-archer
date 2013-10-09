

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.UnknownHostException;

import org.lwjgl.LWJGLException;

import Client.ClientGameManager;
import Client.LibraryLoader;

public class ClientMain {

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

		ClientGameManager client = new ClientGameManager("localhost", 12346);
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
		try {
			client.run();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
