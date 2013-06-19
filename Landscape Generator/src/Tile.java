import java.awt.Color;
import java.awt.image.BufferedImage;

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
	private boolean containsZombies = false;
	private boolean inhabited = false;

	public Tile(TypeEnum type) {
		this.type = type;
	}

	public Tile() {
	}

	public void randomizeType() {
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
		} else if (rnd < plainThreshold) {
			type = TypeEnum.WATER;
		} else if (rnd < mountainThreshold) {
			type = TypeEnum.PLAIN;
		} else {
			type = TypeEnum.MOUNTAIN;
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

	public BufferedImage getTileImage(int x, int y) {

		BufferedImage image = new BufferedImage(x, y,
				BufferedImage.TYPE_INT_ARGB);
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				if (i == 0) {
					image.setRGB(i,j, Color.black.getRGB());
				} else if (j == 0) {
					image.setRGB(i,j, Color.black.getRGB());
				} else if (i == (x - 1)) {
					image.setRGB(i,j, Color.black.getRGB());
				} else if (j == (y - 1)) {
					image.setRGB(i,j, Color.black.getRGB());
				} else {
					image.setRGB(i, j, getRGBA());
				}
			}
		}

		return image;
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
		if (containsZombies) {
			return new Color(255, 0, 0).getRGB();
		}
		if (inhabited) {
			return new Color(255, 255, 255).getRGB();
		}
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
		if (containsZombies) {
			return "Z";
		}
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