import java.awt.Point;

import javax.swing.JFrame;

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

	public void initialize(int _boardWidth, int _boardHeight, int _tileSize) {
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
		try
		{
			gl.Initialize(screenWidth, screenHeight, tileSize, GUIWidth);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			System.exit(0);
		}

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

	public void update() {
		/*
		humanoidManager.update();
		if (inputManager.isLeftMouseClicked()) {
			Point pos = inputManager.getClickLocation();
			if (inputManager.spawnZombieSelected()) {
				humanoidManager.addZombie(pos.x / tileSize, pos.y / tileSize);
			} else if (inputManager.spawnHumanSelected()) {
				humanoidManager.addHuman(pos.x / tileSize, pos.y / tileSize);
			} else {
				humanoidManager.addHouse(pos.x / tileSize, pos.y / tileSize);
			}
		}
		if (inputManager.isRightMouseClicked()) {
			System.out.println("GAMEMANAGER RIGHT CLICK");
			Point pos = inputManager.getClickLocation();
			viewer.setStatus(tileToString(pos.x / tileSize, pos.y / tileSize));
		}
		*/
		int delta = gl.getDelta();
		
		gl.update(delta);
		
		inputManager.update();
		
		humanoidManager.update();
		
		guiHandler.update();
		/*if (inputManager.isLeftMouseClicked()) {
			if (inputManager.spawnZombieSelected()) {
				humanoidManager.addZombie((int) inputManager.getClickLocation()
						.getX() / tileSize, (int) inputManager
						.getClickLocation().getY() / tileSize);
			} else {
				landscape.spawnHuman((int) inputManager.getClickLocation()
						.getX() / tileSize, (int) inputManager
						.getClickLocation().getY() / tileSize);
			}
		}

		if (inputManager.isRightMouseClicked()) {
		System.out.println("right mouse pressed, wont do shit");
		}*/
		inputManager.resetClickLocation();
	}

	private String tileToString(int x, int y) {
		StringBuilder sb = new StringBuilder();
		sb.append(landscape.getTile(x, y).toString());
		sb.append("\n");
		sb.append(humanoidManager.humanoidToString(new Point(x, y)));
		return sb.toString();
	}

	public void draw() {
		gl.initDraw();
		
		landscape.draw(tileSize, gl);
		
		humanoidManager.draw(tileSize, gl);
		
		guiHandler.draw(gl);
		
		gl.endDraw();
	}
}
