package com.panjohnny.pjgl.api.asset;

import com.panjohnny.pjgl.api.PJGL;
import com.panjohnny.pjgl.api.asset.img.SpriteUtil;

import java.io.IOException;
import java.util.HashMap;

/**
 * <h1>Sprite registration</h1>
 * <p>This is in-depth explanation of image loading and image registration</p>
 * <h2>Image Sprites</h2>
 * <p>Image sprites are mainly for {@link com.panjohnny.pjgl.adapt.desktop} adaptation, when {@link java.awt.Image} is used to render sprites.</p>
 * <p>Register them with {@link #registerImageSprite(String, String)}. This method supports loading from classpath and filesystem.</p>
 * <h2>Texture Sprites</h2>
 * <p>These sprites contain integer representing OpenGL texture.</p>
 * <p>Register them with {@link #registerTextureSprite(String, String)}. This method supports loading from filesystem.</p>
 */
@SuppressWarnings("unused")
public final class SpriteRegistry {
    public static final HashMap<String, Sprite<?>> SPRITES = new HashMap<>();
    private static final System.Logger LOGGER = System.getLogger(SpriteRegistry.class.getName());

    /**
     * Same as {@link #registerSprite(Sprite)}, but when registering typeof String automatically calls {@link #registerImageSprite(String, String)}.
     * @implSpec Use only for {@link com.panjohnny.pjgl.adapt.desktop} adaptations (when Image is being supplied to texture). Supports classpath and filesystem.
     * @deprecated Please refrain from using this method as it does not differ from adaptation currently in use and only registers image sprites.
     * @see SpriteRegistry technical details
     */
    public static <T> void registerSprite(String id, T t) {
        if (t instanceof String s) {
            registerImageSprite(id, s);
        } else
            SPRITES.put(id, new Sprite<>(id, t));
    }

    /**
     * Registers Image Sprite. Supports loading from classpath and filesystem.
     * @see SpriteRegistry technical details
     */
    public static void registerImageSprite(String id, String file) {
        try {
            registerSprite(new Sprite<>(id, SpriteUtil.loadImage(file)));
        } catch (IOException e) {
            PJGL.LOGGER.log(System.Logger.Level.ERROR, "Failed to load image: " + file);
        }
    }

    /**
     * <p>Registers Texture Sprite - for <b>OpenGL</b>. Supports loading from filesystem.</p>
     * <p>Loads texture using following parameters:</p>
     * <ul>
     *     <li>GL_TEXTURE_WRAP_S, GL_REPEAT</li>
     *     <li>GL_TEXTURE_WRAP_T, GL_REPEAT</li>
     *     <li>GL_TEXTURE_MIN_FILTER, GL_NEAREST</li>
     *     <li>GL_TEXTURE_MAG_FILTER, GL_NEAREST</li>
     * </ul>
     * <p>You can use {@link SpriteUtil#loadTexture(String, int, int, int, int)} to load your own and change few parameters, then register it using {@link #registerSprite(Sprite)} with {@code Sprite<Integer>}.</p>
     * @see SpriteRegistry technical details
     * @see SpriteUtil#loadTexture(String) default texture parameters
     */
    public static void registerTextureSprite(String id, String file) {
        registerSprite(new Sprite<>(id, SpriteUtil.loadTexture(file)));
    }

    public static void registerSprite(Sprite<?> sprite) {
        SPRITES.put(sprite.getID(), sprite);
    }

    public static Sprite<?> getSprite(String id) {
        return SPRITES.get(id);
    }
}
