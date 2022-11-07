package com.panjohnny.pjgl.api.object;

import com.panjohnny.pjgl.api.utils.AbstractRegistry;

public class GameObjectManagerRegistry extends AbstractRegistry<Class<?>, AbstractGameObjectManager<? extends GameObject>> {
    private final AbstractGameObjectManager.SimpleGameObjectManager defaultManager;

    public GameObjectManagerRegistry() {
        super();
        defaultManager = new AbstractGameObjectManager.SimpleGameObjectManager();
        register(GameObject.class, defaultManager);
    }

    @Override
    public void close() {
        super.close();
    }

    public AbstractGameObjectManager<GameObject> getDefaultManager() {
        return defaultManager;
    }
}
