package com.panjohnny.pjgl.api.event;

@FunctionalInterface
public interface PJGLHook<T> {
    void consume(T t);

    @FunctionalInterface
    interface EmptyHook {
        void consume();
    }
}
