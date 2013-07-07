import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;


public class House {
	private int currentX;
	private int currentY;
	private Landscape landscape;
	private long remainingSleepTime;
	private long lastEntered;
	private int fertility;
	private ImageViewer viewer;
	
	public House(int currentX, int currentY, Landscape landscape, ImageViewer imageViewer) {
		this.currentX = currentX;
		this.currentY = currentY;
		this.landscape = landscape;
		this.viewer = imageViewer;
		generateSleepTime();
		lastEntered = System.currentTimeMillis();
		landscape.getTile(currentX,currentY).buildHouse(true);
		fertility = 0;
	}

	private void generateSleepTime() {
		remainingSleepTime = 1000;

	}

	public void Initialize() {
		// TODO Auto-generated method stub
		
	}
	
	public Point getPos(){
		return new Point(currentX,currentY);
	}
	public void kill(){
		landscape.getTile(currentX, currentY).inhabited(false);
	}

	private void generateFertility() {
		switch (landscape.getTile(currentX, currentY).getType()) {
		case WATER:
			//atm
			break;
		case FORREST:
			fertility += (int) (Math.random()*10);
			break;
		case PLAIN:
			fertility += (int) (Math.random()*20);
			break;
		case MOUNTAIN:
			fertility += (int) (Math.random()*5);
			break;
		}
	}
	public int getFertility(){
		return fertility;
	}
	
	public void setFertility(int i) {
		fertility = i;
	}
	
	private BufferedImage getTileImage(int tileSize) {

		BufferedImage image = new BufferedImage(tileSize, tileSize,
				BufferedImage.TYPE_INT_ARGB);
		for (int i = 0; i < tileSize; i++) {
			for (int j = 0; j < tileSize; j++) {
				if (i == 0) {
					image.setRGB(i, j, Color.black.getRGB());
				} else if (j == 0) {
					image.setRGB(i, j, Color.black.getRGB());
				} else if (i == (tileSize - 1)) {
					image.setRGB(i, j, Color.black.getRGB());
				} else if (j == (tileSize - 1)) {
					image.setRGB(i, j, Color.black.getRGB());
				} else {
					image.setRGB(i, j, getRGBA());
				}
			}
		}

		return image;
	}
	
	public int getRGBA() {
		return new Color(100, 50, 0).getRGB();
	}
	
	public void update() {
		if (remainingSleepTime <= 0) {
			//Has slept enough!
			
			//Genereate new sleep time
			generateSleepTime();
			generateFertility();
			//Randomize movement
//			move();

		} else {
			//Need more sleep!
			remainingSleepTime = remainingSleepTime
					- (System.currentTimeMillis() - lastEntered);
			lastEntered = System.currentTimeMillis();
		}
		
	}

	public void draw(int tileSize) {
		viewer.addImage(currentX*tileSize, currentY*tileSize, getTileImage(tileSize));
	}
}
