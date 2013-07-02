
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
		if(tilePosX>1){
			tile.setWestTile(landscape[tilePosX-1][tilePosY]);
		}
		if(tilePosY>1){
			tile.setNorthTile(landscape[tilePosX][tilePosY-1]);
		}
		tile.randomizeType();
		return tile;
	}
}