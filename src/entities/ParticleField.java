package entities;

import java.util.concurrent.CopyOnWriteArrayList;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import graphics.Shaderprogram;
import utilities.OpenGLUtils;

public class ParticleField implements Renderable, Updateable{
	
	private CopyOnWriteArrayList <Particle> prtclList = new CopyOnWriteArrayList<Particle>();
	private Camera camera;
	public Vector3f position;
	private int count = 25;
	private Scene parent;
	
	public ParticleField(Camera camera, Scene parent, Vector3f position){
		this.position = position;
		this.parent = parent;
		this.camera = camera;
		for(int i = 0; i < count; i++){
			Particle p = new Particle(this, position);
			p.setCamera(camera);
			prtclList.add(p);
		}
	}
	
	
	@Override
	public void render(Shaderprogram shader, Matrix4f projection_matrix) {
		for(Particle each: prtclList) each.render(shader, projection_matrix);
		
	}

	@Override
	public void setCamera(Camera camera) {
		this.camera = camera;
		
	}


	public void killParticle(Particle p) {
		prtclList.remove(p);
		
	}


	@Override
	public void update(double deltaTime) {
		for(Particle each: prtclList) each.proceed(deltaTime);
		if(prtclList.isEmpty()) parent.killParticleField(this);
	}
	
}
