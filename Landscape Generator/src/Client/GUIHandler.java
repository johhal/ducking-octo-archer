package Client;
public class GUIHandler {
	private int nrOfTeams; // teamcolor
	private int selectedTeam;
	
	private int nrOfObjects; //House, humaoids
	private int selectedObject;
	
	public void Initialize(int nrTeams, int nrObjects)
	{
		nrOfTeams = nrTeams;
		selectedTeam = 1;
		
		nrOfObjects = nrObjects;
		selectedObject = 1;
	}
	
	public int getNrOfTeams()
	{
		return nrOfTeams;
	}
	
	public int getNrOfObjects()
	{
		return nrOfObjects;
	}
	
	public int getSelectedTeam()
	{
		return selectedTeam;
	}
	
	public int getSelectedObject()
	{
		return selectedObject;
	}
	
	public void update()
	{
		
	}
	
	public void draw()
	{
		
	}
}
