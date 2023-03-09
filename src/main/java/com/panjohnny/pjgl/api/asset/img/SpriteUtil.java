package com.panjohnny.pjgl.api.asset.img;

import com.panjohnny.pjgl.api.PJGL;
import com.panjohnny.pjgl.api.asset.Sprite;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

/**
 * Utility for handling sprites.
 *
 * @author PanJohnny
 */
public final class SpriteUtil {
    /**
     * Creates sprite from image.
     * @param id Unique identifier.
     * @param fileName Name of file from where the sprite is loaded.
     * @see #loadImage(String)
     * @return Sprite with <code>T</code> of <code>Image</code>.
     * 
     * @see com.panjohnny.pjgl.api.asset.SpriteRegistry#registerSprite(String, Object)
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
     * @see com.panjohnny.pjgl.api.asset.SpriteRegistry#registerSprite(String, Object)
     */
    public static Image loadImage(String fileName) throws IOException {
        return ImageIO.read(Objects.requireNonNull(SpriteUtil.class.getResourceAsStream(fileName)));
    }
}
