package entities;

import org.lwjgl.opengl.GL11;

public class Cross extends Entity {
	
	public Cross(){
		super();
		super.renderMode = GL11.GL_LINES;
	}

	@Override
	protected int[] initIndices() {
	 return new int[]{
				0,3,
				1,4,
				2,5
		};
		
	}

	@Override
	protected float[] initVertexColors() {
		return new float[]{
				0f,0f,0f,
				0f,0f,0f,
				0f,0f,0f,
				0f,0f,0f,
				0f,0f,0f,
				0f,0f,0f,
				0f,0f,0f,
				0f,0f,0f,
				0f,0f,0f,
		};
	}

	@Override
	protected float[] initVertexCoords() {
		return  new float[]{
			1f, 0f, 0f,
			0f, 1f, 0f,
			0f, 0f, 1f,
			-1f, 0f, 0f,
			0f, -1f, 0f,
			0f, 0f, -1f,
		};

	}
}
