package com.panjohnny.pjgl.api.io;

import org.lwjgl.glfw.GLFWKeyCallbackI;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;
public abstract class WindowKeyListener implements GLFWKeyCallbackI {
    @Override
    public void invoke(long window,int key,int scancode,int action,int mods) {
        List<KeyEventMod> modList = KeyEventMod.fromInt(mods);
        if(action == GLFW_PRESS) {
            onKeyPress(window, key, scancode, modList);
        } else if(action == GLFW_RELEASE) {
            onKeyRelease(window, key, scancode, modList);
        }
    }

    public abstract void onKeyPress(long window, int key, int scancode, List<KeyEventMod> mods);

    public abstract void onKeyRelease(long window, int key, int scancode, List<KeyEventMod> mods);

    public enum KeyEventMod {
        SHIFT(GLFW_MOD_SHIFT),
        ALT(GLFW_MOD_ALT),
        CAPS_LOCK(GLFW_MOD_CAPS_LOCK),
        CONTROL(GLFW_MOD_CONTROL),
        NUM_LOCK(GLFW_MOD_NUM_LOCK),
        SUPER(GLFW_MOD_SUPER);

        private final int mod;
        KeyEventMod(int mod) {
            this.mod = mod;
        }

        public static List<KeyEventMod> fromInt(int mods) {
            ArrayList<KeyEventMod> list = new ArrayList<>();
            if (mods == NULL)
                return list;
            for (KeyEventMod mod :
                    KeyEventMod.values()) {
                if ((mods & mod.getMod()) != 0)
                    list.add(mod);
            }
            return list;
        }

        public int getMod() {
            return mod;
        }
    }
}
