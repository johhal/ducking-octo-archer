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
		//Storlek p� omr�den och antal omr�den
		int boardWidth = 50;
		int boardHeight = 75;
		int tileSize = 10; //H�rdkodad p� sina st�llen, r�r ej
		
		//Skapa F�nster
		ImageViewer viewer = new ImageViewer();
		
		//Antal Zombies
		int zombieCount = 0;
		
		// int type = BufferedImage.TYPE_INT_ARGB;
		
		// Skapa v�rlden och generera omr�den
		LandscapeGenerator landGen = new LandscapeGenerator();
		Landscape landscape = new Landscape(landGen.generate(boardWidth, boardHeight), viewer);
		//StringBuilder sb = new StringBuilder();

		// BufferedImage image = new BufferedImage(tileSize*x, tileSize*y,
		// type);
		
		//S�tt ut zombies? zombiechans?
		for (int j = 0; j < boardHeight; j++) {
			for (int i = 0; i < boardWidth; i++) {
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