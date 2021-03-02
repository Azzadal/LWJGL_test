import org.lwjgl.glfw.GLFWVidMode;

import java.nio.ByteBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Main implements Runnable {
    private Thread thread;
    private boolean running = true;
    private long window;

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

        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

        glfwSetWindowPos(window, 100, 100);

        glfwMakeContextCurrent(window);

        glfwShowWindow(window);
    }

    public void update(){
        glfwPollEvents();
    }

    public void render(){
        glfwSwapBuffers(window);
    }

    @Override
    public void run() {
        init();
        while (running){
            update();
            render();

            if (glfwWindowShouldClose(window)) running = false;
        }
    }
}
