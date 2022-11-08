package com.panjohnny.pjgl.api.object;

import com.panjohnny.pjgl.core.helpers.Shader;
import com.panjohnny.pjgl.core.rendering.Camera;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public abstract class AbstractGameObjectRenderer {
    /**
     * Method for outside world to render object. We don't want to render applicable things.
     * <b>Here is example from simple object manager:</b>
     * <pre>
     *     {@code
     * var objects = getObjects();
     * for (GameObject object : objects) {
     *      recommendedRenderer.renderObject(object, world, camera, shader);
     * }
     *     }
     *
     * </pre>
     *
     * @see #isApplicable(GameObject)
     */
    public void renderObject(GameObject object, Matrix4f world, Camera cam, Shader shader) {
        if (isApplicable(object))
            render(object, world, cam, shader);
    }

    protected abstract void render(GameObject object, Matrix4f world, Camera cam, Shader shader);

    /**
     * Checks if object is applicable for rendering
     * @param object object that we check
     * @return by default {@link GameObject#canBeRendered()}
     */
    public boolean isApplicable(GameObject object) {
        return object.canBeRendered();
    }

    public static class SimpleGameObjectRenderer extends AbstractGameObjectRenderer {
        @Override
        protected void render(GameObject object, Matrix4f world, Camera cam, Shader shader) {
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