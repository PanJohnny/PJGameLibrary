package com.panjohnny.pjgl.api.module;

import com.panjohnny.pjgl.api.utils.AbstractRegistry;
import com.panjohnny.pjgl.api.utils.Identifier;

public class PJGLModuleRegistry extends AbstractRegistry<Identifier, PJGLModule.PJGLModuleFactory> {
    public void registerFromInstance(PJGLModule instance, PJGLModule.PJGLModuleFactory factory) {
        register(instance.getIdentifier(), factory);
    }
}
