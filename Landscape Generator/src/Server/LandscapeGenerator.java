package Server;

public class LandscapeGenerator {
	public LandscapeGenerator(){
		
	}
	public Tile[][] generate(int boardWidth, int boardHeight){
		Tile[][] landscape = new Tile[boardWidth][boardHeight];
		for(int i=0;i<boardWidth;i++){
			for(int j=0;j<boardHeight;j++){
				
				landscape[i][j] = randomTile(landscape,i,j);
			}
		}
		return landscape;
	}
	private Tile randomTile(Tile[][] landscape, int tilePosX, int tilePosY) {
		Tile tile = new Tile();
		Tile westTile = null;
		Tile northTile = null;
		if(tilePosX>1){
			westTile = landscape[tilePosX-1][tilePosY];
		}
		if(tilePosY>1){
			northTile = landscape[tilePosX][tilePosY-1];
		}
		tile.randomizeType(westTile, northTile);
		return tile;
	}
}