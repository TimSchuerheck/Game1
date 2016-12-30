package graphics;

import utilities.BufferUtils;
import utilities.OpenGLUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

/**
 * Created by Dennis Dubbert on 07.09.16.
 */
public class VertexArrayObject {

	private int count;
	private int vaoID, iboID, vboID;
	
	
	public VertexArrayObject() {
		vaoID = glGenVertexArrays();
	}

	public void bindData(float[] vboData, int index, int numDimensions) {
		FloatBuffer vboBuffer = BufferUtils.createFloatBuffer(vboData);

		glBindVertexArray(vaoID);

		vboID = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vboID);
		glBufferData(GL_ARRAY_BUFFER, vboBuffer, GL_STATIC_DRAW);
		glVertexAttribPointer(index, numDimensions, GL_FLOAT, false, 0, 0);
		glEnableVertexAttribArray(index);

		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindVertexArray(0);
	}

	public void bindIndices(int[] indices) {
		IntBuffer iboBuffer = BufferUtils.createIntBuffer(indices);

		glBindVertexArray(vaoID);

		count = indices.length;

		iboID = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, iboID);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, iboBuffer, GL_STATIC_DRAW);

		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		glBindVertexArray(0);
	}

	public void bind() {
		glBindVertexArray(vaoID);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, iboID);
	}

	public void unbind() {
		glBindVertexArray(0);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
	}

	public void draw(int mode) {
		glDrawElements(mode, count, GL_UNSIGNED_INT, 0);
		OpenGLUtils.checkForOpenGLError();
	}

	public void render(int mode) {
		bind();
		draw(mode);
	}
}
