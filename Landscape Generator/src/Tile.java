import java.awt.Color;

public class Tile {
	private TypeEnum type = null;
	private Tile northTile = null;
	private Tile westTile = null;
	private double forrestChance = 1;
	private double waterChance = 1;
	private double plainChance = 1;
	private double mountainChance = 1;
	private double forrestThreshold;
	private double waterThreshold;
	private double plainThreshold;
	private double mountainThreshold;

	public Tile(TypeEnum type) {
		this.type = type;
	}

	public Tile() {
	}

	public void randomizeType() {
		if (northTile != null) {
			switch (northTile.getType()) {
			case FORREST:
				forrestChance+=50;
				waterChance+=10;
				break;
			case PLAIN:
				plainChance+=20;
				mountainChance+=5;
				break;
			case MOUNTAIN:
				mountainChance+=20;
				plainChance+=5;
				break;
			case WATER:
				waterChance+=50;
				forrestChance+=10;
				break;
			}
		}
		if (westTile != null) {
			switch (westTile.getType()) {
			case FORREST:
				forrestChance+=80;
				waterChance+=10;
				break;
			case PLAIN:
				plainChance+=20;
				mountainChance+=5;
				break;
			case MOUNTAIN:
				mountainChance+=20;
				plainChance+=5;
				break;
			case WATER:
				waterChance+=50;
				forrestChance+=20;
				break;
			}
		}
		calculateThresholds();
		double rnd = Math.random();
		if(rnd<waterThreshold){
			type = TypeEnum.FORREST;
		}else if(rnd<plainThreshold){
			type = TypeEnum.WATER;
		}else if(rnd<mountainThreshold){
			type = TypeEnum.PLAIN;
		}else{
			type = TypeEnum.MOUNTAIN;
		}
	}

	private void calculateThresholds() {
		forrestThreshold=0;
		waterThreshold = forrestChance/(forrestChance + plainChance + mountainChance + waterChance);
		plainThreshold = waterChance/(forrestChance + plainChance + mountainChance + waterChance)+ waterThreshold;
		mountainThreshold = plainChance/(forrestChance + plainChance + mountainChance + waterChance)+plainThreshold;
//		System.out.println("Forrest="+forrestThreshold+"-"+waterThreshold);
//		System.out.println("Water="+waterThreshold+"-"+plainThreshold);
//		System.out.println("Plain="+plainThreshold+"-"+mountainThreshold);
//		System.out.println("Mountain="+mountainThreshold+"- 1");
	}

	public void setNorthTile(Tile tile) {
		northTile = tile;
	}

	public void setWestTile(Tile tile) {
		westTile = tile;
	}

	public TypeEnum getType() {
		return type;
	}

	public int getRGBA() {
		switch (type) {
		case FORREST:
			return new Color(0, 255, 0).getRGB();
		case PLAIN:
			return new Color(255, 255, 0).getRGB();
		case MOUNTAIN:
			return new Color(127, 127, 127).getRGB();
		case WATER:
			return new Color(0, 0, 255).getRGB();
		}
		return new Color(255, 255, 255).getRGB();
	}

	public String toString() {
		switch (type) {
		case FORREST:
			return "F";
		case PLAIN:
			return "P";
		case MOUNTAIN:
			return "M";
		case WATER:
			return "W";
		}
		return "#";
	}
}