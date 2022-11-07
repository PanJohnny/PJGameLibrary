package com.panjohnny.pjgl.api.utils;

import com.panjohnny.pjgl.core.helpers.Texture;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL12;

import javax.imageio.ImageIO;

import static org.lwjgl.opengl.GL11.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Objects;

@SuppressWarnings("unused")
public final class TextureLoader {
    /**
     * Loads GL 2d texture from image
     * @param image image that the texture will be loaded from
     * @return texture
     * @throws IllegalArgumentException if image didn't had alpha raster
     */
    public static int loadTextureI(BufferedImage image) {
        // check if image has rgb
        if (image.getAlphaRaster() != null)
            throw new IllegalArgumentException("this method accepts only argb images");
        ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * 4);

        Color c;
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                c = new Color(image.getRGB(x, y));

                buffer.put((byte) c.getRed());
                buffer.put((byte) c.getGreen());
                buffer.put((byte) c.getBlue());
                buffer.put((byte) c.getAlpha());
            }
        }

        buffer.flip();

        int textureId = glGenTextures();

        glBindTexture(GL_TEXTURE_2D, textureId);

        //Setup wrap mode
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);

        //Setup texture scaling filtering
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        //Send texel data to OpenGL
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, image.getWidth(), image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);

        return textureId;
    }

    public static Texture loadTexture(BufferedImage image) {
        return new Texture(loadTextureI(image));
    }

    /**
     * @implNote loads resource located in the jar file
     * @param name path to the image
     * @return image
     * @throws IOException if there was error loading image
     * @throws NullPointerException if image was not found
     */
    public static BufferedImage loadImage(String name) throws IOException {
        return ImageIO.read(Objects.requireNonNull(TextureLoader.class.getResourceAsStream(name)));
    }

    /**
     * @implNote loads resource located in the jar file
     * @param name path to the image
     * @return image
     * @throws NullPointerException if image was not found
     */
    public static BufferedImage loadImageAndHandleException(String name)  {
        try {
            return ImageIO.read(Objects.requireNonNull(TextureLoader.class.getResourceAsStream(name)));
        } catch (IOException exception) {
            throw new NullPointerException("Not found");
        }
    }
}
