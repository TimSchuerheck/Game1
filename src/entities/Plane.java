
	package entities;

	
	import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL13;

import graphics.Shaderprogram;
import graphics.Texture;

	public class Plane implements Renderable{
		
		private GraphicsObject ground;
		private final float scale = 300f;
		private final float texReps = 150f;
		private final Texture groundTexture = new Texture("res/textures/grass.jpg");
		private final Texture groundTextureSpecular = new Texture("res/textures/grass_specular.jpg");
		
		public void setCamera(Camera camera){
			ground.setCamera(camera);
		}

		public Plane (){
			ground = new GraphicsObject(vertexCoords, textureCoords, normals, indices, groundTexture);
		}
		
		private float[] normals = new float[]{
			0f,1f,0f,
			0f,1f,0f
		};
		
			private int[] indices = new int[]{
					2, 3, 1,
					1, 0, 2

			};

			private float[] textureCoords = new float[]{
          0.0f, 0.0f,
          texReps, 0.0f,
					0.0f, texReps,
					texReps, texReps
			};

			private float[] vertexCoords = new float[]{
         -scale,0f, -scale,
         scale,0f, -scale,
         -scale,0f,  scale,
         scale,0f, scale
			};

		@Override
		public void render(Shaderprogram shader, Matrix4f projection_matrix) {
			groundTexture.bind(GL13.GL_TEXTURE0);
			shader.setUniform1i("tex0", 0);
			groundTextureSpecular.bind(GL13.GL_TEXTURE1);
			shader.setUniform1i("tex1", 1);
			shader.setUniform3f("light_color", new Vector3f(0f,1f,0.1f));
			shader.setUniform1f("shininess", 1f);
			shader.setUniform3f("light_position", new Vector3f(0f,100f, 0f));
			
			int daytime;
			if(Scene.dayTime) daytime = 0;
			else daytime = 1;
			
			shader.setUniform1i("daytime", daytime);
			ground.render(shader, projection_matrix);

		}

}
