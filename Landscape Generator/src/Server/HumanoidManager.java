package Server;

import java.awt.Point;
import java.util.ArrayList;

import Client.ClientHouse;
import Client.ClientHuman;
import Client.ClientZombie;

import network.Session;

public class HumanoidManager {

	private ArrayList<Zombie> zombies;
	private ArrayList<Human> humans;
	private ArrayList<House> houses;
	private Landscape landscape;

	private int moneyGeneratedThisTurn;
	
	private boolean houseUpdated;
	private boolean humanUpdated;
	private boolean zombieUpdated;

	public void initialize(Landscape _landscape) {
		moneyGeneratedThisTurn = 100;
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
	}

	public boolean addZombie(int xpos, int ypos) {
		// S�tt ut zombies?
		zombies.add(new Zombie(xpos, ypos));
		zombieUpdated = true;
		return true;
	}

	public boolean addHuman(int xpos, int ypos, Session owner) {
		//S�tt ut m�nniskor
		humans.add(new Human(xpos, ypos, owner));
		humanUpdated = true;
		return true;
	}

	public boolean addHouse(int xpos, int ypos, Session owner) {
		//s�tt ut hus
		houses.add(new House(xpos, ypos, owner));
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
						moneyGeneratedThisTurn+=((int)Math.random()*30+20);
						humans.get(j).giveExperience(50);
					}
					if(humans.get(j).getCurrentHitpoints()<=0){
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
			Tile t = landscape.getTile(z.getCurrentX(), z.getCurrentY());
			z.update(t);
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
			Tile t = landscape.getTile(h.getCurrentX(), h.getCurrentY());
			h.update(t);
//			moneyGeneratedThisTurn+=h.getMoney();
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
			Tile t = landscape.getTile(houses.get(i).getCurrentX(), houses.get(i).getCurrentY());
			houses.get(i).update(t);
			if (houses.get(i).getFertility() >= 100) {

				Point pos = landscape.getNearbyAvailableLocation(houses.get(i)
						.getPos().x, houses.get(i).getPos().y);
				if (pos != null) {
					addHuman(pos.x, pos.y, houses.get(i).getOwner());
				}
				houses.get(i).setFertility(0);
			}
		}
	}
	public int getMoneyGeneratedThisTurn(){
		int temp = moneyGeneratedThisTurn;
		moneyGeneratedThisTurn = 0;
		return temp;
	}

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
	
	public ArrayList<ClientHuman> getClientHumans(){
		ArrayList<ClientHuman> temp = new ArrayList<ClientHuman>();
		for(Human h: humans){
			temp.add(h.toClientHuman());
		}
		return temp;
	}
	public ArrayList<ClientZombie> getClientZombies(){
		ArrayList<ClientZombie> temp = new ArrayList<ClientZombie>();
		for(Zombie z: zombies){
			temp.add(z.toClientZombie());
		}
		return temp;
	}
	public ArrayList<ClientHouse> getClientHouses(){
		ArrayList<ClientHouse> temp = new ArrayList<ClientHouse>();
		for(House h: houses){
			temp.add(h.toClientHouse());
		}
		return temp;
	}
	

	public boolean housesUpdated() {
		return houseUpdated;
	}

	public boolean zombiesUpdated() {
		return zombieUpdated;
	}
	public boolean humansUpdated() {
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
