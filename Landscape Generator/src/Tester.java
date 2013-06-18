import java.awt.FlowLayout;
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
		int x = 10;
		int y = 10;
		int type = BufferedImage.TYPE_INT_ARGB;
		Landscape landscape = new Landscape(landGen.generate(x, y));
		StringBuilder sb = new StringBuilder();

		BufferedImage image = new BufferedImage(x, y, type);
		for (int j = 0; j < y; j++) {
			for (int i = 0; i < x; i++) {
				double rnd = Math.random();
				Tile current = landscape.getTile(i, j);
				switch (current.getType()) {
				case FORREST:
					if (rnd < 0.05) {
						current.infest(true);
						new ZombieThread(current, i, j, landscape).start();
					}
					break;
				case PLAIN:
					if (rnd < 0.05) {
						current.infest(true);
						new ZombieThread(current, i, j, landscape).start();
					}
					break;
				case WATER:
					break;
				case MOUNTAIN:
					if (rnd < 0.05) {
						current.infest(true);
						new ZombieThread(current, i, j, landscape).start();
					}
					break;
				}
				image.setRGB(i, j, landscape.getTile(i, j).getRGBA());
				sb.append(landscape.getTile(i, j).toString());
			}
			sb.append("\n");
		}
		JFrame f = new JFrame("World map");
		// ImageIcon icon = new ImageIcon(image);
		JLabel label = new JLabel(new ImageIcon(image));
		f.getContentPane().setLayout(new FlowLayout());
		f.getContentPane().add(new JLabel(new ImageIcon(image)));
		f.pack();
		f.setVisible(true);
		while (true) {
			for (int j = 0; j < y; j++) {
				for (int i = 0; i < x; i++) {
					image.setRGB(i, j, landscape.getTile(i, j).getRGBA());
					sb.append(landscape.getTile(i, j).toString());
				}
				sb.append("\n");
			}
			label.setIcon(new ImageIcon(image));
			System.out.println(sb.toString());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		// System.out.println(sb.toString());

	}
}