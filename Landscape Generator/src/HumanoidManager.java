import java.awt.Point;
import java.util.ArrayList;

public class HumanoidManager {

	
	private ArrayList<Zombie> zombies;
	private ArrayList<Human> humans;
	private Landscape landscape;

	public void Initialize(Landscape lAndscape) {
		this.landscape = lAndscape;
		zombies = new ArrayList<Zombie>();
		humans = new ArrayList<Human>();
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

	public boolean removeZombie() {
		return true;
	}

	public void Update() {
		for(int i = 0; i < zombies.size(); i++)
		{
			zombies.get(i).Update();
		}
		while(zombieKillingSpree());
		
		for(int i=0;i<humans.size();i++){
			humans.get(i).Update();
			if(humans.get(i).getFertility()>=100){
//				System.out.println("HUMANS ARE REPRODUCING!!!");
				Point pos = landscape.getNearbyAvailableLocation(humans.get(i).getPos().x, humans.get(i).getPos().y);
				addHuman(pos.x,pos.y);
				humans.get(i).setFertility(0);
			}
		}
		
//		System.out.println("Zombies: "+zombies.size());
//		System.out.println("Humans: "+humans.size());
		
	}

	private boolean zombieKillingSpree() {
		for(int i = 0; i<zombies.size();i++){
			for(int j = 0; j<humans.size();j++){
				if(zombies.get(i).getPos().equals(humans.get(j).getPos())){
					humans.get(j).kill();
					Point pos = landscape.getNearbyAvailableLocation(zombies.get(i).getPos().x, zombies.get(i).getPos().y);
					addZombie(pos.x,pos.y);
					humans.remove(j);
					return true;
				}
			}
		}
		return false;
	}

	public void Draw() {

	}
}
