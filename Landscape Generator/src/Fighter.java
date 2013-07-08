public class Fighter {
	private int currentHitPoints;
	private int maxHitPoints;
	private int damage;
	private int attack;
	private int armor;
	private int initiative;

	public Fighter(int hitPoints, int damage, int initiative, int attack,
			int armor) {
		this.currentHitPoints = hitPoints;
		this.maxHitPoints = hitPoints;
		this.damage = damage;
		this.initiative = initiative;
		this.attack = attack;
		this.armor = armor;
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
		} else {
			System.out.println("Missed with attackroll: " + attackRoll
					+ " vs. armor: " + armor);
		}
		return currentHitPoints;
	}

	public void setArmor(int armor) {
		this.armor = armor;
	}
	public void setAttack(int attack){
		this.attack = attack;
	}

	private int getDamage() {
		return (int) (Math.random() * damage+1);
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
				if (f.hit(getDamage(), attackRoll()) > 0) {
					hit(f.getDamage(), attackRoll());
				}
			}
		} else {
			while (getCurrentHitpoints() > 0 && f.getCurrentHitpoints() > 0) {
				if (hit(f.getDamage(), attackRoll()) > 0) {
					f.hit(getDamage(), attackRoll());
				}
			}
		}

	}
}
