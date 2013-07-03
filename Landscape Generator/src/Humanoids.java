public class Humanoids {
	private int currentX;
	private int currentY;
	private Landscape landscape;
	private long remainingSleepTime;
	private long lastEntered;

	public Humanoids(int currentX, int currentY, Landscape landscape) {
		this.currentX = currentX;
		this.currentY = currentY;
		this.landscape = landscape;
		generateSleepTime();
		lastEntered = System.currentTimeMillis();
		landscape.getTile(currentX,currentY).infest(true);
	}

	public void Initialize() {

	}

	public void Update() {
		if (remainingSleepTime <= 0) {
			//Has slept enough!
			
			//Genereate new sleep time
			generateSleepTime();

			//Randomize movement
			move();

		} else {
			//Need more sleep!
			remainingSleepTime = remainingSleepTime
					- (System.currentTimeMillis() - lastEntered);
			lastEntered = System.currentTimeMillis();
		}
	}

	private void move() {
		int rnd = (int) (Math.random() * 4);
		int newX = currentX;
		int newY = currentY;
		switch (rnd) {
		case 0:
			newX = currentX + 1;
			break;
		case 1:
			newX = currentX - 1;
			break;
		case 2:
			newY = currentY + 1;
			break;
		case 3:
			newY = currentY - 1;
			break;
		}
		if (landscape.moveZombie(currentX, currentY, newX, newY)) {
			currentX = newX;
			currentY = newY;
		}
	}

	private void generateSleepTime() {
		switch (landscape.getTile(currentX, currentY).getType()) {
		case WATER:
			// går inte, men ville inte ha felmeddelandet.
			break;
		case FORREST:
			remainingSleepTime = (long) (Math.random() * 1000) + 1000;
			break;
		case PLAIN:
			remainingSleepTime = (long) (Math.random() * 1000);
			break;
		case MOUNTAIN:
			remainingSleepTime = (long) (Math.random() * 1000) + 3000;
			break;
		}
	}

	public void Draw() {

	}
}
