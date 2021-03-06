package Server;

import java.awt.Point;

import Client.ClientZombie;

import com.google.gson.internal.StringMap;

public class Zombie extends Fighter {
	private int currentX;
	private int currentY;
	private int newX;
	private int newY;
//	private Landscape landscape;
	private long remainingSleepTime;
	private long lastEntered;

	public Zombie(int currentX, int currentY) {
		super(7, 4, 7, 6, 16);
		this.currentX = currentX;
		this.currentY = currentY;
		
		newX = currentX;
		newY = currentY;
		generateSleepTime(null);
		lastEntered = System.currentTimeMillis();
	}
	
	public Zombie(StringMap sm){
		super(sm);
		currentX = ((Double)sm.get("currentX")).intValue();
		currentY = ((Double)sm.get("currentY")).intValue();
		newX = ((Double)sm.get("newX")).intValue();
		newY = ((Double)sm.get("newY")).intValue();
		remainingSleepTime = ((Double)sm.get("remainingSleepTime")).intValue();
		lastEntered = ((Double)sm.get("lastEntered")).intValue();
	}

	public Point getPos() {
		return new Point(currentX, currentY);
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
//		if (landscape.canMoveZombie(currentX, currentY, newX, newY)) {
//			landscape.getTile(currentX, currentY).infest(false);
//			currentX = newX;
//			currentY = newY;
//			landscape.getTile(currentX, currentY).infest(true);
//		}
	}

	public void generateSleepTime(Tile t) {
		if(t == null){
			remainingSleepTime = 2000;
		}else{
			switch(t.getType()){
			case FORREST:
				remainingSleepTime = ((int) Math.random()*1500)+2000;
				break;
			case MOUNTAIN:
				remainingSleepTime = ((int) Math.random()*2000)+2000;
				break;
			case PLAIN:
				remainingSleepTime = ((int) Math.random()*500)+1000;
				break;
			case WATER:
				remainingSleepTime = ((int) Math.random()*5000)+6000;
				break;
			}
		}
	}

	public void kill() {
	//	landscape.getTile(currentX, currentY).infest(false);
	}

	public void update(Tile t) {
		if (remainingSleepTime <= 0) {
			// Has slept enough!

			// Genereate new sleep time
			generateSleepTime(t);

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
		sb.append("Zombie");
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
		short nt = 1;
		short tp = 6; // texturePosition
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

	public ClientZombie toClientZombie(){
		return new ClientZombie(currentX, currentY, getCurrentHitpoints(), getDamage(), getInitiative(), getAttack(),
				getArmor());
	}
}
