
public class DrawingObject {
	public int posX;
	public int posY;
	public float cr;
	public float cg;
	public float cb;
	public short notTile;
	public DrawingObject(int x, int y, float r, float g, float b, short nt)
	{
		posX = x;
		posY = y;
		cr = r;
		cg = g;
		cb = b;
		notTile = nt;
	}
}
