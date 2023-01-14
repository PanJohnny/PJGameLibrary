package com.panjohnny.pjgl.core.adapters;

import com.panjohnny.pjgl.core.PJGLCore;

public interface WindowAdapter {
    void setVisible(boolean visible);

    boolean shouldClose();

    default void init(PJGLCore core) {

    }

    MouseAdapter getMouse();

    KeyboardAdapter getKeyboard();
}
