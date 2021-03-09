package engine.graphics;

import org.joml.Vector4f;

import static org.lwjgl.opengl.GL46C.*;

public class Renderer {
	private Shader shader;
	private Material material;
	
	public Renderer(Shader shader, Material material) {
		this.shader = shader;
		this.material = material;
	}
	
	public void renderMesh(Mesh mesh) {
		glBindVertexArray(mesh.getVAO());
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glEnableVertexAttribArray(2);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, mesh.getIBO());
		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, mesh.getMaterial().getTextureID());
		material.bind();
		shader.bind();
		shader.setUniformVec4("u_Colour", new Vector4f(1, 1, 0, 1));
		glDrawElements(GL_TRIANGLES, mesh.getIndices().length, GL_UNSIGNED_INT, 0);
		shader.unbind();
		material.unbind();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		glDisableVertexAttribArray(2);
		glBindVertexArray(0);
	}
}