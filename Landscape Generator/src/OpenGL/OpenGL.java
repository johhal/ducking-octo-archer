package OpenGL;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import static org.lwjgl.opengl.GL11.*;

public class OpenGL {
	private int screen_width;
	private int screen_height;
	private int tileHeight;
	private String title = "Ducking-Octo-Archer";
	
	private int tileSize;
	private int difTileObject;
	
	//Hur långt ifrån kanten nere och till vänster man börjar..typ
	private float startTileX;
	private float startTileY;
	private int spaceBetweenTiles;
	
	private float GUIX;
	
	private long last_frame;
	private int fps;
	private long last_fps;
	
	private Camera camera;
	
    public FloatBuffer floatBuffer(float a, float b, float c, float d)
    {
    	float[] data = new float[]{a,b,c,d};
    	FloatBuffer fb = BufferUtils.createFloatBuffer(data.length);
    	fb.put(data);
    	fb.flip();
    	return fb;
     }
	
	public void drawBox(float x, float y, float z, float boxSizeX, float boxSizeY, float boxSizeZ)
	{	
		//front Face
		glNormal3f(0, 0, 1.0f);
		glVertex3f(x, y + boxSizeY, z + boxSizeZ);
		glVertex3f(x + boxSizeX, y + boxSizeY, z + boxSizeZ);
		glVertex3f(x + boxSizeX, y, z + boxSizeZ);
		glVertex3f(x, y, z + boxSizeZ);
		
		//Left Face
		glNormal3f(-1.0f, 0, 0);
		glVertex3f(x + boxSizeX, y + boxSizeY, z + boxSizeZ);
		glVertex3f(x + boxSizeX, y + boxSizeY, z);
		glVertex3f(x + boxSizeX, y, z);
		glVertex3f(x + boxSizeX, y, z + boxSizeZ);
		
		//Back Face
		glNormal3f(0, 0, -1.0f);
		glVertex3f(x + boxSizeX, y + boxSizeY, z);
		glVertex3f(x, y + boxSizeY, z);
		glVertex3f(x, y, z);
		glVertex3f(x + boxSizeX, y, z);
		
		//Right Face
		glNormal3f(1.0f, 0, 0);
		glVertex3f(x, y + boxSizeY, z);
		glVertex3f(x, y + boxSizeY, z + boxSizeZ);
		glVertex3f(x, y, z + boxSizeZ);
		glVertex3f(x, y, z);
		
		
		//Top Face
		glNormal3f(0, 1.0f, 0);
		glVertex3f(x, y + boxSizeY, z);
		glVertex3f(x + boxSizeX, y + boxSizeY, z);
		glVertex3f(x + boxSizeX, y + boxSizeY, z + boxSizeZ);
		glVertex3f(x, y + boxSizeY, z + boxSizeZ);
			
		//Bottom Face
		glNormal3f(0, -1.0f, 0);
		glVertex3f(x + boxSizeX, y, z);
		glVertex3f(x, y, z);
		glVertex3f(x, y, z + boxSizeZ);
		glVertex3f(x + boxSizeX, y, z + boxSizeZ);
		
		
		
		
	}
	
	public void convertAndDraw(int pX, int pY, float CR, float CG, float CB, short notTile)
	{
		float posX = startTileX + notTile*(float)difTileObject/2 + (pX * (tileSize + spaceBetweenTiles));
		float posY = startTileY + notTile*(float)difTileObject/2 + (pY * (tileSize + spaceBetweenTiles));
		
		glColor3f(CR, CG, CB);
		
		drawBox(posX, posY, tileHeight + tileHeight*notTile, tileSize - notTile*difTileObject, tileSize - notTile*difTileObject, tileSize - notTile*difTileObject);
	}
	
	/*
	public void drawGUI(GUIHandler guiHandler)
	{
		int nrOfTeams = guiHandler.getNrOfTeams();
		int nrOfObjects = guiHandler.getNrOfObjects();
		
		glColor3f(0.5f, 0.5f, 0.5f);
		
		float guiX = posX-(screen_width/2.0f);//(posX/(screen_width/2.0f));
		float guiY = posY-(screen_height/2.0f);//*(posY/(screen_height/2.0f));
		float guiZ = posZ;
		
		glVertex3f(guiX + GUIX, guiY + screen_height, guiZ);
		glVertex3f(guiX, guiY + screen_height, guiZ);
		glVertex3f(guiX, guiY, guiZ);
		glVertex3f(guiX + GUIX, guiY, guiZ);
		
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
	*/
	//småstuff
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

	//initsiering
	public void initialize(int screenWidth, int screenHeight, int tileSize, int GUIWidth) throws LWJGLException
	{	
		screen_width = screenWidth;
		screen_height = screenHeight;
		
		this.tileSize = tileSize;
		difTileObject = 1;
		
		startTileX = GUIWidth + 25;
		startTileY = 25;
		tileHeight = 2;
		
		spaceBetweenTiles = 1;
		
		GUIX = GUIWidth;
		
		initDisplay();
		initGL();
		
		camera = new Camera();
		camera.initialize(70, (float)screen_width/screen_height, 0.3f, 1000);
		
		getDelta();
		
		last_fps = getTime();
	}
	
	public void initDisplay() throws LWJGLException
	{
		Display.setDisplayMode(new DisplayMode(screen_width, screen_height));
		Display.create();
		
		Display.setTitle(title);
	}

	public void initGL()
	{ 
		initLight(-400, -300, -800);			
		
		glViewport(0, 0, screen_width, screen_height);
		
		glClearColor(0.0f, 0.0f, 0.0f, 0.5f);
		glClearDepth(1.0f);
		
		glDepthFunc(GL_LEQUAL);
		glEnable(GL_DEPTH_TEST);
		glShadeModel(GL_SMOOTH);
		
		glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
	}
	
	//förflyttning
	public void moveCamera(float ammount, float direction)
	{
		camera.move(ammount, direction);
	}

	//Ljus
	private void setLight(float xPos, float yPos, float zPos)
	{
		//ljusets position
		glLight(GL11.GL_LIGHT0, GL11.GL_POSITION, floatBuffer(xPos, yPos, zPos, 1));
		
		glLight(GL_LIGHT0, GL_DIFFUSE, floatBuffer(1.0f, 1.0f, 1.0f, 1.0f));
		glLight(GL_LIGHT0, GL_AMBIENT, floatBuffer(0.1f, 0.1f, 0.1f, 1.0f));
		glLight(GL_LIGHT0, GL_SPECULAR, floatBuffer(1.0f, 1.0f, 1.0f, 1.0f));
	}
		
	private void initLight(float xPos, float yPos, float zPos)
	{
		glEnable (GL_DEPTH_TEST);
		glEnable (GL_LIGHTING);
		glEnable (GL_LIGHT0);		
		
		glLightModeli(GL11.GL_LIGHT_MODEL_TWO_SIDE,GL11.GL_TRUE);
		
		glEnable(GL_COLOR_MATERIAL);
		glColorMaterial(GL_FRONT_AND_BACK, GL_AMBIENT_AND_DIFFUSE);
        
		setLight(xPos, yPos, zPos);
	}
	
	private int mathExp(int base, int exp)
	{
		int tmp = base;
		for(int i = 0; i < exp; i++)
			tmp *= base;
		if (exp == 0)
			tmp = 1;
		return tmp;
	}
	
	private void translateInput(int input)
	{
		if(input != 0)
		{
			int maxExp = 6;
			if(input/mathExp(10, maxExp-1) != 0)
			{
				input -= mathExp(10, maxExp-1);
				camera.move(0.01f, 1);
			}
			if(input % mathExp(10, maxExp-2) != 0)
			{
				
			}
			if(input % mathExp(10, maxExp-3) != 0)
			{
				
			}
			if(input % mathExp(10, maxExp-4) != 0)
			{
				
			}
			if(input % mathExp(10, maxExp-5) != 0)
			{
				
			}
			if(input % mathExp(10, maxExp-6) != 0)
			{
				
			}
		}
	}
	
	//Updatera
	public void update(int delta, int input)
	{
		translateInput(input);
		updateFPS();
	}
	
	//Rita
	public void initDraw()
	{
		//glViewport(0, 0, screen_width, screen_height);
		
		glClearColor(0.0f, 0.0f, 0.0f, 0.5f);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		
		glLoadIdentity();
		
		camera.setView();
		
		glPushMatrix();
		
		glTranslatef(0, 0, -10);
		
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
