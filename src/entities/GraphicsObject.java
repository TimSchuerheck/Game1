package entities;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import graphics.Shaderprogram;
import graphics.Texture;
import graphics.VertexArrayObject;
import utilities.OBJLoader;

public class GraphicsObject implements Renderable{
	private VertexArrayObject vao;
	private float[] vertexCoords, vertexColors, textureCoords, normals;
	private int[] indices;
	private Camera camera;
	public Matrix4f model_matrix = new Matrix4f();
	private int renderMode = GL11.GL_TRIANGLES;
	private Texture texture;
	private OBJLoader loader;
	
	public GraphicsObject(String path){
		loader = new OBJLoader();
		vao = loader.loadObjModel(path);
		vertexCoords = loader.getVertices();
		
	}
	
	public GraphicsObject(float[] vertexCoords, float[] vertexColors, int[] indices){
		this.vertexColors = vertexColors;
		this.vertexCoords = vertexCoords;
		this.indices = indices;
		initVao();
	}
	
	public GraphicsObject(float[] vertexCoords, int[] indices){
		this.vertexCoords = vertexCoords;
		this.indices = indices;
		vao = new VertexArrayObject();
		vao.bindData(vertexCoords, 0, 3);
		vao.bindIndices(indices);
	}
	
	public GraphicsObject(float[] vertexCoords, float[] textureCoords, float[] normals, int[] indices){
		this.textureCoords = textureCoords;
		this.vertexCoords = vertexCoords;
		this.normals = normals;
		this.indices = indices;
		vao = new VertexArrayObject();
		vao.bindData(vertexCoords, 0, 3);
		vao.bindData(textureCoords, 1, 2);
		vao.bindData(normals, 2, 3);
		vao.bindIndices(indices);
	}
	
	
	public GraphicsObject(float[] vertexCoords, float[] textureCoords, float[] normals, int[] indices, Texture texture){
		this.textureCoords = textureCoords;
		this.vertexCoords = vertexCoords;
		this.normals = normals;
		this.indices = indices;
		this.texture = texture;
		vao = new VertexArrayObject();
		vao.bindData(vertexCoords, 0, 3);
		vao.bindData(textureCoords, 1, 2);
		vao.bindData(normals, 2, 3);
		vao.bindIndices(indices);
	}
	
	public GraphicsObject(float[] vertexCoords, float[] textureCoords, int[] indices, Texture texture){
		this.textureCoords = textureCoords;
		this.vertexCoords = vertexCoords;
		this.indices = indices;
		this.texture = texture;
		initVao2();
	}
	
	private void initVao2(){
		vao = new VertexArrayObject();
		vao.bindData(vertexCoords, 0, 3);
		vao.bindData(textureCoords, 1, 2);
		vao.bindIndices(indices);
	}
	
	protected void initVao() {
		vao = new VertexArrayObject();
		vao.bindData(vertexCoords, 0, 3);
		vao.bindData(vertexColors, 1, 3);
		vao.bindIndices(indices);
	}
	
	public void render(Shaderprogram shader, Matrix4f projection_matrix) {
		shader.useProgram();
		try{
			shader.setUniformMat4f("view", camera.getView_matrix());
			shader.setUniformMat4f("projection", projection_matrix);
			shader.setUniformMat4f("model", model_matrix);
		} catch (Exception e){
			System.out.println(this + " has no camera assigned.");
      System.exit(-1);
		}

		vao.render(renderMode);
	}
	
	public void setCamera(Camera camera) {
		this.camera = camera;
	}

	public float[] getVertices() {
		return vertexCoords;
	}
	
}
