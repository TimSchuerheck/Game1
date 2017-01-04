package entities;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;

import org.lwjgl.opengl.GL32;


import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

import graphics.Shaderprogram;
import windowmanager.MyWindow;



public class Bloom implements Renderable{
	
	GraphicsObject graphics;
	private int framebuffer, texture;
	private int texWidth = 1024,  texHeight = 768;

	public void bindFramebuffer(){
		glBindFramebuffer(GL_DRAW_FRAMEBUFFER, framebuffer);	
		glViewport(0, 0, texWidth, texHeight);
		glClearColor(1.0f, 0.0f, 1.0f, 1.0f);
		glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}
	
	
	public void unbindFramebuffer(){
		glBindFramebuffer(GL_FRAMEBUFFER, 0);
		glClearColor(0f, 0f, 1f, 1f);
		glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}
	
	public Bloom(){
		graphics = new GraphicsObject(vertices, tc, indices);
		

		//Create the framebuffer
		framebuffer = glGenFramebuffers();
		//Bind
		glBindFramebuffer(GL_FRAMEBUFFER, framebuffer);	
		//Create an empty texture
		texture = GL11.glGenTextures();
		//Bind the texture
		glBindTexture(GL11.GL_TEXTURE_2D, texture);
		// Give an empty image to OpenGL
		glTexImage2D(GL_TEXTURE_2D, 0,GL_RGB, MyWindow.windowWidth, MyWindow.windowHeight, 0,GL_RGB, GL_UNSIGNED_BYTE, 0);
		// Filtering
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		
		//Attach the Framebuffer to the texture
		GL32.glFramebufferTexture(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, texture, 0);
		GL20.glDrawBuffers(GL_COLOR_ATTACHMENT0);
		if(glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE) 
			System.out.println("Something went wrong with the drawbuffer :(");
		//unbindFramebuffer();
	}
	
	private float[] vertices = new float[]{
			1f, 1f, 0f,
			1f, -1f, 0f,
			-1f, 1f, 0f,
			-1f, -1f, 0f
	};
	
	private int[] indices = new int[]{
			0,1,2,
			2,3,0
	};
	
	private float[] tc = new float[]{
			1f, 1f,
			1f, 0f,
			0f, 1f,
			0f, 0f
	};

	@Override
	public void render(Shaderprogram shader, Matrix4f projection_matrix) {
		//shader.useProgram();
    GL13.glActiveTexture(GL13.GL_TEXTURE0);
    glBindTexture(GL_TEXTURE_2D, texture);
    
   // OpenGLUtils.checkForOpenGLError();
		shader.setUniform1i("tex0", 0);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		
    //graphics.vao.render(GL11.GL_TRIANGLES);
	}

	@Override
	public void setCamera(Camera camera) {
		
	}
	
}
