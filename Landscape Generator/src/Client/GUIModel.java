package Client;

import java.util.ArrayList;

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
	private Tile[][] tiles;

	public void update(ProtocolMessage pm) {
		ArrayList<Parameter> pmList = pm.getParameterList();
		for (Parameter p : pmList) {
			if(p.getParameterType() == ProtocolEnum.PARAMETER_TYPE.ZOMBIES)
				setZombies((ArrayList<Zombie>)p.getData());
			if(p.getParameterType() == ProtocolEnum.PARAMETER_TYPE.HOUSES)
				setHouses((ArrayList<House>)p.getData());
			if(p.getParameterType() == ProtocolEnum.PARAMETER_TYPE.HUMANS)
				setHumans((ArrayList<Human>)p.getData());
			if(p.getParameterType() == ProtocolEnum.PARAMETER_TYPE.TILES)
				setTiles((Tile[][])p.getData());	
		}	
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

	private void setTiles(Tile[][] tiles) {
		this.tiles = tiles;
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

	public Tile[][] getTiles() {
		return tiles;
	}
	
}
