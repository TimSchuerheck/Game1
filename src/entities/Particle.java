package entities;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL32;

import graphics.Shaderprogram;
import graphics.Texture;
import graphics.VertexArrayObject;

public class Particle implements Renderable{
	
	private static VertexArrayObject vao = new VertexArrayObject();;
	private static float[] vertices = new float[]{0f,1f,0f};
	private static int[] indices = new int[]{0};
	private static Texture texture = new Texture("res/textures/star.png");
	private Camera camera;
	private Matrix4f model_matrix = new Matrix4f();
	private static boolean initialized = false;
	private Vector3f color;
	private Vector3f position = new Vector3f();
	private float lifeTime = 1;
	private float deceaseRate = 0.3f;
	private ParticleField parent;
	private float yRotation;
	private float distance = 0.1f * (float) Math.random() + 0.05f;
	private Vector3f spawnPos;
	
	private void setPosition(){
		model_matrix = new Matrix4f().translate(spawnPos).rotateY(yRotation).translate(position);
	}
	
	private float acc = 0.3f;
	private float velo = 1f;
	private float s = 0.1f;
	public void proceed(double deltaTime){

		if(lifeTime > 0){
			
			position.y += velo * s;
			velo 			 -= acc * s;
			if(position.y < 0) velo = 1f;
			position.x += lifeTime * distance;
			lifeTime -= deceaseRate * deltaTime;
			setPosition();
			
		}else{
			parent.killParticle(this);
		}
	}
	
	public void initVao(){
		vao.bindData(vertices, 0, 3);
		vao.bindIndices(indices);
	}
	
	public Particle(ParticleField parent, Vector3f spawnPos){
		this.spawnPos = spawnPos;
		if(!initialized){
			initialized = true;
			initVao();
		}
		color = new Vector3f((float)Math.random() + 0.3f, (float)Math.random()+ 0.3f, (float)Math.random()+ 0.3f);
		this.parent = parent;
		yRotation = (float) (Math.random() * 360);
	}
	
	
	@Override
	public void render(Shaderprogram shader, Matrix4f projection_matrix) {
		Matrix4f viewproj = new Matrix4f();
		projection_matrix.mul(camera.view_matrix, viewproj);
		shader.useProgram();
		texture.bind(GL13.GL_TEXTURE0);
		shader.setUniform1i("tex0", 0);
		shader.setUniformMat4f("model", model_matrix);
		shader.setUniformMat4f("viewproj", viewproj);
		shader.setUniform3f("inColor", color);
		shader.setUniform1f("lifeTime", lifeTime);
		GL11.glEnable( GL32.GL_PROGRAM_POINT_SIZE );
		vao.render(GL11.GL_POINTS);
	}

	@Override
	public void setCamera(Camera camera) {
		this.camera = camera;
	}
}
