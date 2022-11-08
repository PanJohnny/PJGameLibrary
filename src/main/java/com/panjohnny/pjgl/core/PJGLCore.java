package com.panjohnny.pjgl.core;

import com.panjohnny.pjgl.api.PJGLEvents;
import com.panjohnny.pjgl.api.PJGLRegistries;
import com.panjohnny.pjgl.api.event.OperationInterceptor;
import com.panjohnny.pjgl.core.helpers.Shader;
import com.panjohnny.pjgl.core.rendering.*;
import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import java.awt.*;
import java.io.IOException;
import java.util.Objects;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

@SuppressWarnings("unused")
public class PJGLCore implements Runnable{
    private GLFWWindow glfwWindow;

    public static final int DEFAULT_WIDTH = 1024;
    public static final int DEFAULT_HEIGHT = 760;

    public int fpsSetting = 60;

    public int lastFPS = 0;

    public boolean initialized;
    private final RendererInfo rendererInfo;
    private final CameraFactory cameraFactory;

    public PJGLCore(RendererInfo rendererInfo, CameraFactory cameraFactory) {
        this.rendererInfo = rendererInfo;
        this.cameraFactory = cameraFactory;
    }

    public void run()  {
        init();
        try {
            loop();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Free the window callbacks and destroy the window
        glfwWindow.freeCallbacks();
        glfwWindow.destroy();

        // Terminate GLFW and free the error callback
        glfwTerminate();
        Objects.requireNonNull(glfwSetErrorCallback(null)).free();
    }

    private void init() {
        // Set up an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        PJGLEvents.PJGL_INIT_EVENT.call();

        glfwWindow = new GLFWWindow(DEFAULT_WIDTH, DEFAULT_HEIGHT, "PJGL Project");

        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindow.setVisible(GLFW_FALSE); // the window will stay hidden after creation
        glfwWindow.setResizable(GLFW_TRUE); // the window will be resizable

        PJGLEvents.WINDOW_PREPARE_EVENT.call(glfwWindow);

        // Create the window
        glfwWindow.pack();

        glfwWindow.center();
        PJGLEvents.WINDOW_INIT_EVENT.call(glfwWindow);

        // Make the OpenGL context current
        glfwWindow.context();
        // Enable v-sync
        glfwWindow.setSwapInterval(fpsSetting);

        // Make the window visible
        glfwWindow.show();
    }

    private double frameSkip;

    private Camera camera;
    private void loop() throws IOException {
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

        glEnable(GL_TEXTURE_2D);
        glDisable(GL_DEPTH_TEST);
        glDisable(GL_LIGHTING);

        Matrix4f scale = new Matrix4f().scale(16);

        camera = cameraFactory.create(glfwWindow.getWidthLocal(), glfwWindow.getHeightLocal());
        Shader shader = rendererInfo.defaultShader() != null ? rendererInfo.defaultShader() : new Shader("/shader/shader");

        initialized = true;

        PJGLRegistries.GAME_OBJECT_MANAGER_REGISTRY.close();
        PJGLEvents.GL_LOAD_EVENT.call();

        recalculateFPS();
        long startTime = System.nanoTime();
        int frameCounter = 0;
        long frameCounterStart = System.nanoTime();
        long frameCounterEnd = frameCounterStart + 1_000_000_000L;
        Dimension lastSize = null;
        while (shouldRun()) {
            glfwPollEvents();
            if (System.nanoTime() >= frameCounterEnd) {
                frameCounterStart = System.nanoTime();
                frameCounterEnd = frameCounterStart + 1_000_000_000L;

                lastFPS = frameCounter;

                frameCounter = 0;
            }
            if (System.nanoTime() >= startTime + frameSkip) {
                frameCounter++;

                glClear(GL_COLOR_BUFFER_BIT);

                PJGLRegistries.GAME_OBJECT_MANAGER_REGISTRY.forEach((index, manager) -> manager.renderObjects(scale, camera, shader, rendererInfo.renderer()));

                glfwWindow.swapBuffers();

                long endTime = System.nanoTime();
                long elapsedTime = endTime - startTime;
                startTime = endTime;

                PJGLRegistries.GAME_OBJECT_MANAGER_REGISTRY.forEach((index, manager) -> manager.updateObjects(elapsedTime));
            }
        }
    }

    public void recalculateFPS() {
        frameSkip = (1_000_000_000d  / fpsSetting);
    }

    public Camera getCamera() {
        return camera;
    }

    public GLFWWindow getGlfwWindow() {
        return glfwWindow;
    }

    public boolean shouldRun() {
        if (!glfwWindow.shouldClose())
            return true;
        OperationInterceptor interceptor = new OperationInterceptor();

        PJGLEvents.PJGL_EXIT_EVENT.call(interceptor);

        if (!interceptor.isIntercepted()) {
            PJGLEvents.PJGL_EXIT_EVENT.drop();
            return false;
        }

        glfwWindow.setShouldClose(false);
        return true;
    }
}
