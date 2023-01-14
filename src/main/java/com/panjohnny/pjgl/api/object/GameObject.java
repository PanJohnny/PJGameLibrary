package com.panjohnny.pjgl.api.object;

import java.util.LinkedList;

public abstract class GameObject {

    protected final LinkedList<Component> components;

    public GameObject() {
        components = new LinkedList<>();
    }

    public void update(long deltaTime) {
        components.forEach(module -> {
            if (module.enabled)
                module.update(deltaTime);
        });
    }

    public <T extends Component> T getComponent(Class<T> type) {
        for (Component component : components) {
            if (component.getClass().isAssignableFrom(type) && component.enabled) {
                return type.cast(component);
            }
        }
        return null;
    }

    protected <T extends Component> T addComponent(T t) {
        components.add(t);
        return t;
    }
}
