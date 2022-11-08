package com.panjohnny.pjgl.api.object;

import com.panjohnny.pjgl.core.helpers.Model;
import com.panjohnny.pjgl.core.helpers.Texture;
import org.joml.Matrix4f;

@SuppressWarnings("unused")
public abstract class GameObject {

    protected int x,y,width,height;
    protected final Model model;
    protected final Texture texture;

    public GameObject(int x, int y, int width, int height, Model model, Texture texture) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.model = model;
        this.texture = texture;
    }

    public abstract void update(long delta);

    public void bind() {
        texture.bind(0);
    }

    public void render() {
        model.render();
    }

    public Matrix4f scale() {
        return new Matrix4f().scale(this.width / 16.0f, this.height / 16.0f, 0);
    }

    public int getX() {
        return x;
    }

    public GameObject setX(int x) {
        this.x = x;
        return this;
    }

    public int getY() {
        return y;
    }

    public GameObject setY(int y) {
        this.y = y;
        return this;
    }

    public int getWidth() {
        return width;
    }

    public GameObject setWidth(int width) {
        this.width = width;
        return this;
    }

    public int getHeight() {
        return height;
    }

    public GameObject setHeight(int height) {
        this.height = height;
        return this;
    }

    /**
     * Very important method for checking if object can be rendered.
     * @return true if the object can be rendered
     */
    public boolean canBeRendered() {
        return texture != null;
    }
}
