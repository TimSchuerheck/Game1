package entities;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;

import graphics.Shaderprogram;
import graphics.VertexArrayObject;

/**
 * Consists of an vertex array object, This vao contains informations about the
 * vertex points such as where they are located in world space, perhaps their
 * texture coordinates, normals or color. They must contain the indices how the
 * vertex are to be rendered.
 * 
 * @author Tim
 * 
 */

public class Square implements EntityInterface {

	private VertexArrayObject vao = new VertexArrayObject();
	private float[] vertexCoords;
	private int[] indices;
	private float[] vertexColor;
	
	
	//Render counterclockwise so that the triangles point towards you!
	public Square() {
		vertexCoords = new float[] { 
				-0.5f, 0.5f, 0.0f, 
				-0.5f, -0.5f, 0.0f, 
				0.5f, -0.5f, 0.0f, 
				0.5f, 0.5f, 0.0f,
				-0.5f, 0.5f, 0.0f, 
				0.5f, -0.5f, 0.0f 
				};

		vertexColor = new float[] { 
				1.0f, 0.0f, 0.0f,
				0.0f, 1.0f, 0.0f,
				0.0f, 0.0f, 1.0f,
				0.0f, 1.0f, 1.0f
		};

		indices = new int[] { 
				0,1,2,
				5,3,4
		};

		createVAO();
	}

	private void createVAO() {
		vao = new VertexArrayObject();
		vao.bindData(vertexCoords, 0, 3);
		vao.bindData(vertexColor, 1, 3);
		vao.bindIndices(indices);
	}

	@Override
	public void update() {

	}

	@Override
	public void render(Shaderprogram shader, Matrix4f projection_matrix) {
		shader.useProgram();
		vao.render(GL11.GL_TRIANGLES);
	}
}
