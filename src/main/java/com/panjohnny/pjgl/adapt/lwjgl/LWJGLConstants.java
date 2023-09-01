package com.panjohnny.pjgl.adapt.lwjgl;

@SuppressWarnings("unused")
public class LWJGLConstants {
    public static final LWJGLConstant<Long> WINDOW = new LWJGLConstant<>();

    public static class LWJGLConstant<T> {
        private T t;
        public LWJGLConstant(T t) {
            this.t = t;
        }

        public LWJGLConstant() {

        }

        public void set(T value) {
            if (t == null) {
                t = value;
            } else {
                throw new UnsupportedOperationException("You can't set value for already defined constant");
            }
        }

        public T get() {
            return t;
        }
    }
}
