package Server;

import com.google.gson.internal.StringMap;

public class Fighter {
	private int currentHitPoints;
	private int maxHitPoints;
	private int damage;
	private int attack;
	private int armor;
	private int initiative;
	private int experience;
	private int level;

	public Fighter(int hitPoints, int damage, int initiative, int attack,
			int armor) {
		this.currentHitPoints = hitPoints;
		this.maxHitPoints = hitPoints;
		this.damage = damage;
		this.initiative = initiative;
		this.attack = attack;
		this.armor = armor;
		level = 1;
		experience = 0;
	}
	public Fighter(StringMap sm){
		currentHitPoints =((Double)sm.get("currentHitPoints")).intValue();
		maxHitPoints = ((Double)sm.get("maxHitPoints")).intValue();
		damage = ((Double)sm.get("damage")).intValue();
		attack = ((Double)sm.get("attack")).intValue();
		armor = ((Double)sm.get("armor")).intValue();
		initiative =((Double)sm.get("initiative")).intValue();
	}

	private void levelUp(){
		if(level<10){
			level++;
			int newHp = ((int)Math.random())*5+1;
			currentHitPoints+=newHp;
			maxHitPoints += newHp;
			damage+=1;
			initiative+=1;
			attack+=1;
			armor+=1;
			experience=0;
		}
		System.out.println("Leveled up!");
		
	}
	
	public void giveExperience(int xp){
		experience+=xp;
		if(experience>=((int)Math.pow(2, level))*100){
			levelUp();
		}
	}
	
	public int getAttack() {
		return attack;
	}

	public int getArmor() {
		return armor;
	}

	public int getCurrentHitpoints() {
		return currentHitPoints;
	}

	public int getMaxHitpoints() {
		return maxHitPoints;
	}

	private int hit(int damage, int attackRoll) {
		if (attackRoll >= armor) {
			currentHitPoints -= damage;
		}
		return currentHitPoints;
	}

	public void setArmor(int armor) {
		this.armor = armor;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}

	public int damageRoll() {
		return (int) (Math.random() * damage + 1);
	}

	public int getDamage() {
		return damage;
	}

	public int attackRoll() {
		return (attack + (int) (Math.random() * 20));
	}

	public int rollInitiative() {
		return (initiative + (int) (Math.random() * 20));
	}

	public void setHitPoints(int hitPoints) {
		this.currentHitPoints = hitPoints;
		this.maxHitPoints = hitPoints;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public void setInitiative(int initiative) {
		this.initiative = initiative;
	}

	public void fight(Fighter f) {
		int myInit;
		int otherInit;
		do {
			myInit = rollInitiative();
			otherInit = f.rollInitiative();
		} while (myInit != otherInit);

		if (myInit > otherInit) {
			while (getCurrentHitpoints() > 0 && f.getCurrentHitpoints() > 0) {
				if (f.hit(damageRoll(), attackRoll()) > 0) {
					hit(f.damageRoll(), attackRoll());
				}
			}
		} else {
			while (getCurrentHitpoints() > 0 && f.getCurrentHitpoints() > 0) {
				if (hit(f.damageRoll(), attackRoll()) > 0) {
					f.hit(damageRoll(), attackRoll());
				}
			}
		}

	}
}
