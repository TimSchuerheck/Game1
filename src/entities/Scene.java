package entities;

import java.util.ArrayList;

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
	private Shaderprogram sqr_shader, ground_shader, sky_shader, bunny_shader;
	public FlyThroughCamera camera;
	public Cross cross;
	public Point point;
	Matrix4f projection_matrix, projection_matrix_sky, projection_matrix_shortFPlane;
	private float fov, nplane, fplane;
	private ArrayList<GameObject> cubes;
	private Bunny bun, bun1, bun2 , bun3;
	private SkyBox skyBox;
	private GrassField grassField;
	private Shaderprogram grass_shader, landscape_shader;
	private Landscape landscape;
	private ArrayList <Bunny> bunnyList;

	public Scene() {
		
		bunnyList = new ArrayList<Bunny>();
		
		// init Camera
		fov = 60f;
		nplane = 0.01f;
		fplane = 500f;
		
		//INIT GAME OBJECTS
		camera = new FlyThroughCamera();
		
		skyBox = new SkyBox();
		plane = new Plane();
		hero = new Cube();
		grassField = new GrassField(10000);
		landscape = new Landscape();
		bun = new Bunny(hero, bunnyList);
		bun1 = new Bunny(hero, bunnyList);
		bun2 = new Bunny(hero, bunnyList);
		bun3 = new Bunny(hero, bunnyList);
		
		cubes = new ArrayList<GameObject>();
		for (int i = 0; i<4; i++) {
			cubes.add(new Cube(new Vector3f(1f,0.8f,0f)));
			cubes.get(i).setCamera(camera);
		}
		((Cube) cubes.get(0)).setPosition(new Vector3f(10f, 0f, 10f));
		((Cube) cubes.get(1)).setPosition(new Vector3f(-10f, 0f, -10f));
		((Cube) cubes.get(2)).setPosition(new Vector3f(10f, 0f, -10f));
		((Cube) cubes.get(3)).setPosition(new Vector3f(-10f, 0f, 10f));
		
		camera.setTarget(hero);
		
		//SET CAMERAS
		plane.setCamera(camera);
		bun.setCamera(camera);
		bun1.setCamera(camera);
		bun2.setCamera(camera);
		bun3.setCamera(camera);
		
		
		skyBox.setCamera(camera);
		hero.setCamera(camera);
		grassField.setCamera(camera);
		landscape.setCamera(camera);
		
		bun.setPosition(new Vector3f(0f,0f,10f));
		bun1.setPosition(new Vector3f(0f,0f,-10f));
		bun2.setPosition(new Vector3f(10f,0f,0f));
		bun3.setPosition(new Vector3f(-10f,0f,0f));
		
		bun1.setRotation((float)Math.PI/2);
		bun2.setRotation((float)Math.PI);
		bun3.setRotation((float)Math.PI/4/3f);
		
		bunnyList.add(bun);
		//bunnyList.add(bun1);
		//bunnyList.add(bun2);
		//bunnyList.add(bun3);
		
		//SHADER
		sky_shader = new Shaderprogram("res/shaders/sky.vert", "res/shaders/sky.frag");
		ground_shader = new Shaderprogram("res/shaders/ground.vert", "res/shaders/ground.frag");
		sqr_shader = new Shaderprogram("res/shaders/square.vert", "res/shaders/square.frag");
		bunny_shader = new Shaderprogram("res/shaders/bunny.vert", "res/shaders/bunny.frag");
		grass_shader = new Shaderprogram("res/shaders/grass.vert", "res/shaders/grass.frag");
		landscape_shader = new Shaderprogram("res/shaders/landscape.vert", "res/shaders/landscape.frag");
	}
	
	public void update(double deltaTime) {
		cubes.remove(hero.collisionTest(cubes));
		for(Bunny each: bunnyList) each.update(deltaTime);
	}

	public void render() {
		skyBox.render(sky_shader, projection_matrix_sky);
		plane.render(ground_shader, projection_matrix);
		landscape.render(landscape_shader, projection_matrix_sky);
		for(Bunny each: bunnyList) each.render(bunny_shader, projection_matrix);

		hero.render(sqr_shader, projection_matrix);
		for (GameObject each : cubes) {
			each.render(sqr_shader, projection_matrix);
		}
		grassField.render(grass_shader, projection_matrix);
	}

	public void updatePerspective(int width, int height) {
		projection_matrix = new Matrix4f().perspective((float) Math.toRadians(fov), width / (float)height, nplane, fplane);
		projection_matrix_sky = new Matrix4f().perspective((float) Math.toRadians(fov), width / (float)height, nplane, 30000000);
		projection_matrix_shortFPlane = new Matrix4f().perspective((float) Math.toRadians(fov), width / (float)height, nplane, 30);
		
	}

	public void updateCamera(int width, int height) {
		camera.updateViewMatrix();
	}
}
