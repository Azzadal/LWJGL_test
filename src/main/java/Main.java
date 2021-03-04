import Input.Input;
import graphics.Mesh;
import graphics.Renderer;
import graphics.Shader;
import graphics.Vertex;
import maths.Vector3f;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryUtil;
import shaders.ShaderProgram;

import java.nio.FloatBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Main implements Runnable {
    private Thread game;
    private Window window;
    private Renderer renderer;
    private GLFWCursorPosCallback cursorPos;
    private Shader shader;
    private Mesh mesh = new Mesh(new Vertex[]{
            new Vertex(new Vector3f(-0.5f,  0.5f, 0.0f)),
            new Vertex(new Vector3f(-0.5f, -0.5f, 0.0f)),
            new Vertex(new Vector3f( 0.5f, -0.5f, 0.0f)),
            new Vertex(new Vector3f( 0.5f,  0.5f, 0.0f))
    }, new int[] {
            0, 1, 2,
            0, 3, 2
    });
    private float[] vertices = new float[]{
            0.0f,  0.5f, 0.0f,
            -0.5f, -0.5f, 0.0f,
            0.5f, -0.5f, 0.0f
    };

    public static void main(String[] args) {
        Main game  = new Main();
        game.start();
    }

    private void start(){
        game = new Thread(this, "Test app");
        game.start();
    }

    private void init(){
        window = new Window(800, 600, "test");
        shader = new Shader("resources/shaders/mainVertex.glsl", "resources/shaders/mainFragment.glsl");
        renderer = new Renderer(shader);
        window.setBackgroundColor(1.0f, 0, 0);
        window.create();
        mesh.create();
        shader.create();


        FloatBuffer verticesBuffer = MemoryUtil.memAllocFloat(vertices.length);
        verticesBuffer.put(vertices).flip();

        GL.createCapabilities();
        glClearColor(0.56f, 0.258f, 0.425f, 1.0f);
        System.out.println("OpenGL version: " + glGetString(GL_VERSION));
    }


    private void update() throws InterruptedException {
        window.update();
    }

    private void render() {
        renderer.renderMesh(mesh);
        window.swapBuffers();
    }

    @Override
    public void run() {
        init();
        while (!window.shouldClose() && !Input.keys[GLFW_KEY_ESCAPE]){
            try {
                update();
                render();
                if (Input.keys[GLFW_KEY_F11]){
                    window.setFullscreen(!window.isFullscreen());
                    System.out.println("F11");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        window.destroy();
    }
}
