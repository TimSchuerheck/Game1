package utilities;

import org.joml.Vector2i;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * Created by Dennis Dubbert on 06.09.16.
 */
public class FileUtils {
    private FileUtils(){

    }

    public static int[] loadImage(String path, Vector2i size){
        int[] pixels = null;
        try{
            FileInputStream input = new FileInputStream(path);
            BufferedImage image = ImageIO.read(input);
            size.x = image.getWidth();
            size.y = image.getHeight();
            pixels = new int[size.x * size.y];
            image.getRGB(0, 0, size.x, size.y, pixels, 0, size.x);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        int[] data = new int[size.x * size.y];
        for(int i=0; i<size.x*size.y; i++){
            int a = (pixels[i] & 0xff000000) >> 24;
            int r = (pixels[i] & 0xff0000) >> 16;
            int g = (pixels[i] & 0xff00) >> 8;
            int b = (pixels[i] & 0xff);

            data[i] = a << 24 | b << 16 | g << 8 | r;
        }
        return data;
    }

    public static String loadAsString(String file){
        StringBuilder result = new StringBuilder();
        try{
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String buffer = "";
            while((buffer = reader.readLine()) != null){
                result.append(buffer + '\n');
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result.toString();
    }
}
