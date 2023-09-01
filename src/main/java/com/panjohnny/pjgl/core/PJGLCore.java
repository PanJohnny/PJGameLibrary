package com.panjohnny.pjgl.core;

import com.panjohnny.pjgl.api.PJGL;
import com.panjohnny.pjgl.api.PJGLEvents;
import com.panjohnny.pjgl.api.event.OperationInterceptor;
import com.panjohnny.pjgl.api.object.GameObjectManager;
import com.panjohnny.pjgl.core.adapters.RendererAdapter;
import com.panjohnny.pjgl.core.adapters.WindowAdapter;

import java.io.IOException;

/**
 * Core class of the entire project.
 *
 * @apiNote I left some fields public in order to access information that might come handy for some projects.
 * @author PanJohnny
 */
@SuppressWarnings("unused")
public class PJGLCore implements Runnable {
    public static final int DEFAULT_WIDTH = 1024;
    public static final int DEFAULT_HEIGHT = 760;
    private final WindowAdapter window;
    private final RendererAdapter renderer;
    private final GameObjectManager manager;
    public int fpsSetting = 60;

    public int lastFPS = 0;

    public boolean initialized;

    public Thread thread;
    private double frameSkip;

    public PJGLCore(WindowAdapter window, RendererAdapter renderer) {
        this.window = window;
        this.renderer = renderer;
        this.manager = new GameObjectManager();
        this.thread = new Thread(this, "PJGL-Core");
    }

    public void start() {
        thread.start();
    }

    public void run() {
        init();
        try {
            loop();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void init() {
        PJGLEvents.LOAD.call();

        window.init(this);

        window.setVisible(true);

        PJGLEvents.VISIBLE.call();
    }

    private void loop() throws IOException {
        initialized = true;

        recalculateFPS();

        long startTime = System.currentTimeMillis();

        int frameCounter = 0;
        long frameCounterStart = System.currentTimeMillis();
        long frameCounterEnd = frameCounterStart + 1000L;

        while (shouldRun()) {
            PJGLEvents.LOOP.call();

            if (System.currentTimeMillis() >= frameCounterEnd) {
                frameCounterStart = System.currentTimeMillis();
                frameCounterEnd = frameCounterStart + 1000L;

                lastFPS = frameCounter;

                frameCounter = 0;
                if (fpsSetting != EngineOptions.frameLimit) {
                    fpsSetting = EngineOptions.frameLimit;
                    recalculateFPS();
                }

                if (EngineOptions.logFPS)
                    PJGL.LOGGER.log(System.Logger.Level.INFO, "FPS {0}", lastFPS);
            }

            if (System.currentTimeMillis() >= startTime + frameSkip) {
                frameCounter++;
                long endTime = System.currentTimeMillis();
                long elapsedTime = endTime - startTime;
                startTime = endTime;

                manager.update(elapsedTime);
                PJGLEvents.TICK.call();
                manager.render(renderer);
            }
        }


        if (EngineOptions.logEngineState)
            PJGL.LOGGER.log(System.Logger.Level.INFO, "Closing...");
    }

    public void recalculateFPS() {
        frameSkip = (1000D / fpsSetting);
    }


    public boolean shouldRun() {
        if (!window.shouldClose())
            return true;
        OperationInterceptor interceptor = new OperationInterceptor();

        PJGLEvents.PJGL_EXIT_EVENT.call(interceptor);

        if (!interceptor.isIntercepted()) {
            PJGLEvents.PJGL_EXIT_EVENT.drop();
            PJGLEvents.EXIT.call();
            return false;
        }

        return true;
    }

    public WindowAdapter getWindow() {
        return window;
    }

    public RendererAdapter getRenderer() {
        return renderer;
    }

    public GameObjectManager getManager() {
        return manager;
    }
}
