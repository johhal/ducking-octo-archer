package OpenGL;


import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

public class Face {
	public Vector3f vertex = new Vector3f();
	public Vector3f normal = new Vector3f();
	public Vector3f textures = new Vector3f();
	
	public Vector4f vertex4 = new Vector4f();
	public Vector4f normal4 = new Vector4f();
	public Vector4f textures4 = new Vector4f();
	public int texture;

	public Face(Vector3f vertex,Vector3f textures,Vector3f normal,int texture)
	{
		this.vertex = vertex;
		this.normal = normal;
		this.textures = textures;
		this.texture = texture;
		
		this.vertex4 = null;
		this.normal4 = null;
		this.textures4 = null;
	}
	
	public Face(Vector4f vertex4, Vector4f textures4, Vector4f normal4, int texture)
	{
		this.vertex4 = vertex4;
		this.normal4 = normal4;
		this.textures4 = textures4;
		this.texture = texture;
		
		this.vertex = null;
		this.normal = null;
		this.textures = null;
	}
}