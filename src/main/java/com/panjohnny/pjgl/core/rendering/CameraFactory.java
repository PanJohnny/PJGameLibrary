package com.panjohnny.pjgl.core.rendering;

@FunctionalInterface
public interface CameraFactory {
    Camera create(int width, int height);
}
