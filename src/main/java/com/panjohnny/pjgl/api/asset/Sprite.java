package com.panjohnny.pjgl.api.asset;

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
