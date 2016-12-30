package graphics;

import utilities.BufferUtils;
import utilities.FileUtils;
import utilities.OpenGLUtils;
import org.joml.Vector2i;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL12.GL_TEXTURE_WRAP_R;
import static org.lwjgl.opengl.GL13.GL_TEXTURE_CUBE_MAP;
import static org.lwjgl.opengl.GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_X;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;

/**
 * Created by Dennis Dubbert on 06.09.16.
 */
public class Texture {

    private int texture;
    private Vector2i size = new Vector2i();
    private ArrayList<Vector2i> sizes = new ArrayList<Vector2i>();

    public Texture(String path){
        texture = load(path);
    }

    public Texture(String[] path){
        texture = loadCube(path);
    }

    public int load(String path){
        return createTexture(FileUtils.loadImage(path, size));
    }

    public int loadCube(String[] path){
        ArrayList<int[]> data = new ArrayList<int[]>();
        for(int i = 0; i < path.length; i++){
            sizes.add(i, new Vector2i());
            data.add(i, FileUtils.loadImage(path[i], sizes.get(i)));
        }
        return createCubeTexture(data);
    }

    private int createTexture(int[] data){
        int result = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, result);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, size.x, size.y, 0, GL_RGBA, GL_UNSIGNED_BYTE, BufferUtils.createIntBuffer(data));
        glGenerateMipmap(GL_TEXTURE_2D);
        unbind();
        return result;
    }

    private int createCubeTexture(ArrayList<int[]> data){
        int result = glGenTextures();
        glBindTexture(GL_TEXTURE_CUBE_MAP, result);
        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_R, GL_CLAMP_TO_EDGE);

        for(int i = 0; i<data.size(); i++){
            glTexImage2D(GL_TEXTURE_CUBE_MAP_POSITIVE_X + i, 0, GL_RGBA, sizes.get(i).x, sizes.get(i).y, 0, GL_RGBA, GL_UNSIGNED_BYTE, BufferUtils.createIntBuffer(data.get(i)));
        }

        glGenerateMipmap(GL_TEXTURE_CUBE_MAP);

        unbindCube();
        return result;
    }

    public void bind(int textureUnit){
        glActiveTexture(textureUnit);
        glBindTexture(GL_TEXTURE_2D, texture);
        OpenGLUtils.checkForOpenGLError();
    }

    public void bindCube(int textureUnit){
        glActiveTexture(textureUnit);
        glBindTexture(GL_TEXTURE_CUBE_MAP, texture);
        OpenGLUtils.checkForOpenGLError();
    }

    public void unbind(){
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    public void unbindCube(){
        glBindTexture(GL_TEXTURE_CUBE_MAP, 0);
    }
}
