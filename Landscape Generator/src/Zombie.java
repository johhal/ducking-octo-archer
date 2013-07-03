import java.awt.Point;


public class Zombie {
	private int currentX;
	private int currentY;
	private Landscape landscape;
	private long remainingSleepTime;
	private long lastEntered;

	public Zombie(int currentX, int currentY, Landscape landscape) {
		this.currentX = currentX;
		this.currentY = currentY;
		this.landscape = landscape;
		generateSleepTime();
		lastEntered = System.currentTimeMillis();
		landscape.getTile(currentX,currentY).infest(true);
		// TODO Auto-generated constructor stub
	}
	public Point getPos(){
		return new Point(currentX, currentY);
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
		if (landscape.canMoveZombie(currentX, currentY, newX, newY)) {
			landscape.getTile(currentX, currentY).infest(false);
			currentX = newX;
			currentY = newY;
			landscape.getTile(currentX, currentY).infest(true);
		}
	}

	public void generateSleepTime() {
		switch (landscape.getTile(currentX, currentY).getType()) {
		case WATER:
			// går inte, men ville inte ha felmeddelandet.
			break;
		case FORREST:
			remainingSleepTime = (long) (Math.random() * 1000) + 1500;
			break;
		case PLAIN:
			remainingSleepTime = (long) (Math.random() * 1000)+500;
			break;
		case MOUNTAIN:
			remainingSleepTime = (long) (Math.random() * 1000) + 4000;
			break;
		}
	}
	public void kill() {
		landscape.getTile(currentX, currentY).infest(false);
	}

	public void Draw() {

	}

}
