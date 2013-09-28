package Server;

import java.awt.Point;
import java.util.ArrayList;


public class Landscape {
	private Tile[][] landscape;
	private int sizeX;
	private int sizeY;
	
	public Landscape(Tile[][] Landscape) {
		this.landscape = Landscape;

		sizeX = landscape.length;
		sizeY = landscape[0].length;
	}
	public Landscape(ArrayList<Tile> tiles, int sizeX, int sizeY){
		landscape = new Tile[sizeX][sizeY];
		for(int x = 0; x<sizeX; x++){
			for(int y = 0; y<sizeY; y++){
				landscape[x][y] = tiles.get(x+ x*y);
			}
		}
	}

	public Tile getTile(int x, int y) {
		if (x >= 0 && x < sizeX && y >= 0 && y < sizeY) {
			return landscape[x][y];
		}
		return null;
	}

	public Tile[][] getTiles()
	{
		return landscape;
	}
	public void setTiles(Tile[][] tiles){
		this.landscape = tiles;
	}

	public boolean canMoveZombie(int oldX, int oldY, int newX, int newY) {
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

	public Point getNearbyAvailableLocation(int x, int y) {
		if (validateMove(0, 0, x, y) && !isWater(x, y)) {
			try {
				if (!landscape[x][y].isInfested() && validateMove(0, 0, x, y)
						&& !isWater(x, y) && !landscape[x][y].isInhabited()
						&& !landscape[x][y].hasHouse()) {
					return new Point(x, y);
				} else if (!landscape[x - 1][y].isInfested()
						&& validateMove(0, 0, x - 1, y) && !isWater(x - 1, y)
						&& !landscape[x - 1][y].isInhabited()) {
					return new Point(x - 1, y);
				} else if (!landscape[x - 1][y - 1].isInfested()
						&& validateMove(0, 0, x - 1, y - 1)
						&& !isWater(x - 1, y - 1)
						&& !landscape[x - 1][y - 1].isInhabited()) {
					return new Point(x - 1, y - 1);
				} else if (!landscape[x - 1][y + 1].isInfested()
						&& validateMove(0, 0, x - 1, y + 1)
						&& !isWater(x - 1, y + 1)
						&& !landscape[x - 1][y + 1].isInhabited()) {
					return new Point(x - 1, y + 1);
				} else if (!landscape[x + 1][y].isInfested()
						&& validateMove(0, 0, x + 1, y) && !isWater(x + 1, y)
						&& !landscape[x + 1][y].isInhabited()) {
					return new Point(x + 1, y);
				} else if (!landscape[x + 1][y + 1].isInfested()
						&& validateMove(0, 0, x + 1, y + 1)
						&& !isWater(x + 1, y + 1)
						&& !landscape[x + 1][y + 1].isInhabited()) {
					return new Point(x + 1, y + 1);
				} else if (!landscape[x + 1][y - 1].isInfested()
						&& validateMove(0, 0, x + 1, y - 1)
						&& !isWater(x + 1, y - 1)
						&& !landscape[x + 1][y - 1].isInhabited()) {
					return new Point(x + 1, y - 1);
				} else if (!landscape[x][y - 1].isInfested()
						&& validateMove(0, 0, x, y - 1) && !isWater(x, y - 1)
						&& !landscape[x][y - 1].isInhabited()) {
					return new Point(x, y - 1);
				} else if (!landscape[x][y + 1].isInfested()
						&& validateMove(0, 0, x, y + 1) && !isWater(x, y + 1)
						&& !landscape[x][y + 1].isInhabited()) {
					return new Point(x, y + 1);
				}
			} catch (ArrayIndexOutOfBoundsException e) {
				return null;
			}
		}
		return null;
	}

	public boolean canMoveHuman(int oldX, int oldY, int newX, int newY) {
		if (validateMove(oldX, oldY, newX, newY) && !isWater(newX, newY)) {
			return true;
		}
		return false;
	}
	
//	public ArrayList<DrawingObject> draw()
//	{
//		ArrayList<DrawingObject> dro = new ArrayList<DrawingObject>();
//		for(int i = 0; i < landscape.length; i++)
//		{
//			for(int j = 0; j < landscape[0].length; j++){
//				dro.add(landscape[i][j].draw(i, j));
//			}
//		}
//		return dro;
//	}
}
