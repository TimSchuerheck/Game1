package windowmanager;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;


import entities.Scene;
import utilities.Input;

//VM-Options for MAC: -Djava.awt.headless=true -Djava.library.path=libs/LWJGL/native -XstartOnFirstThread

/**
 * Created by Dennis Dubbert on 11.09.16.
 */
public class MyWindow extends Window {
	private Scene scene;
	public static int windowWidth, windowHeight;
	
	public MyWindow(int width, int height) {
		super(width, height);
		windowWidth = width;
		windowHeight = height;
	}

	// Gets called once after the window is created //
	// e.g. for setting up the scene and OpenGL parameters (e.g. glClearColor,...)
	// //
	public void onInit() {
		glClearColor(1, 1, 1, 1);
		glEnable(GL_CULL_FACE);
		glCullFace(GL_BACK);
		glEnable(GL_DEPTH_TEST);
		glViewport(0, 0, width, height);
		scene = new Scene();
	}

	double lastTimeUpdate = 0;
	public void onUpdate(double currentTime) {
		scene.updatePerspective(width, height);
		scene.updateCamera(width, height);
		if(currentTime > 0.01f + lastTimeUpdate){
			double deltaTime = currentTime - lastTimeUpdate;
			lastTimeUpdate = currentTime;
			scene.updatePerspective(width, height);
			scene.updateCamera(width, height);
			scene.update(deltaTime);
			checkKeys(deltaTime);
			scene.camera.checkForMovement(deltaTime);
		}
	}

	
	private void checkKeys(double deltaTime) {
		if (Input.keys[GLFW_KEY_W]){
			scene.camera.move("FRONT", deltaTime);
			scene.girl.moving();
		}
		if (Input.keys[GLFW_KEY_S]){
			scene.camera.move("BACK", deltaTime);
			scene.girl.moving();
		}
		if (Input.keys[GLFW_KEY_D]){
			scene.camera.move("RIGHT", deltaTime);
			scene.girl.moving();
		}
		if (Input.keys[GLFW_KEY_A]){
			scene.camera.move("LEFT", deltaTime);
			scene.girl.moving();
		}
	}

	// Gets called everytime the image is rendered //
	// currentTime is the time passed since window was created //
	double lastTimeRender = 0;
	public void onRender(double currentTime) {
		if(currentTime > 0f + lastTimeRender){
			//double deltaTime = currentTime - lastTimeRender;
			lastTimeRender = currentTime;
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			scene.render();
		}

	}

	// Gets called whenever window is resized //
	// width / height - The new width and height of the window //
	public void onResize(int width, int height) {
		// simple solution (adjusted to window size)
		this.width = width;

		this.height = height;

		// determin the shorter side
		int shortSide = (width > height) ? height : width;

		// set the viewport with glViewport();
		glViewport(0, 0, shortSide, shortSide);

	}

	// Gets called whenever a key on the keyboard is pressed //
	// cursorPositionX - x-Position inside the window/screen (window preferred)//
	// cursorPositionY - y-Position inside the window/screen (window preferred)//
	// pressedKey - key that got pressed //

	public void onKeyboard(float cursorPositionX, float cursorPositionY, int pressedKey, int mods, boolean pressed) {

		if (pressedKey == GLFW_KEY_ESCAPE) {
			glfwSetWindowShouldClose(window, GL_TRUE);
		}
		
		if (pressedKey == GLFW_KEY_SPACE) {
			Scene.dayTime = !Scene.dayTime;
		}

		if (pressedKey == GLFW_KEY_W)
			Input.keys[GLFW_KEY_W] = pressed;
		if (pressedKey == GLFW_KEY_S)
			Input.keys[GLFW_KEY_S] = pressed;
		if (pressedKey == GLFW_KEY_D)
			Input.keys[GLFW_KEY_D] = pressed;
		if (pressedKey == GLFW_KEY_A)
			Input.keys[GLFW_KEY_A] = pressed;
	}

	// Gets called whenever the mouse is moved or a button on the mouse was
	// pressed/released //
	// cursorPositionX - x-Position inside the window/screen (window preferred)//
	// cursorPositionY - y-Position inside the window/screen (window preferred)//
	// button - Button that was pressed (left/right) //
	// action - Was the button released or pressed? //
	public void onMouse(float cursorPositionX, float cursorPositionY, int button, int action) {

	}
}
