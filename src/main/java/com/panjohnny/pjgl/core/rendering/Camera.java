package com.panjohnny.pjgl.core.rendering;

import org.joml.Matrix4f;
import org.joml.Vector3f;

@SuppressWarnings("unused")
public abstract class Camera {
    private Vector3f position;

    public Camera(Vector3f position) {
        this.position = position;
    }

    public void setPos(Vector3f pos) {
        this.position = pos;
    }

    public void addPos(Vector3f pos) {
        this.position.add(pos);
    }

    public Vector3f getPos() {
        return position;
    }

    public abstract Matrix4f getProjection();
}
