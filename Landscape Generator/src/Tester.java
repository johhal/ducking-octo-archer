import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Tester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		LandscapeGenerator landGen = new LandscapeGenerator();
		int x = 50;
		int y = 50;
		int tileSize = 10;
		int zombieCount = 0;
		ImageViewer viewer = new ImageViewer();
		// int type = BufferedImage.TYPE_INT_ARGB;
		Landscape landscape = new Landscape(landGen.generate(x, y), viewer);
		StringBuilder sb = new StringBuilder();

		// BufferedImage image = new BufferedImage(tileSize*x, tileSize*y,
		// type);
		for (int j = 0; j < y; j++) {
			for (int i = 0; i < x; i++) {
				double rnd = Math.random();
				Tile current = landscape.getTile(i, j);
				switch (current.getType()) {
				case FORREST:
					if (rnd < 0.01) {
						current.infest(true);
						zombieCount++;
						new ZombieThread(i, j, landscape, viewer).start();
					}
					break;
				case PLAIN:
					if (rnd < 0.01) {
						current.infest(true);
						zombieCount++;
						new ZombieThread(i, j, landscape, viewer).start();
					}
					break;
				case WATER:
					break;
				case MOUNTAIN:
					if (rnd < 0.01) {						
						current.infest(true);
						zombieCount++;
						new ZombieThread(i, j, landscape, viewer).start();
					}
					break;
				}
			}
		}
		JFrame f = new JFrame("World map");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		
		f.setContentPane(viewer.getGui());

		f.pack();
		f.setLocationByPlatform(true);
		f.setVisible(true);
		viewer.setMouseListener(landscape);
		
		while (true) {
			viewer.setImage(landscape.getLandscapeImg(tileSize, tileSize));
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}