package entities;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL13;

import graphics.Shaderprogram;
import graphics.Texture;

public class SkyBox implements Renderable{
	
	private final float size = 100000f;
	private Texture[] texture;
	private GraphicsObject graphics;
	private final int DAY = 0, NIGHT = 1;
	private int time;

	public SkyBox(){
		texture = new Texture[]{
				new Texture(new String[]{
						"res/textures/day/cubeSides1.png",
						"res/textures/day/cubeSides3.png",
						"res/textures/day/cubeTop.png",
						"res/textures/day/cubeBot.png",
						"res/textures/day/cubeSides2.png",
						"res/textures/day/cubeSides4.png"}),
				new Texture(new String[]{
						"res/textures/night/cubeSides1.png",
						"res/textures/night/cubeSides3.png",
						"res/textures/night/cubeTop.png",
						"res/textures/night/cubeBot.png",
						"res/textures/night/cubeSides2.png",
						"res/textures/night/cubeSides4.png"})
		};
		graphics = new GraphicsObject(vertexCoords, indices);
		time = DAY;
	}

	 float[] vertexCoords = new float[]{
       size , size, size,
       -size , size , size,
       -size , -size, size,
       size , -size , size,
       size , -size , -size,
       size , size , -size,
       -size , size , -size,
       -size , -size , -size,
	 };

	 int[] indices = new int[]{
       2, 1, 0,    0, 3, 2,
       4, 3, 0,    0, 5, 4,
       6, 5, 0,    0, 1, 6,
       7, 6, 1,    1, 2, 7,
       3, 4, 7,    7, 2, 3,
       6, 7, 4,    4, 5, 6
	 };
	 /*
	 int[] indices = new int[]{
       0, 1, 2,    2, 3, 0,
       0, 3, 4,    4, 5, 0,
       0, 5, 6,    6, 1, 0,
       1, 6, 7,    7, 2, 1,
       7, 4, 3,    3, 2, 7,
       4, 7, 6,    6, 5, 4
	 };
	 */
	@Override
	public void render(Shaderprogram shader, Matrix4f projection_matrix) {
		int daytime;
		if(Scene.dayTime){
			texture[0].bindCube(GL13.GL_TEXTURE0);
			daytime = 0;
		}
		else {
			texture[1].bindCube(GL13.GL_TEXTURE0);
			daytime = 1;
		}
		
		shader.setUniform1i("tex", 0);
		
		shader.setUniform1i("daytime", daytime);
		graphics.render(shader, projection_matrix);
	}

	public void setCamera(Camera camera) {
		graphics.setCamera(camera);
	}

}
