import java.awt.Point;
import java.awt.image.BufferedImage;

public class Landscape {
	private Tile[][] landscape;
	private int sizeX;
	private int sizeY;
	
	private int tileSize;
	private ImageViewer imageViewer;

	public Landscape(Tile[][] Landscape, int tileSize, ImageViewer imageViewer) {
		this.landscape = Landscape;
		this.tileSize = tileSize;

		sizeX = landscape.length;
		sizeY = landscape[0].length;

		this.imageViewer = imageViewer;
	}

	public Tile getTile(int x, int y) {
		if (x >= 0 && x < sizeX && y >= 0 && y < sizeY) {
			return landscape[x][y];
		}
		return null;
	}

	public int getTileSize() {
		return tileSize;
	}

	public boolean canMoveZombie(int oldX, int oldY, int newX,
			int newY) {
		if (validateMove(oldX, oldY, newX, newY) && !isWater(newX, newY)) {
			if (!landscape[newX][newY].isInfested()) {			
				return true;
			}
		}
		return false;
	}
	
	public int getBoardWidth() {
		return sizeX;
	}
	public int getBoardHeight() {
		return sizeY;
	}

	private boolean isWater(int x, int y) {
		if (x < 0 || x >= sizeX) {
			return false;
		}
		if (y < 0 || y >= sizeY) {
			return false;
		}

		return landscape[x][y].getType() == TypeEnum.WATER;
	}

	private boolean validateMove(int oldX, int oldY, int newX, int newY) {
		if (oldX < 0 || oldX >= sizeX) {
			return false;
		}
		if (oldY < 0 || oldY >= sizeY) {
			return false;
		}
		if (newX < 0 || newX >= sizeX) {
			return false;
		}
		if (newY < 0 || newY >= sizeY) {
			return false;
		}
		return true;
	}

	public BufferedImage getLandscapeImg() {
		int type = BufferedImage.TYPE_INT_ARGB;
		BufferedImage image = new BufferedImage(tileSize * sizeX, tileSize
				* sizeY, type);
		for (int i = 0; i < sizeX; i++) {
			for (int j = 0; j < sizeY; j++) {
				image.createGraphics().drawImage(
						getTile(i, j).getTileImage(tileSize, tileSize),
						i * tileSize, j * tileSize, null);
			}
		}
		return image;
	}

	public Point getNearbyAvailableLocation(int x, int y) {
		if (validateMove(0, 0, x, y) && !isWater(x, y)) {
			try{
				if (!landscape[x][y].isInfested() && validateMove(0, 0, x, y)
						&& !isWater(x, y) && !landscape[x][y].isInhabited()&&!landscape[x][y].hasHouse()) {
					return new Point(x,y);
				} else if (!landscape[x - 1][y].isInfested()
						&& validateMove(0, 0, x - 1, y) && !isWater(x - 1, y)
						&& !landscape[x - 1][y].isInhabited()) {
					return new Point(x-1,y);
				} else if (!landscape[x - 1][y - 1].isInfested()
						&& validateMove(0, 0, x - 1, y - 1)
						&& !isWater(x - 1, y - 1)
						&& !landscape[x - 1][y - 1].isInhabited()) {
					return new Point(x-1,y-1);
				} else if (!landscape[x - 1][y + 1].isInfested()
						&& validateMove(0, 0, x - 1, y + 1)
						&& !isWater(x - 1, y + 1)
						&& !landscape[x - 1][y + 1].isInhabited()) {
					return new Point(x-1,y+1);
				} else if (!landscape[x + 1][y].isInfested()
						&& validateMove(0, 0, x + 1, y) && !isWater(x + 1, y)
						&& !landscape[x + 1][y].isInhabited()) {
					return new Point(x+1,y);
				} else if (!landscape[x + 1][y + 1].isInfested()
						&& validateMove(0, 0, x + 1, y + 1)
						&& !isWater(x + 1, y + 1)
						&& !landscape[x + 1][y + 1].isInhabited()) {
					return new Point(x+1,y+1);
				} else if (!landscape[x + 1][y - 1].isInfested()
						&& validateMove(0, 0, x + 1, y - 1)
						&& !isWater(x + 1, y - 1)
						&& !landscape[x + 1][y - 1].isInhabited()) {
					return new Point(x+1,y-1);
				} else if (!landscape[x][y - 1].isInfested()
						&& validateMove(0, 0, x, y - 1) && !isWater(x, y - 1)
						&& !landscape[x][y - 1].isInhabited()) {
					return new Point(x,y-1);
				} else if (!landscape[x][y + 1].isInfested()
						&& validateMove(0, 0, x, y + 1) && !isWater(x, y + 1)
						&& !landscape[x][y + 1].isInhabited()) {
					return new Point(x,y+1);
				}
			}catch (ArrayIndexOutOfBoundsException e){
				return null;
			}
		}
		return null;
	}
}
