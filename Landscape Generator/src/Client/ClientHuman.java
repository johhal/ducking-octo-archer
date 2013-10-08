package Client;

import java.awt.Point;



import Server.DrawingObject;
import Server.Fighter;

import com.google.gson.internal.StringMap;

public class ClientHuman extends Fighter {
	private int currentX;
	private int currentY;
	private int newX;
	private int newY;
	private int money;

	public ClientHuman(int currentX, int currentY, int hitPoints, int damage, int initiative, int attack,
			int armor) {
		super(hitPoints, damage, initiative, attack, armor);
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
	}
	
	public ClientHuman(StringMap sm) {
		super(sm);
		currentX = ((Double) sm.get("currentX")).intValue();
		currentY = ((Double) sm.get("currentY")).intValue();
		newX = ((Double) sm.get("newX")).intValue();
		newY = ((Double) sm.get("newY")).intValue();
		money = ((Double) sm.get("money")).intValue();
	}

	public Point getPos() {
		return new Point(currentX, currentY);
	}

	public void setNewY(int newY) {
		this.newY = newY;
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

	public int getMoney() {
		return money;
	}
}
