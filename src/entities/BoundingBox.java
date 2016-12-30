package entities;


import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.awt.Rectangle;

import org.joml.Intersectionf;
import org.joml.Matrix3f;

import graphics.Shaderprogram;

public class BoundingBox implements Renderable{
	
	GraphicsObject graphics;
	Rectangle bounds;
	private float[] vertices;
	/*
	private Vector2f[] bounds;
	private Vector2f[] transformedBounds;
	private Vector2f pos = new Vector2f();
	private float rotation;
	*/
	
	public void setLocation(Vector3f pos){
		graphics.model_matrix = new Matrix4f().translate(pos);
		bounds.setLocation((int)(pos.x * 100), (int)(pos.z * 100));
		
		/*
		this.pos = new Vector2f(pos.x, pos.z);
		applyTransformations();
		*/
	}
	/*
	public void setRotation(float theta){
		
		graphics.model_matrix.rotateY(theta);
		rotation = theta;
		applyTransformations();
		
	}
	*/
	/*
	private void applyTransformations(){
		Matrix3f rot = new Matrix3f().rotateY(rotation);
		for (int i = 0; i < bounds.length; i++){
			Vector3f v = new Vector3f(bounds[i].x, 0f, bounds[i].y)
					.add(pos.x, 0f, pos.y)
					.mul(rot);
			transformedBounds[i] = new Vector2f(v.x, v.z);
		}
		System.out.println(transformedBounds[0]);
	}
	*/
	/*
	public Vector2f[] getTBounds(){
		return transformedBounds;
	}
	*/
	public Rectangle getBounds(){
		return bounds;
	}
	
	public boolean intersects(BoundingBox b){
		if(b.getBounds().intersects(bounds))return true;
		/*
		float v0x = transformedBounds[0].x, v0y = transformedBounds[0].y,
				v1x = transformedBounds[1].x, v1y = transformedBounds[1].y,
				v2x = transformedBounds[2].x, v2y = transformedBounds[2].y,
				v3x = transformedBounds[3].x, v3y = transformedBounds[3].y;
		Vector2f v0 = new Vector2f(v0x, v0y), v1 = new Vector2f(v1x, v1y), v2 = new Vector2f(v2x, v2y),
				v3 = new Vector2f(v3x, v3y);

		for(int i = 0; i < 4; i++){
			Vector2f p = new Vector2f(b.getTBounds()[i].x, b.getTBounds()[i].y);	
			
			if(Intersectionf.testPointTriangle(p, v0, v1, v2)) return true;
			if(Intersectionf.testPointTriangle(p, v1, v2, v3)) return true;
		}
		*/
		return false;
	}
	
	public void initBoundingBox(float[] vertexCoords) {
		float smallestX = vertexCoords[0], smallestZ = vertexCoords[0], 
				biggestX = vertexCoords[0], biggestZ = vertexCoords[0];
		//check for the vertex with the smallest/biggest x and z coordinate
		for(int i = 0; i < vertexCoords.length; i+=3){
			smallestX = vertexCoords[i] 	< smallestX ? vertexCoords[i]		: smallestX;
			smallestZ = vertexCoords[i+2] < smallestZ ? vertexCoords[i+2]	:	smallestZ;
			biggestX 	= vertexCoords[i] 	> biggestX 	? vertexCoords[i]		: biggestX;
			biggestZ 	= vertexCoords[i+2] > biggestZ 	? vertexCoords[i+2]	: biggestZ;
		}
		vertices = new float[]{smallestX, 3f, smallestZ, biggestX, 3f, smallestZ, smallestX, 3f, biggestZ, biggestZ, 3f, biggestZ};
		bounds = new Rectangle((int)(smallestX*100), (int)(smallestZ*100), (int)(biggestX*100), (int)(biggestZ*100));

		/*
		bounds = new Vector2f[]{new Vector2f(smallestX, smallestZ), new Vector2f( smallestX, biggestZ),
					new Vector2f(biggestX, smallestZ), new Vector2f(biggestX, biggestX)};
		transformedBounds = bounds;
		*/
		initGraphics();
	}

	private void initGraphics() {
		/*
		for	(int i = 0; i <= 9; i += 3) {
			vertices[i] = bounds[i/3].x;
			vertices[i+1] = 3f;
			vertices[i+2] = bounds[i/3].y;
		}*/

		int[] indices = new int[]{
				2,1,0,
				3,1,2
		};
		float[] color = new float[]{1f,1f,0f	,1f,1f,0f,	1f,1f,0f,	1f,1f,0f};
		graphics = new GraphicsObject(vertices, color, indices);
	}

	@Override
	public void render(Shaderprogram shader, Matrix4f projection_matrix) {
		graphics.render(shader, projection_matrix);
	}
	@Override
	public void setCamera(Camera camera) {
		// TODO Auto-generated method stub
		
	}
}
