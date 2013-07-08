import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;

public class Human {
	private int currentX;
	private int currentY;
	private Landscape landscape;
	private long remainingSleepTime;
	private ImageViewer viewer;
	private long lastEntered;
	private boolean isWarrior;
	
	public Human(int currentX, int currentY, Landscape landscape, ImageViewer imageViewer) {
		this.currentX = currentX;
		this.currentY = currentY;
		this.landscape = landscape;
		this.viewer = imageViewer;
		generateSleepTime();
		lastEntered = System.currentTimeMillis();
		landscape.getTile(currentX, currentY).inhabited(true);
		if(Math.random()<0.2){
			System.out.println("A WARRIOR IS BORN!!!");
			isWarrior = true;
		}
	}

	private void generateSleepTime() {
		remainingSleepTime = 1000;

	}

	public void initialize() {
		// TODO Auto-generated method stub

	}

	public Point getPos() {
		return new Point(currentX, currentY);
	}

	public void kill() {
		landscape.getTile(currentX, currentY).inhabited(false);
	}

	private void move() {
		int rnd = (int) (Math.random() * 4);
		int newX = currentX;
		int newY = currentY;
		switch (rnd) {
		case 0:
			newX = currentX + 1;
			break;
		case 1:
			newX = currentX - 1;
			break;
		case 2:
			newY = currentY + 1;
			break;
		case 3:
			newY = currentY - 1;
			break;
		}
		if (landscape.canMoveHuman(currentX, currentY, newX, newY)) {
			landscape.getTile(currentX, currentY).inhabited(false);
			currentX = newX;
			currentY = newY;
			landscape.getTile(currentX, currentY).inhabited(true);
		}
	}

	public boolean isWarrior() {
		// TODO Auto-generated method stub
		return isWarrior;
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
		return new Color(255, 255, 255).getRGB();
	}
	
	
	
	public void update() {
		if (remainingSleepTime <= 0) {
			// Has slept enough!

			// Genereate new sleep time
			generateSleepTime();

			// Randomize movement
			move();

		} else {
			// Need more sleep!
			remainingSleepTime = remainingSleepTime
					- (System.currentTimeMillis() - lastEntered);
			lastEntered = System.currentTimeMillis();
		}

	}
	
	public void draw(int tileSize) {
		viewer.addImage(currentX*tileSize, currentY*tileSize, getTileImage(tileSize));
	}

}
