package com.panjohnny.pjgl.api.state;

import com.panjohnny.pjgl.api.PJGL;

public final class StateCheck {
    public static void post() {
        if (!PJGL.isInitialized())
            throw new IllegalStateException("PJGL is not initialized");
    }

    public static void pre() {
        if (PJGL.isInitialized())
            throw new IllegalStateException("PJGL is initialized");
    }
}
