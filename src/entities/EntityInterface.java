package entities;

import org.joml.Matrix4f;

import graphics.Shaderprogram;

public interface EntityInterface {
	 public abstract void update();
	void render(Shaderprogram shader, Matrix4f projection_matrix);
}
