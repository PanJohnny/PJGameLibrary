package com.panjohnny.pjgl.api;

import com.panjohnny.pjgl.core.rendering.*;
import com.panjohnny.pjgl.api.state.PostInit;
import com.panjohnny.pjgl.api.state.PreInit;
import com.panjohnny.pjgl.api.state.StateCheck;
import com.panjohnny.pjgl.core.PJGLCore;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains API methods that should make your life easier
 * @see #run()
 */
@SuppressWarnings({"unused"})
public final class PJGL {

    public static final double VERSION = 0.1D;
    private static PJGLCore core;

    public static final List<GameApplication> APPS = new ArrayList<>();

    private static Thread pjglThread;
    private static RendererInfo rendererInfo = RendererInfo.defaultRendererInfo();
    private static CameraFactory cameraFactory = OrthographicCamera::new;

    /**
     * Registers app to {@link #APPS}
     * @param application app to register
     */
    @PreInit
    public static void registerApplication(GameApplication application) {
        StateCheck.pre();
        APPS.add(application);
    }

    /**
     * Modifies renderer info used to run the core
     * @param info renderer info
     */
    @PreInit
    public static void setRenderInfo(RendererInfo info) {
        StateCheck.pre();
        PJGL.rendererInfo = info;
    }

    /**
     * Modifies camera used to run the core
     * @param camera camera
     */
    @PreInit
    public static void useCamera(Camera camera) {
        cameraFactory = (x,y) -> camera;
    }

    /**
     * Modifies camera used to run the core
     * @param camera camera factory
     */
    @PreInit
    public static void useCamera(CameraFactory camera) {
        cameraFactory = camera;
    }

    /**
     * Runs the app and initializes it. This is the last method that should be called as it is pre-init. This method runs core async!
     */
    @PreInit
    public synchronized static void run() {
        StateCheck.pre();
        core = new PJGLCore(rendererInfo, cameraFactory);
        APPS.forEach(GameApplication::registerAll);
        pjglThread = new Thread(core, "PJGL-main");
        pjglThread.start();
    }

    public static boolean isInitialized() {
        return core != null && core.initialized;
    }


    /**
     * @return {@link GLFWWindow} window
     */
    @PostInit
    public static GLFWWindow getWindow() {
        StateCheck.post();
        return core.getGlfwWindow();
    }

    /**
     * @return thread that the core is running in
     */
    @PostInit
    public static Thread getMainThread() {
        StateCheck.post();
        return pjglThread;
    }

    /**
     * Sets max fps and updates the renderer
     * @param fps frames per second
     */
    @PostInit
    public static void setMaxFPS(int fps) {
        StateCheck.post();
        core.fpsSetting = fps;
        core.recalculateFPS();
        getWindow().setFrameRate(fps);
    }

    /**
     * @return current frame rate set
     */
    @PostInit
    public static int getMaxFPS() {
        StateCheck.post();
        return core.fpsSetting;
    }

    /**
     * @return FPS in the last second
     */
    @PostInit
    public static int getLastFPS() {
        StateCheck.post();
        return core.lastFPS;
    }

    /**
     * @return camera that is used to render
     */
    @PostInit
    public static Camera getCamera() {
        StateCheck.post();
        return core.getCamera();
    }
}
