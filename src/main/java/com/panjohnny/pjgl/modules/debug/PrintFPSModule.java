package com.panjohnny.pjgl.modules.debug;

import com.panjohnny.pjgl.api.PJGLEvents;
import com.panjohnny.pjgl.api.PJGLRegistries;
import com.panjohnny.pjgl.api.event.OperationInterceptor;
import com.panjohnny.pjgl.api.module.PJGLModule;
import com.panjohnny.pjgl.api.object.GameObject;
import com.panjohnny.pjgl.api.object.UpdateReceiver;
import com.panjohnny.pjgl.api.utils.Identifier;
import com.panjohnny.pjgl.core.PJGLCore;

public class PrintFPSModule extends PJGLModule {
    public PrintFPSModule(PJGLCore core) {
        super(core);
    }

    @Override
    public void registerAll() {
        // I just need onLoad
        PJGLEvents.GL_LOAD_EVENT.listen(this::onLoad);
    }

    @Override
    public void onInit() {

    }

    @Override
    public void onLoad() {
        PJGLRegistries.GAME_OBJECT_MANAGER_REGISTRY.get(GameObject.class).add(new UpdateReceiver() {
            long lastTime;

            @Override
            public void update(long delta) {
                if (lastTime + 1000L <= System.currentTimeMillis()) {
                    System.out.println("FPS: " + core.lastFPS);
                    lastTime = System.currentTimeMillis();
                }
            }
        });
    }

    @Override
    public void onExit(OperationInterceptor closeOperationInterceptor) {

    }

    @Override
    public Identifier getIdentifier() {
        return new Identifier("pjgl_debug", "print_fps");
    }
}
