package entities;

import org.joml.Matrix4f;
import org.joml.Vector3f;

/**
 * Created by Dennis Dubbert on 09.11.16.
 */
public abstract class Camera {
    protected float sideAngle; // also known as "phi"
    protected float downAngle; // also known as "theta"
    protected float cameraDistance;
    protected Vector3f lookAt;
    protected Vector3f upVector;
    protected Vector3f cameraPosition;
    protected Vector3f target;
    protected Matrix4f view_matrix;
    
    public Vector3f getCameraPosition(){
    	return cameraPosition;
    }
    
  	public Vector3f getTargetPosition(){
  		return target;
  	}

    public Camera(){
        sideAngle                 = (float)Math.toRadians(0);
        downAngle                 = (float)Math.toRadians(0);
        cameraDistance            = 10;
        lookAt                    = new Vector3f(0);
        upVector                  = new Vector3f(0,1,0);
        cameraPosition            = new Vector3f();
        view_matrix               = new Matrix4f();
    }

    public abstract void updateViewMatrix();

    public abstract void moveCameraUpwards();

    public abstract void moveCameraDownwards();

    public abstract void turnCameraRight(double deltaTime);

    public abstract void turnCameraLeft(double deltaTime);

    public abstract void moveCameraForwards(double deltaTime);

    public abstract void moveCameraBackwards(double deltaTime);

    public Matrix4f getView_matrix(){
        return view_matrix;
    }
}
