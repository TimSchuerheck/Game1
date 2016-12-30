package entities;

import org.joml.Matrix3f;
import org.joml.Vector3f;

public class Jump {
	
	private float lifeTime;
	private float deceaseSub;
	private Vector3f destination;
	private Vector3f start;
	private final float LENGTH = 10f;
	private float angle;
	private final float DIR = (float)Math.toRadians(40d);
	private Bunny parent;
	private float height = 1f;
	
	public Jump(Vector3f direction, Bunny parent){
		System.out.println();
		this.parent = parent;
		start = new Vector3f(parent.position);
		lifeTime = 1;
		deceaseSub = 0.7f;
		angle = ((int)(Math.random() * 3) -1) * DIR;
		Vector3f parentOrientation = new Vector3f(direction);
		Matrix3f jumpDirRotation = new Matrix3f().rotateY(angle);
		Vector3f scaledParentOrientation = new Vector3f();
		Vector3f rotatedScaledOrientation = new Vector3f();
		Vector3f rotatedScaledInParentSpace = new Vector3f();
		parentOrientation.mul(LENGTH, scaledParentOrientation);
		scaledParentOrientation.mul(jumpDirRotation, rotatedScaledOrientation);
		rotatedScaledOrientation.add(parent.position, rotatedScaledInParentSpace);
		destination = rotatedScaledInParentSpace;
		parent.setRotation(parent.rotation + angle);
	}
	
	public void proceed(double deltaTime){
		if (lifeTime > 0){
			lifeTime -= deceaseSub * deltaTime;
			Vector3f muledStart = new Vector3f(start), 
					muledDest = new Vector3f(destination),
					newPos = new Vector3f();
			muledStart.mul(lifeTime);
			muledDest.mul(1 - lifeTime);
			muledStart.add(muledDest, newPos);
			
			float hFac;
			if(lifeTime > 0.5f) hFac = (1 - (lifeTime - 0.5f)/0.5f) * height;
			else hFac = (lifeTime / 0.5f) * height;
			
			
			System.out.println(height);
			newPos.add(0f, height * hFac, 0f);
			
			parent.setPosition(newPos);
			
			if(lifeTime > 0.5) parent.state = parent.JUMP_1;
			else parent.state = parent.JUMP_2;
			
		}
		else parent.killJump();
	}
}
