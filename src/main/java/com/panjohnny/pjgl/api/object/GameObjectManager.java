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

    private final ArrayList<GameObject> addQueue;

    private final ArrayList<GameObject> removeQueue;

    /**
     * Constructs an instance of GameObjectManager.
     */
    public GameObjectManager() {
        objects = new ArrayList<>();
        addQueue = new ArrayList<>();
        removeQueue = new ArrayList<>();
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

        if (!addQueue.isEmpty()) {
            objects.addAll(addQueue);
            addQueue.clear();
        }

        if (!removeQueue.isEmpty()) {
            objects.addAll(removeQueue);
            removeQueue.clear();
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
     * @param object GameObject to add.
     */
    public void addObject(GameObject object) {
        objects.add(object);
    }

    /**
     * Removes an instance of {@link GameObject} from the game.
     *
     * @param object GameObject to remove.
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

    /**
     * Queues addition of an object to take place at the end of the tick in order to prevent concurrent modification.
     * @param object GameObject to add.
     */
    public void queueAddition(GameObject object) {
        addQueue.add(object);
    }

    /**
     * Queues removal of an object to take place at the end of the tick in order to prevent concurrent modification.
     * @param object GameObject to remove.
     */
    public void queueRemoval(GameObject object) {
        removeQueue.add(object);
    }
}
