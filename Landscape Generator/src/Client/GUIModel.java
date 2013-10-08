package Client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import com.google.gson.internal.StringMap;

import network.Parameter;
import network.ProtocolEnum;
import network.ProtocolMessage;

import Server.House;
import Server.Human;
import Server.Tile;
import Server.Zombie;

public class GUIModel {
	private ArrayList<ClientZombie> zombies;
	private ArrayList<ClientHouse> houses;
	private ArrayList<ClientHuman> humans;
	private int sizeX, sizeY = 0;
	private ArrayList<ArrayList<Tile>> tiles;
	private ActionListener listener;
	private boolean locked = false;
	private int money;
	private double cycle;
	private String timeOfDay;

	public synchronized void update(ProtocolMessage pm) {
		while (locked) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		locked = true;
		ArrayList<Parameter> pmList = pm.getParameterList();
		for (Parameter p : pmList) {
			if (p.getParameterType() == ProtocolEnum.PARAMETER_TYPE.HOUSES) {
				updateHouses(p);
			}
			if (p.getParameterType() == ProtocolEnum.PARAMETER_TYPE.HUMANS) {
				updateHumans(p);
			}

			if (p.getParameterType() == ProtocolEnum.PARAMETER_TYPE.ZOMBIES) {
				updateZombies(p);
			}

			if (p.getParameterType() == ProtocolEnum.PARAMETER_TYPE.TILES) {
				updateTiles(p);
			}
			if (p.getParameterType() == ProtocolEnum.PARAMETER_TYPE.MONEY) {
				updateMoney(p);
			}
			if (p.getParameterType() == ProtocolEnum.PARAMETER_TYPE.CYCLE) {
				updateCycle(p);
			}
			if (p.getParameterType() == ProtocolEnum.PARAMETER_TYPE.TIME_OF_DAY) {
				updateTimeOfDay(p);
			}
			
			
			
			locked = false;
			notifyAll();
		}

		/*
		 * if(listener != null){ listener.actionPerformed(new ActionEvent(this,
		 * 0, "DRAW")); }
		 */
	}

	private void updateTimeOfDay(Parameter p) {
		timeOfDay = (String) p.getData();
	}

	private void updateCycle(Parameter p) {
		cycle = (Double) p.getData();
	}

	private void updateMoney(Parameter p) {
		money = ((Double) p.getData()).intValue();
	}

	private void updateTiles(Parameter p) {
		ArrayList<ArrayList<StringMap>> temp = (ArrayList<ArrayList<StringMap>>) p
				.getData();

		tiles = new ArrayList<ArrayList<Tile>>();
		int i = 0;
		for (ArrayList<StringMap> al : temp) {
			tiles.add(new ArrayList<Tile>());
			for (StringMap m : al) {
				tiles.get(i).add(new Tile(m));
			}
			i++;
		}
	}

	private void updateZombies(Parameter p) {
		ArrayList<StringMap> tempZombies = (ArrayList<StringMap>) p
				.getData();
		zombies = new ArrayList<ClientZombie>();
		for (StringMap sm : tempZombies) {
			zombies.add(new ClientZombie(sm));
		}
	}

	private void updateHumans(Parameter p) {
		ArrayList<StringMap> tempHumans = (ArrayList<StringMap>) p
				.getData();
		humans = new ArrayList<ClientHuman>();
		for (StringMap sm : tempHumans) {
			humans.add(new ClientHuman(sm));
		}
	}

	private void updateHouses(Parameter p) {
		ArrayList<StringMap> tempHouses = (ArrayList<StringMap>) p
				.getData();
		houses = new ArrayList<ClientHouse>();

		for (StringMap al : tempHouses)
			houses.add(new ClientHouse(al));
	}

	public GUIModel() {
		zombies = new ArrayList<ClientZombie>();
		houses = new ArrayList<ClientHouse>();
		humans = new ArrayList<ClientHuman>();
		tiles = new ArrayList<ArrayList<Tile>>();
		money = 0;
		// listener = new ActionListener();
	}

	public void addActionListener(ActionListener listener) {
		this.listener = listener;
	}

	private void setZombies(ArrayList<ClientZombie> zombies) {
		this.zombies = zombies;
	}

	private void setHouses(ArrayList<ClientHouse> houses) {
		this.houses = houses;
	}

	private void setHumans(ArrayList<ClientHuman> humans) {
		this.humans = humans;
	}

	public synchronized ArrayList<ClientZombie> getZombies() {
		while (locked) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return zombies;
	}

	public synchronized ArrayList<ClientHouse> getHouses() {
		while (locked) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return houses;
	}

	public synchronized ArrayList<ClientHuman> getHumans() {
		while (locked) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return humans;
	}

	public synchronized ArrayList<ArrayList<Tile>> getTiles() {
		while (locked) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return tiles;
	}

	public int getSizeX() {
		return sizeX;
	}

	public void setSizeX(int sizeX) {
		this.sizeX = sizeX;
	}

	public int getSizeY() {
		return sizeY;
	}

	public void setSizeY(int sizeY) {
		this.sizeY = sizeY;
	}

	public int getMoney() {
		return money;
	}

	public void removeMoney(int money) {
		this.money -= money;

	}
}
