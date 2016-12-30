package entities;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;

public class Point extends Entity{
	
	public Vector3f position;
	
	public Point(Vector3f position){
		super();
		super.renderMode = GL11.GL_POINTS;
		GL11.glPointSize(15.0f);
		this.position = position;
	}
	
	public void rotate(float theta, float phi){
		float r = 1f;
		position.x = r * (float)Math.cos(theta);
		position.y = r * (float)Math.sin(theta);
		//theta 
		//phi
	}
	
	public void update(){
		super.model_matrix = new Matrix4f().translate(position);
	}
	
	@Override
	protected int[] initIndices() {
		
		return new int[]{0};
	}

	@Override
	protected float[] initVertexColors() {
		return new float[]{ 1.0f, 0.0f, 0.0f };
	}

	@Override
	protected float[] initVertexCoords() {
		return new float[]{ 0f, 0f, 0f };
	}

}
