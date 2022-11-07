package com.panjohnny.pjgl.api.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

@SuppressWarnings("unused")
public abstract class AbstractRegistry<K, V> {
    private Map<K, V> registered;
    private boolean accepting = true;
    public AbstractRegistry() {
        this.registered = new HashMap<>();
    }

    /**
     * Registers value to the key
     * @param key key
     * @param value value
     * @throws IllegalStateException if the registry is closed {{@link #isOpen()}}
     */
    public void register(K key, V value) {
        if (!accepting)
            throw new IllegalStateException("The registry is already closed");
        registered.put(key, value);
    }

    public void close() {
        accepting = false;
        registered = Collections.unmodifiableMap(registered);
    }

    public boolean isOpen() {
        return accepting;
    }

    public V get(K key) {
        if (accepting)
            throw new IllegalStateException("The registry is yet open");
        return registered.get(key);
    }

    protected Map<K, V> getRegistered() {
        return registered;
    }

    /**
     * Runs foreach on the registry
     *
     * @param action consumer that would accept all the values
     * @throws IllegalStateException if the registry is closed {{@link #isOpen()}}
     */
    public void forEach(BiConsumer<K, V> action) {
        if (accepting)
            throw new IllegalStateException("Registry is not yet closed");
        getRegistered().forEach(action);
    }
}
