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
		
		//Skapa lyssnare till mus/tangentbord
		inputManager = new InputManager();
		inputManager.initilize();
		
		//mer skärmstuff
		viewer.setActionListener(inputManager);
		viewer.setMouseListener(inputManager);

		// Spela!!
		run();

	}

	public void run()
	{
		while (true) {
			update();
			draw();
		}

	}
	public void update() {
		humanoidManager.update();
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

	public void draw() {
		viewer.setImage(landscape.getLandscapeImg());
		humanoidManager.draw(tileSize);
	}
}
