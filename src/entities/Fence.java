package entities;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL13;

import graphics.Shaderprogram;
import graphics.Texture;

public class Fence implements Renderable{
	
	float h = 2f;
	float r = 30f;
	
	Texture tex = new Texture("res/textures/fence.jpg");
	GraphicsObject graphics;

	
	public Fence(){
		graphics = new GraphicsObject(vertices, tc, indices, tex);
	}
	
	
	float[] vertices = new float[]{
			100f, 0f, -100f,
			100f, h, -100f,
			
			100f, 0f, 100f,
			100f, h, 100f,
			
			-100f, 0f, 100f,
			-100f, h, 100f,
			
			-100f, 0f, -100f,
			-100f, h, -100f
	};
	
	float[] tc = new float[]{
			0f,1f,
			0f,0f,
			r,1f,
			r,0f,
			
			0f,1f,
			0f,0f,
			r,1f,
			r,0f,
			
			0f,1f,
			0f,0f,
			r,1f,
			r,0f,
			
			0f,1f,
			0f,0f,
			r,1f,
			r,0f,
			
			0f,1f,
			0f,0f,
			r,1f,
			r,0f,
			
			0f,1f,
			0f,0f,
			r,1f,
			r,0f,
			
			0f,1f,
			0f,0f,
			r,1f,
			r,0f,
			
			0f,1f,
			0f,0f,
			r,1f,
			r,0f	
	};
	
	int[] indices = new int[]{
		0,1,2,
		2,1,3,
		
		2,3,4,
		4,3,5,
		
		4,6,7,
		7,5,4,
		
		6,7,1,
		1,0,6,
		
		2,1,0,
		3,1,2,
		
		4,3,2,
		5,3,4,
		
		7,6,4,
		4,5,7,
		
		1,7,6,
		6,0,1,
	};

	@Override
	public void render(Shaderprogram shader, Matrix4f projection_matrix) {
		tex.bind(GL13.GL_TEXTURE0);
		shader.setUniform1i("tex0", 0);
		graphics.render(shader, projection_matrix);
		
	}

	@Override
	public void setCamera(Camera camera) {
		graphics.setCamera(camera);
		
	}
}
