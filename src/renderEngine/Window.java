package renderEngine;

import org.lwjgl.glfw.GLFW;

public class Window {

	private int width, height;
	private String title;
	private long window;
	
	
	public Window(int width, int height, String title) {
		this.width = width;
		this.height = height;
		this.title = title;
	}
	
	public void create() {
		if(!GLFW.glfwInit()) {
			System.err.println("GLFW wasnt initialized");
			System.exit(-1);
		}
		window = GLFW.glfwCreateWindow(width, height, title, 0, 0);
		if(window == 0) {
			System.err.println("Window wasnt created");
			System.exit(-1);
		}
		GLFW.glfwShowWindow(window);
	}
	
	public void update() {
		GLFW.glfwPollEvents();
		GLFW.glfwSwapBuffers(window);
	}
	
	public boolean shouldClose() {
		return GLFW.glfwWindowShouldClose(window);
	}
	
	
}
