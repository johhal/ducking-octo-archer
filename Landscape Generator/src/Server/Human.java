package Server;

import java.awt.Point;

import com.google.gson.internal.StringMap;

public class Human extends Fighter {
	private int currentX;
	private int currentY;
	private int newX;
	private int newY;
//	private Landscape landscape;
	private long remainingSleepTime;
	private long lastEntered;

	
	public Human(int currentX, int currentY, Landscape landscape) {
		super(4, 2, 4, 4, 15);
		if (Math.random() < 0.2) {
			setDamage(4);
			setInitiative(10);
			setHitPoints(10);
			setArmor(18);
			setAttack(8);
		}
		this.currentX = currentX;
		this.currentY = currentY;
		newX = currentX;
		newY = currentY;
	//	this.landscape = landscape;
		generateSleepTime();
		lastEntered = System.currentTimeMillis();
	//	landscape.getTile(currentX, currentY).inhabited(true);
	}
	public Human(StringMap sm){
		super(sm);
		currentX = ((Double)sm.get("currentX")).intValue();
		currentY = ((Double)sm.get("currentY")).intValue();
		newX = ((Double)sm.get("newX")).intValue();
		newY = ((Double)sm.get("newY")).intValue();
		remainingSleepTime = ((Double)sm.get("remainingSleepTime")).intValue();
		lastEntered = ((Double)sm.get("lastEntered")).intValue();
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
		//landscape.getTile(currentX, currentY).inhabited(false);
	}

	public void setNewY(int newY) {
		this.newY = newY;
	}

	private void move() {
		int rnd = (int) (Math.random() * 4);
		newX = currentX;
		newY = currentY;
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
//		if (landscape.canMoveHuman(currentX, currentY, newX, newY)) {
//			landscape.getTile(currentX, currentY).inhabited(false);
//			currentX = newX;
//			currentY = newY;
//			landscape.getTile(currentX, currentY).inhabited(true);
//		}
	}

	public void update() {
		if (remainingSleepTime <= 0) {
			// Has slept enough!

			// Genereate new sleep time
			generateSleepTime();

			// Randomize movement
			move();

		} else {
			// Need more sleep!
			remainingSleepTime = remainingSleepTime
					- (System.currentTimeMillis() - lastEntered);
			lastEntered = System.currentTimeMillis();
		}

	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Human");
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
		
		short nt = 1; // notTile, value 1 if not a tile.
		short tp = 5; // texturePosition
		return new DrawingObject(currentX, currentY, tp, nt);
	}
	public int getCurrentX() {
		return currentX;
	}

	public void setCurrentX(int currentX) {
		this.currentX = currentX;
	}

	public int getCurrentY() {
		return currentY;
	}

	public void setCurrentY(int currentY) {
		this.currentY = currentY;
	}

	public int getNewX() {
		return newX;
	}

	public void setNewX(int newX) {
		this.newX = newX;
	}

	public int getNewY() {
		return newY;
	}
}
