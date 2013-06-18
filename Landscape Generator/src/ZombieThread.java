public class ZombieThread extends Thread {

	private Tile currentLocation;
	private int currentX;
	private int currentY;
	private Landscape landscape;

	public ZombieThread(Tile currentLocation, int currentX, int currentY,
			Landscape landscape) {
		this.currentLocation = currentLocation;
		this.currentX = currentX;
		this.currentY = currentY;
		this.landscape = landscape;
	}

	public void run() {
		while (true) {
			int rnd = (int) Math.random() * 3;
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
			landscape.moveZombie(currentX, currentY, newX, newY);
			currentX=newX;
			currentY=newY;
			try {
				Thread.sleep((long) (Math.random() * 1000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
