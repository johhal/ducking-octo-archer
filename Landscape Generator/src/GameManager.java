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
		// Storlek p� omr�den och antal omr�den
		boardWidth = 50;
		boardHeight = 50;
		tileSize = 10;

		// Skapa F�nster
		viewer = new ImageViewer();

		// Skapa v�rlden och generera omr�den
		LandscapeGenerator landGen = new LandscapeGenerator();
		landscape = new Landscape(landGen.generate(boardWidth, boardHeight),
				tileSize, viewer);

		// Sk�rmstuff
		JFrame f = new JFrame("World map");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		f.setContentPane(viewer.getGui());

		f.pack();
		f.setLocationByPlatform(true);
		f.setVisible(true);
		viewer.setMouseListener(landscape);

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
			} else {
				landscape.spawnHuman((int) inputManager.getClickLocation()
						.getX() / tileSize, (int) inputManager
						.getClickLocation().getY() / tileSize);
			}
		}

		// if (inputManager.isRightMouseClicked()) {
		// System.out.println("right mouse pressed, wont do shit");
		// }
	}

	public void Draw() {
		viewer.setImage(landscape.getLandscapeImg());
	}
}
