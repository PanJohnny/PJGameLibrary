package com.panjohnny.pjgl.adapt.lwjgl;

import com.panjohnny.pjgl.core.adapters.KeyboardAdapter;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallbackI;

public class GLFWKeyboard implements KeyboardAdapter, GLFWKeyCallbackI {
    private final boolean[] keys;

    public GLFWKeyboard() {
        this.keys = new boolean[GLFW.GLFW_KEY_LAST];
    }
    @Override
    public boolean isKeyDown(int keyCode) {
        return keys[keyCode];
    }

    /**
     * Unsupported with GLFWKeyboard. Please use {@link #isKeyDown(int)}
     */
    @Override
    @Deprecated
    public boolean isKeyDown(char character) {
        return false;
    }

    /**
     * Unsupported with GLFWKeyboard. Please use {@link #isKeyUp(int)}
     */
    @Override
    @Deprecated
    public boolean isKeyUp(char character) {
        return false;
    }

    @Override
    public void invoke(long window, int key, int scancode, int action, int mods) {
        if (key >= 0 && key < keys.length) {
            keys[key] = action != GLFW.GLFW_RELEASE;
        }
    }
}
