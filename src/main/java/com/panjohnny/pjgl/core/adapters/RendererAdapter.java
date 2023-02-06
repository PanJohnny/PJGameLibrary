package com.panjohnny.pjgl.core.adapters;

import com.panjohnny.pjgl.api.object.GameObject;

import java.util.List;

/**
 * Adapter for rendering list of game objects.
 *
 * @author PanJohnny
 */
public interface RendererAdapter {
    void render(List<GameObject> objects);
}
