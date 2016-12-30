package graphics;

import utilities.OpenGLUtils;
import utilities.ShaderUtils;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL11.glGetIntegerv;
import static org.lwjgl.opengl.GL20.*;

/**
 * Created by Dennis Dubbert on 06.09.16.
 */
public class Shaderprogram {
    private int ID;
    private Map<String, Integer> locationCache = new HashMap<String, Integer>();

    public Shaderprogram(String vertex, String fragment){
        ID = ShaderUtils.load(vertex, fragment);
    }

    public int getUniformLocation(String name){

        if(locationCache.containsKey(name)){
            return locationCache.get(name);
        }

        int result = glGetUniformLocation(ID, name);

        if(result == -1){
            System.err.println("Could not find uniform variable '" +  name + "' !");
        }else{
            locationCache.put(name, result);
        }
        return result;
    }

    public void setUniform1i(String name, int value){
        if(!isEnabled()) useProgram();
        glUniform1i(getUniformLocation(name), value);
    }

    public void setUniform1f(String name, float value){
        if(!isEnabled()) useProgram();
        glUniform1f(getUniformLocation(name), value);
    }

    public void setUniform2f(String name, float x, float y){
        if(!isEnabled()) useProgram();
        glUniform2f(getUniformLocation(name), x, y);
    }

    public void setUniform3f(String name, Vector3f v){
        if(!isEnabled()) useProgram();
        glUniform3f(getUniformLocation(name), v.x, v.y, v.z);
    }

    public void setUniformMat4f(String name, Matrix4f matrix){
        if(!isEnabled()) useProgram();
        FloatBuffer fb = BufferUtils.createFloatBuffer(16);
        glUniformMatrix4fv(getUniformLocation(name), false, matrix.get(fb));
    }

    public void useProgram(){
        glUseProgram(ID);
        OpenGLUtils.checkForOpenGLError();
    }

    private boolean isEnabled(){
        IntBuffer temp = BufferUtils.createIntBuffer(1);
        glGetIntegerv(GL_CURRENT_PROGRAM, temp);
        return temp.get(0) == ID;
    }
}
