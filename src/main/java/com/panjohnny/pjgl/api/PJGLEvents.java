package com.panjohnny.pjgl.api;

import com.panjohnny.pjgl.api.event.OperationInterceptor;
import com.panjohnny.pjgl.api.event.PJGLEvent;
import com.panjohnny.pjgl.api.event.PJGLOneUseEvent;
import com.panjohnny.pjgl.core.PJGLCore;
import com.panjohnny.pjgl.core.rendering.GLFWWindow;

public final class PJGLEvents {
    /**
     * Called after GLFW has initialized. It is first in {@link PJGLCore}#init() method. You should load data here, although not objects.
     * @see GameApplication#init()
     */
    public static final PJGLOneUseEvent.Empty PJGL_INIT_EVENT = new PJGLOneUseEvent.Empty();

    /**
     * Called before {@link GLFWWindow#pack()}. You can modify glfw hints here.
     */
    public static final PJGLEvent<GLFWWindow> WINDOW_PREPARE_EVENT = new PJGLOneUseEvent<>();

    /**
     * Called after {@link GLFWWindow#pack()}. You can modify window properties here.
     * @see GameApplication#modifyWindow(GLFWWindow)
     */
    public static final PJGLEvent<GLFWWindow> WINDOW_INIT_EVENT = new PJGLOneUseEvent<>();

    /**
     * Called after OpenGL load. Here you can do any opengl operations.
     * @see GameApplication#load()
     */
    public static final PJGLOneUseEvent.Empty GL_LOAD_EVENT = new PJGLOneUseEvent.Empty();

    /**
     * Reached when window.shouldClose() is true. Can be intercepted which means that the window would not close and application will remain running. Should be used to save your app.
     * @see GLFWWindow#shouldClose()
     */
    public static final PJGLEvent<OperationInterceptor> PJGL_EXIT_EVENT = new PJGLEvent<>();
}
