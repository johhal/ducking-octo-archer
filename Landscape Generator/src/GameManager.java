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
	private HumanoidManager humanoidManager;
	
	ImageViewer viewer;
	
	Landscape landscape;
	
	public int Initialize()
	{
		//Storlek p� omr�den och antal omr�den
		boardWidth = 50;
		boardHeight = 50;
		tileSize = 10; 
		
		//Skapa F�nster
		viewer = new ImageViewer();
			
		// Skapa v�rlden och generera omr�den
		LandscapeGenerator landGen = new LandscapeGenerator();
		landscape = new Landscape(landGen.generate(boardWidth, boardHeight), tileSize, viewer);		
		
		//Sk�rmstuff
		JFrame f = new JFrame("World map");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		f.setContentPane(viewer.getGui());

		f.pack();
		f.setLocationByPlatform(true);
		f.setVisible(true);
		viewer.setMouseListener(landscape);
				
		//Skapa Varelser
		humanoidManager = new HumanoidManager();
		humanoidManager.Initialize(landscape);
		
		//Spela!!
		while (true) {
			Update();
			Draw();
		}

	}
	
	public void Update()
	{
		humanoidManager.Update();
	}
	
	public void Draw()
	{
		viewer.setImage(landscape.getLandscapeImg());
	}
}
