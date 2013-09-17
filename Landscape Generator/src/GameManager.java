import java.awt.Point;
import java.util.ArrayList;

import org.lwjgl.LWJGLException;

import OpenGL.OpenGL;


public class GameManager {
	private int boardWidth;
	private int boardHeight;
	private int tileSize;
	
	private int screenWidth;
	private int screenHeight;
	
	private int GUIWidth;
	
	private GUIHandler guiHandler;
	
	private HumanoidManager humanoidManager;
	public InputManager inputManager;

	Landscape landscape;
	
	private OpenGL gl = new OpenGL(); 

	public void initialize(int _boardWidth, int _boardHeight, int _tileSize) throws LWJGLException{
		// Storlek p� omr�den och antal omr�den
		boardWidth = _boardWidth;
		boardHeight = _boardHeight;
		tileSize = _tileSize;

		screenWidth = 800;
		screenHeight = 600;
		
		GUIWidth = 200;
		
		System.out.println("1");
		// Skapa v�rlden och generera omr�den
		LandscapeGenerator landGen = new LandscapeGenerator();
		landscape = new Landscape(landGen.generate(boardWidth, boardHeight));

		System.out.println("2");
		
		// Skapa Varelser
		humanoidManager = new HumanoidManager();
		humanoidManager.initialize(landscape);

		System.out.println("3");
		
		inputManager = new InputManager();
		inputManager.initilize();
		
		System.out.println("4");
		// Sk�rmstuff
		gl.initialize(screenWidth, screenHeight, tileSize, GUIWidth);
		

		System.out.println("5");
		
		guiHandler = new GUIHandler();
		guiHandler.Initialize(3, 2);
		System.out.println("6");
	}

	public void run() 
	{
		// Spela!!
		while (gl.isRunning()) {
			update();
			draw();
		}
		gl.quitGL();
	}
	
	private String tileToString(int x, int y) {
		StringBuilder sb = new StringBuilder();
		sb.append(landscape.getTile(x, y).toString());
		sb.append("\n");
		sb.append(humanoidManager.humanoidToString(new Point(x, y)));
		return sb.toString();
	}

	public void update() {		
		int input = inputManager.update();
		
		humanoidManager.update();
		
		guiHandler.update();
		
		gl.update(gl.getDelta(), input);
		
		inputManager.resetClickLocation();	
	}

	public void draw() {
		gl.initDraw();
		
		ArrayList<DrawingObject> otd = new ArrayList<DrawingObject>();
		DrawingObject cd;
		
		otd = landscape.draw();
		for(int i=0; i<otd.size(); i++)
		{
			cd = otd.get(i);
			gl.convertAndDraw(cd.posX, cd.posY, cd.cr, cd.cg, cd.cb, cd.notTile);
		}
		
		otd = humanoidManager.draw();
		
		for(int i=0; i<otd.size(); i++)
		{
			cd = otd.get(i);
			gl.convertAndDraw(cd.posX, cd.posY, cd.cr, cd.cg, cd.cb, cd.notTile);
		}
		//guiHandler.draw();
		
		gl.endDraw();
	}
}
