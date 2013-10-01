package OpenGL;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Resourceloader {
	public static ModelVectors loadModel(File f) throws FileNotFoundException,
			IOException {
		BufferedReader reader = new BufferedReader(new FileReader(f));

		ModelVectors m = new ModelVectors();
		String line;
		Texture currentTexture = null;
		while ((line = reader.readLine()) != null) {
			if (line.startsWith("v ")) {
				float x = Float.valueOf(line.split(" ")[1]);
				float y = Float.valueOf(line.split(" ")[2]);
				float z = Float.valueOf(line.split(" ")[3]);
				m.verticies.add(new Vector3f(x, y, z));
			} else if (line.startsWith("vn ")) {
				float x = Float.valueOf(line.split(" ")[1]);
				float y = Float.valueOf(line.split(" ")[2]);
				float z = Float.valueOf(line.split(" ")[3]);
				m.normals.add(new Vector3f(x, y, z));
			} else if (line.startsWith("vt ")) {
				float x = Float.valueOf(line.split(" ")[1]);
				float y = Float.valueOf(line.split(" ")[2]);
				m.texVerticies.add(new Vector2f(x, 1 - y));
			} else if (line.startsWith("f ")) {
				if (line.split(" ").length != 5) {
					Vector3f vertexIndicies = new Vector3f(
							Float.valueOf(line.split(" ")[1].split("/")[0]) - 1,
							Float.valueOf(line.split(" ")[2].split("/")[0]) - 1,
							Float.valueOf(line.split(" ")[3].split("/")[0]) - 1);
					Vector3f textureIndicies = new Vector3f(
							Float.valueOf(line.split(" ")[1].split("/")[1]) - 1,
							Float.valueOf(line.split(" ")[2].split("/")[1]) - 1,
							Float.valueOf(line.split(" ")[3].split("/")[1]) - 1);
					Vector3f normalIndicies = new Vector3f(
							Float.valueOf(line.split(" ")[1].split("/")[2]) - 1,
							Float.valueOf(line.split(" ")[2].split("/")[2]) - 1,
							Float.valueOf(line.split(" ")[3].split("/")[2]) - 1);

					m.faces.add(new Face(vertexIndicies, textureIndicies,
							normalIndicies, currentTexture.getTextureID()));
				}
				else
				{
					Vector4f vertexIndicies = new Vector4f(
							Float.valueOf(line.split(" ")[1].split("/")[0]) - 1,
							Float.valueOf(line.split(" ")[2].split("/")[0]) - 1,
							Float.valueOf(line.split(" ")[3].split("/")[0]) - 1,
							Float.valueOf(line.split(" ")[4].split("/")[0]) - 1);
					Vector4f textureIndicies = new Vector4f(
							Float.valueOf(line.split(" ")[1].split("/")[1]) - 1,
							Float.valueOf(line.split(" ")[2].split("/")[1]) - 1,
							Float.valueOf(line.split(" ")[3].split("/")[1]) - 1,
							Float.valueOf(line.split(" ")[4].split("/")[1]) - 1);
					Vector4f normalIndicies = new Vector4f(
							Float.valueOf(line.split(" ")[1].split("/")[2]) - 1,
							Float.valueOf(line.split(" ")[2].split("/")[2]) - 1,
							Float.valueOf(line.split(" ")[3].split("/")[2]) - 1,
							Float.valueOf(line.split(" ")[4].split("/")[2]) - 1);

					m.faces.add(new Face(vertexIndicies, textureIndicies,
							normalIndicies, currentTexture.getTextureID()));
					
				}
			} else if (line.startsWith("g ")) {
				if (line.length() > 2) {
					String path = f.getParent();
					String name = f.getName().split(".obj")[0];
					name += "Texture";
					
					currentTexture = TextureLoader.getTexture(
					"PNG",
					ResourceLoader.getResourceAsStream(path + "/" + name + ".png" ));
					
					// System.out.println(currentTexture.getTextureID());
				}
			}
		}

		reader.close();

//		System.out.println(m.verticies.size() + " verticies");
//		System.out.println(m.normals.size() + " normals");
//		System.out.println(m.texVerticies.size() + " texture coordinates");
//		System.out.println(m.faces.size() + " faces");
		return m;
	}

	public Texture getTexture(String fileType, String fileName)
			throws FileNotFoundException, IOException {
		return TextureLoader.getTexture(fileType, new FileInputStream(new File(
				fileName)));
	}
}