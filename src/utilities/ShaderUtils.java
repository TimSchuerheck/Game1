package utilities;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;

/**
 * Created by Dennis Dubbert on 06.09.16.
 */
public class ShaderUtils {

    private ShaderUtils(){

    }

    public static int load(String vertPath, String fragPath){
        String vert = FileUtils.loadAsString(vertPath);
        String frag = FileUtils.loadAsString(fragPath);
        return create(vert, frag);
    }

    public static int create(String vert, String frag){
        int program = glCreateProgram();
        int vertID = glCreateShader(GL_VERTEX_SHADER);
        int fragID = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(vertID, vert);
        glShaderSource(fragID, frag);

        glCompileShader(vertID);
        OpenGLUtils.printShaderInfoLog(vertID);

        glCompileShader(fragID);
        OpenGLUtils.printShaderInfoLog(fragID);

        glAttachShader(program, vertID);
        glAttachShader(program, fragID);

        glLinkProgram(program);
        glValidateProgram(program);

        glDeleteShader(vertID);
        glDeleteShader(fragID);

        return program;
    }
}
