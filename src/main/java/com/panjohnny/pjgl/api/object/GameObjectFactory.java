package com.panjohnny.pjgl.api.object;

@SuppressWarnings("unused")
@FunctionalInterface
public interface GameObjectFactory<T extends GameObject> {
    T create(int x, int y, int width, int height);
}
