public class HumanThread extends Thread {

	private int currentX;
	private int currentY;
	private Landscape landscape;
	private ImageViewer imageViewer;

	public HumanThread(int currentX, int currentY, Landscape landscape,
			ImageViewer imageViewer) {
		this.currentX = currentX;
		this.currentY = currentY;
		this.landscape = landscape;
		this.imageViewer = imageViewer;
	}

	public void run() {
		long tileFactor = 0;
		switch (landscape.getTile(currentX, currentY).getType()) {
		case FORREST:
			tileFactor = 10000;
			break;
		case PLAIN:
			tileFactor = 5000;
			break;
		case MOUNTAIN:
			tileFactor = 15000;
			break;
		}
		boolean alive = true;
		while (alive) {
			try {
				Thread.sleep((long) (Math.random() * 10000) + tileFactor);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			alive = landscape.getTile(currentX, currentY).isInhabited();
			if (alive) {
				landscape.spawnHuman(currentX, currentY);
			}
		}
	}
}
