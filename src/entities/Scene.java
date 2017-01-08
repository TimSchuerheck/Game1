package entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import graphics.Shaderprogram;
import utilities.OpenGLUtils;

/**
 * So for a better overview there is this scene class. One can find all the
 * entities to be rendered, and its shaders as well as the view and projection
 * matices.
 * 
 * @author Tim
 */
public class Scene {
	
	public static final int WIDTH = 1424;
	public static final int HEIGHT = 1024;
	
	public static boolean dayTime = true;
	
	public Plane plane;
	private Shaderprogram sqr_shader, ground_shader, sky_shader, bunny_shader, 
												bloom_shader, grass_shader, landscape_shader, star_shader,
												girl_shader;
	public FlyThroughCamera camera;
	Matrix4f projection_matrix, projection_matrix_sky;
	private float fov, nplane, fplane;
	private SkyBox skyBox;
	private GrassField grassField;
	private Landscape landscape;
	public CopyOnWriteArrayList <Bunny> bunnyList = new CopyOnWriteArrayList<Bunny>();
	public CopyOnWriteArrayList <ParticleField> particleFieldList = new CopyOnWriteArrayList<ParticleField>();
	private Bloom bloom;
	private Fence fence;
	
	public Hero girl;

	public Scene() {

		// init Camera
		fov = 60f;
		nplane = 0.01f;
		fplane = 500f;
		
		//INIT GAME OBJECTS
		camera = new FlyThroughCamera();
		skyBox = new SkyBox();
		plane = new Plane();
		grassField = new GrassField(2000);
		landscape = new Landscape();
		fence = new Fence();
		girl = new Hero();

		camera.setTarget(girl);
		
		//SET CAMERAS
		plane.setCamera(camera);
		fence.setCamera(camera);
		girl.setCamera(camera);
		
		skyBox.setCamera(camera);
		grassField.setCamera(camera);
		landscape.setCamera(camera);
		
		for(int i = 0; i < 5; i++){
			spawnBunny();
		}
		
		bloom = new Bloom();
		
		//SHADER
		sky_shader = new Shaderprogram("res/shaders/sky.vert", "res/shaders/sky.frag");
		ground_shader = new Shaderprogram("res/shaders/ground.vert", "res/shaders/ground.frag");
		sqr_shader = new Shaderprogram("res/shaders/square.vert", "res/shaders/square.frag");
		bunny_shader = new Shaderprogram("res/shaders/bunny.vert", "res/shaders/bunny.frag");
		grass_shader = new Shaderprogram("res/shaders/grass.vert", "res/shaders/grass.frag");
		landscape_shader = new Shaderprogram("res/shaders/landscape.vert", "res/shaders/landscape.frag");
		bloom_shader = new Shaderprogram("res/shaders/bloom.vert", "res/shaders/bloom.frag");
		star_shader = new Shaderprogram("res/shaders/star.vert", "res/shaders/star.frag");
		girl_shader = new Shaderprogram("res/shaders/girl.vert", "res/shaders/girl.frag");
	}
	
	private double passedTime;
	
	public void update(double deltaTime) {
		for(Bunny each: bunnyList) each.update(deltaTime);
		for(ParticleField each: particleFieldList) each.update(deltaTime);
		girl.update(deltaTime);
		if(Scene.dayTime)
			passedTime += deltaTime;
		if(passedTime > 30){
			Scene.dayTime = false;
			passedTime = 0;
		}
	}

	public void render() {
		bloom.bindFramebuffer();

		skyBox.render(sky_shader, projection_matrix_sky);

		landscape.render(landscape_shader, projection_matrix_sky);
		for(Bunny each: bunnyList) each.render(bunny_shader, projection_matrix);

		grassField.render(grass_shader, projection_matrix);
		for(ParticleField each: particleFieldList) each.render(star_shader, projection_matrix);
		fence.render(grass_shader, projection_matrix);
		plane.render(ground_shader, projection_matrix);
		
		girl.render(girl_shader, projection_matrix);
		
		bloom.unbindFramebuffer();

		bloom.render(bloom_shader, projection_matrix);
	}

	public void updatePerspective(int width, int height) {
		projection_matrix = new Matrix4f().perspective((float) Math.toRadians(fov), width / (float)height, nplane, fplane);
		projection_matrix_sky = new Matrix4f().perspective((float) Math.toRadians(fov), width / (float)height, nplane, 30000000);
	}

	public void updateCamera(int width, int height) {
		camera.updateViewMatrix();
	}
	
	public void killBunny(Bunny bun){
		bunnyList.remove(bun);
	}
	
	public void spawnBunny(){
		Bunny bun = new Bunny(girl, this);
		bun.setCamera(camera);
		bun.setPosition(new Vector3f((float) (Math.random()*200 - 100), 0f, (float) (Math.random()*200 - 100)));
		bun.setRotation((float) Math.toDegrees(Math.random()*360));
		bunnyList.add(bun);
	}
	
	public void killParticleField(ParticleField pf){
		particleFieldList.remove(pf);
	}
	
	public void spawnParticleField(Vector3f position){
		ParticleField pf = new ParticleField(camera, this, position);
		particleFieldList.add(pf);
	}
}
