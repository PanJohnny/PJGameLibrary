package com.panjohnny.pjgl.api;

import com.panjohnny.pjgl.api.object.GameObjectManager;
import com.panjohnny.pjgl.core.EngineOptions;
import com.panjohnny.pjgl.core.PJGLCore;
import com.panjohnny.pjgl.core.adapters.KeyboardAdapter;
import com.panjohnny.pjgl.core.adapters.MouseAdapter;
import com.panjohnny.pjgl.core.adapters.RendererAdapter;
import com.panjohnny.pjgl.core.adapters.WindowAdapter;

import java.io.IOException;
import java.util.Properties;

@SuppressWarnings({"unchecked", "unused"})
public class PJGL {
    public final static System.Logger LOGGER = System.getLogger("PJGL");
    private static PJGL instance;
    private final PJGLCore core;
    private final Properties properties;

    private PJGL(WindowAdapter windowAdapter, RendererAdapter rendererAdapter) {
        this.core = new PJGLCore(windowAdapter, rendererAdapter);
        if (EngineOptions.logEngineState)
            LOGGER.log(System.Logger.Level.INFO, "PJGL initialized");
        properties = new Properties();

        try {
            properties.load(PJGL.class.getResourceAsStream("/.properties"));
        } catch (IOException e) {
            LOGGER.log(System.Logger.Level.WARNING, "Failed to load .properties file");
        }
    }

    /*



     */
    public static void init(WindowAdapter windowAdapter, RendererAdapter rendererAdapter) {
        instance = new PJGL(windowAdapter, rendererAdapter);
    }

    public static void init(PJGLInitializer initializer) {
        instance = new PJGL(initializer.createWindowAdapter(), initializer.createRendererAdapter());
    }

    public static PJGL getInstance() {
        return instance;
    }

    public static boolean isInitialized() {
        return instance != null;
    }

    public void start() {
        core.start();
        if (EngineOptions.logEngineState)
            LOGGER.log(System.Logger.Level.INFO, "PJGL started");
    }

    public String getVersion() {
        return properties.getProperty("pjgl.version");
    }

    public GameObjectManager getManager() {
        return core.getManager();
    }

    public <T extends WindowAdapter> T getWindow() {
        return (T) core.getWindow();
    }

    public <T extends KeyboardAdapter> T getKeyboard() {
        return (T) core.getWindow().getKeyboard();
    }

    public <T extends MouseAdapter> T getMouse() {
        return (T) core.getWindow().getMouse();
    }

    public <T extends RendererAdapter> T getRenderer() {
        return (T) core.getRenderer();
    }

    public PJGLCore getCore() {
        return core;
    }
}
