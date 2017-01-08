package entities;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import graphics.Shaderprogram;
import graphics.Texture;
import graphics.VertexArrayObject;

public class EvilBunnyModel implements Renderable{

	VertexArrayObject eyesVAO = new VertexArrayObject();
	private static Texture texture = new Texture("res/textures/eyes.png");
	private Camera camera;
	private Matrix4f model_matrix = new Matrix4f();
	private static Shaderprogram eye_shader = new Shaderprogram("res/shaders/star.vert", "res/shaders/star.frag");
	
	private float[] eyesVertex = new float[]{
			0f, 1f, 0f
	};
	
	private int[] eyesIndices = new int[]{
			0
	};
	
	public EvilBunnyModel(){
		eyesVAO.bindData(eyesVertex, 0, 3);
		eyesVAO.bindIndices(eyesIndices);
	}

	@Override
	public void render(Shaderprogram shader, Matrix4f projection_matrix) {
		eye_shader.useProgram();
		eye_shader.setUniformMat4f("model", model_matrix);
		Matrix4f viewproj = new Matrix4f();
		
		projection_matrix.mul(camera.view_matrix, viewproj);
		eye_shader.setUniformMat4f("viewproj", viewproj);
		eye_shader.setUniform1f("lifeTime", 1f);
		eyesVAO.render(GL11.GL_POINTS);
	}

	@Override
	public void setCamera(Camera camera) {
		this.camera = camera;
	}
}
