package com.panjohnny.pjgl.api.event;


import java.util.ArrayList;
import java.util.List;

/**
 * An event that can be fired repeatedly with set type parameter indicating passed argument.
 * @param <T> Parameter that is passed when calling this event.
 *
 * @author PanJohnny
 *
 * @see Empty
 * @see PJGLOneUseEvent
 * @see PJGLOneUseEvent.Empty
 */
@SuppressWarnings("unused")
public class PJGLEvent<T> {
    private List<PJGLHook<T>> listeners;
    private boolean dropped;

    public PJGLEvent() {
        listeners = new ArrayList<>();
    }

    public void listen(PJGLHook<T> hook) {
        if (dropped)
            throw new IllegalStateException("PJGLEvent already dropped");
        listeners.add(hook);
    }

    public void call(T t) {
        if (dropped)
            throw new IllegalStateException("PJGLEvent already dropped");
        for (PJGLHook<T> listener : listeners) {
            listener.consume(t);
        }
    }

    public void drop() {
        if (dropped)
            throw new IllegalStateException("PJGLEvent already dropped");
        dropped = true;
        listeners = null;
    }

    public int getListenerCount() {
        return listeners.size();
    }

    /**
     * An event that can be fired repeatedly with set type to <code>Void</code>, meaning that no argument needs to be passed when calling it and none provided when listening to it.
     *
     * @see PJGLEvent
     * @see PJGLOneUseEvent
     * @see PJGLOneUseEvent.Empty
     *
     * @author PanJohnny
     */
    public static class Empty extends PJGLEvent<Void> {
        private List<PJGLHook.EmptyHook> listeners;
        private boolean dropped;

        public Empty() {
            listeners = new ArrayList<>();
        }

        public void listen(PJGLHook.EmptyHook hook) {
            if (dropped)
                throw new IllegalStateException("PJGLEvent already dropped");
            listeners.add(hook);
        }

        public void call() {
            listeners.forEach(PJGLHook.EmptyHook::consume);
            super.call(null);
        }

        public void drop() {
            listeners = null;
            super.drop();
        }
    }
}
