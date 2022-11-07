package com.panjohnny.pjgl.core.rendering;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class OrthographicCamera extends Camera{
    private Matrix4f projection;

    public OrthographicCamera(int width, int height) {
        super(new Vector3f(0, 0, 0));
        projection = new Matrix4f().setOrtho2D(-width/2f, width/2f, -height/2f, height/2f);
    }

    public Matrix4f getProjection() {
        Matrix4f target = new Matrix4f();
        Matrix4f pos = new Matrix4f().setTranslation(getPos());

        target = projection.mul(pos, target);
        return target;
    }

    public void changeSize(int width, int height) {
        projection = new Matrix4f().setOrtho2D(-width/2f, width/2f, -height/2f, height/2f);
    }
}
