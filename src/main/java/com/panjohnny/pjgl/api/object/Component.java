package com.panjohnny.pjgl.api.object;

/**
 * Represents a component of a {@link GameObject}.
 *
 * @author PanJohnny
 */
public abstract class Component {
    /**
     * The owner {@link GameObject} of this component.
     */
    protected final GameObject owner;

    /**
     * Indicates if the component is enabled or not.
     */
    public boolean enabled = true;

    /**
     * Constructs a new component for the given {@link GameObject}.
     *
     * @param owner the owner GameObject of this component
     */
    public Component(GameObject owner) {
        this.owner = owner;
        onLoad();
    }

    /**
     * This method is called when the component is loaded.
     */
    public abstract void onLoad();

    /**
     * This method is called every frame to update the component.
     *
     * @param deltaTime the time elapsed since the last frame
     */
    public abstract void update(long deltaTime);
}
