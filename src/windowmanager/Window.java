package windowmanager;

import utilities.Input;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * Created by Dennis Dubbert on 06.09.16.
 */
public abstract class Window {
	
	protected long window;
	protected int width, height;

	public Window(int width, int height) {
		this.width = width;
		this.height = height;
		start();
	}

	private void init() {
		if (glfwInit() != GL_TRUE) {
			System.err.println("Could not initialize our glfw!");
			return;
		}

		glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);

		window = glfwCreateWindow(width, height, "My Game", NULL, NULL);
		glfwSetWindowPos(window, 80, 30);

		if (window == NULL) {
			System.err.println("Could not create our Window!");
			return;
		}

		Input.init(window, this);
		glfwMakeContextCurrent(window);
		glfwShowWindow(window);

		GL.createCapabilities();

		System.out.println("Your OpenGL version is " + glGetString(GL_VERSION));
		onInit();
	}

	public void start() {
		init();

		while (true) {

			update();
			render();

			if (glfwWindowShouldClose(window) == GL_TRUE) {
				break;
			}
		}

		glfwDestroyWindow(window);
		glfwTerminate();
	}

	private void update() {
		glfwPollEvents();
		onUpdate(glfwGetTime());
	}

	private void render() {
		onRender(glfwGetTime());
		glfwSwapBuffers(window);
	}

	protected abstract void onInit();

	protected abstract void onUpdate(double currentTime);

	protected abstract void onRender(double currentTime);

	public abstract void onResize(int width, int height);

	public abstract void onKeyboard(float cursorPositionX, float cursorPositionY, int pressedKey, int mods,
			boolean pressed);

	public abstract void onMouse(float cursorPositionX, float cursorPositionY, int button, int action);

}
