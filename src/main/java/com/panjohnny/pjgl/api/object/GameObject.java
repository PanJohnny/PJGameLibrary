package com.panjohnny.pjgl.api.object;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * <p>
 * The abstract GameObject class is a basic implementation of an object in a game.
 * It consists of a linked list of {@link Component} objects that make up the object's behavior.
 * </p>
 * <p>
 * The update method calls the update method of all components within the object.
 * The getComponent method can be used to retrieve a specific component based on its type.
 * The addComponent method adds a component to the object's linked list of components.
 * </p>
 * @author PanJohnny
 */
public abstract class GameObject implements Serializable {

    /**
     * A linked list of {@link Component} objects making up the behavior of the game object.
     */
    public final LinkedList<Component> components;

    /**
     * Constructs a new GameObject instance.
     */
    public GameObject() {
        components = new LinkedList<>();
    }

    /**
     * Calls the update method on all enabled components within the game object.
     *
     * @param deltaTime the time passed since the last update
     */
    public void update(long deltaTime) {
        components.forEach(module -> {
            if (module.enabled)
                module.update(deltaTime);
        });
    }

    /**
     * Retrieves a specific component based on its type.
     *
     * @param type the class of the component to retrieve
     * @param <T>  the type of the component
     * @return the first enabled component of the specified type, or null if none was found
     */
    public <T extends Component> T getComponent(Class<T> type) {
        for (Component component : components) {
            if (component.getClass().isAssignableFrom(type) && component.enabled) {
                return type.cast(component);
            }
        }
        return null;
    }

    /**
     * Adds a component to the linked list of components.
     *
     * @param t the component to add
     * @param <T> the type of the component
     * @return the added component
     */
    protected <T extends Component> T addComponent(T t) {
        components.add(t);
        return t;
    }
}
