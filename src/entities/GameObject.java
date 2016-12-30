package entities;

import java.util.ArrayList;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import graphics.Shaderprogram;

public class GameObject implements Renderable{
	public BoundingBox boundingBox;
	protected GraphicsObject graphics;
	protected Vector3f position = new Vector3f();;
	protected float rotation = 0f;
	protected float scale = 1f;
	private Matrix4f model = new Matrix4f();
	
	public GameObject(){
		boundingBox = new BoundingBox();
	}
	
	public void initBoundingBox(){
		boundingBox.initBoundingBox(graphics.getVertices());
	}
	
	public void setPosition(Vector3f pos){
		boundingBox.setLocation(pos);
		position = pos;
		updateModel_matrix();
	}
	
	public void setScale(float scale){
		this.scale = scale;
		updateModel_matrix();
	}
	
	private void updateModel_matrix(){
		model = new Matrix4f().translate(position).scale(scale).rotateY(rotation);
	}
	
	public void setRotation(float theta){
		rotation = theta;
		updateModel_matrix();
	//boundingBox.setRotation(theta);
	}
	
	protected void setGraphics(GraphicsObject graphics){
		this.graphics = graphics;
	}
	
	public void setCamera(Camera camera){
		graphics.setCamera(camera);
		boundingBox.graphics.setCamera(camera);
	}
	
	public GameObject collisionTest(ArrayList<GameObject> gameObjects){
		for(GameObject each: gameObjects){
			if(each != this && each.boundingBox.intersects(boundingBox))
				return each;
		}
		return null;
	}

	@Override
	public void render(Shaderprogram shader, Matrix4f projection_matrix) {
		graphics.model_matrix = model;
		graphics.render(shader, projection_matrix);
		//boundingBox.render(shader, projection_matrix);
	}
}
