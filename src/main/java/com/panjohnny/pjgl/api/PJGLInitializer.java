package com.panjohnny.pjgl.api;

import com.panjohnny.pjgl.core.adapters.RendererAdapter;
import com.panjohnny.pjgl.core.adapters.WindowAdapter;

/**
 * The PJGLInitializer interface is used to initialize the engine.
 * This interface provides two methods that are required to be implemented to create a functional engine.
 *
 * @see com.panjohnny.pjgl.adapt.desktop.JDInitializer
 * @author PanJohnny
 */
public interface PJGLInitializer {
    /**
     * Creates a WindowAdapter instance that will be used by PJGL engine.
     *
     * @return The window adapter implementation
     */
    WindowAdapter createWindowAdapter();

    /**
     * Creates a RendererAdapter instance that will be used by PJGL engine.
     *
     * @return The renderer adapter implementation
     */
    RendererAdapter createRendererAdapter();
}
