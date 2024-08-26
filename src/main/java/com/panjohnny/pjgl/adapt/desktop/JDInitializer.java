package com.panjohnny.pjgl.adapt.desktop;

import com.panjohnny.pjgl.adapt.Adaptation;
import com.panjohnny.pjgl.api.PJGLInitializer;
import com.panjohnny.pjgl.core.PJGLCore;
import com.panjohnny.pjgl.core.adapters.RendererAdapter;
import com.panjohnny.pjgl.core.adapters.WindowAdapter;

/**
 * Initializer to quickly start your project with this adaptation.
 *
 * @author PanJohnny
 */
@Adaptation("java-desktop@pjgl")
public class JDInitializer implements PJGLInitializer {

    private final String title;
    private final int width, height;

    public JDInitializer(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
    }

    public JDInitializer(String title) {
        this(title, PJGLCore.DEFAULT_WIDTH, PJGLCore.DEFAULT_HEIGHT);
    }

    @Override
    public WindowAdapter createWindowAdapter() {
        return new JDWindow(title, width, height);
    }

    @Override
    public RendererAdapter createRendererAdapter() {
        return new JDRenderer();
    }

    @Override
    public String getIdentifier() {
        return "java-desktop@pjgl";
    }
}
