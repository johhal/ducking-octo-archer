import java.awt.Color;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;
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
	private int screen_width;
	private int screen_height;
	private int tileHeight;
	private String title = "Ducking-Octo-Archer";
	
	private int tileSize;
	private int difTileObject;
	
	private float posX;
	private float posY;
	private float posZ;
	
	//Hur långt ifrån kanten nere och till vänster man börjar..typ
	private float startTileX;
	private float startTileY;
	private int spaceBetweenTiles;
	
	private float GUIX;
	
	private float rotationX;
	private float rotationY;
	private float rotationZ;
	private float rotationAngle;
	
	private long last_frame;
	private int fps;
	private long last_fps;
	
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
	
	public void drawTile(Tile tile, float posX, float posY)
	{
		posX = startTileX + (tileSize + spaceBetweenTiles)*posX;
		posY = startTileY + (tileSize + spaceBetweenTiles)*posY;
		Color c = tile.getRGBA();
		glColor3f((float)(c.getRed())/255, (float)(c.getGreen())/255, (float)(c.getBlue())/255);
		
		GL11.glMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT_AND_DIFFUSE, floatBuffer(1.0f, 0.0f, 0.0f, 1.0f));
		
		drawBox(posX, posY, 1, tileSize, tileSize, tileHeight);
	}	
	
	public void drawHuman(Human h)
	{
		float posX = startTileX + (float)difTileObject/2 + (h.getPos().x * (tileSize + spaceBetweenTiles));
		float posY = startTileY + (float)difTileObject/2 + (h.getPos().y * (tileSize + spaceBetweenTiles));
		
		glColor3f(1.0f, 1.0f, 1.0f);
		
		drawBox(posX, posY, tileHeight, tileSize - difTileObject, tileSize - difTileObject, tileSize - difTileObject);
	}
	
	public void drawZombie(Zombie z)
	{
		float posX = startTileX + (float)difTileObject/2 + (z.getPos().x * (tileSize + spaceBetweenTiles));
		float posY = startTileY + (float)difTileObject/2 + (z.getPos().y * (tileSize + spaceBetweenTiles));
		
		glColor3f(1.0f, 0.0f, 0.0f);
		GL11.glMaterial(GL11.GL_FRONT, GL11.GL_EMISSION, floatBuffer(0.0f, 0.0f, 0.0f, 1.0f));
		
		drawBox(posX, posY, tileHeight, tileSize - difTileObject, tileSize - difTileObject, tileSize - difTileObject);
	}
	
	public void drawHouse(House h)
	{
		float posX = startTileX + (float)difTileObject/2 + (h.getPos().x * (tileSize + spaceBetweenTiles));
		float posY = startTileY + (float)difTileObject/2 + (h.getPos().y * (tileSize + spaceBetweenTiles));
		
		glColor3f(0.7f, 0.3f, 0.1f);
		
		drawBox(posX, posY, tileHeight, tileSize - difTileObject, tileSize - difTileObject, tileSize - difTileObject);
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
		difTileObject = 2;
		
		startTileX = GUIWidth + 25;
		startTileY = 25;
		tileHeight = 5;
		
		posX = screen_height/2;
		posY = screen_width/2;
		posZ = 200;
		
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
	
	public void moveForward(int y)
	{
		posY += 5*y; //Stämmer nog inte Får duga tills rotation införs
	}
	
	public void moveSideways(int x)
	{
		posX += 5*x;
	}
	
	public void zoom(int zoom)
	{
		posZ += 3*zoom;
	}
	
	private void setCamera(float angle)
	{	
		//cange to projectionmatrix
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		
		//perspective
		float widthHeigthRatio = (float)screen_width/(float)screen_height;
		GLU.gluPerspective(angle, widthHeigthRatio, 1.0f, 5000.0f);
		GLU.gluLookAt(0, 0, posZ, 0, 0, 0, 0, 1, 0);
		
		//Change back to model view matrix
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
	}
	
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
		
		setLight(xPos, yPos, zPos);
	}
	
	public void initGL()
	{ 
		initLight(800, 600, -800);			
		glEnable(GL_COLOR_MATERIAL);
		glColorMaterial(GL_FRONT_AND_BACK, GL_AMBIENT_AND_DIFFUSE);

		
		glViewport(0, 0, screen_width, screen_height);
		setCamera(45);
		
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
		rotationZ = rotZ;
		rotationAngle = 45;
		
		setCamera(rotationAngle);
		rotationZ = rotY;
//		rotationAngle += 0.15f;
		
		updateFPS();
	}
	
	private int x = 0;
	public void initDraw()
	{
		glViewport(0, 0, screen_width, screen_height);
		
		glClearColor(0.0f, 0.0f, 0.0f, 0.5f);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		if(x > 500)
			x=-200;
		else
			x++;
		setLight(x*6, 300, -800);
		
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
