package com.panjohnny.pjgl.api;

import com.panjohnny.pjgl.api.event.OperationInterceptor;
import com.panjohnny.pjgl.core.rendering.GLFWWindow;

public abstract class GameApplication {

    protected void registerAll() {
        PJGLEvents.WINDOW_INIT_EVENT.listen(this::modifyWindow);
        PJGLEvents.PJGL_INIT_EVENT.listen(this::init);
        PJGLEvents.GL_LOAD_EVENT.listen(this::load);
        PJGLEvents.PJGL_EXIT_EVENT.listen(this::close);
    }

    /**
     * Method where everything should be initialized and your data loaded. Called before {@link #modifyWindow(GLFWWindow)}
     * @apiNote Beware that you should not do any GL operations here. That includes loading models and shaders!
     */
    public abstract void init();

    /**
     * Method called to modify the window from its default state. Should be used to introduce {@link com.panjohnny.pjgl.api.io} listeners.
     * @param window GLFWWindow to modify
     * @see GLFWWindow
     */
    public abstract void modifyWindow(GLFWWindow window);

    /**
     * Place where you should register objects and modify rendering (OpenGL) aspects if you want to. Called after {@link #modifyWindow(GLFWWindow)}
     */
    public abstract void load();

    /**
     * Place where you should save your data, called when {@link GLFWWindow#shouldClose()}
     * @param closeOperationInterceptor interceptor that can stop the program from shutting down, use with caution
     */
    public abstract void close(OperationInterceptor closeOperationInterceptor);
}
