package Server;

import java.awt.Point;

import network.Session;

import Client.ClientHouse;

import com.google.gson.internal.StringMap;

public class House extends Fighter{
	private int currentX;
	private int currentY;
	//private Landscape landscape;
	private long remainingSleepTime;
	private long lastEntered;
	private int fertility;
	
	private Session owner;
	
	public House(int currentX, int currentY, Session owner) {
		super(10,2,4,4,18);
		this.currentX = currentX;
		this.currentY = currentY;
		generateSleepTime();
		lastEntered = System.currentTimeMillis();
		fertility = 0;
		this.owner = owner;
	}

	public House(StringMap sm) {
		super(10,2,4,4,18);
		
		currentX =  ((Double)sm.get("currentX")).intValue();
		currentY =  ((Double)sm.get("currentY")).intValue();
		//landscape = ;
		remainingSleepTime = ((Double)sm.get("remainingSleepTime")).longValue();
		lastEntered = ((Double)sm.get("lastEntered")).longValue();
		fertility = ((Double)sm.get("fertility")).intValue();
		
		//((Double)sm.get("forrestChance")).doubleValue();
	
	}
	
	public Session getOwner(){
		return owner;
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
		//landscape.getTile(currentX, currentY).inhabited(false);
	}

	private void generateFertility(Tile t) {
		switch (t.getType()) {
		case WATER:
			//atm
			break;
		case FORREST:
			fertility += (int) (Math.random()*5);
			break;
		case PLAIN:
			fertility += (int) (Math.random()*10);
			break;
		case MOUNTAIN:
			fertility += (int) (Math.random()*2);
			break;
		}
	}
	public int getFertility(){
		return fertility;
	}
	
	public void setFertility(int i) {
		fertility = i;
	}
	
	public void update(Tile t) {
		if (remainingSleepTime <= 0) {
			//Has slept enough!
			
			//Genereate new sleep time
			generateSleepTime();
			generateFertility(t);
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
		short nt = 1;
		short tp = 4; // texturePosition
		return new DrawingObject(currentX, currentY, tp, nt);
	}

	public int getCurrentX() {
		// TODO Auto-generated method stub
		return currentX;
	}

	public int getCurrentY() {
		// TODO Auto-generated method stub
		return currentY;
	}
	
	public ClientHouse toClientHouse(){
		return new ClientHouse(currentX, currentY, getCurrentHitpoints(), getDamage(), getInitiative(), getAttack(), getArmor());
	}
}
