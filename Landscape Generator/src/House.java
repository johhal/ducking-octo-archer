import java.awt.Point;


public class House {
	private int currentX;
	private int currentY;
	private Landscape landscape;
	private long remainingSleepTime;
	private long lastEntered;
	private int fertility;
	
	public House(int currentX, int currentY, Landscape landscape) {
		this.currentX = currentX;
		this.currentY = currentY;
		this.landscape = landscape;
		generateSleepTime();
		lastEntered = System.currentTimeMillis();
		landscape.getTile(currentX,currentY).buildHouse(true);
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

	private void generateFertility() {
		switch (landscape.getTile(currentX, currentY).getType()) {
		case WATER:
			//atm
			break;
		case FORREST:
			fertility += (int) (Math.random()*10);
			break;
		case PLAIN:
			fertility += (int) (Math.random()*20);
			break;
		case MOUNTAIN:
			fertility += (int) (Math.random()*5);
			break;
		}
	}
	public int getFertility(){
		return fertility;
	}

	private void move() {
		if (landscape.getTile(currentX, currentY).isInhabited()) {
			Point p = landscape.getNearbyAvailableLocation(currentX, currentY);
			if(p!=null){
			landscape.getTile((int)p.getX(),(int)p.getY()).inhabited(true);
			}
		}
	}
	

	public void setFertility(int i) {
		fertility = i;
	}
	
	public void update() {
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

	public void draw() {
		// TODO Auto-generated method stub
		
	}
}
