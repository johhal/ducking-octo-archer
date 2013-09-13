import java.awt.Color;
import java.util.ArrayList;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import static org.lwjgl.opengl.GL11.*;


public class OpenGL {
	int screen_width;
	int screen_height;
	int tileHeight;
	String title = "Ducking-Octo-Archer";
	
	int tileSize;
	
	float posX;
	float posY;
	float posZ;
	
	//Hur långt ifrån kanten nere och till vänster man börjar..typ
	float startTileX;
	float startTileY;
	int spaceBetweenTiles;
	
	float GUIX;
	
	float rotationX;
	float rotationY;
	float rotationZ;
	float rotationAngle;
	
	long last_frame;
	int fps;
	long last_fps;
	
	public void drawBox(float x, float y, float z, float boxSizeX, float boxSizeY, float boxSizeZ)
	{	
		//s1
		glVertex3f(x + boxSizeX, y + boxSizeY, z);
		glVertex3f(x, y + boxSizeY, z);
		glVertex3f(x, y, z);
		glVertex3f(x + boxSizeX, y, z);
		
		//s2
		glVertex3f(x, y + boxSizeY, z + boxSizeZ);
		glVertex3f(x + boxSizeX, y + boxSizeY, z + boxSizeZ);
		glVertex3f(x + boxSizeX, y, z + boxSizeZ);
		glVertex3f(x, y, z + boxSizeZ);
		
		//s3
		glVertex3f(x + boxSizeX, y, z);
		glVertex3f(x, y, z);
		glVertex3f(x, y, z + boxSizeZ);
		glVertex3f(x + boxSizeX, y, z + boxSizeZ);
		
		//s4
		glVertex3f(x, y + boxSizeY, z);
		glVertex3f(x + boxSizeX, y + boxSizeY, z);
		glVertex3f(x + boxSizeX, y + boxSizeY, z + boxSizeZ);
		glVertex3f(x, y + boxSizeY, z + boxSizeZ);
		
		//s5
		glVertex3f(x, y + boxSizeY, z);
		glVertex3f(x, y + boxSizeY, z + boxSizeZ);
		glVertex3f(x, y, z + boxSizeZ);
		glVertex3f(x, y, z);
		
		//s6
		glVertex3f(x + boxSizeX, y + boxSizeY, z + boxSizeZ);
		glVertex3f(x + boxSizeX, y + boxSizeY, z);
		glVertex3f(x + boxSizeX, y, z);
		glVertex3f(x + boxSizeX, y, z + boxSizeZ);
		
	}
	
	public void drawTile(Tile tile, float posX, float posY)
	{
		posX = startTileX + (tileSize + spaceBetweenTiles)*posX;
		posY = startTileY + (tileSize + spaceBetweenTiles)*posY;
		Color c = tile.getRGBA();
		glColor3f((float)(c.getRed())/255, (float)(c.getGreen())/255, (float)(c.getBlue())/255);
		
		
		drawBox(posX, posY, 1, tileSize, tileSize, tileHeight);
	}	
	
	public void drawHuman(Human h)
	{
		float posX = startTileX + spaceBetweenTiles + (h.getPos().x * (tileSize + spaceBetweenTiles)) - tileSize/2;
		float posY = startTileY + spaceBetweenTiles + (h.getPos().y * (tileSize + spaceBetweenTiles)) - tileSize/2;
		
		glColor3f(1.0f, 0.0f, 0.0f);
		
		drawBox(posX, posY, tileHeight, tileSize - 2, tileSize - 2, tileSize - 2);
	}
	
	public void drawZombie(Zombie z)
	{
		float posX = startTileX + spaceBetweenTiles + (z.getPos().x * (tileSize + spaceBetweenTiles)) - tileSize/2;
		float posY = startTileY + spaceBetweenTiles + (z.getPos().y * (tileSize + spaceBetweenTiles)) - tileSize/2;
		
		glColor3f(1.0f, 0.0f, 0.0f);
		
		drawBox(posX, posY, tileHeight, tileSize - 2, tileSize - 2, tileSize - 2);
	}
	
	public void drawHouse(House h)
	{
		float posX = startTileX + spaceBetweenTiles + (h.getPos().x * (tileSize + spaceBetweenTiles)) - tileSize/2;
		float posY = startTileY + spaceBetweenTiles + (h.getPos().y * (tileSize + spaceBetweenTiles)) - tileSize/2;
		
		glColor3f(1.0f, 0.0f, 0.0f);
		
		drawBox(posX, posY, tileHeight, tileSize - 2, tileSize - 2, tileSize - 2);
	}
	
	public void drawGUI(GUIHandler guiHandler)
	{
		int nrOfTeams = guiHandler.getNrOfTeams();
		int nrOfObjects = guiHandler.getNrOfObjects();
		
		glColor3f(0.5f, 0.5f, 0.5f);
		
		glVertex2f(GUIX, 600);
		glVertex2f(0, 600);
		glVertex2f(0, 0);
		glVertex2f(GUIX, 0);
		
		for(int i = 0; i < nrOfTeams; i++)
		{
			if(i+1 == guiHandler.getSelectedTeam())
			{
				glColor3f(0.0f, 0.0f, 0.0f);
				
				glVertex2f(10 + i * ((GUIX - 10)/nrOfTeams) - 2, 590 + 2);
				glVertex2f(10 + i * ((GUIX - 10)/nrOfTeams) + 20 + 2, 590 + 2);
				glVertex2f(10 + i * ((GUIX - 10)/nrOfTeams) + 20 + 2, 570 -2);
				glVertex2f(10 + i * ((GUIX - 10)/nrOfTeams) -2 , 570 - 2);
			}
			
			glColor3f(0.5f * i, 0.6f / i, 0.5f);
			glVertex2f(10 + i * ((GUIX - 10)/nrOfTeams), 590);
			glVertex2f(10 + i * ((GUIX - 10)/nrOfTeams) + 20, 590);
			glVertex2f(10 + i * ((GUIX - 10)/nrOfTeams) + 20, 570);
			glVertex2f(10 + i * ((GUIX - 10)/nrOfTeams), 570);
		}
	}
	
	public boolean isRunning()
	{
		return !Display.isCloseRequested();
	}
	
	public void quitGL()
	{
		Display.destroy();
		System.exit(0);
	}
	
	public int getDelta()
	{
		long time = getTime();
		int delta = (int) (time - last_frame);
		last_frame = time;
		
		return delta;
	}
	
	public long getTime()
	{
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}
	
	public void updateFPS()
	{
		if(getTime() - last_fps > 1000)
		{			
			fps = 0;
			
			last_fps += 1000;
		}
		
		fps++;
	}
	
	public void Initialize(int screenWidth, int screenHeight, int tileSize, int GUIWidth) throws LWJGLException
	{	
		screen_width = screenWidth;
		screen_height = screenHeight;
		
		this.tileSize = tileSize;
		
		startTileX = GUIWidth + 25;
		startTileY = 25;
		tileHeight = 5;
		
		posX = screenWidth/2;
		posY = screenHeight/2;
		posZ = 1000;
		
		rotationX = 0.0f;
		rotationY = 0.0f;
		rotationZ = 0.0f;
		rotationAngle = 0.0f;
		
		spaceBetweenTiles = 5;
		
		GUIX = GUIWidth;
		
		Display.setDisplayMode(new DisplayMode(screen_width, screen_height));
		Display.create();
		
		Display.setTitle(title);
		
		initGL();
		getDelta();
		
		last_fps = getTime();
	}
	
	public void moveForward(int x)
	{
		posY += 5*x; //Stämmer nog inte Får duga tills rotation införs
	}
	
	public void moveSideways(int x)
	{
		posX += 5*x;
	}
	
	
	public void initGL()
	{
		glViewport(0, 0, screen_width, screen_height);
		
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		
		GLU.gluPerspective(45.0f, (float)screen_width/(float)screen_height, 1.0f, 1900.0f);
		
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		
		glClearColor(0.0f, 0.0f, 0.0f, 0.5f);
		glClearDepth(1.0f);
		
		glDepthFunc(GL_LEQUAL);
		glEnable(GL_DEPTH_TEST);
		glShadeModel(GL_SMOOTH);
		
		glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
	}
	
	
	public void update(int delta, float rotX, float rotY, float rotZ)
	{
		rotationX = rotX;
		rotationY = rotY;
		rotationZ = rotY;
//		rotationAngle += 0.15f;
		
		updateFPS();
	}
	
	
	public void initDraw()
	{
		glViewport(0, 0, screen_width, screen_height);
		
		glClearColor(0.0f, 0.0f, 0.0f, 0.5f);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		
		glLoadIdentity();
		
		glTranslatef(-posX, -posY, -posZ);
		
		glRotatef(rotationAngle, rotationX, rotationY, rotationZ);//vinkel
		
		glBegin(GL_QUADS);
	}
	
	
	public void endDraw()
	{
		glEnd();
		
		glPopMatrix();
		
		//Ritar ut på skärmen
		Display.update();
		
		//Sätter fpsen till 60
		//Display.sync(60);
	}
}
