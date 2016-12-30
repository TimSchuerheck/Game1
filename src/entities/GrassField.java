package entities;

import java.util.ArrayList;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.SimplexNoise;
import graphics.Shaderprogram;

public class GrassField implements Renderable{
	
	private ArrayList<Grass> list = new ArrayList<Grass>(); 
	private Camera camera;
	
	
	public GrassField(int count){
		for(int i = 0; i < count; i++){
			float x = (float) (Math.random()-0.5f)*200, y = (float) (Math.random()-0.5f)*200;
			float nscale = 50;
			list.add(new Grass((SimplexNoise.noise(x/nscale,y/nscale)+1.05f)*2+0.2f,x ,y));
		
		}
	
	}
	
	@Override
	public void render(Shaderprogram shader, Matrix4f projection_matrix) {

		for(Grass each: list) {
			Vector3f dTargetGrass = new Vector3f();
			camera.getTargetPosition().sub(each.getPosition(), dTargetGrass);
			if(dTargetGrass.length() < 50f){
				each.render(shader, projection_matrix);
			}
		
		}
	
	}

	@Override
	public void setCamera(Camera camera) {
		this.camera = camera;
		for(Grass each: list) {
			each.setCamera(camera);
		}
		
	}

}
