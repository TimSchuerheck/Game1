package entities;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL13;

import graphics.Shaderprogram;
import graphics.Texture;

public class Landscape implements Renderable{

	GraphicsObject graphics;
	Texture texture;
	
	public Landscape(){
		graphics = new GraphicsObject("res/models/landscape.obj");
		graphics.model_matrix.translate(0f, -100f, 0f);
		graphics.model_matrix.scale(5000f, 400f,5000f);
		texture = new Texture("res/textures/felsen.jpg");
	}
	
	@Override
	public void render(Shaderprogram shader, Matrix4f projection_matrix) {
		texture.bind(GL13.GL_TEXTURE0);
		shader.setUniform1i("tex0", 0);
		int daytime;
		if(Scene.dayTime) daytime = 0;
		else daytime = 1;
		shader.setUniform1i("daytime", daytime);
		graphics.render(shader, projection_matrix);
		
	}

	@Override
	public void setCamera(Camera camera) {
		graphics.setCamera(camera);
		
	}

}
