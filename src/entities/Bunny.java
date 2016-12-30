package entities;

import java.util.ArrayList;

import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL13;

import graphics.Shaderprogram;
import graphics.Texture;

public class Bunny extends GameObject implements Renderable, Updateable{
	
	public int state;
	public final int IDLE = 0, STANDING = 1, JUMP_1 = 2, JUMP_2 = 3;
	private GameObject hero;
	private ArrayList<Bunny> parentHook;
	private Jump jump;
	
	private static GraphicsObject[] models = {
			new GraphicsObject("res/models/bunny_idle.obj"), 	
			new GraphicsObject("res/models/bunny_standing.obj"), 	
			new GraphicsObject("res/models/bunny_jump_1.obj"), 	
			new GraphicsObject("res/models/bunny_jump_2.obj"), 	
	};
	
	public static void initBunnyModels(){
		for (int i = 0; i < 4; i++){
			models[i].model_matrix.scale(0.02f);
		}
	}
	
	@Override
	public void setCamera(Camera camera){
		for (GraphicsObject each: models) each.setCamera(camera);
	}
	
	private static Texture environmentMap = new Texture(new String[]{
			"res/textures/light_box/cubeSides1.png",
			"res/textures/light_box/cubeSides3.png",
			"res/textures/light_box/cubeTop.png",
			"res/textures/light_box/cubeBot.png",
			"res/textures/light_box/cubeSides2.png",
			"res/textures/light_box/cubeSides4.png"
	});
	
	public Bunny(GameObject hero, ArrayList<Bunny> bunnyList){
		this.hero = hero;
		this.parentHook = bunnyList;
		state = IDLE;
		graphics = models[state];
		setScale(0.02f);
		initBoundingBox();
	}
	
	@Override
	public void render(Shaderprogram shader, Matrix4f projection_matrix) {
		graphics = models[state];
		environmentMap.bindCube(GL13.GL_TEXTURE0);
		shader.setUniform1i("tex0", 0);
		shader.setUniform1f("shininess", 10f);
		shader.setUniform3f("light_position", new Vector3f(0f,100f, 50f));
		// TODO Auto-generated method stub
		super.render(shader, projection_matrix);
	}

	@Override
	public void update(double deltaTime) {
		Vector3f heroToBunny = new Vector3f();
		this.position.sub(hero.position, heroToBunny);
		float distance = heroToBunny.length();
		if(distance < 15f) state = STANDING;
		else state = IDLE;
		
		float heroRotOffset = (float)Math.PI/2f;
		Vector3f dir = new Vector3f(0f, 0f, 1f).mul(new Matrix3f().rotateY(rotation));
		Vector3f heroDir = new Vector3f(0f, 0f, 1f).mul(new Matrix3f().rotateY(hero.rotation + heroRotOffset));
		dir.normalize();
		heroDir.normalize();
		float alpha = dir.dot(heroDir);
		
		if(alpha < -0.5f && distance < 8f && jump == null) jump = new Jump(dir, this);
		if(jump != null) jump.proceed(deltaTime);
	}
	
	private void jump(double deltaTime){
		

	}
	
	public void killJump(){
		jump = null;
	}
	
}
