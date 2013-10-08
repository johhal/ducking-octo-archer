package OpenGL;

import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL11.*;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

public class VertexBufferObject {
	private int nrOfVertices;
	private int vertexSize;
	
	private int vboVertexHandle;
	private int vboColorHandle;
	
	private FloatBuffer vertexData;
	private FloatBuffer colorData;
	
	public VertexBufferObject(float[] vertices, int nrOfVertices, int vertexSize)
	{
		this.nrOfVertices = nrOfVertices;
		this.vertexSize = vertexSize;
		
		vertexData = BufferUtils.createFloatBuffer(this.nrOfVertices*this.vertexSize);
		vertexData.put(vertices);
		vertexData.flip();
		
		colorData =  BufferUtils.createFloatBuffer(this.nrOfVertices*3);
		colorData.put(new float[]{1, 0, 0, 0, 1, 0, 0, 0, 1});
		colorData.flip();
		
		vboVertexHandle = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vboVertexHandle);
		glBufferData(GL_ARRAY_BUFFER, vertexData, GL_STATIC_DRAW);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		glBindVertexArray(0);
		
		vboColorHandle = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vboColorHandle);
		glBufferData(GL_ARRAY_BUFFER, colorData, GL_STATIC_DRAW);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		glBindVertexArray(0);
	}
	
	public void draw()
	{
		
		
//		glBindBuffer(GL_ARRAY_BUFFER, vboColorHandle);
//		glColorPointer(3, GL_FLOAT, 0, 0L);
//		
//		glEnableClientState(GL_VERTEX_ARRAY);
//		glEnableClientState(GL_COLOR_ARRAY);
		
		glBindVertexArray(vboVertexHandle);
		glEnableVertexAttribArray(0);
		
		glDrawArrays(GL_TRIANGLES, 0, vertexSize);

		glDisableVertexAttribArray(0);
		glBindVertexArray(0);
		
//		glDisableClientState(GL_COLOR_ARRAY);
//		glDisableClientState(GL_VERTEX_ARRAY);
		
		
	}
	
	public void close()
	{
		glDisableVertexAttribArray(0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		glDeleteBuffers(vboVertexHandle);
		glDeleteBuffers(vboColorHandle);
		
		glBindVertexArray(0);
		glDeleteVertexArrays(vboVertexHandle);
	}
}
