package Client;

import java.awt.Point;

import Server.DrawingObject;
import Server.Fighter;

import com.google.gson.internal.StringMap;

public class ClientHouse extends Fighter{
	private int currentX;
	private int currentY;
	private int fertility;
	
	public ClientHouse(int currentX, int currentY, int hitPoints, int damage, int initiative, int attack,
			int armor) {
		super(hitPoints,damage,initiative,attack,armor);
		this.currentX = currentX;
		this.currentY = currentY;
		fertility = 0;
	}
	
	
	public ClientHouse(StringMap sm) {
		super(sm);
		
		currentX =  ((Double)sm.get("currentX")).intValue();
		currentY =  ((Double)sm.get("currentY")).intValue();
		fertility = ((Double)sm.get("fertility")).intValue();
	}
	
	
	public Point getPos(){
		return new Point(currentX,currentY);
	}
	public int getFertility(){
		return fertility;
	}
	
	public void setFertility(int i) {
		fertility = i;
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
		return currentX;
	}

	public int getCurrentY() {
		return currentY;
	}
}
