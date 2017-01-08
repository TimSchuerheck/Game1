package entities;

import org.joml.Matrix3f;
import org.joml.Vector3f;

public class Jump {
	
	private float lifeTime = 1;
	private final float deceaseSub = 1f;;
	private Vector3f destination;
	private Vector3f start;
	private final float LENGTH = 12f;
	private float angle;
	private final float DIR[] = new float[]{0f, 45f, 90f, 135f, 180f, 225f, 270f, 315f};
	private Bunny parent;
	private float height = 1f;
	private int count;
	
	public Jump(Bunny parent){
		count = 3;
		this.parent = parent;

		angle = (float)Math.toRadians(DIR[(int)(Math.random() * 8)]);
		init();
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
			
			if(height * hFac > 0)
			newPos.add(0f, height * hFac, 0f);
			
			parent.setPosition(newPos);
			
			if(lifeTime > 0.5) parent.state = parent.JUMP_1;
			else parent.state = parent.JUMP_2;
			
		} else if(count > 0) {
			init();
		}
		else parent.killJump();
	}
	
	private void init(){
		count--;
		lifeTime = 1;
		start = new Vector3f(parent.position);
		Vector3f parentOrientation = new Vector3f(0f, 0f, 1f).mul(new Matrix3f().rotateY(parent.rotation));
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
}
