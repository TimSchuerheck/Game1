package utilities;

import org.lwjgl.opengl.GL30;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

/**
 * Created by martineisemann on 09.11.16.
 */
public class OpenGLUtils {
    //// Error related functions ////
    public static void checkForOpenGLError()
    {
        boolean errorOccured = false;

        int errorCode;
        while ((errorCode = glGetError()) != GL_NO_ERROR)
        {
            errorOccured = true;

            String error = "";
            switch (errorCode)
            {
                case GL_INVALID_ENUM:                  error = "INVALID_ENUM"; break;
                case GL_INVALID_VALUE:                 error = "INVALID_VALUE"; break;
                case GL_INVALID_OPERATION:             error = "INVALID_OPERATION"; break;
                case GL_STACK_OVERFLOW:                error = "STACK_OVERFLOW"; break;
                case GL_STACK_UNDERFLOW:               error = "STACK_UNDERFLOW"; break;
                case GL_OUT_OF_MEMORY:                 error = "OUT_OF_MEMORY"; break;
                case GL30.GL_INVALID_FRAMEBUFFER_OPERATION: error = "INVALID_FRAMEBUFFER_OPERATION"; break;
            }
            System.err.println("An OpenGL error occured in " + DebugUtils.getFileAndLine(3));
            System.err.println(error);
        }

        if(errorOccured){
            System.exit(-1);
        }
    }

    /**
     * \brief Prints the shader info log of a vertex or fragment shader
     *
     * \param shaderID - ID of the shader to be tested for errors
     */
    public static void printShaderInfoLog(int shaderID)
    {
        int result = glGetShaderi(shaderID, GL_COMPILE_STATUS);
        if(result == GL_FALSE){
            int length = glGetShaderi(shaderID, GL_INFO_LOG_LENGTH);
            String logString = "";
            if(length > 0){
                logString = glGetShaderInfoLog(shaderID, length);
            }

            System.err.println(logString);
            System.exit(-1);
        }

        checkForOpenGLError();
    }
}
