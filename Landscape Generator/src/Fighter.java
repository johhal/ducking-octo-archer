public class Fighter {
	private int currentHitPoints;
	private int maxHitPoints;
	private int damage;
	private int initiative;

	public Fighter(int hitPoints, int damage, int initiative) {
		this.currentHitPoints = hitPoints;
		this.maxHitPoints = hitPoints;
		this.damage = damage;
		this.initiative = initiative;
	}

	public int getCurrentHitpoints() {
		return currentHitPoints;
	}
	public int getMaxHitpoints(){
		return maxHitPoints;
	}

	private int hit(int damage) {
		currentHitPoints -= damage;
		return currentHitPoints;
	}

	private int getDamage() {
		return (int) (Math.random() * damage);
	}

	public int rollInitiative() {
		return (int) (Math.random() * initiative);
	}
	public void sethitPoints(int hitPoints){
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
		
		if(myInit>otherInit){
			while(getCurrentHitpoints()>0 && f.getCurrentHitpoints()>0){
				if(f.hit(getDamage())>0){
					hit(f.getDamage());
				}
				System.out.println("hp left: "+ getCurrentHitpoints()+" f: "+f.getCurrentHitpoints());
			}
		}else{
			while(getCurrentHitpoints()>0 && f.getCurrentHitpoints()>0){
				if(hit(f.getDamage())>0){
					f.hit(getDamage());
				}
				System.out.println("hp left: "+ getCurrentHitpoints()+" f: "+f.getCurrentHitpoints());
			}
		}

	}
}
