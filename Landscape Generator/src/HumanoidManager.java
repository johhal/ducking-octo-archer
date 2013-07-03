import java.awt.Point;
import java.util.ArrayList;

public class HumanoidManager {

	private ArrayList<Zombie> zombies;
	private ArrayList<Human> humans;
	private ArrayList<House> houses;
	private Landscape landscape;

	public void Initialize(Landscape lAndscape) {
		this.landscape = lAndscape;
		zombies = new ArrayList<Zombie>();
		humans = new ArrayList<Human>();
		houses = new ArrayList<House>();
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
		// Sätt ut zombies? zombiechans?
		zombies.add(new Zombie(xpos, ypos, landscape));
		return true;
	}

	public boolean addHuman(int xpos, int ypos) {
		humans.add(new Human(xpos, ypos, landscape));
		return true;
	}

	public boolean addHouse(int xpos, int ypos) {
		houses.add(new House(xpos, ypos, landscape));
		return true;
	}

	public boolean removeZombie() {
		return true;
	}

	public void Update() {
		for (int i = 0; i < zombies.size(); i++) {
			zombies.get(i).Update();
		}
		while (zombieKillingSpree())
			;

		for (int i = 0; i < humans.size(); i++) {
			humans.get(i).Update();
			if (humans.get(i).getFertility() >= 100) {
				Point pos = landscape.getNearbyAvailableLocation(humans.get(i)
						.getPos().x, humans.get(i).getPos().y);
				if (pos != null) {
					addHuman(pos.x, pos.y);
				}
				humans.get(i).setFertility(0);
			}
		}
		for (int i = 0; i < houses.size(); i++) {
			houses.get(i).Update();
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

	private boolean zombieKillingSpree() {
		for (int i = 0; i < zombies.size(); i++) {
			for (int j = 0; j < humans.size(); j++) {
				if (zombies.get(i).getPos().equals(humans.get(j).getPos())) {
					if (!humans.get(j).isWarrior()) {
						humans.get(j).kill();
						Point pos = landscape.getNearbyAvailableLocation(
								zombies.get(i).getPos().x, zombies.get(i)
										.getPos().y);
						if (pos != null) {
							addZombie(pos.x, pos.y);
						}
						humans.remove(j);
					}else{
						if(Math.random()<0.9){
							zombies.get(i).kill();
							zombies.remove(i);
							System.out.println("A ZOMBIE HAS BEEN SLAYED BY A GREAT WARRIOR!!!");
						}
					}
					return true;

				}
			}
		}
		return false;
	}

	public void Draw() {

	}
}
