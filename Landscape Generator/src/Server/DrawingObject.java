package Server;

public class DrawingObject {
	public int posX;
	public int posY;
	public short texturePos;
	public short notTile;
	public DrawingObject(int x, int y, short texPos, short nt)
	{
		posX = x;
		posY = y;
		texturePos = texPos;
		notTile = nt;
	}
}
