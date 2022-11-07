package com.panjohnny.pjgl.api.io;

import org.lwjgl.glfw.GLFWCursorEnterCallbackI;
import org.lwjgl.glfw.GLFWCursorPosCallbackI;
import org.lwjgl.glfw.GLFWMouseButtonCallbackI;

import java.util.List;

import static org.lwjgl.glfw.GLFW.*;

@SuppressWarnings("unused")
public class WindowMouseListener {

    private final GLFWCursorEnterCallbackI cursorEnter;
    private final GLFWCursorPosCallbackI cursorPos;
    private final GLFWMouseButtonCallbackI mouseButton;
    public WindowMouseListener() {
        mouseButton = (window, button, action, mods) -> {
            if(action == GLFW_PRESS) {
                onButtonPress(window, button, WindowKeyListener.KeyEventMod.fromInt(mods));
            } else if (action == GLFW_RELEASE) {
                onButtonRelease(window, button, WindowKeyListener.KeyEventMod.fromInt(mods));
            }
        };

        cursorPos = this::onMove;

        cursorEnter = (w, b) -> {
            if(b)
                onEnter(w);
            else
                onLeave(w);
        };
    }

    public void onButtonPress(long window, int button, List<WindowKeyListener.KeyEventMod> mods) {
    }

    public void onButtonRelease(long window, int button, List<WindowKeyListener.KeyEventMod> mods) {
    }

    public void onMove(long window, double x, double y) {
    }

    public void onEnter(long window) {

    }

    public void onLeave(long window) {

    }

    @SuppressWarnings("ALL")
    public void registerAll(long window) {
        glfwSetMouseButtonCallback(window, mouseButton);
        glfwSetCursorEnterCallback(window, cursorEnter);
        glfwSetCursorPosCallback(window, cursorPos);
    }

    public GLFWCursorEnterCallbackI getCursorEnter() {
        return cursorEnter;
    }

    public GLFWCursorPosCallbackI getCursorPos() {
        return cursorPos;
    }

    public GLFWMouseButtonCallbackI getMouseButton() {
        return mouseButton;
    }
}
