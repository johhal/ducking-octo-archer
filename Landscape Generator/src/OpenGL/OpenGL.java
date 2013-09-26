package OpenGL;

import java.awt.Point;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Vector3f;

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
    
    public boolean isCloseRequested()
    {
    	return Display.isCloseRequested();
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
	
	public void convertAndDraw(int pX, int pY, float ColR, float ColG, float ColB, short notTile)
	{
		float posX = startTileX + notTile*(float)difTileObject/2 + (pX * (tileSize + spaceBetweenTiles));
		float posY = startTileY + notTile*(float)difTileObject/2 + (pY * (tileSize + spaceBetweenTiles));
		
		glColor3f(ColR, ColG, ColB);
		
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
		
		startTileX = 0;
		startTileY = 0;
		tileHeight = 2;
		
		spaceBetweenTiles = 1;
		
		GUIX = GUIWidth;

		initDisplay();

		initGL();

		camera = new Camera();
		camera.initialize(70f, (float)screen_width/screen_height, 0.3f, 1000f);	//, posX, posY, posZ);
		System.out.println("3");
		getDelta();
		
		last_fps = getTime();
	}
	
	private void initDisplay() throws LWJGLException
	{
		Display.setDisplayMode(new DisplayMode(screen_width, screen_height));
		Display.create();
		
		Display.setTitle(title);
	}

	private void initGL()
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
		camera.moveXY(ammount, direction);
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
			if(input/mathExp(10, 7) != 0)
			{
				//C
				input -= mathExp(10, 7);
				camera.moveXY(-1f, 90);
			}
			if(input/mathExp(10, 6) != 0)
			{
				//Z
				input -= mathExp(10, 6);
				camera.moveXY(1f, 90);
			}
			if(input/mathExp(10, 5) != 0)
			{
				//E
				input -= mathExp(10, 5);
				camera.moveXZ(-1f, 1);
			}
			if(input/mathExp(10, 4) != 0)
			{
				//Q
				input -= mathExp(10, 4);
				camera.moveXZ(1f, 1);
			}
			if(input/mathExp(10, 3) != 0)
			{
				//S
				input -= mathExp(10, 3);
				camera.rotateX(1);
			}
			if(input/mathExp(10, 2) != 0)
			{
				//W
				input -= mathExp(10, 2);
				camera.rotateX(-1);
			}
			if(input/mathExp(10, 1) != 0)
			{
				//D
				input -= mathExp(10, 1);
				camera.rotateY(1f);
			}
			if(input/mathExp(10, 0) != 0)
			{
				//A
				input -= mathExp(10, 0);
				camera.rotateY(-1f);
			}
		}
	}
	
	//Updatera
	public Point update(int delta, int input, Point p)
	{
		Point p2 = new Point(p.x, p.y);
		translateInput(input);
		if(p2.x != 0 || p2.y != 0)
		{
			Vector3f v = getMousePositionIn3dCoords(p2.x, p2.y);
			p2.x = (int)v.x/ (tileSize + spaceBetweenTiles);
			p2.y = (int)v.y/ (tileSize + spaceBetweenTiles);
		}
		updateFPS();
		return p2;
	}
	
	//Rita
	public void initDraw()
	{
		glViewport(0, 0, screen_width, screen_height);
		
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


	static IntBuffer viewport = BufferUtils.createIntBuffer(16);
	static FloatBuffer modelview = BufferUtils.createFloatBuffer(16);
	static FloatBuffer projection = BufferUtils.createFloatBuffer(16);
	static FloatBuffer winZ = BufferUtils.createFloatBuffer(20);
	static FloatBuffer position = BufferUtils.createFloatBuffer(3);
	   
	static public Vector3f getMousePositionIn3dCoords(int mouseX, int mouseY)
	{

		viewport.clear();
		modelview.clear();
		projection.clear();
		winZ.clear();;
		position.clear();
	    float winX, winY;


	    GL11.glGetFloat( GL11.GL_MODELVIEW_MATRIX, modelview );
	    GL11.glGetFloat( GL11.GL_PROJECTION_MATRIX, projection );
	    GL11.glGetInteger( GL11.GL_VIEWPORT, viewport );
	    
	    winX = (float)mouseX;
	    winY = /* (float)viewport.get(3) -  */  //Uncomment this if you invert Y
	         (float)mouseY;

	    GL11.glReadPixels(mouseX, (int)winY, 1, 1, GL11.GL_DEPTH_COMPONENT, GL11.GL_FLOAT, winZ);

	    float zz = winZ.get();

	    GLU.gluUnProject(winX, winY, zz, modelview, projection, viewport, position);



	    Vector3f v = new Vector3f (position.get(0),position.get(1),position.get(2));


	    return v; 
	    }



}



