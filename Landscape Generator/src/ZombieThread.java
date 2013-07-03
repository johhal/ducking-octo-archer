public class ZombieThread extends Thread {

	private int currentX;
	private int currentY;
	private Landscape landscape;

	public ZombieThread(int currentX, int currentY, Landscape landscape) {
		this.currentX = currentX;
		this.currentY = currentY;
		this.landscape = landscape;
	}

	public void run() {
		landscape.getTile(currentX, currentY).infest(true);
		while (true) {
			try {
				switch (landscape.getTile(currentX, currentY).getType()) {
				case WATER:
					// g�r inte, men ville inte ha felmeddelandet.
					break;
				case FORREST:
					Thread.sleep((long) (Math.random() * 1000) + 1000);
					break;
				case PLAIN:
					Thread.sleep((long) (Math.random() * 1000));
					break;
				case MOUNTAIN:
					Thread.sleep((long) (Math.random() * 1000) + 3000);
					break;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
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
			if (landscape.moveZombie(currentX, currentY, newX, newY)) {
				currentX = newX;
				currentY = newY;
			}
		}
	}
}
