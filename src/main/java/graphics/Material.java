package graphics;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import utils.FileUtils;

import java.io.IOException;

import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL13.glDeleteTextures;

public class Material {
    private String path;
    private Texture texture;
    private float width, height;
    private int textureID;

    public Material(String path) {
        this.path = path;
    }

    public void create(){
        try {
            texture = TextureLoader.getTexture(path.split("[.]")[1], Material.class.getResourceAsStream(path), GL_LINEAR);
            width = texture.getWidth();
            height = texture.getHeight();
            textureID = texture.getTextureID();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void destroy(){
        glDeleteTextures(textureID);
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public int getTextureID() {
        return textureID;
    }
}
