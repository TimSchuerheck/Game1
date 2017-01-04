package entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import graphics.Shaderprogram;

/**
 * So for a better overview there is this scene class. One can find all the
 * entities to be rendered, and its shaders as well as the view and projection
 * matices.
 * 
 * @author Tim
 */
public class Scene {
	public GameObject hero;
	public Plane plane;
	private Shaderprogram sqr_shader, ground_shader, sky_shader, bunny_shader, 
												bloom_shader, grass_shader, landscape_shader, star_shader;
	public FlyThroughCamera camera;
	Matrix4f projection_matrix, projection_matrix_sky;
	private float fov, nplane, fplane;
	private Bunny bun;
	private SkyBox skyBox;
	private GrassField grassField;
	private Landscape landscape;
	public CopyOnWriteArrayList <Bunny> bunnyList = new CopyOnWriteArrayList<Bunny>();
	public CopyOnWriteArrayList <ParticleField> particleFieldList = new CopyOnWriteArrayList<ParticleField>();
	private Bloom bloom;

	public Scene() {
		
		
		// init Camera
		fov = 60f;
		nplane = 0.01f;
		fplane = 500f;
		
		//INIT GAME OBJECTS
		camera = new FlyThroughCamera();
		skyBox = new SkyBox();
		plane = new Plane();
		hero = new Cube();
		grassField = new GrassField(2000);
		landscape = new Landscape();
		bun = new Bunny(hero, this);

		camera.setTarget(hero);
		
		//SET CAMERAS
		plane.setCamera(camera);
		bun.setCamera(camera);
		
		skyBox.setCamera(camera);
		hero.setCamera(camera);
		grassField.setCamera(camera);
		landscape.setCamera(camera);
		
		
		bun.setPosition(new Vector3f(0f,0f,10f));
		
		bunnyList.add(bun);
		//bunnyList.add(bun1);
		//bunnyList.add(bun2);
		//bunnyList.add(bun3);
		
		//bloom = new Bloom();
		
		//SHADER
		sky_shader = new Shaderprogram("res/shaders/sky.vert", "res/shaders/sky.frag");
		ground_shader = new Shaderprogram("res/shaders/ground.vert", "res/shaders/ground.frag");
		sqr_shader = new Shaderprogram("res/shaders/square.vert", "res/shaders/square.frag");
		bunny_shader = new Shaderprogram("res/shaders/bunny.vert", "res/shaders/bunny.frag");
		grass_shader = new Shaderprogram("res/shaders/grass.vert", "res/shaders/grass.frag");
		landscape_shader = new Shaderprogram("res/shaders/landscape.vert", "res/shaders/landscape.frag");
		bloom_shader = new Shaderprogram("res/shaders/bloom.vert", "res/shaders/bloom.frag");
		star_shader = new Shaderprogram("res/shaders/star.vert", "res/shaders/star.frag");
	}
	
	public void update(double deltaTime) {
		for(Bunny each: bunnyList) each.update(deltaTime);
		for(ParticleField each: particleFieldList) each.update(deltaTime);
	}

	public void render() {
		//bloom.bindFramebuffer();
		skyBox.render(sky_shader, projection_matrix_sky);
		plane.render(ground_shader, projection_matrix);
		landscape.render(landscape_shader, projection_matrix_sky);
		for(Bunny each: bunnyList) each.render(bunny_shader, projection_matrix);

		hero.render(sqr_shader, projection_matrix);

		grassField.render(grass_shader, projection_matrix);
		for(ParticleField each: particleFieldList) each.render(star_shader, projection_matrix);
		//bloom.unbindFramebuffer();
		//bloom.render(bloom_shader, projection_matrix);
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
	
	public void killParticleField(ParticleField pf){
		particleFieldList.remove(pf);
	}
	
	public void spawnParticleField(Vector3f position){
		ParticleField pf = new ParticleField(camera, this, position);
		particleFieldList.add(pf);
	}
}
