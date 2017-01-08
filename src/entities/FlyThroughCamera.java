package entities;

import java.nio.DoubleBuffer;

import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;

/**
 * Created by Dennis Dubbert on 09.11.16.
 */
public class FlyThroughCamera extends Camera {

	private float acceleration = 2f;
	private float decelerationX, decelerationY;
	private final float DECELERATION = 4.5f;
	private final float LIMIT = 1f;
	Vector3f targetToCamera;
	Vector2f velocity = new Vector2f();
	private GameObject targetObject;
	
	public Vector3f getTargetToCamera(){
		return targetToCamera;
	}
	
	public FlyThroughCamera() {
		super();
		cameraPosition = new Vector3f();
		sideAngle = (float) Math.toRadians(-90);
		downAngle = (float) Math.toRadians(90);
		target = new Vector3f(0f, 1.5f, 0f);
		targetToCamera = new Vector3f(0, 0, cameraDistance);
		targetToCamera.add(target, cameraPosition);
		
		long window = GLFW.glfwGetCurrentContext();
		DoubleBuffer x = BufferUtils.createDoubleBuffer(1);
		DoubleBuffer y = BufferUtils.createDoubleBuffer(1);
		GLFW.glfwGetCursorPos(window, x, y);
		lastX = x.get(0);
		lastY = y.get(0);
		

	}

	public void setTarget(GameObject target){
		this.targetObject = target;
	}
	
	public void move(String dir, double deltaTime) {
		switch (dir) {
		case "FRONT":
			decelerationY = 0;
			if (velocity.y <= LIMIT)
				velocity.y += acceleration * (float) deltaTime;
			break;
		case "BACK":
			decelerationY = 0;
			if (velocity.y >= -LIMIT)
				velocity.y -= acceleration * (float) deltaTime;
			break;
		case "LEFT":
			decelerationX = 0;
			if (velocity.x <= LIMIT)
				velocity.x += acceleration * (float) deltaTime;
			break;
		case "RIGHT":
			decelerationX = 0;
			if (velocity.x >= -LIMIT)
				velocity.x -= acceleration * (float) deltaTime;
			break;
		}
		

	}

	public void checkForMovement(double deltaTime) {
		if(velocity.y < -0.05f || velocity.y > 0.05f || velocity.x < -0.05f || velocity.x > 0.05f){
			turnCameraLeft(deltaTime);
			moveCameraBackwards(deltaTime);
		}

		if (velocity.y > 0)
			velocity.y -= decelerationY * (float) deltaTime;

		if (velocity.y < 0)
			velocity.y += decelerationY * (float) deltaTime;

		if (velocity.x > 0)
			velocity.x -= decelerationX * (float) deltaTime;

		if (velocity.x < 0){
			velocity.x += decelerationX * (float) deltaTime;
		}


	}

	double lastX = 0, lastY = 0;
	@Override
	public void updateViewMatrix() {
		long window = GLFW.glfwGetCurrentContext();
		DoubleBuffer x = BufferUtils.createDoubleBuffer(1);
		DoubleBuffer y = BufferUtils.createDoubleBuffer(1);
		GLFW.glfwGetCursorPos(window, x, y);
		GLFW.glfwSetInputMode(GLFW.glfwGetCurrentContext(), GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_DISABLED);
		double deltaX = x.get(0) - lastX, deltaY = y.get(0) - lastY;
		lastX = x.get(0); 
		lastY = y.get(0);
		float sensitivity = 0.2f;
		sideAngle += (float) Math.toRadians(deltaX * sensitivity);
		downAngle += (float) Math.toRadians(deltaY * sensitivity);
		
		downAngle = downAngle < 0.3 ? 0.3f: downAngle;
		downAngle = downAngle > 1.5f ? 1.5f: downAngle;
		
		targetToCamera = new Vector3f(0, cameraDistance, 0);
		Matrix3f transformationsMatrix = new Matrix3f();
		transformationsMatrix.rotateY(-sideAngle);
		if(Math.toDegrees(downAngle)<170)transformationsMatrix.rotateZ(downAngle);
		targetToCamera.mul(transformationsMatrix);
		targetToCamera.add(target, cameraPosition);
		view_matrix = new Matrix4f().lookAt(cameraPosition, target, upVector);
		decelerationX = DECELERATION;
		decelerationY = DECELERATION;
		targetObject.setPosition(new Vector3f(target).sub(0f, 1.5f, 0f));
		targetObject.setRotation(-sideAngle);
	}

	@Override
	public void moveCameraUpwards() {
		downAngle -= (float) Math.toRadians(3);
	}

	@Override
	public void moveCameraDownwards() {
		downAngle += (float) Math.toRadians(3);
	}

	// This actually moves the camera instead of turning it and is redundant
	@Override
	public void turnCameraRight(double deltaTime) {
		Vector3f viewDirection = new Vector3f(targetToCamera.negate());
		viewDirection.y = 0;
		Vector3f rightDirection = viewDirection.cross(new Vector3f(0f, 1f, 0f));
		if(velocity.x > 0.1f)
			target.add(rightDirection.mul((float) (velocity.x * deltaTime)));
	}

	// This actually moves the camera instead of turning it
	@Override
	public void turnCameraLeft(double deltaTime) {
		Vector3f viewDirection = new Vector3f(targetToCamera);
		viewDirection.y = 0;
		Vector3f leftDirection = viewDirection.cross(new Vector3f(0f, 1f, 0f));
		if(velocity.y < -0.1f || velocity.y > 0.1f);
			target.add(leftDirection.mul((float) (velocity.x * deltaTime)));
	}

	@Override
	public void moveCameraForwards(double deltaTime) {
		Vector3f viewDirection = new Vector3f(targetToCamera);
		viewDirection.y = 0;
		if(velocity.y > 0.1f)
			target.sub(viewDirection.mul((float) (velocity.y * deltaTime)));
	}

	@Override
	public void moveCameraBackwards(double deltaTime) {
		Vector3f viewDirection = new Vector3f(targetToCamera);
		viewDirection.y = 0;
		if(velocity.y < -0.1f || velocity.y > 0.1f && target.x < 100 && target.x > -100);
			target.sub(viewDirection.mul((float) (velocity.y * deltaTime)));
	}
}
