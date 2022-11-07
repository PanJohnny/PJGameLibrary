package com.panjohnny.pjgl.core.helpers;

public abstract class Wrapper<T> {
    protected final T value;
    public Wrapper(T value) {
        this.value = value;
    }
}
