package com.panjohnny.pjgl.api.event;

/**
 * Functional interface used to listen for events.
 *
 * @see PJGLEvent
 * @param <T> Type of object consumed during event.
 *
 * @author PanJohnny
 */
@FunctionalInterface
public interface PJGLHook<T> {
    void consume(T t);


    /**
     * Functional interface used to listen for events.
     *
     * @see PJGLEvent.Empty
     *
     * @author PanJohnny
     */
    @FunctionalInterface
    interface EmptyHook {
        void consume();
    }
}
