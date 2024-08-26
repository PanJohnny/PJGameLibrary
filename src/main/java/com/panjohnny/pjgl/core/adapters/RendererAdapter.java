package com.panjohnny.pjgl.core.adapters;

import com.panjohnny.pjgl.api.camera.OrthographicCamera2D;
import com.panjohnny.pjgl.api.object.GameObject;

import java.util.List;

/**
 * Adapter for rendering list of game objects.
 *
 * @author PanJohnny
 */
@SuppressWarnings("unused")
public interface RendererAdapter {
    void render(List<GameObject> objects);

    /**
     * <b>Optional</b> camera that can be implemented by adaptations.
     * @since v0.3.7-alpha
     * @see OrthographicCamera2D
     * @implNote Other camera types not supported. Create an issue or pull request.
     * @throws UnsupportedOperationException If the camera is not implemented by the adaptation.
     */
    default OrthographicCamera2D getCamera() {
           throw new UnsupportedOperationException("This function is not implemented by this adaptation. Please see the documentation.");
    }
}
