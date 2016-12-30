package entities;

import org.joml.Matrix4f;

import graphics.Shaderprogram;

public interface Renderable {
	public abstract void render(Shaderprogram shader, Matrix4f projection_matrix);
	public abstract void setCamera(Camera camera);
}
