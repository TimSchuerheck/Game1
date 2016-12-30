package entities;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import graphics.Shaderprogram;

public class Cube extends GameObject implements Renderable {

	private Vector3f color = new Vector3f(1f, 21 / 100f, 28 / 100f);

	public Cube() {
		graphics = new GraphicsObject(vertexCoords, vertexColors, indices);
		initBoundingBox();
	}

	public Cube(Vector3f color) {
		this.color = color;
		graphics = new GraphicsObject(vertexCoords, vertexColors, indices);
		initBoundingBox();
	}
	
	int[] indices = new int[] {
			// front
			0, 1, 2, 2, 3, 0,
			// back
			6, 5, 4, 4, 7, 6,
			// top
			7, 4, 0, 0, 3, 7,
			// bottom
			1, 5, 6, 6, 2, 1,
			// left
			2, 6, 7, 7, 3, 2,
			// right
			4, 5, 1, 1, 0, 4 };

	float[] vertexColors = new float[] { color.x, color.y, color.z, color.x, color.y, color.z, color.x, color.y, color.z,
			color.x, color.y, color.z, color.x, color.y, color.z, color.x, color.y, color.z, color.x, color.y, color.z,
			color.x, color.y, color.z };

	float[] vertexCoords = new float[] { -0.5f, 2f, 0.5f, -0.5f, 0f, 0.5f, 0.5f, 0f, 0.5f, 0.5f, 2f, 0.5f, -0.5f, 2f,
			-0.5f, -0.5f, 0f, -0.5f, 0.5f, 0f, -0.5f, 0.5f, 2f, -0.5f };

}
