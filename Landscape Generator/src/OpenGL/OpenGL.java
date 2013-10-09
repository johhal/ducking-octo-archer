package OpenGL;

import java.awt.Point;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;

import static org.lwjgl.opengl.GL11.*;

public class OpenGL {
	private int screen_width;
	private int screen_height;
	private int tileHeight;
	private String title = "Ducking-Octo-Archer";

	private int tileSize;
	private int difTileObject;

	private float startTileX;
	private float startTileY;
	private int spaceBetweenTiles;

	ArrayList<Texture> textures;
	Resourceloader resourceLoader;

	private float GUIX;

	private long last_frame;
	private int fps;
	private long last_fps;

	ArrayList<Model> models;

	ArrayList<VertexBufferObject> vboArrayList;

	private Camera camera;

	public FloatBuffer floatBuffer(float a, float b, float c, float d) {
		float[] data = new float[] { a, b, c, d };
		FloatBuffer fb = BufferUtils.createFloatBuffer(data.length);
		fb.put(data);
		fb.flip();
		return fb;
	}

	public void drawBox(float x, float y, float z, float boxSizeX,
			float boxSizeY, float boxSizeZ) {
		// front Face
		glNormal3f(0, 0, 1.0f);
		GL11.glTexCoord2f(0, 1);
		glVertex3f(x, y + boxSizeY, z + boxSizeZ);
		GL11.glTexCoord2f(0, 0);
		glVertex3f(x + boxSizeX, y + boxSizeY, z + boxSizeZ);
		GL11.glTexCoord2f(1, 0);
		glVertex3f(x + boxSizeX, y, z + boxSizeZ);
		GL11.glTexCoord2f(1, 1);
		glVertex3f(x, y, z + boxSizeZ);

		//Right Face
		glNormal3f(-1.0f, 0, 0);
		GL11.glTexCoord2f(1, 0);
		glVertex3f(x + boxSizeX, y + boxSizeY, z + boxSizeZ);
		GL11.glTexCoord2f(1, 1);
		glVertex3f(x + boxSizeX, y + boxSizeY, z);
		GL11.glTexCoord2f(0, 1);
		glVertex3f(x + boxSizeX, y, z);
		GL11.glTexCoord2f(0, 0);
		glVertex3f(x + boxSizeX, y, z + boxSizeZ);

		//Back Face
		glNormal3f(0, 0, -1.0f);
		GL11.glTexCoord2f(0, 1);
		glVertex3f(x, y + boxSizeY, z);
		GL11.glTexCoord2f(0, 0);
		glVertex3f(x + boxSizeX, y + boxSizeY, z);
		GL11.glTexCoord2f(1, 0);
		glVertex3f(x + boxSizeX, y, z);
		GL11.glTexCoord2f(1, 1);
		glVertex3f(x, y, z);

		//Left Face
		glNormal3f(1.0f, 0, 0);
		GL11.glTexCoord2f(1, 0);
		glVertex3f(x, y + boxSizeY, z + boxSizeZ);
		GL11.glTexCoord2f(1, 1);
		glVertex3f(x, y + boxSizeY, z);
		GL11.glTexCoord2f(0, 1);
		glVertex3f(x, y, z);
		GL11.glTexCoord2f(0, 0);
		glVertex3f(x, y, z + boxSizeZ);

		//Top Face
		glNormal3f(0, 1.0f, 0);
		GL11.glTexCoord2f(0, 1);
		glVertex3f(x + boxSizeX, y + boxSizeY, z);
		GL11.glTexCoord2f(1, 1);
		glVertex3f(x, y + boxSizeY, z);
		GL11.glTexCoord2f(1, 0);
		glVertex3f(x, y + boxSizeY, z + boxSizeZ);
		GL11.glTexCoord2f(0, 0);
		glVertex3f(x + boxSizeX, y + boxSizeY, z + boxSizeZ);

		//Bottom Face		
		glNormal3f(0, -1.0f, 0);
		GL11.glTexCoord2f(0, 1);
		glVertex3f(x + boxSizeX, y, z);
		GL11.glTexCoord2f(1, 1);
		glVertex3f(x, y, z);
		GL11.glTexCoord2f(1, 0);
		glVertex3f(x, y, z + boxSizeZ);
		GL11.glTexCoord2f(0, 0);
		glVertex3f(x + boxSizeX, y, z + boxSizeZ);
	}

	public void convertAndDraw(int pX, int pY, short texture, short notTile) throws FileNotFoundException, IOException{
		textures.get(texture).bind();
		glBegin(GL_QUADS);

		float posX = startTileX + notTile*(float)difTileObject/2 + (pX * (tileSize + spaceBetweenTiles));
		float posZ = startTileY + notTile*(float)difTileObject/2 + (pY * (tileSize + spaceBetweenTiles));

		drawBox(posX, tileHeight + tileHeight*notTile, posZ, tileSize - notTile*difTileObject, tileSize - notTile*difTileObject, tileSize - notTile*difTileObject);
		glEnd();
	}

	public void drawGUI() {
		GL11.glTexCoord2f(0, 1);
		glVertex2f(0, 0);
		GL11.glTexCoord2f(0, 0);
		glVertex2f(0, screen_height);
		GL11.glTexCoord2f(1, 0);
		glVertex2f(screen_width, 0);
		GL11.glTexCoord2f(1, 1);
		glVertex2f(screen_width, screen_height);

		/*
		 * int nrOfTeams = guiHandler.getNrOfTeams(); int nrOfObjects =
		 * guiHandler.getNrOfObjects();
		 * 
		 * glColor3f(0.5f, 0.5f, 0.5f);
		 * 
		 * float guiX = posX-(screen_width/2.0f);//(posX/(screen_width/2.0f));
		 * float guiY =
		 * posY-(screen_height/2.0f);//*(posY/(screen_height/2.0f)); float guiZ
		 * = posZ;
		 * 
		 * glVertex3f(guiX + GUIX, guiY + screen_height, guiZ); glVertex3f(guiX,
		 * guiY + screen_height, guiZ); glVertex3f(guiX, guiY, guiZ);
		 * glVertex3f(guiX + GUIX, guiY, guiZ);
		 * 
		 * for(int i = 0; i < nrOfTeams; i++) { if(i+1 ==
		 * guiHandler.getSelectedTeam()) { glColor3f(0.0f, 0.0f, 0.0f);
		 * 
		 * glVertex2f(10 + i * ((GUIX - 10)/nrOfTeams) - 2, 590 + 2);
		 * glVertex2f(10 + i * ((GUIX - 10)/nrOfTeams) + 20 + 2, 590 + 2);
		 * glVertex2f(10 + i * ((GUIX - 10)/nrOfTeams) + 20 + 2, 570 -2);
		 * glVertex2f(10 + i * ((GUIX - 10)/nrOfTeams) -2 , 570 - 2); }
		 * 
		 * glColor3f(0.5f * i, 0.6f / i, 0.5f); glVertex2f(10 + i * ((GUIX -
		 * 10)/nrOfTeams), 590); glVertex2f(10 + i * ((GUIX - 10)/nrOfTeams) +
		 * 20, 590); glVertex2f(10 + i * ((GUIX - 10)/nrOfTeams) + 20, 570);
		 * glVertex2f(10 + i * ((GUIX - 10)/nrOfTeams), 570); }
		 */
	}


	// småstuff
	public boolean isRunning() {
		return !Display.isCloseRequested();
	}

	public void quitGL() {
		Display.destroy();
		System.exit(0);
	}

	public int getDelta() {
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
		if(getTime() - last_fps > 1000){			
			fps = 0;

			last_fps += 1000;
		}

		fps++;
	}

	private void loadTextures() throws FileNotFoundException, IOException {
		textures.add(resourceLoader.getTexture("PNG",
				"resources/landscapes/water.png"));// = );
		textures.add(resourceLoader.getTexture("PNG",
				"resources/landscapes/forest.png")); // =
														// resourceLoader.getTexture("PNG",
														// "resources/landscapes/forest.png");
		textures.add(resourceLoader.getTexture("PNG",
				"resources/landscapes/plains.png"));// =
													// resourceLoader.getTexture("PNG",
													// "resources/landscapes/plains.png");
		textures.add(resourceLoader.getTexture("PNG",
				"resources/landscapes/mountain.png"));// =
														// resourceLoader.getTexture("PNG",
														// "resources/landscapes/mountain.png");
		textures.add(resourceLoader.getTexture("PNG",
				"resources/house_button_logo.png")); // =
														// resourceLoader.getTexture("PNG",
														// "resources/house_button_logo.png");
		textures.add(resourceLoader.getTexture("PNG",
				"resources/human_button_logo.png"));// =
													// resourceLoader.getTexture("PNG",
													// "resources/human_button_logo.png");
		textures.add(resourceLoader.getTexture("PNG",
				"resources/zombie_button_logo.png")); // =
														// resourceLoader.getTexture("PNG",
														// "resources/zombie_button_logo.png");
	}

	// initsiering
	public void initialize(int screenWidth, int screenHeight, int tileSize,
			int GUIWidth) throws LWJGLException, FileNotFoundException,
			IOException {
		
		screen_width = screenWidth;
		screen_height = screenHeight;

		this.tileSize = tileSize;
		difTileObject = 1;

		startTileX = 0;
		startTileY = 0;
		tileHeight = 2;

		spaceBetweenTiles = 0;

		GUIX = GUIWidth;

		initDisplay();

		initGL();

		getDelta();

		last_fps = getTime();

		textures = new ArrayList<Texture>();
		resourceLoader = new Resourceloader();
		loadTextures();

		vboArrayList = new ArrayList<VertexBufferObject>();

		float [] asd = {10f, 10f, 10f,
						10f, 10f, 20f,
						10f, 20f, 10f};
						/*10f, 20f, 20f,
						20f, 10f, 10f,
						20f, 10f, 20f,
						20f, 20f, 10f,
						20f, 20f, 20f};*/


		vboArrayList.add(new VertexBufferObject(asd, 3, 3));

		models = new ArrayList<Model>();

		models.add(new Model("resources/ratbandit/Rat_bandit.obj"));

		//models.add(new Model("resources/kub.obj"));

		//models.add(new Model("resources/HEro_pj/amulett/amulett.obj"));

	}

	private void initDisplay() throws LWJGLException {
		Display.setDisplayMode(new DisplayMode(screen_width, screen_height));
		Display.create();

		Display.setTitle(title);
	}

	private void initGL() {
		glViewport(0, 0, screen_width, screen_height);

		glClearColor(0.0f, 0.0f, 0.0f, 0.5f);
		glClearDepth(1.0f);

		glDepthFunc(GL_LEQUAL);
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_TEXTURE_2D);
		glShadeModel(GL_SMOOTH);

		glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);

		initLight(100, 200, 100);		

		camera = new Camera();
		camera.initialize(70f, (float) screen_width / screen_height, 0.3f,
				1000f); // , posX, posY, posZ);
	}


	// Ljus
	public void setLight(float xPos, float yPos, float zPos) {
		// ljusets position
		glLight(GL11.GL_LIGHT0, GL11.GL_POSITION,
				floatBuffer(xPos, yPos, zPos, 1));

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


	private int mathExp(int base, int exp) {
		int tmp = base;
		for (int i = 0; i < exp; i++)
			tmp *= base;
		if (exp == 0)
			tmp = 1;
		return tmp;
	}

	private void translateInput(int input, int boardSizeX, int boardSizeY) {
		if (input != 0) {
			if (input / mathExp(10, 7) != 0) {
				// C
				input -= mathExp(10, 7);
				camera.moveSideways(-1f);
			}
			if (input / mathExp(10, 6) != 0) {
				// Z
				input -= mathExp(10, 6);
				camera.moveSideways(1f);

			}
			if (input / mathExp(10, 5) != 0) {
				// E
				input -= mathExp(10, 5);
				camera.moveForward(-1f);
			}
			if (input / mathExp(10, 4) != 0) {
				// Q
				input -= mathExp(10, 4);
				camera.moveForward(1f);

			}
			if (input / mathExp(10, 3) != 0) {
				// S
				input -= mathExp(10, 3);
				camera.rotateX(1);
			}
			if (input / mathExp(10, 2) != 0) {
				// W
				input -= mathExp(10, 2);
				camera.rotateX(-1);
			}
			if (input / mathExp(10, 1) != 0) {
				// D
				input -= mathExp(10, 1);
				camera.rotateY(1f);
			}
			if (input / mathExp(10, 0) != 0) {
				// A
				input -= mathExp(10, 0);
				camera.rotateY(-1f);
			}
		}

		Vector3f position = camera.getPosition();
		if(position.x > startTileX){
			position.x = startTileX;
		}
		if (position.x < -(boardSizeX*(tileSize + spaceBetweenTiles) - startTileX)){
			position.x = -(boardSizeX*(tileSize + spaceBetweenTiles) - startTileX); 
		}
		if(position.z > startTileY){
			position.z = startTileY;
		}
		if(position.z < -(boardSizeY*(tileSize + spaceBetweenTiles) - startTileY)){
			position.z = -(boardSizeY*(tileSize + spaceBetweenTiles) - startTileY);
		}
		camera.setPosition(position);
	}


	//Updatera
	public Point update(int delta, int input, Point p, int boardSizeX, int boardSizeY){
		Point p2 = new Point(p.x, p.y);
		translateInput(input, boardSizeX, boardSizeY);
		if (p2.x != -1 || p2.y != -1) {
			Vector3f v = getMousePositionIn3dCoords(p2.x, p2.y);
			p2.x = (int) (v.x + startTileX) / (tileSize + spaceBetweenTiles);
			p2.y = (int) (v.z + startTileY) / (tileSize + spaceBetweenTiles);
		}
		updateFPS();

		return p2;
	}


	// Rita
	public void initDraw(int boardSizeX, int boardSizeY) {
		glViewport(0, 0, screen_width, screen_height);

		glClearColor(1.0f, 0.0f, 0.0f, 0.5f);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		glLoadIdentity();

		camera.setView();

		glPushMatrix();

		glTranslatef(0, -10, 0);

		textures.get(0).bind();
		glBegin(GL_QUADS);

		float differ = 0.5f;
		drawBox(startTileX - differ, 0, startTileY - differ, 
				boardSizeX * (tileSize + spaceBetweenTiles) + differ * 2, // x
				tileHeight + 60, // y
				boardSizeY * (tileSize + spaceBetweenTiles) + differ * 2);// z

		glEnd();

		for (VertexBufferObject vbo : vboArrayList) {
			vbo.draw();
		}

		// for(Model model : models){
		// model.draw();
		// }
		glPopMatrix();
	}

	public void endDraw() {

		// Ritar ut på skärmen
		Display.update();

		// Sätter fpsen till 60
		// Display.sync(60);
	}

	static IntBuffer viewport = BufferUtils.createIntBuffer(16);
	static FloatBuffer modelview = BufferUtils.createFloatBuffer(16);
	static FloatBuffer projection = BufferUtils.createFloatBuffer(16);
	static FloatBuffer winZ = BufferUtils.createFloatBuffer(20);
	static FloatBuffer position = BufferUtils.createFloatBuffer(3);

	static public Vector3f getMousePositionIn3dCoords(int mouseX, int mouseY) {

		viewport.clear();
		modelview.clear();
		projection.clear();
		winZ.clear();
		position.clear();
		float winX, winY;


		GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, modelview);
		GL11.glGetFloat(GL11.GL_PROJECTION_MATRIX, projection);
		GL11.glGetInteger(GL11.GL_VIEWPORT, viewport);

		winX = (float) mouseX;
		winY = /* (float)viewport.get(3) - */// Uncomment this if you invert Y
		(float) mouseY;

		GL11.glReadPixels((int) winX, (int) winY, 1, 1,
				GL11.GL_DEPTH_COMPONENT, GL11.GL_FLOAT, winZ);

		float zz = winZ.get();

		GLU.gluUnProject(winX, winY, zz, modelview, projection, viewport,
				position);

		Vector3f v = new Vector3f(position.get(0), position.get(1),
				position.get(2));

		return v;
	}

	public int getTileSize() {
		return tileSize;
	}

	public int getSpaceBetweenTiles() {
		return spaceBetweenTiles;
	}
}
