package OpenGL;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.*;

import org.lwjgl.util.vector.Vector3f;

public class Camera {
	private Vector3f position;
	
	private float rotX;
	private float rotY;
	private float rotZ;

	private float fov; //Field of View
	private float aspect;//Aspect ratio avgör hur openGl ska göra om fov till en rektangel. typ

	private float near; 
	private float far;
	// near clipping plane
	//säger hur nära saker får vara för att ritas 
	//(Tar bort saker som är för nära.) (far clipping view tar bort för långt bort.)
	//Det är denna som avgör vad som ska klippas bort, 
	//gör allt snabbare, och mindre buggigt :)	

	public void initialize(float fov, float aspect, float near, float far)
	{
		position = new Vector3f();
		position.x = -20;
		position.y = -30;
		position.z = -10;

		rotX = 0;
		rotY = 180;
		rotZ = 0;

		this.fov = fov;
		this.aspect = aspect;
		this.near = near;
		this.far = far;

		initProjection();
	}

	private void initProjection()
	{
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		gluPerspective(fov, aspect, near, far);
		glMatrixMode(GL_MODELVIEW);
	}

	public void setView()
	{
		glRotatef(rotX, 1, 0, 0);
		glRotatef(rotY, 0, 1, 0);
		glRotatef(rotZ, 0, 0, 1);

		glTranslatef(position.x, position.y, position.z);
	}

	public void rotateX(float ammount)
	{
		rotX += ammount;
	}

	public void rotateY(float ammount)
	{
		rotY += ammount;
	}

	public void rotateZ(float ammount)
	{
		rotZ += ammount;
	}

	public void moveForward(float ammount)
	{
		float angle = rotY;
		Vector3f newPosition = new Vector3f(position);
		float v = ammount;
		float katAdjacent = v * (float)Math.cos(Math.toRadians(angle));
		float katFar = (float)Math.sin(Math.toRadians(angle) * v);
		
		newPosition.x -= katFar;
		newPosition.z += katAdjacent;
		
		position.x = newPosition.x;
		position.z = newPosition.z;
	}

	public void moveSideways(float ammount)
	{
		float angle = rotY-90;
		Vector3f newPosition = new Vector3f(position);
		float v = ammount;
		float katAdjacent = v * (float)Math.cos(Math.toRadians(angle));
		float katFar = (float)Math.sin(Math.toRadians(angle) * v);
		
		newPosition.x -= katFar;
		newPosition.z += katAdjacent;
		
		position.x = newPosition.x;
		position.z = newPosition.z;
	}

	public float getRotX()
	{
		return rotX;
	}

	public float getRotY()
	{
		return rotY;
	}

	public float getRotZ()
	{
		return rotZ;
	}

	public void setRotX(float x)
	{
		rotX = x;
	}

	public void setRotY(float y)
	{
		rotY = y;
	}

	public void setRotZ(float z)
	{
		rotZ = z;
	}
	
	public Vector3f getPosition()
	{
		return new Vector3f(position.x, position.y, position.z);
	}
	
	public void setPosition(Vector3f position){
		this.position = position;
	}

}