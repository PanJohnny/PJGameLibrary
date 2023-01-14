package com.panjohnny.pjgl.core.adapters;

import com.panjohnny.pjgl.api.object.GameObject;

import java.util.List;

public interface RendererAdapter {
    void render(List<GameObject> objects);
}
