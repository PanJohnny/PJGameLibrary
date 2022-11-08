package com.panjohnny.pjgl.api;

import com.panjohnny.pjgl.api.module.PJGLModuleRegistry;
import com.panjohnny.pjgl.api.object.GameObjectManagerRegistry;

public final class PJGLRegistries {
    public static final GameObjectManagerRegistry GAME_OBJECT_MANAGER_REGISTRY = new GameObjectManagerRegistry();

    public static final PJGLModuleRegistry MODULE_REGISTRY = new PJGLModuleRegistry();
}
