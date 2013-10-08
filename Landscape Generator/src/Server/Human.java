package Server;

import java.awt.Point;

import network.Session;

import Client.ClientHuman;

import com.google.gson.internal.StringMap;

public class Human extends Fighter {
	private int currentX;
	private int currentY;
	private int newX;
	private int newY;
//	private Landscape landscape;
	private long remainingSleepTime;
	private long lastEntered;
	
	private Session owner;

	
	public Human(int currentX, int currentY, Session owner) {
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
		generateSleepTime(null);
		lastEntered = System.currentTimeMillis();
		this.owner = owner;
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
	
	private void generateMoney(Tile t){
		switch(t.getType()){
		case FORREST:
			owner.addMoney((int) (Math.random()*3)+1);
			break;
		case MOUNTAIN:
			owner.addMoney((int) (Math.random()*5)+1);
			break;
		case PLAIN:
			owner.addMoney((int) (Math.random()*2)+1);
			break;
		case WATER:
			owner.addMoney((int) (Math.random()*10)+1);
			break;
		}
	}
	
	private void generateSleepTime(Tile t) {
		if(t == null){
			remainingSleepTime = 1000;
		}else{
			switch(t.getType()){
			case FORREST:
				remainingSleepTime = ((int) Math.random()*1000)+1000;
				break;
			case MOUNTAIN:
				remainingSleepTime = ((int) Math.random()*2000)+1000;
				break;
			case PLAIN:
				remainingSleepTime = ((int) Math.random()*500)+500;
				break;
			case WATER:
				remainingSleepTime = ((int) Math.random()*5000)+5000;
				break;
			}
		}
	}

	public void initialize() {
		// TODO Auto-generated method stub

	}

	public Point getPos() {
		return new Point(currentX, currentY);
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
	}

	public void update(Tile t) {
		if (remainingSleepTime <= 0) {
			move();
			generateExperience(t);
			generateMoney(t);
			generateSleepTime(t);

		} else {
			remainingSleepTime = remainingSleepTime
					- (System.currentTimeMillis() - lastEntered);
			lastEntered = System.currentTimeMillis();
		}

	}

	private void generateExperience(Tile t) {
		switch(t.getType()){
		case FORREST:
			giveExperience(((int) Math.random()*5));
			break;
		case MOUNTAIN:
			giveExperience(((int) Math.random()*7));
			break;
		case PLAIN:
			giveExperience(((int) Math.random()*2));
			break;
		case WATER:
			giveExperience(((int) Math.random()*10));
			break;
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
	public ClientHuman toClientHuman(){
		return new ClientHuman(currentX, currentY, getCurrentHitpoints(), getDamage(), getInitiative(), getAttack(),
				getArmor());
	}
}
