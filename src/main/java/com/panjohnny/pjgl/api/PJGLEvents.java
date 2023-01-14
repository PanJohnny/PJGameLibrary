package com.panjohnny.pjgl.api;

import com.panjohnny.pjgl.api.event.OperationInterceptor;
import com.panjohnny.pjgl.api.event.PJGLEvent;
import com.panjohnny.pjgl.api.event.PJGLOneUseEvent;
import com.panjohnny.pjgl.core.adapters.WindowAdapter;

public final class PJGLEvents {

    /**
     * Ran before window initialization.
     */
    public static final PJGLEvent.Empty LOAD = new PJGLOneUseEvent.Empty();

    /**
     * Called when window is set to be visible (for the first time).
     */
    public static final PJGLOneUseEvent.Empty VISIBLE = new PJGLOneUseEvent.Empty();

    /**
     * Called every loop.
     *
     * @deprecated Left here for polling events only!
     */
    @SuppressWarnings("all")
    public static final PJGLEvent.Empty LOOP = new PJGLEvent.Empty();

    /**
     * Called every tick.
     */
    public static final PJGLEvent.Empty TICK = new PJGLEvent.Empty();


    /**
     * Reached when window.shouldClose() is true. Can be intercepted which means that the window would not close and application will remain running. Should be used to save your app.
     *
     * @see WindowAdapter#shouldClose()
     */
    public static final PJGLEvent<OperationInterceptor> PJGL_EXIT_EVENT = new PJGLEvent<>();
}
