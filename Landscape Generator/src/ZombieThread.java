public class ZombieThread extends Thread {

	private int currentX;
	private int currentY;
	private Landscape landscape;
	private ImageViewer imageViewer;

	public ZombieThread(int currentX, int currentY,
			Landscape landscape, ImageViewer imageViewer) {
		this.currentX = currentX;
		this.currentY = currentY;
		this.landscape = landscape;
		this.imageViewer = imageViewer;
	}

	public void run() {
		while (true) {
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
				imageViewer.setImage(landscape.getLandscapeImg(landscape.getTileSize(), landscape.getTileSize()));
			}
			try {
				switch(landscape.getTile(currentX, currentY).getType()){
				case WATER:
					//går inte, men ville inte ha felmeddelandet.
					break;
				case FORREST:
					Thread.sleep((long) (Math.random() * 1000)+1000);
					break;
				case PLAIN:
					Thread.sleep((long) (Math.random() * 1000));
					break;
				case MOUNTAIN:
					Thread.sleep((long) (Math.random() * 1000)+3000);
					break;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
