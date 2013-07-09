import java.awt.Point;

import javax.swing.JFrame;

public class GameManager {
	private int boardWidth;
	private int boardHeight;
	private int tileSize;
	private HumanoidManager humanoidManager;
	public InputManager inputManager;

	ImageViewer viewer;

	Landscape landscape;

	public void initialize(int _boardWidth, int _boardHeight, int _tileSize) {
		// Storlek på områden och antal områden
		boardWidth = _boardWidth;
		boardHeight = _boardHeight;
		tileSize = _tileSize;

		// Skapa Fönster
		viewer = new ImageViewer();

		// Skapa världen och generera områden
		LandscapeGenerator landGen = new LandscapeGenerator();
		landscape = new Landscape(landGen.generate(boardWidth, boardHeight),
				tileSize, viewer);

		// Skärmstuff
		JFrame f = new JFrame("World map");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		f.setContentPane(viewer.getGui());

		f.pack();
		f.setLocationByPlatform(true);
		f.setVisible(true);

		// Skapa Varelser
		humanoidManager = new HumanoidManager();
		humanoidManager.initialize(landscape, viewer);

		// Skapa lyssnare till mus/tangentbord
		inputManager = new InputManager();
		inputManager.initilize();

		// mer skärmstuff
		viewer.setActionListener(inputManager);
		viewer.setMouseListener(inputManager);

		// Spela!!
		run();

	}

	public void run() {
		while (true) {
			update();
			draw();
		}

	}

	public void update() {
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
	}

	private String tileToString(int x, int y) {
		StringBuilder sb = new StringBuilder();
		sb.append(landscape.getTile(x, y).toString());
		sb.append("\n");
		sb.append(humanoidManager.humanoidToString(new Point(x, y)));
		return sb.toString();
	}

	public void draw() {
		viewer.setImage(landscape.getLandscapeImg());
		humanoidManager.draw(tileSize);
	}
}
