package OpenGL;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.*;

public class Camera {
	private float posX;
	private float posY;
	private float posZ;
	
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
		posX = -400;
		posY = -300;
		posZ = -200;
		
		rotX = 0;
		rotY = 0;
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
		
		glTranslatef(posX, posY, posZ);
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
	
	public void moveXY(float ammount, float direction)
	{
		posY += ammount * Math.sin(Math.toRadians(rotY + 90 * direction));
		posX += ammount * Math.cos(Math.toRadians(rotY + 90 * direction));
	}
	
	public void moveXZ(float ammount, float direction)
	{
		posZ += ammount * Math.sin(Math.toRadians(rotY + 90 * direction));
		posX += ammount * Math.cos(Math.toRadians(rotY + 90 * direction));
	}
	
	public float getX()
	{
		return posX;
	}
	
	public float getY()
	{
		return posY;
	}
	
	public float getZ()
	{
		return posZ;
	}
	
	public void setX(float x)
	{
		posX = x;
	}
	
	public void setY(float y)
	{
		posY = y;
	}
	
	public void setZ(float z)
	{
		posZ = z;
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
	
}
