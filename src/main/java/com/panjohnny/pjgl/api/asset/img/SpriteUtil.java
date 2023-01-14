package com.panjohnny.pjgl.api.asset.img;

import com.panjohnny.pjgl.api.PJGL;
import com.panjohnny.pjgl.api.asset.Sprite;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public final class SpriteUtil {
    public static Sprite<Image> createImageSprite(String id, String fileName) {
        try {
            return new Sprite<>(id, loadImage(fileName));
        } catch (IOException e) {
            PJGL.LOGGER.log(System.Logger.Level.ERROR, "Failed to load image: " + fileName);
            return null;
        }
    }

    public static Image loadImage(String fileName) throws IOException {
        return ImageIO.read(Objects.requireNonNull(SpriteUtil.class.getResourceAsStream(fileName)));
    }
}
