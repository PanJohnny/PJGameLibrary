package com.panjohnny.pjgl.api.asset;

import com.panjohnny.pjgl.api.PJGL;
import com.panjohnny.pjgl.api.asset.img.SpriteUtil;

import java.io.IOException;
import java.util.HashMap;

public final class SpriteRegistry {
    public static final HashMap<String, Sprite<?>> SPRITES = new HashMap<>();

    /**
     * When registering typeof String automatically calls {@link #registerImageSprite(String, String)}.
     */
    public static <T> void registerSprite(String id, T t) {
        if (t instanceof String s) {
            registerImageSprite(id, s);
        } else
            SPRITES.put(id, new Sprite<>(id, t));
    }

    public static void registerImageSprite(String id, String file) {
        try {
            registerSprite(id, SpriteUtil.loadImage(file));
        } catch (IOException e) {
            PJGL.LOGGER.log(System.Logger.Level.ERROR, "Failed to load image: " + file);
        }
    }

    public static void registerSprite(Sprite<?> sprite) {
        SPRITES.put(sprite.getID(), sprite);
    }

    public static Sprite<?> getSprite(String id) {
        return SPRITES.get(id);
    }
}
