package com.panjohnny.pjgl.api.module;

import com.panjohnny.pjgl.api.PJGLEvents;
import com.panjohnny.pjgl.api.event.OperationInterceptor;
import com.panjohnny.pjgl.api.utils.Identifier;
import com.panjohnny.pjgl.core.PJGLCore;
import com.panjohnny.pjgl.core.rendering.GLFWWindow;

public abstract class PJGLModule {

    protected PJGLCore core;

    public PJGLModule(PJGLCore core) {
        this.core = core;
    }

    /**
     * Internal method for registering <code>on#</code> methods.
     */
    public void registerAll() {
        PJGLEvents.PJGL_EXIT_EVENT.listen(this::onExit);
        PJGLEvents.GL_LOAD_EVENT.listen(this::onLoad);
        PJGLEvents.PJGL_INIT_EVENT.listen(this::onInit);
    }

    /**
     * Method where everything should be initialized and your data loaded.
     * @apiNote Beware that you should not do any GL operations here. That includes loading models and shaders!
     */
    public abstract void onInit();

    /**
     * Place where you should register objects and modify rendering (OpenGL) aspects if you want to.
     */
    public abstract void onLoad();

    /**
     * Place where you should save your data, called when {@link GLFWWindow#shouldClose()}
     * @param closeOperationInterceptor interceptor that can stop the program from shutting down, use with caution
     */
    public abstract void onExit(OperationInterceptor closeOperationInterceptor);

    public abstract Identifier getIdentifier();

    @FunctionalInterface
    public interface PJGLModuleFactory {
        PJGLModule create(PJGLCore core);
    }
}
