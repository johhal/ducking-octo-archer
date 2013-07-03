import javax.swing.JFrame;

public class GameManager {
	private int boardWidth;
	private int boardHeight;
	private int tileSize;
	private HumanoidManager humanoidManager;
	public InputManager inputManager;

	ImageViewer viewer;

	Landscape landscape;

	public int Initialize() {
		// Storlek på områden och antal områden
		boardWidth = 50;
		boardHeight = 50;
		tileSize = 10;

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
		humanoidManager.Initialize(landscape);
		inputManager = new InputManager();
		inputManager.Initilize();
		viewer.setActionListener(inputManager);
		viewer.setMouseListener(inputManager);

		// Spela!!
		while (true) {
			Update();
			Draw();
		}

	}

	public void Update() {
		humanoidManager.Update();
		if (inputManager.isLeftMouseClicked()) {
			if (inputManager.spawnZombieSelected()) {
				humanoidManager.addZombie((int) inputManager.getClickLocation()
						.getX() / tileSize, (int) inputManager
						.getClickLocation().getY() / tileSize);
			} else if (inputManager.spawnHumanSelected()){
				humanoidManager.addHuman((int) inputManager.getClickLocation()
						.getX() / tileSize, (int) inputManager
						.getClickLocation().getY() / tileSize);
			} else {
				humanoidManager.addHouse((int) inputManager.getClickLocation()
						.getX() / tileSize, (int) inputManager
						.getClickLocation().getY() / tileSize);
			}
		}
	}

	public void Draw() {
		viewer.setImage(landscape.getLandscapeImg());
	}
}
