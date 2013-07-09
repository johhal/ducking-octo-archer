import java.awt.Point;
import java.util.ArrayList;

public class HumanoidManager {

	private ArrayList<Zombie> zombies;
	private ArrayList<Human> humans;
	private ArrayList<House> houses;
	private Landscape landscape;
	private ImageViewer viewer;

	public void initialize(Landscape _landscape, ImageViewer viewer) {
		this.landscape = _landscape;
		this.viewer = viewer;
		
		//Initiera zombies + människor + hus
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
		// Sätt ut zombies?
		zombies.add(new Zombie(xpos, ypos, landscape, viewer));
		return true;
	}

	public boolean addHuman(int xpos, int ypos) {
		//Sätt ut människor
		humans.add(new Human(xpos, ypos, landscape, viewer));
		return true;
	}

	public boolean addHouse(int xpos, int ypos) {
		//sätt ut hus
		houses.add(new House(xpos, ypos, landscape, viewer));
		return true;
	}

	private boolean zombieKillingSpree() {
		//Skapa nya zombies och kolla om de står på en människa, isf skapa ny zombie.
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
		}
		while (zombieKillingSpree())
			;
		
		//Uppdatera Människor
		for(Human h: humans){
			h.update();
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
				houses.get(i).setFertility(-100);
			}
		}
	}


	public void draw(int tileSize) {
		for(Zombie z: zombies){
			z.draw(tileSize);
		}
		for(Human h: humans){
			h.draw(tileSize);
		}
		for(House h: houses){
			h.draw(tileSize);
		}
		viewer.redrawImage();
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
}
