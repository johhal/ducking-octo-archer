import java.awt.Point;

import javax.swing.JFrame;

import org.lwjgl.LWJGLException;

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

	ImageViewer viewer;

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
		
		// Skapa F�nster
		viewer = new ImageViewer();

		// Skapa v�rlden och generera omr�den
		LandscapeGenerator landGen = new LandscapeGenerator();
		landscape = new Landscape(landGen.generate(boardWidth, boardHeight),
				tileSize, viewer);
		
		// Sk�rmstuff
		gl.Initialize(screenWidth, screenHeight, tileSize, GUIWidth);

		// Skapa Varelser
		humanoidManager = new HumanoidManager();
		humanoidManager.initialize(landscape, viewer);

		inputManager = new InputManager();
		try
		{
			inputManager.initilize();
		}
		catch(Exception ex)
		{
			
		}
		
		guiHandler = new GUIHandler();
		guiHandler.Initialize(3, 2);
		
		// mer sk�rmstuff
		viewer.setActionListener(inputManager);
		viewer.setMouseListener(inputManager);
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
		inputManager.update(gl);
		
		humanoidManager.update();
		
		guiHandler.update();
		
		gl.update(gl.getDelta(), 0, 0, 0);
		
		inputManager.resetClickLocation();	
	}

	public void draw() {
		gl.initDraw();
		
		landscape.draw(tileSize, gl);
		
		humanoidManager.draw(tileSize, gl);
		
		guiHandler.draw(gl);
		
		gl.endDraw();
	}
}
