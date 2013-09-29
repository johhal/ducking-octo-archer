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
	private ArrayList<Zombie> zombies;
	private ArrayList<House> houses;
	private ArrayList<Human> humans;
	private int sizeX, sizeY = 0;
	private ArrayList<ArrayList<Tile>> tiles;
	private ActionListener listener;
	private boolean locked = false;

	public synchronized void update(ProtocolMessage pm) {
		while(locked){
			try{
				wait();
			} catch(InterruptedException e){
				e.printStackTrace();
			}
		}
		locked = true;
		ArrayList<Parameter> pmList = pm.getParameterList();
		for (Parameter p : pmList) {
			if (p.getParameterType() == ProtocolEnum.PARAMETER_TYPE.ZOMBIES)
				setZombies((ArrayList<Zombie>) p.getData());
			if (p.getParameterType() == ProtocolEnum.PARAMETER_TYPE.HOUSES) {
				ArrayList<StringMap> tempHouses = (ArrayList<StringMap>) p
						.getData();
				houses = new ArrayList<House>();

				for (StringMap al : tempHouses)
					houses.add(new House(al));
			}
			if (p.getParameterType() == ProtocolEnum.PARAMETER_TYPE.HUMANS) {
				ArrayList<StringMap> tempHumans = (ArrayList<StringMap>) p
						.getData();
				humans = new ArrayList<Human>();
				for (StringMap sm : tempHumans) {
					humans.add(new Human(sm));
				}
			}
			
			if (p.getParameterType() == ProtocolEnum.PARAMETER_TYPE.ZOMBIES) {
				ArrayList<StringMap> tempZombies = (ArrayList<StringMap>) p
						.getData();
				zombies = new ArrayList<Zombie>();
				for (StringMap sm : tempZombies) {
					zombies.add(new Zombie(sm));
				}
			}

			if (p.getParameterType() == ProtocolEnum.PARAMETER_TYPE.TILES) {
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
			locked = false;
			notifyAll();
		}

		/*
		 * if(listener != null){ listener.actionPerformed(new ActionEvent(this,
		 * 0, "DRAW")); }
		 */
	}

	public GUIModel() {
		zombies = new ArrayList<Zombie>();
		houses = new ArrayList<House>();
		humans = new ArrayList<Human>();
		tiles = new ArrayList<ArrayList<Tile>>();
		// listener = new ActionListener();
	}

	public void addActionListener(ActionListener listener) {
		this.listener = listener;
	}

	private void setZombies(ArrayList<Zombie> zombies) {
		this.zombies = zombies;
	}

	private void setHouses(ArrayList<House> houses) {
		this.houses = houses;
	}

	private void setHumans(ArrayList<Human> humans) {
		this.humans = humans;
	}

	public synchronized ArrayList<Zombie> getZombies() {
		while(locked){
			try{
				wait();
			} catch(InterruptedException e){
				e.printStackTrace();
			}
		}
		return zombies;
	}

	public synchronized ArrayList<House> getHouses() {
		while(locked){
			try{
				wait();
			} catch(InterruptedException e){
				e.printStackTrace();
			}
		}
		return houses;
	}

	public synchronized ArrayList<Human> getHumans() {
		while(locked){
			try{
				wait();
			} catch(InterruptedException e){
				e.printStackTrace();
			}
		}
		return humans;
	}

	public synchronized ArrayList<ArrayList<Tile>> getTiles() {
		while(locked){
			try{
				wait();
			} catch(InterruptedException e){
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
}
