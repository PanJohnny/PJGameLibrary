package com.panjohnny.pjgl.api.asset;

/**
 * Implementation of {@link Drawable} that should be implemented by renderer adapter.
 *
 * @see com.panjohnny.pjgl.api.object.components.SpriteRenderer
 * @see com.panjohnny.pjgl.api.asset.img.SpriteUtil
 * @param <T> Type of sprite (type of data it holds).
 *
 * @author PanJohnny
 */
public class Sprite<T> implements Drawable {
    private final String id;
    private final T image;

    public Sprite(String id, T image) {
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
