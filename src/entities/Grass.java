package entities;

import java.io.IOException;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL13;

import graphics.Shaderprogram;
import graphics.Texture;

public class Grass implements Renderable{
	
	private Camera camera;

	private static Texture[] texture = new Texture[]{
			new Texture("res/textures/billboardgrass0001_lowres.png"), 
			new Texture("res/textures/billboardgrass0002_lowres.png"),
			new Texture("res/textures/billboardblueflowers_lowres.png"),
			new Texture("res/textures/billboardredflowers_lowres.png")
	};
	
	private static float width = 1024/1024f, height = 1024/1024f;
	
	private static float[] vertices = new float[]{
			0f, 0f, 0f,
			0f, height, 0f,
			0f, 0f, width,
			0f, height, width
	};
	
	private static int[] indices = new int[]{
			0,1,2,
			2,1,3,
			2,1,0,
			3,1,2
	};
	
	private static float[] tc = new float[]{
			0f, 0f,
			1f, 0f,
			0f, 1f,
			1f, 1f,
			
			0f, 0f,
			1f, 0f,
			0f, 1f,
			1f, 1f
	};
	
	private static float[] normals = new float[]{
			1f, 0f, 0f,
			1f, 0f, 0f,
			-1f, 0f, 0f,
			-1f, 0f, 0f
	};
	
	private static GraphicsObject graphics = new GraphicsObject(vertices, tc, normals, indices);
	
	private Matrix4f model = new Matrix4f();
	private int texSelector;
	private Vector3f position;
	private Matrix4f unrotated;
	
	public Vector3f getPosition(){
		return position;
	}
	
	public Grass(float scale, float x, float y){
		this.position = new Vector3f(x, 0f, y);
		texSelector = (int)(Math.random() * 4);
		unrotated = new Matrix4f();
		model.translate(x, 0f, y);
		model.rotate((float) Math.PI / 2f, 1f, 0f, 0f);
		model.rotate((float) (Math.random() * Math.PI*2) / 2f, 0f, 0f, 1f);
		model.translate(0f,0f,-scale);
		model.scale(scale);
		unrotated = model;
	}

	@Override
	public void render(Shaderprogram shader, Matrix4f projection_matrix) {
		/*
		Vector3f grassToCamera = new Vector3f();
		camera.cameraPosition.sub(position, grassToCamera);
		Vector3f cameraToTarget = new Vector3f();
		FlyThroughCamera cam = (FlyThroughCamera) camera;
		cameraToTarget = cam.getTargetToCamera();
		float angle = grassToCamera.dot(cameraToTarget);

		model = new Matrix4f(unrotated).rotate((float)Math.toRadians(angle), 0f, 0f, 1f);
	*/
		graphics.model_matrix = model;
		texture[texSelector].bind(GL13.GL_TEXTURE0);
		shader.setUniform1i("tex0", 0);
		int daytime;
		if(Scene.dayTime) daytime = 0;
		else daytime = 1;
		shader.setUniform1i("daytime", daytime);
		graphics.render(shader, projection_matrix);
	}

	@Override
	public void setCamera(Camera camera) {
		this.camera = camera;
		graphics.setCamera(camera);
	}
}
