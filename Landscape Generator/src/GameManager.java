import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class GameManager {
	private int boardWidth;
	private int boardHeight;
	private int tileSize;
	private int zombieCount;
	
	ImageViewer viewer;
	
	Landscape landscape;
	
	public int Initzialize()
	{
		//Storlek på områden och antal områden
		boardWidth = 50;
		boardHeight = 50;
		tileSize = 10; 
		
		//Antal Zombies
		int zombieCount = 0;
		
		//Skapa Fönster
		viewer = new ImageViewer();
			
		// Skapa världen och generera områden
		LandscapeGenerator landGen = new LandscapeGenerator();
		landscape = new Landscape(landGen.generate(boardWidth, boardHeight), tileSize, viewer);
		
		addZombies();
		
		
		JFrame f = new JFrame("World map");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		
		f.setContentPane(viewer.getGui());

		f.pack();
		f.setLocationByPlatform(true);
		f.setVisible(true);
		viewer.setMouseListener(landscape);
				
		
		while (true) {
			viewer.setImage(landscape.getLandscapeImg());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
	
	private int addZombies()
	{
		//Sätt ut zombies? zombiechans?
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
		return 0;
	}
	
	public void Update()
	{
		
	}
	
	public void Draw()
	{
		
	}
}
