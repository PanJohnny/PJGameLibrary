package com.panjohnny.pjgl.api.camera;

/**
 * Simple orthographic camera implementation. Override {@link #apply(Object)} to apply effects.
 *
 * @since 0.3.7-alpha
 * @author PanJohnny
 */
@SuppressWarnings("unused")
public abstract class OrthographicCamera2D {
    protected double transformX, transformY;
    protected double scaleX = 1, scaleY = 1;
    protected int width, height;


    public void move(double x, double y) {
        this.transformX += x;
        this.transformY += y;
    }

    public void scale(double scaleX, double scaleY) {
        this.scaleX += scaleX;
        this.scaleY += scaleY;
    }

    public double getTransformX() {
        return transformX;
    }

    public void setTransformX(double transformX) {
        this.transformX = transformX;
    }

    public double getTransformY() {
        return transformY;
    }

    public void setTransformY(double transformY) {
        this.transformY = transformY;
    }

    public double getScaleX() {
        return scaleX;
    }

    public void setScaleX(double scaleX) {
        this.scaleX = scaleX;
    }

    public double getScaleY() {
        return scaleY;
    }

    public void setScaleY(double scaleY) {
        this.scaleY = scaleY;
    }

    public void setSize(int width, int height) {
        setWidth(width);
        setHeight(height);
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Used for renderers to apply data or use data in their camera implementations.
     */
    public abstract <T> void apply(T handle);
}
