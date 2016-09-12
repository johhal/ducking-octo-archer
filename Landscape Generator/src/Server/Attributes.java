package Server;

import java.util.Random;

public class Attributes {
	private int strength;
	private int dexterity;
	private int constitution;
	private int intelligence;
	private int wisdom;
	private int charisma;

	public Attributes(int strength, int dexterity, int constitution,
			int intelligence, int wisdom, int charisma) {
		this.strength = strength;
		this.dexterity = dexterity;
		this.constitution = constitution;
		this.intelligence = intelligence;
		this.wisdom = wisdom;
		this.charisma = charisma;
	}

	public Attributes() {
		rollAttributes();
	}

	private void rollAttributes() {
		strength = highestOfThree(6);
		dexterity = highestOfThree(6);
		constitution = highestOfThree(6);
		intelligence = highestOfThree(6);
		wisdom = highestOfThree(6);
		charisma = highestOfThree(6);
	}

	private int highestOfThree(int dice) {
		int rolls = 0;
		Random r = new Random();

		for (int j = 0; j < 3; j++) {
			int value = 0;
			for (int i = 0; i < 3; i++) {
				value += r.nextInt(dice) + 1;
			}
			if(value> rolls){
				rolls = value;
			}
		}
		return rolls;
	}

	public void setStrength(int strength) {
		this.strength = strength;
	}

	public void setDexterity(int dexterity) {
		this.dexterity = dexterity;
	}

	public void setConstitution(int constitution) {
		this.constitution = constitution;
	}

	public void setIntelligence(int intelligence) {
		this.intelligence = intelligence;
	}

	public void setWisdom(int wisdom) {
		this.wisdom = wisdom;
	}

	public void setCharisma(int charisma) {
		this.charisma = charisma;
	}

	public int getStrength() {
		return strength;
	}

	public int getDexterity() {
		return dexterity;
	}

	public int getConstitution() {
		return constitution;
	}

	public int getIntelligence() {
		return intelligence;
	}

	public int getWisdom() {
		return wisdom;
	}

	public int getCharisma() {
		return charisma;
	}
	
	public int getStrengthModifier() {
		return getModifier(strength);
	}

	public int getDexterityModifier() {
		return getModifier(dexterity);
	}

	public int getConstitutionModifier() {
		return getModifier(constitution);
	}

	public int getIntelligenceModifier() {
		return getModifier(intelligence);
	}

	public int getWisdomModifier() {
		return getModifier(wisdom);
	}

	public int getCharismaModifier() {
		return getModifier(charisma);
	}
	private int getModifier(int attribute){
		return (int) (attribute/2 - 5);
	}
	
	public void decreaseStrength(int amount) {
		strength = decreaseAttribute(strength, amount);
	}

	public void decreaseDexterity(int amount) {
		dexterity = decreaseAttribute(dexterity, amount);
	}

	public void decreaseConstitution(int amount) {
		constitution = decreaseAttribute(constitution, amount);
	}

	public void decreaseIntelligence(int amount) {
		intelligence = decreaseAttribute(intelligence, amount);
	}

	public void decreaseWisdom(int amount) {
		wisdom = decreaseAttribute(wisdom, amount);
	}

	public void decreaseCharisma(int amount) {
		charisma = decreaseAttribute(charisma, amount);
	}

	private int decreaseAttribute(int attributes,int amount){
		if(attributes - amount <=0){
			return 1;
		}else{
			return attributes - amount;
		}
	}

	public void increaseStrength(int amount) {
		strength+=amount;
	}

	public void increaseConstitution(int amount) {
		constitution += amount;
		
	}
	
}
