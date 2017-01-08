package entities;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL32;

import graphics.Shaderprogram;
import graphics.Texture;
import graphics.VertexArrayObject;

public class Monster extends GameObject implements Renderable{
	
	private static VertexArrayObject vao = new VertexArrayObject();;
	private static float[] vertices = new float[]{0f,2f,0f};
	private static int[] indices = new int[]{0};
	private static Texture texture = new Texture("res/textures/monster.png");
	private Camera camera;
	public Matrix4f model_matrix = new Matrix4f();
	
	private static Shaderprogram monster_shader = new Shaderprogram("res/shaders/monster.vert", "res/shaders/monster.frag");
	
	boolean initialized = false;
	
	public Monster(){
		if(!initialized){
			initialized = true;
			initVao();
		}
	}
	
	@Override
	public void setCamera(Camera camera) {
		this.camera = camera;
	}
	
	public void initVao(){
		vao.bindData(vertices, 0, 3);
		vao.bindIndices(indices);
	}
	
	@Override
	public void render(Shaderprogram shader, Matrix4f projection_matrix) {
		Matrix4f translated_model = new Matrix4f(model_matrix).translate(0, 60, 0);
		Matrix4f viewproj = new Matrix4f();
		projection_matrix.mul(camera.view_matrix, viewproj);
		monster_shader.useProgram();
		texture.bind(GL13.GL_TEXTURE0);
		monster_shader.setUniform1i("tex0", 0);
		monster_shader.setUniformMat4f("model", translated_model);
		monster_shader.setUniformMat4f("viewproj", viewproj);
		GL11.glEnable( GL32.GL_PROGRAM_POINT_SIZE );
		vao.render(GL11.GL_POINTS);
	}
}
