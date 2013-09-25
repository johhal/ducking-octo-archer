package Client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import network.Parameter;
import network.ProtocolEnum;
import network.ProtocolMessage;

import Server.House;
import Server.Human;
import Server.Landscape;
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
			if(p.getParameterType() == ProtocolEnum.PARAMETER_TYPE.ZOMBIES)
				setZombies((ArrayList<Zombie>)p.getData());
			if(p.getParameterType() == ProtocolEnum.PARAMETER_TYPE.HOUSES)
				setHouses((ArrayList<House>)p.getData());
			if(p.getParameterType() == ProtocolEnum.PARAMETER_TYPE.HUMANS)
				setHumans((ArrayList<Human>)p.getData());
			if(p.getParameterType() == ProtocolEnum.PARAMETER_TYPE.TILES){
				tiles = (ArrayList<ArrayList<Tile>>)p.getData();
			}
		}
		if(listener != null){
			listener.actionPerformed(new ActionEvent(this, 0, "DRAW"));
		}
	}
	
	public void addActionListener(ActionListener listener){
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

	public Tile[][] getTiles() {
		Tile[][] tileList = new Tile[tiles.size()][tiles.get(0).size()];
		for(int i = 0; i < tiles.size(); i++){
			for(int j = 0; j< tiles.size(); j++){
				tileList[i][j] = tiles.get(i).get(j);
			}
		}
		return tileList;
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
