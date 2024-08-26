package com.panjohnny.pjgl.adapt.lwjgl;

import com.panjohnny.pjgl.adapt.Adaptation;
import com.panjohnny.pjgl.core.adapters.MouseAdapter;
import org.lwjgl.BufferUtils;

import java.nio.DoubleBuffer;

import static com.panjohnny.pjgl.adapt.lwjgl.LWJGLConstants.WINDOW;
import static org.lwjgl.glfw.GLFW.*;

/**
 * Mouse adapter aiming to provide basic mouse control.
 *
 * @author PanJohnny
 */
@Adaptation("lwjgl@pjgl")
public class GLFWMouse implements MouseAdapter {
    @Override
    public boolean isButtonDown(int keyCode) {
        return glfwGetMouseButton(WINDOW.get(), keyCode) == GLFW_PRESS;
    }

    @Override
    public double getX() {
        DoubleBuffer posX = BufferUtils.createDoubleBuffer(1);
        glfwGetCursorPos(WINDOW.get(), posX, null);
        return posX.get(0);
    }

    @Override
    public double getY() {
        DoubleBuffer posY = BufferUtils.createDoubleBuffer(1);
        glfwGetCursorPos(WINDOW.get(), null, posY);
        return posY.get(0);
    }

    @Override
    public boolean isOnWindow() {
        return glfwGetWindowAttrib(WINDOW.get(), GLFW_FOCUSED) == GLFW_TRUE;
    }
}
