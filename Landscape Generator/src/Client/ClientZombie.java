package Client;


import java.awt.Point;

import Server.DrawingObject;
import Server.Fighter;

import com.google.gson.internal.StringMap;

public class ClientZombie extends Fighter {
	private int currentX;
	private int currentY;
	private int newX;
	private int newY;

	public ClientZombie(int currentX, int currentY, int hitPoints, int damage, int initiative, int attack,
			int armor) {
		super(hitPoints, damage, initiative, attack, armor);
		this.currentX = currentX;
		this.currentY = currentY;
		newX = currentX;
		newY = currentY;
	}
	
	public ClientZombie(StringMap sm){
		super(sm);
		currentX = ((Double)sm.get("currentX")).intValue();
		currentY = ((Double)sm.get("currentY")).intValue();
		newX = ((Double)sm.get("newX")).intValue();
		newY = ((Double)sm.get("newY")).intValue();
	}

	public Point getPos() {
		return new Point(currentX, currentY);
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

}

