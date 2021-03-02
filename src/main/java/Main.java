import Input.*;
import com.sun.org.apache.xml.internal.serializer.utils.Utils;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import shaders.ShaderProgram;

import java.nio.ByteBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Main implements Runnable {
    private Thread thread;
    private boolean running = true;
    private long window;
    private GLFWKeyCallback keyCallback;
    private GLFWCursorPosCallback cursorPos;
    private ShaderProgram shaderProgram;

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
        shaderProgram.createVertexShader(Utils.loadResource("/vertex.vs"));
        shaderProgram.createFragmentShader(Utils.loadResource("/fragment.fs"));
        shaderProgram.link();
    }

    @Override
    public void run() {
        init();
        while (running){
            try {
                update();
                render();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                shaderProgram.cleanup();
            }

            if (glfwWindowShouldClose(window)) running = false;
        }
        keyCallback.free();
    }
}
