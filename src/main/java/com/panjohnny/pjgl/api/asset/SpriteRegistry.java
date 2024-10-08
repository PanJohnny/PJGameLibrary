package com.panjohnny.pjgl.api.asset;

import com.panjohnny.pjgl.api.PJGL;
import com.panjohnny.pjgl.api.asset.atlas.TextureAtlas;
import com.panjohnny.pjgl.api.util.SpriteUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <h1>Sprite registration</h1>
 * <p>This is in-depth explanation of image loading and image registration</p>
 * <h2>Image Sprites</h2>
 * <p>Image sprites are mainly for {@link com.panjohnny.pjgl.adapt.desktop} adaptation, when {@link java.awt.Image} is used to render sprites.</p>
 * <p>Register them with {@link #registerImageSprite(String, String)}. This method supports loading from classpath and filesystem.</p>
 * <h2>Texture Sprites</h2>
 * <p>These sprites contain integer representing OpenGL texture.</p>
 * <p>Register them with {@link #registerTextureSprite(String, String)}. This method supports loading from filesystem.</p>
 * @author PanJohnny
 */
@SuppressWarnings("unused")
public final class SpriteRegistry {
    public static final HashMap<String, Sprite<?>> SPRITES = new HashMap<>();
    private static final System.Logger LOGGER = System.getLogger(SpriteRegistry.class.getName());

    /**
     * Same as {@link #registerSprite(Sprite)}, but when registering typeof String automatically calls {@link #registerImageSprite(String, String)}.
     * @apiNote Use only for {@link com.panjohnny.pjgl.adapt.desktop} adaptations (when Image is being supplied to texture). Supports classpath and filesystem.
     * @deprecated Please refrain from using this method as it does not differ from adaptation currently in use and only registers image sprites.
     * @see SpriteRegistry technical details
     */
    @Deprecated
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
     * <p>Registers Texture Sprite as atlas - for <b>OpenGL</b>. Supports loading from filesystem.</p>
     * <p>Loads texture using following parameters:</p>
     * <ul>
     *     <li>GL_TEXTURE_WRAP_S, GL_REPEAT</li>
     *     <li>GL_TEXTURE_WRAP_T, GL_REPEAT</li>
     *     <li>GL_TEXTURE_MIN_FILTER, GL_NEAREST</li>
     *     <li>GL_TEXTURE_MAG_FILTER, GL_NEAREST</li>
     * </ul>
     * <p>You can use {@link SpriteUtil#loadTexture(String, int, int, int, int, java.util.function.BiConsumer)} to load your own and change few parameters, then register it using {@link #registerSprite(Sprite)} with {@code Sprite<Integer>}.</p>
     * @see SpriteRegistry technical details
     * @see SpriteUtil#loadTexture(String, java.util.function.BiConsumer) default texture parameters
     * @return TextureAtlas representing the sprite atlas
     */
    public static TextureAtlas registerTextureSpriteAsAtlas(String id, String file) {
        AtomicInteger width = new AtomicInteger();
        AtomicInteger height = new AtomicInteger();

        int texture = SpriteUtil.loadTexture(file, (w, h) -> {
            width.set(w);
            height.set(h);
        });

        return new TextureAtlas(id, texture, width.get(), height.get());
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
     * <p>You can use {@link SpriteUtil#loadTexture(String, int, int, int, int, java.util.function.BiConsumer)} to load your own and change few parameters, then register it using {@link #registerSprite(Sprite)} with {@code Sprite<Integer>}.</p>
     * @see SpriteRegistry technical details
     * @see SpriteUtil#loadTexture(String, java.util.function.BiConsumer) default texture parameters
     */
    public static void registerTextureSprite(String id, String file) {
        registerSprite(new Sprite<>(id, SpriteUtil.loadTexture(file, null)));
    }

    /**
     * Creates temporary texture sprite if someone is using G2D renderer. This is experimental feature and would be reworked. Applies only the first frame due to technical limitations.
     */
    public static void createTemporaryTextureSprite(Object object, BufferedImage image) {
        try {
            File file = new File("./.pjgl-temp/", object.toString() + "-render.png");
            if (!file.exists() && !file.mkdirs() && !file.createNewFile()) {
                PJGL.LOGGER.log(System.Logger.Level.WARNING, "failed to create temp texture sprite, file does not exist and cannot be created");
                return;
            }

            ImageIO.write(image, "PNG", file);
            PJGL.LOGGER.log(System.Logger.Level.WARNING, "Used G2D to render one object, please be aware that this only happens on the first render due to limitations. Change adaptation OR rework your renderer.");
            registerTextureSprite(object.toString(), file.getAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void registerSprite(Sprite<?> sprite) {
        SPRITES.put(sprite.getID(), sprite);
    }

    public static Sprite<?> getSprite(String id) {
        return SPRITES.get(id);
    }
}
