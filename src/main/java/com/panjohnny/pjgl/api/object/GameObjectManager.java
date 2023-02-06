package com.panjohnny.pjgl.api.object;

import com.panjohnny.pjgl.core.adapters.RendererAdapter;

import java.util.ArrayList;

/**
 * The GameObjectManager class manages all the instances of {@link GameObject} in the game.
 *
 * @author PanJohnny
 */
@SuppressWarnings("unused")
public class GameObjectManager {

    private final ArrayList<GameObject> objects;

    /**
     * Constructs an instance of GameObjectManager.
     */
    public GameObjectManager() {
        objects = new ArrayList<>();
    }

    /**
     * Updates all the instances of {@link GameObject} in the game.
     *
     * @param delta the elapsed time since the last update.
     */
    public void update(long delta) {
        for (GameObject object : objects) {
            object.update(delta);
        }
    }

    /**
     * Renders all the instances of {@link GameObject} in the game.
     *
     * @param renderer the renderer used for rendering.
     */
    public void render(RendererAdapter renderer) {
        renderer.render(objects);
    }

    /**
     * Adds a new instance of {@link GameObject} to the game.
     *
     * @param object the new instance of GameObject.
     */
    public void addObject(GameObject object) {
        objects.add(object);
    }

    /**
     * Removes an instance of {@link GameObject} from the game.
     *
     * @param object the instance of GameObject to remove.
     */
    public void removeObject(GameObject object) {
        objects.remove(object);
    }

    /**
     * Returns a list of all instances of {@link GameObject} in the game.
     *
     * @return a list of all instances of GameObject.
     */
    public ArrayList<GameObject> getObjects() {
        return objects;
    }
}
