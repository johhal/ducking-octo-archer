import java.util.ArrayList;

public class HumanoidManager {

	
	private ArrayList<Humanoids> humanoids;
	private Landscape landscape;

	public void Initialize(Landscape lAndscape) {
		this.landscape = lAndscape;
		humanoids = new ArrayList<Humanoids>();
		for (int j = 0; j < landscape.getBoardHeight(); j++) {
			for (int i = 0; i < landscape.getBoardWidth(); i++) {
				double rnd = Math.random();
				Tile current = landscape.getTile(i, j);

				switch (current.getType()) {
				case FORREST:
					if (rnd < 0.01) {
						addZombie(i, j);
					}
					break;
				case PLAIN:
					if (rnd < 0.01) {
						addZombie(i, j);
					}
					break;
				case WATER:
					break;
				case MOUNTAIN:
					if (rnd < 0.01) {
						addZombie(i, j);
					}
					break;
				}
			}
		}
	}

	public boolean addZombie(int xpos, int ypos) {
		// Sätt ut zombies? zombiechans?
		//new ZombieThread(xpos, ypos, landscape).start();
		humanoids.add(new Humanoids(xpos, ypos, landscape));
		return true;
	}

	public boolean removeZombie() {
		return true;
	}

	public void Update() {
		for(int i = 0; i < humanoids.size(); i++)
		{
			humanoids.get(i).Update();
		}
	}

	public void Draw() {

	}
}
