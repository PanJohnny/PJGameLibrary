package com.panjohnny.pjgl.api.object;

import com.panjohnny.pjgl.core.helpers.Shader;
import com.panjohnny.pjgl.core.rendering.Camera;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractGameObjectManager<T extends GameObject> {
    private final List<T> objects;
    public AbstractGameObjectManager() {
        this.objects = new ArrayList<>();
    }

    public List<T> getObjects() {
        return objects;
    }

    public abstract void renderObjects(Matrix4f world, Camera camera, Shader shader, AbstractGameObjectRenderer recommendedRenderer);
    public abstract void updateObjects(long deltaTime);

    public static class SimpleGameObjectManager extends AbstractGameObjectManager<GameObject> {

        @Override
        public void renderObjects(Matrix4f world, Camera camera, Shader shader, AbstractGameObjectRenderer recommendedRenderer) {
            var objects = getObjects();
            for (GameObject object : objects) {
                recommendedRenderer.renderObject(object, world, camera, shader);
            }
        }

        @Override
        public void updateObjects(long deltaTime) {
            var objects = getObjects();
            for (GameObject object : objects) {
                object.update(deltaTime);
            }
        }
    }

    public void add(T t) {
        objects.add(t);
    }
}
