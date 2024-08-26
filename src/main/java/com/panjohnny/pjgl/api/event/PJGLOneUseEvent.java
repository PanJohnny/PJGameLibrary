package com.panjohnny.pjgl.api.event;

/**
 * An event that can be fired only once with set type parameter indicating passed argument.
 * @param <T> Parameter that is passed when calling this event.
 *
 * @author PanJohnny
 *
 * @see Empty
 * @see PJGLEvent
 * @see PJGLEvent.Empty
 */
public class PJGLOneUseEvent<T> extends PJGLEvent<T> {
    @Override
    public void call(T t) {
        super.call(t);
        drop();
    }

    /**
     * An event that can be fired only once with set type to <code>Void</code>, meaning that no argument needs to be passed when calling it and none provided when listening to it.
     *
     * @see PJGLEvent
     * @see PJGLOneUseEvent
     * @see PJGLEvent.Empty
     *
     * @author PanJohnny
     */
    public static class Empty extends PJGLEvent.Empty {
        @Override
        public void call() {
            super.call();
            drop();
        }

        @Override
        public void call(Void t) {
            super.call(t);
            drop();
        }
    }
}
