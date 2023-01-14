package com.panjohnny.pjgl.api.object;

import com.panjohnny.pjgl.core.adapters.RendererAdapter;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class GameObjectManager {

    private final ArrayList<GameObject> objects;

    public GameObjectManager() {
        objects = new ArrayList<>();
    }

    public void update(long delta) {
        for (GameObject object : objects) {
            object.update(delta);
        }
    }

    public void render(RendererAdapter renderer) {
        renderer.render(objects);
    }

    public void addObject(GameObject object) {
        objects.add(object);
    }

    public void removeObject(GameObject object) {
        objects.remove(object);
    }

    public ArrayList<GameObject> getObjects() {
        return objects;
    }
}
