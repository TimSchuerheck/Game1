package utilities;


import windowmanager.Window;
import org.lwjgl.glfw.*;

import static org.lwjgl.glfw.GLFW.*;


/**
 * Created by Dennis Dubbert on 06.09.16.
 */

public class Input {

    private static GLFWErrorCallback        errorCallback;
    private static GLFWKeyCallback          keyCallback;
    private static GLFWMouseButtonCallback  mouseButtonCallback;
    private static GLFWCursorPosCallback    cursorPosCallback;
    private static GLFWWindowSizeCallback   windowSizeCallback;
    private static float x;
    private static float y;
    public static boolean[] keys = new boolean[65536];

    public static void init(long windowID, Window win) {
        errorCallback = GLFWErrorCallback.createPrint(System.err);

        cursorPosCallback = new GLFWCursorPosCallback() {
            @Override
            public void invoke(long window, double xpos, double ypos) {
                x = (float) xpos;
                y = (float) ypos;
            }
        };

        mouseButtonCallback = new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long window, int button, int action, int mods) {
                win.onMouse(x, y, button, action);
            }
        };

        keyCallback = new GLFWKeyCallback(){
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                if(action != GLFW_RELEASE) {
                    win.onKeyboard(x, y, key, mods, true);
                } else {
                  win.onKeyboard(x, y, key, mods, false);
                }
            }
        };

        windowSizeCallback = new GLFWWindowSizeCallback() {
            @Override
            public void invoke(long window, int width, int height) {
                win.onResize(width, height);
            }
        };

        //glfwSetErrorCallback(errorCallback);
        glfwSetCursorPosCallback(windowID, cursorPosCallback);
        glfwSetMouseButtonCallback(windowID, mouseButtonCallback);
        glfwSetKeyCallback(windowID, keyCallback);
        glfwSetWindowSizeCallback(windowID, windowSizeCallback);
    }

    public static GLFWKeyCallback getKeyCallback(){
        return keyCallback;
    }
    public static GLFWErrorCallback getErrorCallback() {
        return errorCallback;
    }
    public static GLFWMouseButtonCallback getMouseButtonCallback() {
        return mouseButtonCallback;
    }
    public static GLFWCursorPosCallback getCursorPosCallback() {
        return cursorPosCallback;
    }
}
