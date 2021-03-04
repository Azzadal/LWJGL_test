import maths.Vector3f;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWVidMode;
import Input.Input;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwGetPrimaryMonitor;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;

public class Window {
    private int width;
    private int height;
    private String title;
    private Vector3f background = new Vector3f(0, 0, 0);
    private long window;
    private int frames;
    private long time;
    private GLFWKeyCallback keyCallback;
    private GLFWWindowSizeCallback sizeCallback;
    private boolean isResized;
    private boolean isFullscreen;
    private int[] windowPosX = new int[1], windowPosY = new int[1];

    public Window(int width, int height, String title) {
        this.width = width;
        this.height = height;
        this.title = title;
    }

    public void create(){
        if (!glfwInit()){
            System.err.println("Failed!");
        }
        window = glfwCreateWindow(width, height, title, isFullscreen ? glfwGetPrimaryMonitor() : 0, 0);
        GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        windowPosX[0] = (videoMode.width() - width) / 2;
        windowPosY[0] = (videoMode.height() - height) / 2;
        glfwSetWindowPos(window, (videoMode.width() - width) / 2, (videoMode.height() - height) / 2);
        glfwShowWindow(window);
        glfwSwapInterval(1);
        glfwMakeContextCurrent(window);
        GL.createCapabilities();
        glEnable(GL_DEPTH_TEST);
        time = System.currentTimeMillis();
        createCallBacks();
    }
    float c = 0.01f, d;
    public void update() throws InterruptedException {
        if (isResized){
            glViewport(0, 0, width, height);
            isResized = false;
        }
        glClearColor(background.getX(), background.getY(), background.getZ(), 1.0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glfwPollEvents();
        frames++;
        if (System.currentTimeMillis() > time + 1000){
            glfwSetWindowTitle(window, title + " | FPS: " + frames);
            time = System.currentTimeMillis();
            frames = 0;
        }

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

    private void createCallBacks(){
        keyCallback = new Input();
        glfwSetKeyCallback(window, keyCallback);
        sizeCallback = new GLFWWindowSizeCallback() {
            @Override
            public void invoke(long window, int w, int h) {
                width = w;
                height = h;
                isResized = true;
            }
        };
        glfwSetWindowSizeCallback(window, sizeCallback);
    }

    public void swapBuffers(){
        glfwSwapBuffers(window);
    }

    public boolean shouldClose(){
        return glfwWindowShouldClose(window);
    }

    public void destroy(){
        System.out.println("Destroyed");
        glfwWindowShouldClose(window);
        glfwDestroyWindow(window);
        glfwTerminate();
        keyCallback.free();
    }

    public void setBackgroundColor(float r, float g, float b){
        background.set(r, g, b);
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getWindow() {
        return window;
    }

    public void setWindow(long window) {
        this.window = window;
    }

    public boolean isFullscreen() {
        return isFullscreen;
    }

    public void setFullscreen(boolean fullscreen) {
        isFullscreen = fullscreen;
        isResized = true;
        if (isFullscreen) {
            glfwGetWindowPos(window, windowPosX, windowPosY);
            glfwSetWindowMonitor(window, glfwGetPrimaryMonitor(), 0, 0, width, height, 0);
        } else {
            glfwSetWindowMonitor(window, 0, windowPosX[0], windowPosY[0], width, height, 0);
        }
    }
}
