public abstract class Humanoid {
	protected int currentX;
	protected int currentY;
	protected Landscape landscape;

	public Humanoid(int currentX, int currentY, Landscape landscape) {
		this.currentX = currentX;
		this.currentY = currentY;
		this.landscape = landscape;
	}

	abstract public void Initialize();

	abstract public void Update();

	abstract public void move();

	abstract public void Draw();
}
