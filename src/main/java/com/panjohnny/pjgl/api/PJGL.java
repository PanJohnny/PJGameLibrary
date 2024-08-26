package com.panjohnny.pjgl.api;

import com.panjohnny.pjgl.adapt.Adaptation;
import com.panjohnny.pjgl.api.object.GameObjectManager;
import com.panjohnny.pjgl.core.EngineOptions;
import com.panjohnny.pjgl.core.PJGLCore;
import com.panjohnny.pjgl.core.adapters.KeyboardAdapter;
import com.panjohnny.pjgl.core.adapters.MouseAdapter;
import com.panjohnny.pjgl.core.adapters.RendererAdapter;
import com.panjohnny.pjgl.core.adapters.WindowAdapter;

import java.io.IOException;
import java.util.Properties;

/**
 * The main class for the PJGL game library.
 *
 * <p>This class provides access to the core functionality of the PJGL game engine, including the game object manager,
 * window adapter, keyboard adapter, mouse adapter, and renderer adapter. It also allows starting the game engine and retrieving its version.</p>
 *
 * <p>To use the PJGL game engine, create an instance of {@link PJGL} by calling one of the {@link #init(WindowAdapter, RendererAdapter)} or
 * {@link #init(PJGLInitializer)} methods. After that, you can start the engine using the {@link #start()} method and access other components using the
 * {@link #getManager()}, {@link #getWindow()}, {@link #getKeyboard()}, {@link #getMouse()}, {@link #getRenderer()}, and {@link #getCore()} methods.</p>
 *
 * <p>The {@link #getVersion()} method returns the version of the PJGL library, which is specified in the .properties file.</p>
 *
 * @author PanJohnny
 */
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

    /**
     * Initializes the singleton instance of the PJGL class with the specified WindowAdapter and RendererAdapter.
     *
     * @param windowAdapter the window adapter
     * @param rendererAdapter the renderer adapter
     */
    public static void init(WindowAdapter windowAdapter, RendererAdapter rendererAdapter) {
        instance = new PJGL(windowAdapter, rendererAdapter);
    }

    /**
     * Initializes the singleton instance of the PJGL class with the specified PJGLInitializer.
     *
     * @param initializer the PJGL initializer
     */
    public static void init(PJGLInitializer initializer) {
        instance = new PJGL(initializer.createWindowAdapter(), initializer.createRendererAdapter());
    }

    /**
     * Gets the singleton instance of the PJGL class.
     *
     * @return the singleton instance of the PJGL class
     */
    public static PJGL getInstance() {
        return instance;
    }

    /**
     * Returns whether the PJGL instance is initialized.
     *
     * @return true if the PJGL instance is initialized, false otherwise
     */
    public static boolean isInitialized() {
        return instance != null;
    }

    /**
     * Starts the game loop.
     */
    public void start() {
        core.start();
        if (EngineOptions.logEngineState)
            LOGGER.log(System.Logger.Level.INFO, "PJGL started");
    }

    /**
     * Gets the version of the PJGL library.
     *
     * @return the version of the PJGL library
     */
    public String getVersion() {
        return properties.getProperty("pjgl.version");
    }

    /**
     * Gets the game object manager.
     *
     * @return the game object manager
     */
    public GameObjectManager getManager() {
        return core.getManager();
    }

    /**
     * Gets the window adapter.
     *
     * @return the window adapter
     */
    public <T extends WindowAdapter> T getWindow() {
        return (T) core.getWindow();
    }

    /**
     * Gets the keyboard adapter.
     *
     * @return the keyboard adapter
     */
    public <T extends KeyboardAdapter> T getKeyboard() {
        return (T) core.getWindow().getKeyboard();
    }

    /**
     * Gets the mouse adapter.
     *
     * @return the mouse adapter
     */
    public <T extends MouseAdapter> T getMouse() {
        return (T) core.getWindow().getMouse();
    }

    /**
     * Gets the renderer adapter.
     *
     * @return the renderer adapter
     */
    public <T extends RendererAdapter> T getRenderer() {
        return (T) core.getRenderer();
    }

    /**
     * Gets the PJGLCore instance. Use with mild caution.
     *
     * @return the PJGLCore instance
     */
    public PJGLCore getCore() {
        return core;
    }

    /**
     * Gets which adaptation PJGL is currently running. Uses reflections api. Should return string in the form of {@code adaptation-id@project} for example {@code java-desktop@pjgl}.
     * In case of {@link Adaptation} annotation not present returns {@code unknown@unknown}.
     *
     * @return the value of {@link Adaptation} annotation on {@link RendererAdapter}
     */
    public String getAdaptation() {
        Class<?> c = core.getRenderer().getClass();
        if (c.isAnnotationPresent(Adaptation.class)) {

            return c.getAnnotation(Adaptation.class).value();
        }

        return "unknown@unknown";
    }
}
