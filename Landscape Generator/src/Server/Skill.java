package Server;

public class Skill {
	String skillName;
	int value;
	
	public Skill(String skillName, int value){
		this.skillName = skillName;
		this.value = value;
	}
	
	public String getSkillName(){
		return skillName;
	}
	public int getValue(){
		return value;
	}
}
