package com.panjohnny.pjgl.api.asset.img;

import com.panjohnny.pjgl.api.PJGL;
import com.panjohnny.pjgl.api.asset.Sprite;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.stb.STBImage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

/**
 * Utility for handling sprites.
 *
 * @author PanJohnny
 */
public final class SpriteUtil {
    private static final System.Logger LOGGER = System.getLogger(SpriteUtil.class.getName());
    /**
     * Creates sprite from image.
     * @param id Unique identifier.
     * @param fileName Name of file from where the sprite is loaded.
     * @see #loadImage(String)
     * @return Sprite with <code>T</code> of <code>Image</code>.
     * 
     * @see com.panjohnny.pjgl.api.asset.SpriteRegistry#registerImageSprite(String, String)
     */
    @Deprecated
    public static Sprite<Image> createImageSprite(String id, String fileName) {
        try {
            return new Sprite<>(id, loadImage(fileName));
        } catch (IOException e) {
            PJGL.LOGGER.log(System.Logger.Level.ERROR, "Failed to load image: " + fileName);
            return null;
        }
    }

    /**
     * Loads an image from fileName.
     *
     * @implNote Uses {@link Class#getResourceAsStream(String)}.
     * @param fileName Name of the file.
     * @return Loaded image.
     * @throws IOException When something goes wrong.
     * @see com.panjohnny.pjgl.api.asset.SpriteRegistry#registerImageSprite(String, String)
     * @throws FileNotFoundException if file is not found as resource or as a file
     */
    public static Image loadImage(String fileName) throws IOException {
        // Attempt to load from stream
        try (InputStream resourceAsStream = SpriteUtil.class.getResourceAsStream(fileName)) {
            if (resourceAsStream == null) {
                // Attempt to load as file
                File file = new File(fileName);
                if (file.exists()) {
                    // proceed
                    return ImageIO.read(file);
                } else {
                    throw new FileNotFoundException("Image not found as resource or as a file");
                }
            }
            return ImageIO.read(resourceAsStream);
        }
    }

    /**
     * Loads texture using following parameters:
     * <ul>
     *     <li>GL_TEXTURE_WRAP_S, GL_REPEAT</li>
     *     <li>GL_TEXTURE_WRAP_T, GL_REPEAT</li>
     *     <li>GL_TEXTURE_MIN_FILTER, GL_NEAREST</li>
     *     <li>GL_TEXTURE_MAG_FILTER, GL_NEAREST</li>
     * </ul>
     *
     * @param file path to file
     * @return texture id
     * @see #loadTexture(String, int, int, int, int)
     */
    public static int loadTexture(String file) {
        return loadTexture(file, GL11.GL_REPEAT, GL11.GL_REPEAT, GL11.GL_NEAREST, GL11.GL_NEAREST);
    }
    /**
     * Loads texture from filesystem using STBImage
     * @param file path to file
     * @param min_filter GL_TEXTURE_MIN_FILTER
     * @param mag_filter GL_TEXTURE_MAG_FILTER
     * @param wrap_s GL_TEXTURE_WRAP_S
     * @param wrap_t GL_TEXTURE_WRAP_T
     * @return texture id
     */
    public static int loadTexture(String file, int wrap_s, int wrap_t, int min_filter, int mag_filter) {
        IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer height = BufferUtils.createIntBuffer(1);
        IntBuffer channels = BufferUtils.createIntBuffer(1);

        // Load the image data using STB
        ByteBuffer imageBuffer = STBImage.stbi_load(file, width, height, channels, 0);
        if (imageBuffer == null) {
            throw new RuntimeException("Failed to load image: " + file + ", Reason: " + STBImage.stbi_failure_reason());
        }

        if (GLFW.glfwGetCurrentContext() == 0) {
            LOGGER.log(System.Logger.Level.ERROR, "OpenGL context is not initialized, loading textures is supported after window opens");
            throw new RuntimeException("OpenGL context not initialized");
        }
        // Generate a new OpenGL texture
        int textureId = GL11.glGenTextures();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId);

        // Upload the image data to the texture
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width.get(), height.get(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, imageBuffer);

        // Set texture parameters
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, wrap_s);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, wrap_t);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, min_filter);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, mag_filter);

        // Free the image data buffer
        STBImage.stbi_image_free(imageBuffer);

        return textureId;
    }
}
