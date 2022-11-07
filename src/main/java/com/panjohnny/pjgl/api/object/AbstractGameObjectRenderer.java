package com.panjohnny.pjgl.api.object;

import com.panjohnny.pjgl.core.helpers.Shader;
import com.panjohnny.pjgl.core.rendering.Camera;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public abstract class AbstractGameObjectRenderer {
    public abstract void render(GameObject object, Matrix4f world, Camera cam, Shader shader);

    public static class SimpleGameObjectRenderer extends AbstractGameObjectRenderer {
        @Override
        public void render(GameObject object, Matrix4f world, Camera cam, Shader shader) {
            object.bind();
            Matrix4f pos = new Matrix4f().translate(new Vector3f(object.getX(), object.getY(), 0));
            Matrix4f target = new Matrix4f();

            cam.getProjection().mul(world, target);
            target.mul(pos);
            target.mul(object.scale());

            shader.bind();
            shader.setUniformInteger("sampler", 0);
            shader.setUniformMatrix4f("projection", target);

            object.render();
        }
    }
}