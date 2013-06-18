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
		int x = 100;
		int y = 100;
		int type = BufferedImage.TYPE_INT_ARGB;
		Tile[][] landscape = landGen.generate(x, y);
		StringBuilder sb = new StringBuilder();

		BufferedImage image = new BufferedImage(x, y, type);

		for (int j = 0; j < y; j++) {
			for (int i = 0; i < x; i++) {
				image.setRGB(i, j, landscape[i][j].getRGBA());
				sb.append(landscape[i][j].toString());
			}
			sb.append("\n");
		}
		System.out.println(sb.toString());
		JFrame f = new JFrame("World map");
		f.getContentPane().setLayout(new FlowLayout());
		f.getContentPane().add(new JLabel(new ImageIcon(image)));
		f.pack();
		f.setVisible(true);

	}
}