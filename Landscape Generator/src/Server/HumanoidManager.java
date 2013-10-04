package Server;

import java.awt.Point;
import java.util.ArrayList;

public class HumanoidManager {

	private ArrayList<Zombie> zombies;
	private ArrayList<Human> humans;
	private ArrayList<House> houses;
	private Landscape landscape;
	
	private boolean houseUpdated;
	private boolean humanUpdated;
	private boolean zombieUpdated;

	public void initialize(Landscape _landscape) {
		this.landscape = _landscape;
		houseUpdated = true;
		humanUpdated = true;
		zombieUpdated = true;
		//Initiera zombies + m�nniskor + hus
		zombies = new ArrayList<Zombie>();
		humans = new ArrayList<Human>();
		houses = new ArrayList<House>();
		
		//Slumpa ut zombies
		for (int j = 0; j < landscape.getBoardHeight(); j++) {
			for (int i = 0; i < landscape.getBoardWidth(); i++) {
				double rnd = Math.random();
				Tile current = landscape.getTile(i, j);

				switch (current.getType()) {
				case FORREST:
					if (rnd < 0.01) {
						addZombie(i, j);
					}
					break;
				case PLAIN:
					if (rnd < 0.01) {
						addZombie(i, j);
					}
					break;
				case WATER:
					break;
				case MOUNTAIN:
					if (rnd < 0.01) {
						addZombie(i, j);
					}
					break;
				}
			}	
		}
		addHouse(25,25);
		addHuman(25,24);
	}

	public boolean addZombie(int xpos, int ypos) {
		// S�tt ut zombies?
		zombies.add(new Zombie(xpos, ypos, landscape));
		zombieUpdated = true;
		return true;
	}

	public boolean addHuman(int xpos, int ypos) {
		//S�tt ut m�nniskor
		humans.add(new Human(xpos, ypos, landscape));
		humanUpdated = true;
		return true;
	}

	public boolean addHouse(int xpos, int ypos) {
		//s�tt ut hus
		houses.add(new House(xpos, ypos, landscape));
		houseUpdated = true;
		return true;
	}

	private boolean zombieKillingSpree() {
		//Skapa nya zombies och kolla om de st�r p� en m�nniska, isf skapa ny zombie.
		for (int i = 0; i < zombies.size(); i++) {
			for (int j = 0; j < humans.size(); j++) {
				if (zombies.get(i).getPos().equals(humans.get(j).getPos())) {
					zombies.get(i).fight(humans.get(j));
					if(zombies.get(i).getCurrentHitpoints()<=0){
						zombies.get(i).kill();
						zombies.remove(i);
					}
					if(humans.get(j).getCurrentHitpoints()<=0){
						humans.get(j).kill();
						Point pos = landscape.getNearbyAvailableLocation(
								zombies.get(i).getPos().x, zombies.get(i)
										.getPos().y);
						if (pos != null) {
							addZombie(pos.x, pos.y);
						}
						humans.remove(j);
					}
					zombieUpdated= true;
					humanUpdated = true;
					return true;
				}
			}
		}
		for (int i = 0; i < zombies.size(); i++) {
			for (int j = 0; j < houses.size(); j++) {
				if (zombies.get(i).getPos().equals(houses.get(j).getPos())) {
					zombies.get(i).fight(houses.get(j));
					if(zombies.get(i).getCurrentHitpoints()<=0){
						zombies.get(i).kill();
						zombies.remove(i);
					}
					if(houses.get(j).getCurrentHitpoints()<=0){
						houses.get(j).kill();
						houses.remove(j);
					}
					zombieUpdated = true;
					houseUpdated = true;
					return true;
				}
			}
		}
		return false;
	}

	public void update() {
		//Uppdatera Zombies
		for(Zombie z: zombies){
			z.update();
			int newX = z.getNewX();
			int newY = z.getNewY();
			if(newX!=z.getCurrentX() || newY!=z.getCurrentY()){
				if(landscape.canMoveHuman(z.getCurrentX(), z.getCurrentY(), newX, newY)){
					z.setCurrentX(newX);
					z.setCurrentY(newY);
					setZombieUpdated(true);
				}
			}
		}
		while (zombieKillingSpree())
			;
		
		//Uppdatera M�nniskor
		for(Human h: humans){
			h.update();
			int newX = h.getNewX();
			int newY = h.getNewY();
			if(newX!=h.getCurrentX() || newY!=h.getCurrentY()){
				if(landscape.canMoveHuman(h.getCurrentX(), h.getCurrentY(), newX, newY)){
					h.setCurrentX(newX);
					h.setCurrentY(newY);
					setHumanUpdated(true);
				}
			}
		}
		
		//Uppdatera Hus
		for (int i = 0; i < houses.size(); i++) {
			houses.get(i).update();
			if (houses.get(i).getFertility() >= 100) {

				Point pos = landscape.getNearbyAvailableLocation(houses.get(i)
						.getPos().x, houses.get(i).getPos().y);
				if (pos != null) {
					addHuman(pos.x, pos.y);
				}
				houses.get(i).setFertility(0);
			}
		}
	}


//	public ArrayList<DrawingObject> draw() {
//		ArrayList<DrawingObject> dro = new ArrayList<DrawingObject>();
//		for(Zombie z: zombies){
//			dro.add(z.draw());
//		}
//		for(Human h: humans){
//			dro.add(h.draw());
//		}
//		for(House h: houses){
//			dro.add(h.draw());
//		}
//		return dro;
//	}

	public String humanoidToString(Point p) {
		StringBuilder sb = new StringBuilder();
		for(Zombie z: zombies){
			if(z.getPos().equals(p)){
				sb.append(z.toString());
			}
		}
		for(Human h: humans){
			if(h.getPos().equals(p)){
				sb.append(h.toString());
			}
		}
		for(House h: houses){
			if(h.getPos().equals(p)){
				sb.append(h.toString());
			}
		}
		return sb.toString();
	}
	public ArrayList<Human> getHumans(){
		return humans;
	}
	public ArrayList<Zombie> getZombies(){
		return zombies;
	}
	public ArrayList<House> getHouses(){
		return houses;
	}

	public boolean housesUpdated() {
		// TODO Auto-generated method stub
		return houseUpdated;
	}

	public boolean zombiesUpdated() {
		// TODO Auto-generated method stub
		return zombieUpdated;
	}
	public boolean humansUpdated() {
		// TODO Auto-generated method stub
		return humanUpdated;
	}

	public void setHouseUpdated(boolean houseUpdated) {
		this.houseUpdated = houseUpdated;
	}

	public void setHumanUpdated(boolean humanUpdated) {
		this.humanUpdated = humanUpdated;
	}

	public void setZombieUpdated(boolean zombieUpdated) {
		this.zombieUpdated = zombieUpdated;
	}
	
}
