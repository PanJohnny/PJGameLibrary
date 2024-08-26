package com.panjohnny.pjgl.api.asset;

import com.panjohnny.pjgl.api.util.SpriteUtil;

import java.io.Serializable;

/**
 * Implementation of {@link Drawable} that should be implemented by renderer adapter.
 *
 * @see com.panjohnny.pjgl.api.object.components.SpriteRenderer
 * @see SpriteUtil
 * @param <T> Type of sprite (type of data it holds).
 *
 * @author PanJohnny
 */
public class Sprite<T> implements Drawable, Serializable {
    private final String id;
    transient private final T image;

    public Sprite(String id, T image) {
        // Left this here for adaptation, this should be really deprecated
        if (image != null && !SpriteRegistry.SPRITES.containsKey(id))
            SpriteRegistry.registerSprite(this);
        this.id = id;
        this.image = image;
    }

    @Override
    public String getID() {
        return id;
    }

    public T getImage() {
        return image;
    }

    @Override
    public Object getValue() {
        return image;
    }

    public boolean of(Class<?> clazz) {
        return image != null && clazz.isAssignableFrom(image.getClass());
    }
}
