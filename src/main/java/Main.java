import Input.CursorInput;
import Input.Input;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryUtil;
import shaders.ShaderProgram;

import java.nio.FloatBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.opengl.GL30C.glBindVertexArray;
import static org.lwjgl.system.MemoryUtil.NULL;
import static org.lwjgl.system.MemoryUtil.memFree;

public class Main implements Runnable {
    private Thread thread;
    private boolean running = true;
    private long window;
    private GLFWKeyCallback keyCallback;
    private GLFWCursorPosCallback cursorPos;
    private ShaderProgram shaderProgram;

    float[] vertices = new float[]{
            0.0f,  0.5f, 0.0f,
            -0.5f, -0.5f, 0.0f,
            0.5f, -0.5f, 0.0f
    };

    public static void main(String[] args) {
        Main game  = new Main();
        game.start();
    }

    public void start(){
        running = true;
        thread = new Thread(this, "Test app");
        thread.start();
    }

    public void init(){
        if (!glfwInit()) System.err.println("Failed");

        glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);

        window = glfwCreateWindow(800, 600, "My App", NULL, NULL);

        glfwSetCursorPosCallback(window, cursorPos = new CursorInput());

        glfwSetKeyCallback(window, keyCallback = new Input());

        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

        glfwSetWindowPos(window, 100, 100);

        glfwMakeContextCurrent(window);

        glfwSwapInterval(1);

        glfwShowWindow(window);

        GL.createCapabilities();
        glClearColor(0.56f, 0.258f, 0.425f, 1.0f);
        System.out.println("OpenGL version: " + glGetString(GL_VERSION));


        FloatBuffer verticesBuffer = MemoryUtil.memAllocFloat(vertices.length);
        verticesBuffer.put(vertices).flip();

        int vaoId = glGenVertexArrays();
        glBindVertexArray(vaoId);

        int vboId = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboId);
        glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);
        memFree(verticesBuffer);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

        // Unbind the VBO
        glBindBuffer(GL_ARRAY_BUFFER, 0);

// Unbind the VAO
        glBindVertexArray(0);

        if (verticesBuffer != null) {
            MemoryUtil.memFree(verticesBuffer);
        }

    }

    float c = 0.01f, d;

    public void update() throws InterruptedException {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glfwPollEvents();
        d = c;
        if (Input.keys[GLFW_KEY_SPACE]){
            glClearColor(c, 0.258f, d, 1.0f);
            Thread.sleep(100);
            System.out.println("Нажат пробел");
            c += 0.01f;
            d -= 0.01f;
        }
        if (Input.keys[GLFW_KEY_D]){
            glClearColor(c, 0.258f, 0.125f, d);
            Thread.sleep(100);
            System.out.println("Нажат D");
            c -= 0.01f;
            d += 0.01f;
        }
    }

    public void render() throws Exception {
        glfwSwapBuffers(window);
        shaderProgram = new ShaderProgram();
        shaderProgram.createVertexShader(Utils.loadResource("src\\main\\java\\shaders\\vertex.vs"));
        shaderProgram.createFragmentShader(Utils.loadResource("src\\main\\java\\shaders\\fragment.fs"));
        shaderProgram.link();
        shaderProgram.bind();


    }

    @Override
    public void run() {
        init();
        while (running){
            try {
                update();
                render();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                shaderProgram.cleanup();
            }

            if (glfwWindowShouldClose(window)) running = false;
        }
        keyCallback.free();
    }
}
