package Server;

import java.awt.Point;

public class House extends Fighter{
	private int currentX;
	private int currentY;
	private Landscape landscape;
	private long remainingSleepTime;
	private long lastEntered;
	private int fertility;
	
	public House(int currentX, int currentY, Landscape landscape) {
		super(10,2,4,4,18);
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
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("House");
		sb.append("\n");
		sb.append("HP: " + getCurrentHitpoints() + "/" + getMaxHitpoints());
		sb.append("\n");
		sb.append("Attack: " + getAttack());
		sb.append("\n");
		sb.append("Armor: " + getArmor());
		sb.append("\n");
		sb.append("Damage: " + getDamage());
		return sb.toString();
	}
	
	public DrawingObject draw() {
		short sh = 1;
		float n = (float)getCurrentHitpoints() / (float)getMaxHitpoints();
		return new DrawingObject(currentX, currentY, n, n/2, 0, sh);
	}
}
