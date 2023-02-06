package com.panjohnny.pjgl.core.adapters;

import com.panjohnny.pjgl.core.PJGLCore;

/**
 * Adapter with some methods important for the library.
 *
 * @implSpec Implement all methods please.
 * @author PanJohnny
 */
public interface WindowAdapter {
    void setVisible(boolean visible);

    boolean shouldClose();

    default void init(PJGLCore core) {

    }

    MouseAdapter getMouse();

    KeyboardAdapter getKeyboard();
}
