package Server;

import java.util.ArrayList;
import java.util.Random;

public class CharacterSheet {
	
	private int currentHitPoints;
	private int maxHitPoints;
	private int damageRoll;
	private int damageBonus;
	private int attackBonus;
	private int armor;
	private int initiative;
	private int experience;
	private int level;
	
	private Attributes attributes;
	private ArrayList<Skill> skills;
	private ArrayList<Feat> feats;
	
	public CharacterSheet(){
		attributes = new Attributes();
		skills = new ArrayList<Skill>();
		feats = new ArrayList<Feat>();
		
		generateStats();
	}

	private void generateStats() {
		Random r = new Random();
		
		maxHitPoints = r.nextInt(6)+1+attributes.getConstitutionModifier();
		currentHitPoints = maxHitPoints;
		damageRoll = 4;
		damageBonus = attributes.getStrengthModifier();
		
		attackBonus = attributes.getDexterityModifier();
		armor = 10 + attributes.getDexterityModifier();
		initiative = attributes.getDexterityModifier();
		experience = 0;
		level = 1;
	}
	
	private void levelUp(){
		if(level<10){
			level++;
			int newHp = ((int)Math.random())*6+attributes.getConstitutionModifier();
			currentHitPoints+=newHp;
			maxHitPoints += newHp;
			damageBonus+=1;
			initiative+=1;
			attackBonus+=1;
			armor+=1;
			experience=0;
		}
		System.out.println("Leveled up!");
		
	}
	
	public ArrayList<Skill> getSkills() {
		return skills;
	}
	
	public ArrayList<Feat> getFeats() {
		return feats;
	}
	
	public int getDamageRoll(){
		return damageRoll;
	}
	
	public void setDamageRoll(int damageRoll){
		this.damageRoll = damageRoll;
	}
	
	public void giveExperience(int xp){
		experience+=xp;
		if(experience>=((int)Math.pow(2, level))*100){
			levelUp();
		}
	}
	
	public int getAttack() {
		return attackBonus;
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

	public void setArmor(int armor) {
		this.armor = armor;
	}

	public void setAttack(int attack) {
		this.attackBonus = attack;
	}

	public int getDamageBonus() {
		return damageBonus;
	}

	public int rollInitiative() {
		return (initiative + (int) (Math.random() * 20));
	}

	public void setHitPoints(int hitPoints) {
		this.currentHitPoints = hitPoints;
		this.maxHitPoints = hitPoints;
	}

	public void setDamage(int damage) {
		this.damageBonus = damage;
	}

	public void setInitiative(int initiative) {
		this.initiative = initiative;
	}

	public int getInitiative(){
		return initiative;
	}

	public void decreaseCurrentHitPoints(int damage) {
		currentHitPoints -=damage;
	}
	public int attackRoll(){
		Random r = new Random();
		return r.nextInt(20)+1+attackBonus;
	}
	public int damageRoll(){
		Random r = new Random();
		return r.nextInt(damageRoll)+1+damageBonus;
	}

	public void zombify() {
		attributes.decreaseCharisma(10);
		attributes.decreaseDexterity(4);
		attributes.decreaseWisdom(5);
		attributes.decreaseIntelligence(10);
		attributes.increaseStrength(5);
		attributes.increaseConstitution(5);
		
		// TODO Auto-generated method stub
		
	}
	
	
}
