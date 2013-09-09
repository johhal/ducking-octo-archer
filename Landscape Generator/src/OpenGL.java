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
	
	String title = "Ducking-Octo-Archer";
	
	int tileSize;
	
	//Hur långt ifrån kanten nere och till vänster man börjar..typ
	float startTileX;
	float startTileY;
	
	float GUIX;
	
	float rotation = 0.0f;
	
	long last_frame;
	int fps;
	long last_fps;
	
	public void drawTile(Tile tile, float posX, float posY)
	{
		posX = startTileX + (tileSize + 2)*posX;
		posY = startTileY + (tileSize + 2)*posY;
		Color c = tile.getRGBA();
		glColor3f((float)(c.getRed())/255, (float)(c.getGreen())/255, (float)(c.getBlue())/255);
		
		glVertex2f(posX - tileSize/2, posY - tileSize/2);
		glVertex2f(posX + tileSize/2, posY - tileSize/2);
		glVertex2f(posX + tileSize/2, posY + tileSize/2);
		glVertex2f(posX - tileSize/2, posY + tileSize/2);
	}	
	
	public void drawHuman(Human h)
	{
		int posX = h.getPos().x;
		int posY = h.getPos().y;
		
		glColor3f(1.0f, 0.0f, 0.0f);
		
		
		glVertex2f(startTileX + 1 + (posX * (tileSize + 1)) - tileSize/2, startTileY + 1 + (posY * (tileSize + 1)) - tileSize/2);
		glVertex2f(startTileX - 1 + (posX * (tileSize + 1)) + tileSize/2, startTileY + 1 + (posY * (tileSize + 1)) - tileSize/2);
		glVertex2f(startTileX - 1 + (posX * (tileSize + 1)) + tileSize/2, startTileY - 1 + (posY * (tileSize + 1)) + tileSize/2);
		glVertex2f(startTileX + 1 + (posX * (tileSize + 1)) - tileSize/2, startTileY - 1 + (posY * (tileSize + 1)) + tileSize/2);
	}
	
	public void drawZombie(Zombie z)
	{
		int posX = z.getPos().x;
		int posY = z.getPos().y;
		
		glColor3f(1.0f, 0.0f, 0.0f);
		
		drawCube(startTileX + 2 + (posX * (tileSize + 2)) - tileSize/2, startTileY + 2 + (posY * (tileSize + 2)) - tileSize/2, 1, -tileSize+2);
		
		//glVertex2f(startTileX + 1 + (posX * (tileSize + 1)) - tileSize/2, startTileY + 1 + (posY * (tileSize + 1)) - tileSize/2);
		//glVertex2f(startTileX - 1 + (posX * (tileSize + 1)) + tileSize/2, startTileY + 1 + (posY * (tileSize + 1)) - tileSize/2);
		//glVertex2f(startTileX - 1 + (posX * (tileSize + 1)) + tileSize/2, startTileY - 1 + (posY * (tileSize + 1)) + tileSize/2);
		//glVertex2f(startTileX + 1 + (posX * (tileSize + 1)) - tileSize/2, startTileY - 1 + (posY * (tileSize + 1)) + tileSize/2);
	}
	
	public void drawHouse(House h)
	{
		int posX = h.getPos().x;
		int posY = h.getPos().y;
		
		glColor3f(1.0f, 0.0f, 0.0f);
		
		glVertex2f(startTileX + 1 + (posX * (tileSize + 1)) - tileSize/2, startTileY + 1 + (posY * (tileSize + 1)) - tileSize/2);
		glVertex2f(startTileX - 1 + (posX * (tileSize + 1)) + tileSize/2, startTileY + 1 + (posY * (tileSize + 1)) - tileSize/2);
		glVertex2f(startTileX - 1 + (posX * (tileSize + 1)) + tileSize/2, startTileY - 1 + (posY * (tileSize + 1)) + tileSize/2);
		glVertex2f(startTileX + 1 + (posX * (tileSize + 1)) - tileSize/2, startTileY - 1 + (posY * (tileSize + 1)) + tileSize/2);
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
	
	
	
	public void Initialize(int screenWidth, int screenHeight, int tileSize, int GUIWidth) throws LWJGLException
	{	
		screen_width = screenWidth;
		screen_height = screenHeight;
		
		this.tileSize = tileSize;
		
		startTileX = GUIWidth + 25;
		startTileY = 25;
		
		GUIX = GUIWidth;
		
		Display.setDisplayMode(new DisplayMode(screen_width, screen_height));
		Display.create();
		
		Display.setTitle(title);
		
		initGL();
		getDelta();
		
		last_fps = getTime();
		
		Keyboard.create();
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
	
	public void drawCube(float x, float y, float z, float cubeSize)
	{	
		//s1
		glVertex3f(x - cubeSize, y - cubeSize, z);
		glVertex3f(x, y - cubeSize, z);
		glVertex3f(x, y, z);
		glVertex3f(x - cubeSize, y, z);
		
		//s2
		glVertex3f(x, y - cubeSize, z - cubeSize);
		glVertex3f(x -cubeSize, y - cubeSize, z - cubeSize);
		glVertex3f(x - cubeSize, y, z - cubeSize);
		glVertex3f(x, y, z - cubeSize);
		
		//s3
		glVertex3f(x - cubeSize, y, z);
		glVertex3f(x, y, z);
		glVertex3f(x, y, z - cubeSize);
		glVertex3f(x - cubeSize, y, z - cubeSize);
		
		//s4
		glVertex3f(x, y - cubeSize, z);
		glVertex3f(x - cubeSize, y - cubeSize, z);
		glVertex3f(x - cubeSize, y - cubeSize, z - cubeSize);
		glVertex3f(x, y - cubeSize, z - cubeSize);
		
		//s5
		glVertex3f(x, y - cubeSize, z);
		glVertex3f(x, y - cubeSize, z - cubeSize);
		glVertex3f(x, y, z - cubeSize);
		glVertex3f(x, y, z);
		
		//s6
		glVertex3f(x - cubeSize, y - cubeSize, z - cubeSize);
		glVertex3f(x - cubeSize, y - cubeSize, z);
		glVertex3f(x - cubeSize, y, z);
		glVertex3f(x - cubeSize, y, z - cubeSize);
		
	}
	
	public void initGL()
	{
		glViewport(0, 0, screen_width, screen_height);
		
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		
		GLU.gluPerspective(45.0f, (float)screen_width/(float)screen_height, 1.0f, 1000.0f);
		
		System.out.println((float)screen_width/(float)screen_height);
		
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		
		glClearColor(0.0f, 0.0f, 0.0f, 0.5f);
		glClearDepth(1.0f);
		
		glDepthFunc(GL_LEQUAL);
		glEnable(GL_DEPTH_TEST);
		glShadeModel(GL_SMOOTH);
		
		glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
	
		//glOrtho(0, screen_width, 0, screen_height, -1, 1);
		//glMatrixMode(GL_MODELVIEW);
	}
	
	
	
	public void update(int delta)
	{
		rotation = 10;
		updateFPS();
	}
	
	public void initDraw()
	{
		glViewport(0, 0, screen_width, screen_height);
		
		glClearColor(0.0f, 0.0f, 0.0f, 0.5f);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		
		glLoadIdentity();
		
		glTranslatef(-400, -300, -750.0f);
		
		glRotatef(rotation, 0f, 0f, 0f);//vinkel
		
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
