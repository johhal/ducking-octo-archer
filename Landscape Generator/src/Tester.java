public class Tester {
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
		GameManager gm = new GameManager();
		try{
			gm.initialize(50, 50, 10);
		}
		catch (Exception ex)
		{
			System.exit(0);
		}
		gm.run();
	}
}
