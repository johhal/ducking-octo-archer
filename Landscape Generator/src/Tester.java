import org.lwjgl.LWJGLException;

public class Tester {
	public static void main(String[] args) throws LWJGLException {
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
		
		GameManager gm = new GameManager();
		try{
			gm.initialize(50, 50, 2);
		}
		catch (Exception ex)
		{
			System.out.println("initsiering misslyckades");
			System.exit(0);
			
		}
		gm.run();
	}
}
