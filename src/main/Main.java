package main;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import static org.lwjgl.opengl.GL46C.*;
import engine.graphics.Material;
import engine.graphics.Mesh;
import engine.graphics.Renderer;
import engine.graphics.Shader;
import engine.graphics.Vertex;
import engine.io.Input;
import engine.io.Window;

public class Main implements Runnable {
	private Window window;
	private Renderer renderer;
	private Shader shader;
	private Material material1 = new Material("resources/textures/whts.png");

	private Mesh mesh1 = new Mesh(new Vertex[] {
			new Vertex(new Vector3f(-0.5f, 0.5f, 0), new Vector3f(1.0f, 1.0f, 0f), new Vector2f(0.0f, 0.0f)),
			new Vertex(new Vector3f(-0.5f, -0.5f, 0.0f), new Vector3f(0.78f, 1.0f, 0.0f), new Vector2f(0.0f, 1.0f)),
			new Vertex(new Vector3f( 0.5f, -0.5f, 0.0f), new Vector3f(0.0f, 0.6f, 1.0f), new Vector2f(1.0f, 1.0f)),
			new Vertex(new Vector3f( 0.5f,  0.5f, 0.0f), new Vector3f(1.0f, 1.0f, 0.0f), new Vector2f(1.0f, 0.0f))
		}, new int[] {
			0, 1, 2,
			0, 2, 3
		}, material1,
			new Vector3f(0, 0, 0), new Vector3f(0, 0, 0));
	
	private void start() {
		Thread game = new Thread(this, "game");
		game.start();
	}
	
	private void init() {
		int WIDTH = 1280;
		int HEIGHT = 760;
		window = new Window(WIDTH, HEIGHT, "Game");
		shader = new Shader("/shaders/mainVertex.glsl", "/shaders/mainFragment.glsl");

		renderer = new Renderer(shader, material1);
		window.setBackgroundColor(1.0f, 0, 0);
		window.create();
		shader.bind();
		shader.setUniformInt("u_TextureSampler", 0);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		mesh1.create();
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
		renderer.renderMesh(mesh1);
		window.swapBuffers();
	}
	
	private void close() {
		window.destroy();
		mesh1.destroy();
		shader.destroy();
	}
	
	public static void main(String[] args) {
		new Main().start();
	}
}