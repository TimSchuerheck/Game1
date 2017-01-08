package entities;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL32;


import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

import graphics.Shaderprogram;
import graphics.Texture;
import utilities.BufferUtils;
import utilities.OpenGLUtils;
import windowmanager.MyWindow;



public class Bloom implements Renderable, Updateable{
	
	GraphicsObject graphics;
	private int framebuffer;
	Texture texture;
	private int texWidth = Scene.WIDTH,  texHeight = Scene.HEIGHT;
	private int texture_color;
	private int texture_depth;
	private float time;
	private Matrix4f model = new Matrix4f();

	public void bindFramebuffer(){
		glBindFramebuffer(GL_FRAMEBUFFER, framebuffer);	
		glViewport(0, 0, texWidth, texHeight);
		glClearColor(1.0f, 0.0f, 1.0f, 1.0f);
		glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glFrontFace(GL11.GL_CCW);
	}
	
	public void unbindFramebuffer(){
		glBindFramebuffer(GL_FRAMEBUFFER, 0);
		glViewport(0, 0, texWidth, texHeight);
		glClearColor(0f, 0f, 1f, 1f);
		glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glFrontFace(GL11.GL_CW);
	}
	
	public Bloom(){
		graphics = new GraphicsObject(vertices, tc, indices);
		GenerateFBO();
		
	}
	
	public void GenerateFBO(){
		framebuffer = glGenFramebuffers();
		glBindFramebuffer(GL_FRAMEBUFFER, framebuffer);	
		
		generateColorTexture();
		generateDepthTexture();

		GL32.glFramebufferTexture(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, texture_color, 0);
		GL32.glFramebufferTexture(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, texture_depth, 0);
		GL20.glDrawBuffers(GL_COLOR_ATTACHMENT0);
		
		if (glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE) System.out.println("Frame Buffer is not complete.");
		glBindFramebuffer(GL_FRAMEBUFFER, 0);
	}
	

	private void generateDepthTexture() {
		texture_depth = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, texture_depth);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
		glTexImage2D(GL_TEXTURE_2D, 0, GL30.GL_DEPTH_COMPONENT32F, texWidth, texHeight, 0, GL_DEPTH_COMPONENT, GL_FLOAT, 0);
		
	}

	private void generateColorTexture() {
		
		texture_color = glGenTextures();
		
		glBindTexture(GL_TEXTURE_2D, texture_color);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, texWidth, texHeight, 0, GL_RGBA, GL_UNSIGNED_BYTE, 0);

		OpenGLUtils.checkForOpenGLError();
		
	}

	private float[] vertices = new float[]{
			
			-1f, -1f, 0f,
			1f, -1f, 0f,
			-1f, 1f, 0f,
			1f, 1f, 0f
			
	};
	
	
	private int[] indices = new int[]{
		
			3,1,0,
			0,2,3
			
	};
	
	private float[] tc = new float[]{
			
			0f, 0f,
			1f, 0f,
			0f, 1f,
			1f, 1f
			
	};
	

	@Override
	public void render(Shaderprogram shader, Matrix4f projection_matrix) {
		//GL11.glEnable(GL11.GL_TEXTURE_2D);
		shader.useProgram();
    GL13.glActiveTexture(GL13.GL_TEXTURE0);
    glBindTexture(GL_TEXTURE_2D, texture_color);
		shader.setUniform1i("tex0", 0);
		shader.setUniform1i("width", Scene.WIDTH);
		shader.setUniform1i("height", Scene.HEIGHT);
		
		/*
		GL13.glActiveTexture(GL13.GL_TEXTURE1);
		glBindTexture(GL_TEXTURE_2D, texture_depth);
		shader.setUniform1i("tex1", texture_depth);
		*/
    graphics.vao.render(GL11.GL_TRIANGLES);
	}

	@Override
	public void setCamera(Camera camera) {
		
	}

	@Override
	public void update(double deltaTime) {
		if(!Scene.dayTime){
			time += deltaTime;
			time = (float) (time % (Math.PI * 2));
			model = new Matrix4f().rotateZ(time);
		} else {
			time = 0;
		}
	}
	
}
