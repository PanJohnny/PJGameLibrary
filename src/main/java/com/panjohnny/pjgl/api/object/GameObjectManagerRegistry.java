package com.panjohnny.pjgl.api.object;

import com.panjohnny.pjgl.api.utils.AbstractRegistry;

public class GameObjectManagerRegistry extends AbstractRegistry<Class<? extends GameObject>, AbstractGameObjectManager<GameObject>> {
    private final AbstractGameObjectManager.SimpleGameObjectManager defaultManager;

    public GameObjectManagerRegistry() {
        super();
        defaultManager = new AbstractGameObjectManager.SimpleGameObjectManager();
    }

    @Override
    public void close() {
        if (getRegistered().isEmpty())
            register(GameObject.class, defaultManager);
        super.close();
    }
}
