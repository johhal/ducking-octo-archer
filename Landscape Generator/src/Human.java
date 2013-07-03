import java.awt.Point;


public class Human {
	private int currentX;
	private int currentY;
	private Landscape landscape;
	private long remainingSleepTime;
	private long lastEntered;
	private int fertility;
	
	public Human(int currentX, int currentY, Landscape landscape) {
		this.currentX = currentX;
		this.currentY = currentY;
		this.landscape = landscape;
		generateSleepTime();
		lastEntered = System.currentTimeMillis();
		landscape.getTile(currentX,currentY).inhabited(true);
		fertility = 0;
	}

	private void generateSleepTime() {
		remainingSleepTime = 1000;

	}

	public void Initialize() {
		// TODO Auto-generated method stub
		
	}
	
	public Point getPos(){
		return new Point(currentX,currentY);
	}
	public void kill(){
		landscape.getTile(currentX, currentY).inhabited(false);
	}

	public void Update() {
		if (remainingSleepTime <= 0) {
			//Has slept enough!
			
			//Genereate new sleep time
			generateSleepTime();
			generateFertility();
			//Randomize movement
//			move();

		} else {
			//Need more sleep!
			remainingSleepTime = remainingSleepTime
					- (System.currentTimeMillis() - lastEntered);
			lastEntered = System.currentTimeMillis();
		}
		
	}

	private void generateFertility() {
		switch (landscape.getTile(currentX, currentY).getType()) {
		case WATER:
			//atm
			break;
		case FORREST:
			fertility += (int) Math.random()*10+5;
			break;
		case PLAIN:
			fertility += (int) Math.random()*30+5;
			break;
		case MOUNTAIN:
			fertility += (int) Math.random()*5+5;
			break;
		}
	}
	public int getFertility(){
		return fertility;
	}

	private void move() {
		if (landscape.getTile(currentX, currentY).isInhabited()) {
			Point p = landscape.getNearbyAvailableLocation(currentX, currentY);
			landscape.getTile((int)p.getX(),(int)p.getY()).inhabited(true);
		}
	}

	public void Draw() {
		// TODO Auto-generated method stub
		
	}

	public void setFertility(int i) {
		fertility = i;
	}

}
