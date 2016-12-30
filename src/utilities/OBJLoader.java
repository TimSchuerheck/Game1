
package utilities;


import graphics.VertexArrayObject;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


public class OBJLoader {
		
		private float[] verticesArray;
		
		public float[] getVertices(){
			return verticesArray;
		}
		
    public VertexArrayObject loadObjModel(String filePath) {

        FileReader fr = null;
        try {
            fr = new FileReader(new File(filePath));
        } catch (FileNotFoundException e) {
            System.err.println("*OBJ-Datei " + filePath + " konnte nicht gefunden werden.");
            e.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(fr);
        String line;
        List<Vector3f> vertices = new ArrayList<Vector3f>();
        List<Vector3f> normals = new ArrayList<Vector3f>();
        List<Vector2f> textures = new ArrayList<Vector2f>();
        List<Integer>  indices = new ArrayList<Integer>();

        float[] normalsArray = null;
        float[] texturesArray = null;
        int[] indicesArray = null;

        try {
            while (true) {
                line = reader.readLine();
                String[] currentLine = line.split(" ");
               // System.out.println(line);
                if (line.startsWith("#")) continue;
                if(line.startsWith("o "))	continue;
                if(line.startsWith("mtllib ")) continue;
                if(line.startsWith("usemtl ")) continue;
                if(line.startsWith("s "))	continue;
                if(line.startsWith("g ")) continue;
                if(line.equals("")) continue;

                if (line.startsWith("v ")) {
                		Vector3f vertex;
                		try{
                    vertex = new Vector3f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]),
                            Float.parseFloat(currentLine[3]));
                		} catch (Exception e) {
                		vertex = new Vector3f(Float.parseFloat(currentLine[2]), Float.parseFloat(currentLine[3]),
                          Float.parseFloat(currentLine[4]));
                		}
                    vertices.add(vertex);
                } else if(line.startsWith("vt ")){
                    Vector2f texture = new Vector2f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]));
                    textures.add(texture);
                } else if (line.startsWith("vn ")) {
                    Vector3f normal = new Vector3f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]),
                            Float.parseFloat(currentLine[3]));
                    normals.add(normal);
                } else if (line.startsWith("f ")) {
                    texturesArray = new float[vertices.size() *2];
                    normalsArray = new float[vertices.size() * 3];
                    break;
                } else
                    break;
            }

            while (line != null) {
                if (!line.startsWith("f ")) {
                    line = reader.readLine();
                    continue;
                }
                try{
                String[] currentLine = line.split(" ");
                String[] vertex1 = currentLine[1].split("/");
                String[] vertex2 = currentLine[2].split("/");
                String[] vertex3 = currentLine[4].split("/");
                String[] vertex4 = currentLine[2].split("/");
                String[] vertex5 = currentLine[3].split("/");
                String[] vertex6 = currentLine[4].split("/");
                
                processVertex(vertex1, indices, textures, normals, texturesArray, normalsArray);
                processVertex(vertex2, indices, textures, normals, texturesArray, normalsArray);
                processVertex(vertex3, indices, textures, normals, texturesArray, normalsArray);
                processVertex(vertex4, indices, textures, normals, texturesArray, normalsArray);
                processVertex(vertex5, indices, textures, normals, texturesArray, normalsArray);
                processVertex(vertex6, indices, textures, normals, texturesArray, normalsArray);
                } catch (Exception e){
                	 String[] currentLine = line.split(" ");
                   String[] vertex1 = currentLine[1].split("/");
                   String[] vertex2 = currentLine[2].split("/");
                   String[] vertex3 = currentLine[3].split("/");
                   processVertex(vertex1, indices, textures, normals, texturesArray, normalsArray);
                   processVertex(vertex2, indices, textures, normals, texturesArray, normalsArray);
                   processVertex(vertex3, indices, textures, normals, texturesArray, normalsArray);
                }
                line = reader.readLine();
            }
            reader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        verticesArray = new float[vertices.size() * 3];
        indicesArray = new int[indices.size()];

        int vertexPointer = 0;
        for (Vector3f vertex : vertices) {
            verticesArray[vertexPointer++] = vertex.x;
            verticesArray[vertexPointer++] = vertex.y;
            verticesArray[vertexPointer++] = vertex.z;
        }
        for (int i = 0; i < indices.size(); i++) {
            indicesArray[i] = indices.get(i);
        }
        
        VertexArrayObject vao = new VertexArrayObject();
        vao.bindData(verticesArray, 0, 3);
        vao.bindData(texturesArray, 1, 2);
        vao.bindData(normalsArray, 2, 3);
        vao.bindIndices(indicesArray);
        OpenGLUtils.checkForOpenGLError();
        return vao;
    }

    private static void processVertex(String[] vertexData,  List<Integer> indices,
                                      List<Vector2f> textures, List<Vector3f> normals,
                                      float[] texturesArray, float[] normalsArray) {
        int currentVertexPointer = Integer.parseInt(vertexData[0]) - 1;
        indices.add(currentVertexPointer);
        Vector2f currentTex = textures.get(Integer.parseInt(vertexData[1]) - 1);
        texturesArray[currentVertexPointer*2] = currentTex.x;
        texturesArray[currentVertexPointer*2+1] = 1 - currentTex.y;

        Vector3f currentNorm = normals.get(Integer.parseInt(vertexData[2]) - 1);
        normalsArray[currentVertexPointer * 3] = currentNorm.x;
        normalsArray[currentVertexPointer * 3 + 1] = currentNorm.y;
        normalsArray[currentVertexPointer * 3 + 2] = currentNorm.z;
    }
}

