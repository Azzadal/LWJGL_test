package graphics;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL30.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class Renderer {
    private Shader shader;

    public Renderer(Shader shader) {
        this.shader = shader;
    }

    public void renderMesh(Mesh mesh){
        glBindVertexArray(mesh.getVAO());
        glEnableVertexAttribArray(0);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, mesh.getIBO());
        shader.bind();
        glDrawElements(GL_TRIANGLES, mesh.getIndices().length, GL_UNSIGNED_INT, 0);
        shader.unbind();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        glDisableVertexAttribArray(0);
        glBindVertexArray(0);
    }
}
