package com.panjohnny.pjgl.adapt.lwjgl;

import com.panjohnny.pjgl.api.PJGLEvents;
import com.panjohnny.pjgl.api.PJGLInitializer;
import com.panjohnny.pjgl.core.adapters.RendererAdapter;
import com.panjohnny.pjgl.core.adapters.WindowAdapter;

import java.util.Objects;

import static com.panjohnny.pjgl.adapt.lwjgl.LWJGLConstants.WINDOW;
import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;

public class LWJGLInitializer implements PJGLInitializer {
    private final String title;
    private final int width, height;

    public LWJGLInitializer(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;

        preparePJGLEvents();
    }

    private void preparePJGLEvents() {
        PJGLEvents.EXIT.listen(this::onExit);

        // For GLFW Window
        PJGLEvents.LOOP.listen(org.lwjgl.glfw.GLFW::glfwPollEvents);
    }

    private void onExit() {
        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(WINDOW.get());
        glfwDestroyWindow(WINDOW.get());

        // Terminate GLFW and free the error callback
        glfwTerminate();
        Objects.requireNonNull(glfwSetErrorCallback(null)).free();
    }

    @Override
    public WindowAdapter createWindowAdapter() {
        return new GLFWWindow(title, width, height);
    }

    @Override
    public RendererAdapter createRendererAdapter() {
        return new OpenGLRenderer();
    }
}
