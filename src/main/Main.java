package main;

import org.lwjgl.glfw.GLFW;

import engine.graphics.Material;
import engine.graphics.Mesh;
import engine.graphics.Renderer;
import engine.graphics.Shader;
import engine.graphics.Vertex;
import engine.io.Input;
import engine.io.Window;
import engine.maths.Vector2f;
import engine.maths.Vector3f;

public class Main implements Runnable {
	private Window window;
	private Renderer renderer;
	private Shader shader;

	private Mesh mesh = new Mesh(new Vertex[] {
			new Vertex(new Vector3f(-0.5f,  0.5f, 0.0f), new Vector3f(1.0f, 1.0f, 0.0f), new Vector2f(0.0f, 0.0f)),
			new Vertex(new Vector3f(-0.5f, -0.5f, 0.0f), new Vector3f(0.78f, 1.0f, 0.0f), new Vector2f(0.0f, 1f)),
			new Vertex(new Vector3f( 0.5f, -0.5f, 0.0f), new Vector3f(0.0f, 0.6f, 1.0f), new Vector2f(0.5f, 1.0f)),
			new Vertex(new Vector3f( 0.5f,  0.5f, 0.0f), new Vector3f(1.0f, 1.0f, 0.0f), new Vector2f(0.5f, 0.0f))
		}, new int[] {
			0, 1, 2,
			0, 2, 3
		}, new Material("/textures/5.png"));
	
	private void start() {
		Thread game = new Thread(this, "game");
		game.start();
	}
	
	private void init() {
		int WIDTH = 1280;
		int HEIGHT = 760;
		window = new Window(WIDTH, HEIGHT, "Game");
		shader = new Shader("/shaders/mainVertex.glsl", "/shaders/mainFragment.glsl");
		renderer = new Renderer(shader);
		window.setBackgroundColor(1.0f, 0, 0);
		window.create();
		mesh.create();
		shader.create();
	}
	
	public void run() {
		init();
		while (!window.shouldClose() && !Input.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) {
			update();
			render();
			if (Input.isKeyDown(GLFW.GLFW_KEY_F11)) window.setFullscreen(!window.isFullscreen());
		}
		close();
	}
	
	private void update() {
		window.update();
		if (Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT)) System.out.println("X: " + Input.getScrollX() + ", Y: " + Input.getScrollY());
	}
	
	private void render() {
		renderer.renderMesh(mesh);
		window.swapBuffers();
	}
	
	private void close() {
		window.destroy();
		mesh.destroy();
		shader.destroy();
	}
	
	public static void main(String[] args) {
		new Main().start();
	}
}