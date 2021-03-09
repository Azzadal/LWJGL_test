package engine.graphics;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL46C.*;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.system.MemoryUtil;

public class Mesh {
	private Vertex[] vertices;
	private int[] indices;
	private Material material;
	private int vao, pbo, ibo, cbo, tbo;
	protected Vector3f rotation;
	protected Vector3f scale;
	
	public Mesh(Vertex[] vertices, int[] indices, Material material, Vector3f rotation, Vector3f scale) {
		this.vertices = vertices;
		this.indices = indices;
		this.material = material;
		this.rotation = new Vector3f(rotation);
		this.scale = new Vector3f(scale);
	}

	public void create() {
		material.create();
		
		vao = glCreateVertexArrays();
		glBindVertexArray(vao);
		
		FloatBuffer positionBuffer = MemoryUtil.memAllocFloat(vertices.length * 3);
		float[] positionData = new float[vertices.length * 3];
		for (int i = 0; i < vertices.length; i++) {
			positionData[i * 3] = vertices[i].getPosition().x;
			positionData[i * 3 + 1] = vertices[i].getPosition().y;
			positionData[i * 3 + 2] = vertices[i].getPosition().z;
		}
		positionBuffer.put(positionData).flip();
		
		pbo = storeData(positionBuffer, 0, 3);
		
		FloatBuffer colorBuffer = MemoryUtil.memAllocFloat(vertices.length * 3);
		float[] colorData = new float[vertices.length * 3];
		for (int i = 0; i < vertices.length; i++) {
			colorData[i * 3] = vertices[i].getColor().x;
			colorData[i * 3 + 1] = vertices[i].getColor().y;
			colorData[i * 3 + 2] = vertices[i].getColor().z;
		}
		colorBuffer.put(colorData).flip();
		
		cbo = storeData(colorBuffer, 1, 3);
		
		FloatBuffer textureBuffer = MemoryUtil.memAllocFloat(vertices.length * 2);
		float[] textureData = new float[vertices.length * 2];
		for (int i = 0; i < vertices.length; i++) {
			textureData[i * 2] = vertices[i].getTextureCoord().x;
			textureData[i * 2 + 1] = vertices[i].getTextureCoord().y;
		}
		textureBuffer.put(textureData).flip();
		
		tbo = storeData(textureBuffer, 2, 2);
		
		IntBuffer indicesBuffer = MemoryUtil.memAllocInt(indices.length);
		indicesBuffer.put(indices).flip();
		
		ibo = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
	}
	
	private int storeData(FloatBuffer buffer, int index, int size) {
		int bufferID = glCreateBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, bufferID);
		glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
		glVertexAttribPointer(index, size, GL_FLOAT, false, 0, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		return bufferID;
	}

	public Matrix4f getModelMatrix(){
		Matrix4f modelMatrix = new Matrix4f().translate(vertices[0].getPosition());
	}
	
	public void destroy() {
		glDeleteBuffers(pbo);
		glDeleteBuffers(cbo);
		glDeleteBuffers(ibo);
		glDeleteBuffers(tbo);
		
		glDeleteVertexArrays(vao);
		
		material.destroy();
	}

	public Vertex[] getVertices() {
		return vertices;
	}

	public int[] getIndices() {
		return indices;
	}

	public int getVAO() {
		return vao;
	}

	public int getPBO() {
		return pbo;
	}
	
	public int getCBO() {
		return cbo;
	}
	
	public int getTBO() {
		return tbo;
	}

	public int getIBO() {
		return ibo;
	}
	
	public Material getMaterial() {
		return material;
	}
}