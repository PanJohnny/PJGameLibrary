package com.panjohnny.pjgl.api.event;

public class PJGLOneUseEvent<T> extends PJGLEvent<T> {
    @Override
    public void call(T t) {
        super.call(t);
        drop();
    }

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
