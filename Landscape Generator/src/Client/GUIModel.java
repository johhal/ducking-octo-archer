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

	public void update(ProtocolMessage pm) {
		ArrayList<Parameter> pmList = pm.getParameterList();
		for (Parameter p : pmList) {
			if (p.getParameterType() == ProtocolEnum.PARAMETER_TYPE.ZOMBIES)
				setZombies((ArrayList<Zombie>) p.getData());
			if (p.getParameterType() == ProtocolEnum.PARAMETER_TYPE.HOUSES)
			{
				ArrayList<StringMap> temp = (ArrayList<StringMap>) p.getData();
				houses = new ArrayList<House>();
				
				for(StringMap al: temp)
					houses.add(new House(al));
				//setHouses((ArrayList<House>) p.getData());
			}
			if (p.getParameterType() == ProtocolEnum.PARAMETER_TYPE.HUMANS)
				setHumans((ArrayList<Human>) p.getData());
			if (p.getParameterType() == ProtocolEnum.PARAMETER_TYPE.TILES) {
				ArrayList<ArrayList<StringMap>> temp = (ArrayList<ArrayList<StringMap>>) p.getData();
				tiles = new ArrayList<ArrayList<Tile>>();
				int i = 0;
				for(ArrayList<StringMap> al: temp){
					tiles.add(new ArrayList<Tile>());
					for(StringMap m: al){
						tiles.get(i).add(new Tile(m));
					}
					i++;
				}
			}
		}
		/*
		 * if(listener != null){ listener.actionPerformed(new ActionEvent(this,
		 * 0, "DRAW")); }
		 */
	}

	public GUIModel()
	{
		zombies = new ArrayList<Zombie>();
		houses = new ArrayList<House>();
		humans = new ArrayList<Human>();
		tiles = new ArrayList<ArrayList<Tile>>();
		//listener = new ActionListener();
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

	public ArrayList<Zombie> getZombies() {
		return zombies;
	}

	public ArrayList<House> getHouses() {
		return houses;
	}

	public ArrayList<Human> getHumans() {
		return humans;
	}

	public ArrayList<ArrayList<Tile>> getTiles() {
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
