package entities;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL13;

import graphics.Shaderprogram;
import graphics.Texture;

public class Hero extends GameObject implements Renderable, Updateable{
	
	public void moving(){
		moving = true;
	}
	
	private boolean moving = false;
	
	private static GraphicsObject graphics_standing = new GraphicsObject("res/models/girl_frames/gl6.obj");
	
	private static GraphicsObject graphics_model[] = new GraphicsObject[]{
			new GraphicsObject("res/models/girl_frames/gl2.obj"),
			new GraphicsObject("res/models/girl_frames/gl3.obj"),
			new GraphicsObject("res/models/girl_frames/gl4.obj"),
			new GraphicsObject("res/models/girl_frames/gl5.obj")
	};
	
	private static GraphicsObject graphics_hair = new GraphicsObject("res/models/girl_hair.obj");
	
	private Shaderprogram hair_shader = new Shaderprogram("res/shaders/hair.vert", "res/shaders/hair.frag");
	
	private Texture girl_texture = new Texture("res/textures/girl.jpg");
	private Texture hair_texture = new Texture("res/textures/hair.png");
	
	private static Texture environmentMap = new Texture(new String[]{
			"res/textures/light_box/cubeSides1.png",
			"res/textures/light_box/cubeSides3.png",
			"res/textures/light_box/cubeTop.png",
			"res/textures/light_box/cubeBot.png",
			"res/textures/light_box/cubeSides2.png",
			"res/textures/light_box/cubeSides4.png"
	});
	
	private int frame = 0;
	
	public Hero(){
		setScale(0.03f);
	}
	
	@Override
	public void setCamera(Camera camera) {
		
		for(GraphicsObject each: graphics_model) each.setCamera(camera);
		graphics_hair.setCamera(camera);
		graphics_standing.setCamera(camera);
		
	}
	
	@Override
	public void render(Shaderprogram shader, Matrix4f projection_matrix) {
		
		Matrix4f rotOffset = new Matrix4f(model);
		rotOffset.rotateY((float) Math.toRadians(90)).translate(0f, 0f, 1f);
		
		graphics_model[frame].model_matrix = rotOffset;

			graphics_standing.model_matrix = rotOffset;
		
		environmentMap.bindCube(GL13.GL_TEXTURE0);
		shader.setUniform1i("tex0", 0);
		girl_texture.bind(GL13.GL_TEXTURE1);
		shader.setUniform1i("tex1", 1);
		
		if(!moving) graphics_standing.render(shader, projection_matrix);
		else graphics_model[frame].render(shader, projection_matrix);
		
		graphics_hair.model_matrix = rotOffset;
		graphics_hair.model_matrix.translate(0f, 0f, -22f);
		
		hair_texture.bind(GL13.GL_TEXTURE0);
		shader.setUniform1i("tex0", 0);
		
		graphics_hair.render(hair_shader, projection_matrix);
		

	}
	
	
	private float timeSum;
	private boolean up = true;
	@Override
	public void update(double deltaTime) {
		timeSum += deltaTime;

		float framespeed = 0.3f;
		if(timeSum > framespeed){
			timeSum = 0f;
			if(frame == 3) up = false;
			if(frame == 0) up = true;
			if(up) frame++;
			else frame--;
			System.out.println(frame);
		}
		moving = false;
	}

}
