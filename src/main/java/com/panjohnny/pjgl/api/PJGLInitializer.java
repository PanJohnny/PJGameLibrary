package com.panjohnny.pjgl.api;

import com.panjohnny.pjgl.core.adapters.RendererAdapter;
import com.panjohnny.pjgl.core.adapters.WindowAdapter;

public interface PJGLInitializer {
    WindowAdapter createWindowAdapter();

    RendererAdapter createRendererAdapter();
}
