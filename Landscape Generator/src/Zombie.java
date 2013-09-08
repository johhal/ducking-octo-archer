import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;

public class Zombie extends Fighter {
	private int currentX;
	private int currentY;
	private Landscape landscape;
	private long remainingSleepTime;
	private long lastEntered;
	private ImageViewer viewer;

	public Zombie(int currentX, int currentY, Landscape landscape,
			ImageViewer viewer) {
		super(7, 4, 7, 6, 16);
		this.currentX = currentX;
		this.currentY = currentY;
		this.landscape = landscape;
		generateSleepTime();
		lastEntered = System.currentTimeMillis();
		landscape.getTile(currentX, currentY).infest(true);
		this.viewer = viewer;
		// TODO Auto-generated constructor stub
	}

	public Point getPos() {
		return new Point(currentX, currentY);
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
		if (landscape.canMoveZombie(currentX, currentY, newX, newY)) {
			landscape.getTile(currentX, currentY).infest(false);
			currentX = newX;
			currentY = newY;
			landscape.getTile(currentX, currentY).infest(true);
		}
	}

	public void generateSleepTime() {
		switch (landscape.getTile(currentX, currentY).getType()) {
		case WATER:
			// g�r inte, men ville inte ha felmeddelandet.
			break;
		case FORREST:
			remainingSleepTime = (long) (Math.random() * 1000) + 1500;
			break;
		case PLAIN:
			remainingSleepTime = (long) (Math.random() * 1000) + 500;
			break;
		case MOUNTAIN:
			remainingSleepTime = (long) (Math.random() * 1000) + 4000;
			break;
		}
	}

	public void kill() {
		landscape.getTile(currentX, currentY).infest(false);
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
		int n = (int) (((double) getCurrentHitpoints() / (double) getMaxHitpoints()) * 255);
		return new Color(n, 0, 0).getRGB();
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

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Zombie");
		sb.append("\n");
		sb.append("HP: " + getCurrentHitpoints() + "/" + getMaxHitpoints());
		sb.append("\n");
		sb.append("Attack: " + getAttack());
		sb.append("\n");
		sb.append("Armor: " + getArmor());
		sb.append("\n");
		sb.append("Damage: " + getDamage());
		return sb.toString();

	}

	public void draw(int tileSize, OpenGL gl) {
		gl.drawZombie(this);
		//viewer.addImage(currentX * tileSize, currentY * tileSize,
			//	getTileImage(tileSize));
	}

}
