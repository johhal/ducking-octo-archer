public class HumanoidManager extends Thread {

	
	private Humanoids humanoids[];
	private Landscape landscape;

	public void Initialize(Landscape lAndscape) {
		this.landscape = lAndscape;
		humanoids = new Humanoids[0];
		for (int j = 0; j < landscape.getBoardHeight(); j++) {
			for (int i = 0; i < landscape.getBoardWidth(); i++) {
				double rnd = Math.random();
				Tile current = landscape.getTile(i, j);

				switch (current.getType()) {
				case FORREST:
					if (rnd < 0.01) {
//						current.infest(true);
						addZombie(i, j);
					}
					break;
				case PLAIN:
					if (rnd < 0.01) {
//						current.infest(true);
						addZombie(i, j);
					}
					break;
				case WATER:
					break;
				case MOUNTAIN:
					if (rnd < 0.01) {
//						current.infest(true);
						addZombie(i, j);
					}
					break;
				}

			}
		}
	}

	public boolean addZombie(int xpos, int ypos) {
		// Sätt ut zombies? zombiechans?
		new ZombieThread(xpos, ypos, landscape).start();
		return true;
	}

	public boolean removeZombie() {
		return true;
	}

	public void Update() {
		for(int i = 0; i < humanoids.length; i++)
		{
			humanoids[i].Update();
		}
	}

	public void Draw() {

	}
}
