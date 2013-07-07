import java.awt.Point;

public class Human {
	private int currentX;
	private int currentY;
	private Landscape landscape;
	private long remainingSleepTime;
	private long lastEntered;
	private int fertility;
	private boolean isWarrior;

	public Human(int currentX, int currentY, Landscape landscape) {
		this.currentX = currentX;
		this.currentY = currentY;
		this.landscape = landscape;
		generateSleepTime();
		lastEntered = System.currentTimeMillis();
		landscape.getTile(currentX, currentY).inhabited(true);
		fertility = 0;
		if(Math.random()<0.2){
			System.out.println("A WARRIOR IS BORN!!!");
			isWarrior = true;
		}
	}

	private void generateSleepTime() {
		remainingSleepTime = 1000;

	}

	public void initialize() {
		// TODO Auto-generated method stub

	}

	public Point getPos() {
		return new Point(currentX, currentY);
	}

	public void kill() {
		landscape.getTile(currentX, currentY).inhabited(false);
	}

	private void generateFertility() {
		switch (landscape.getTile(currentX, currentY).getType()) {
		case WATER:
			// atm
			break;
		case FORREST:
			fertility += (int) (Math.random() * 7);
			break;
		case PLAIN:
			fertility += (int) (Math.random() * 10);
			break;
		case MOUNTAIN:
			fertility += (int) (Math.random() * 5);
			break;
		}
	}

	public int getFertility() {
		return fertility;
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
		if (landscape.canMoveHuman(currentX, currentY, newX, newY)) {
			landscape.getTile(currentX, currentY).inhabited(false);
			currentX = newX;
			currentY = newY;
			landscape.getTile(currentX, currentY).inhabited(true);
		}
	}

	public void setFertility(int i) {
		fertility = i;
	}

	public boolean isWarrior() {
		// TODO Auto-generated method stub
		return isWarrior;
	}
	
	public void update() {
		if (remainingSleepTime <= 0) {
			// Has slept enough!

			// Genereate new sleep time
			generateSleepTime();
			generateFertility();
			move();
			// Randomize movement
			// move();

		} else {
			// Need more sleep!
			remainingSleepTime = remainingSleepTime
					- (System.currentTimeMillis() - lastEntered);
			lastEntered = System.currentTimeMillis();
		}

	}
	
	public void draw() {
		// TODO Auto-generated method stub

	}

}
