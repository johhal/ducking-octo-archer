package Server;

import java.awt.Color;
import java.awt.image.BufferedImage;

import com.google.gson.internal.StringMap;

public class Tile {
	private TypeEnum type = null;
	private double forrestChance = 1;
	private double waterChance = 1;
	private double plainChance = 1;
	private double mountainChance = 1;
	private double forrestThreshold;
	private double waterThreshold;
	private double plainThreshold;
	private double mountainThreshold;
	private boolean containsZombies = false;
	private boolean inhabited = false;
	private boolean hasHouse = false;
	private short texture;
	public Tile(TypeEnum type) {
		this.type = type;
	}

	public Tile(StringMap sm){
		type = stringToType((String)sm.get("type"));
		forrestChance = ((Double)sm.get("forrestChance")).doubleValue();
		mountainChance = ((Double)sm.get("mountainChance")).doubleValue();
		waterChance = ((Double)sm.get("waterChance")).doubleValue();
		plainChance = ((Double)sm.get("plainChance")).doubleValue();
		
		forrestThreshold = ((Double)sm.get("forrestThreshold")).doubleValue();
		waterThreshold = ((Double)sm.get("waterThreshold")).doubleValue();
		plainThreshold = ((Double)sm.get("plainThreshold")).doubleValue();
		mountainThreshold = ((Double)sm.get("mountainThreshold")).doubleValue();
		
		containsZombies = ((Boolean)sm.get("containsZombies"));
		inhabited = ((Boolean)sm.get("inhabited"));
		hasHouse = ((Boolean)sm.get("hasHouse"));
		
		texture = ((Double)sm.get("texture")).shortValue();
	}
	private TypeEnum stringToType(String s) {
		if(s.equals(TypeEnum.FORREST.toString())){
			return TypeEnum.FORREST;
		}
		if(s.equals(TypeEnum.PLAIN.toString())){
			return TypeEnum.PLAIN;
		}
		if(s.equals(TypeEnum.WATER.toString())){
			return TypeEnum.WATER;
		}
		if(s.equals(TypeEnum.MOUNTAIN.toString())){
			return TypeEnum.MOUNTAIN;
		}
		return TypeEnum.MOUNTAIN;
	}

	public Tile() {
	}

	public void randomizeType(Tile westTile, Tile northTile) {
		if (northTile != null) {
			switch (northTile.getType()) {
			case FORREST:
				forrestChance += 50;
				waterChance += 10;
				break;
			case PLAIN:
				plainChance += 20;
				mountainChance += 5;
				break;
			case MOUNTAIN:
				mountainChance += 20;
				plainChance += 5;
				break;
			case WATER:
				waterChance += 50;
				forrestChance += 10;
				break;
			}
		}
		if (westTile != null) {
			switch (westTile.getType()) {
			case FORREST:
				forrestChance += 5;
				waterChance += 0;
				break;
			case PLAIN:
				plainChance += 90;
				mountainChance += 15;
				break;
			case MOUNTAIN:
				mountainChance += 20;
				plainChance += 10;
				break;
			case WATER:
				waterChance += 5;
				forrestChance += 0;
				break;
			}
		}
		calculateThresholds();
		double rnd = Math.random();
		if (rnd < waterThreshold) {
			type = TypeEnum.FORREST;
			texture = 1;
		} else if (rnd < plainThreshold) {
			type = TypeEnum.WATER;
			texture = 0;
		} else if (rnd < mountainThreshold) {
			type = TypeEnum.PLAIN;
			texture = 2;
		} else {
			type = TypeEnum.MOUNTAIN;
			texture = 3;
		}
	}

	public void infest(boolean infest) {
		containsZombies = infest;
	}

	public void inhabited(boolean inhabit) {
		inhabited = inhabit;
	}

	public boolean isInfested() {
		return containsZombies;
	}

	public boolean isInhabited() {
		return inhabited;
	}

	private void calculateThresholds() {
		forrestThreshold = 0;
		waterThreshold = forrestChance
				/ (forrestChance + plainChance + mountainChance + waterChance);
		plainThreshold = waterChance
				/ (forrestChance + plainChance + mountainChance + waterChance)
				+ waterThreshold;
		mountainThreshold = plainChance
				/ (forrestChance + plainChance + mountainChance + waterChance)
				+ plainThreshold;
	}

	public TypeEnum getType() {
		return type;
	}
	public String toString(){
		switch (type){
		case FORREST:
			return "Forrest";
		case PLAIN:
			return "Plain";
		case MOUNTAIN:
			return "Mountain";
		case WATER:
			return "Water";
		}
		return "";
		
	}

	public boolean hasHouse() {
		return hasHouse;
	}

	public void buildHouse(boolean b) {
		hasHouse = b;
	}
	
	public DrawingObject draw(int x, int y)
	{
		short nt = 0;
		DrawingObject dro = new DrawingObject(x, y, texture, nt);
		return dro;
	}
}