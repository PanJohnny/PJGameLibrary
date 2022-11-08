package com.panjohnny.pjgl.core.rendering;

import com.panjohnny.pjgl.api.io.WindowMouseListener;
import com.panjohnny.pjgl.api.io.WindowKeyListener;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.system.MemoryStack;

import java.awt.*;
import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.*;

/**
 * This class is wrapper for the windowPointer it holds and also builder for glfw window.
 */
@SuppressWarnings("unused")
public class GLFWWindow {
    private long windowPointer;
    private int height, width;
    private String title;
    private boolean fullscreen;

    private boolean packed;
    public GLFWWindow(int width, int height, String title) {
        this.width = width;
        this.height = height;
        this.title = title;
    }

    /**
     * Creates the window
     */
    public long pack() {
        this.windowPointer = glfwCreateWindow(width, height, title, fullscreen ? glfwGetPrimaryMonitor() : NULL, NULL);
        if(windowPointer == NULL)
            throw new RuntimeException("Failed to create the GLFW window");
        packed = true;
        return windowPointer;
    }

    /**
     * @apiNote this should be used before {@link #pack()}
     */
    public void fullscreen() {
        if (isUnpacked())
            fullscreen = !fullscreen;
        else
            throw new IllegalStateException("Failed to set fullscreen, fullscreen must be called before #pack()");
    }

    /**
     * @apiNote this should be used before {@link #pack()}
     * @return fullscreen ? true : false
     */
    public boolean isFullscreen() {
        if (!packed)
            return fullscreen;
        return glfwGetWindowMonitor(windowPointer) != NULL;
    }

    /**
     * Resizes window
     * @param width new width
     * @param height new height
     */
    public void resize(int width, int height) {
        if(packed)
            glfwSetWindowSize(this.windowPointer, width, height);
        this.width = width;
        this.height = height;
    }

    /**
     * @deprecated this method is deprecated use {@link #getSize()} or for locally saved {@link #getWidthLocal()}
     * @return width of window
     */
    public int getWidth() {
        if(packed)
            updateLocal();
        return width;
    }

    public Dimension getSize() {
        if(packed)
            updateLocal();
        return new Dimension(width, height);
    }

    /**
     * @deprecated this method is deprecated use {@link #getSize()} or for locally saved {@link #getHeightLocal()}
     * @return height of window
     */
    public int getHeight() {
        if(packed)
            updateLocal();
        return height;
    }

    /**
     * @apiNote this should be used before {@link #pack()}
     * @return last saved local height
     */
    public int getHeightLocal() {
        return height;
    }

    /**
     * @apiNote this should be used before {@link #pack()}
     * @return last saved local width
     */
    public int getWidthLocal() {
        return width;
    }

    /**
     * Updates local values
     * @apiNote window needs to be packed in order to do this
     * @see #pack()
     */
    public void updateLocal() {
        WindowChecks.packed(this);
        try ( MemoryStack stack = stackPush() ) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(windowPointer, pWidth, pHeight);

            this.height = pHeight.get(0);
            this.width = pWidth.get(0);
        }
    }

    /**
     * Sets the window resizable
     * @param resizable if the window is resizable
     * @apiNote window can't be packed
     */
    public void setResizable(int resizable) {
        if(packed) {
            throw new IllegalStateException("Window is already packed");
        } else {
            glfwWindowHint(GLFW_RESIZABLE, resizable);
        }
    }

    /**
     * Sets the window visible
     * @param visible if the window is visible
     */
    public void setVisible(int visible) {
        if(packed) {
            if(visible == GLFW_TRUE) {
                glfwShowWindow(this.windowPointer);
            } else {
                glfwHideWindow(this.windowPointer);
            }
        } else {
            glfwWindowHint(GLFW_VISIBLE, visible);
        }
    }

    /**
     * @apiNote this should be called before using {@link #pack()}, although method to change title stores the new title locally
     * @return saved local title
     */
    public String getTitleLocal() {
        return title;
    }

    /**
     * Sets new title
     * @param title new title
     */
    public void setTitle(String title) {
        if(packed)
            glfwSetWindowTitle(this.windowPointer, title);
        this.title = title;
    }

    /**
     * @throws IllegalStateException if window is not packed
     */
    public void show() {
        WindowChecks.packed(this);
        glfwShowWindow(this.windowPointer);
    }

    /**
     * @throws IllegalStateException if window is not packed
     */
    public void hide() {
        WindowChecks.packed(this);
        glfwHideWindow(this.windowPointer);
    }

    /**
     * centers window
     * @throws IllegalStateException if window is not packed
     * @throws NullPointerException if videoMode is null
     */
    public void center() {
        WindowChecks.packed(this);
        // Get the thread stack and push a new frame
        try ( MemoryStack stack = stackPush() ) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(this.windowPointer, pWidth, pHeight);

            // Get the resolution of the primary monitor
            GLFWVidMode videoMode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            if (videoMode == null)
                throw new NullPointerException("Video mode is null");

            // Center the window
            glfwSetWindowPos(
                    windowPointer,
                    (videoMode.width() - pWidth.get(0)) / 2,
                    (videoMode.height() - pHeight.get(0)) / 2
            );
        } // the stack frame is popped automatically
    }

    /**
     * @throws IllegalStateException if window is not packed
     */
    public void setWindowPos(int x, int y) {
        WindowChecks.packed(this);
        glfwSetWindowPos(this.windowPointer, x, y);
    }

    /**
     * @throws IllegalStateException if window is not packed
     */
    public void setKeyListener(WindowKeyListener listener) {
        WindowChecks.packed(this);
        glfwSetKeyCallback(this.windowPointer, listener);
    }

    public void setMouseListener(WindowMouseListener listener) {
        WindowChecks.packed(this);
        listener.registerAll(this.windowPointer);
    }

    public void vsync() {
        glfwSwapInterval(1);
    }

    public void setSwapInterval(int interval) {
        glfwSwapInterval(interval);
    }

    public void setFrameRate(int fps) {
        glfwSwapInterval(fps);
    }

    public void context() {
        glfwMakeContextCurrent(windowPointer);
    }

    /**
     * @throws IllegalStateException if window is not packed
     */
    public boolean shouldClose() {
        WindowChecks.packed(this);
        return glfwWindowShouldClose(this.windowPointer);
    }

    /**
     * @throws IllegalStateException if window is not packed
     */
    public void setShouldClose(boolean value) {
        WindowChecks.packed(this);
        glfwSetWindowShouldClose(this.windowPointer, value);
    }

    /**
     * @throws IllegalStateException if window is not packed
     */
    public void close() {
        WindowChecks.packed(this);
        glfwSetWindowShouldClose(this.windowPointer, true);
    }

    /**
     * @throws IllegalStateException if window is not packed
     */
    public void restore() {
        WindowChecks.packed(this);
        glfwRestoreWindow(this.windowPointer);
    }

    /**
     * @throws IllegalStateException if window is not packed
     */
    public void swapBuffers() {
        WindowChecks.packed(this);
        glfwSwapBuffers(windowPointer);
    }

    /**
     * @throws IllegalStateException if window is not packed
     */
    public void maximize() {
        WindowChecks.packed(this);
        glfwMaximizeWindow(this.windowPointer);
    }

    /**
     * @throws IllegalStateException if window is not packed
     */
    public void iconify() {
        WindowChecks.packed(this);
        glfwIconifyWindow(this.windowPointer);
    }

    /**
     * @throws IllegalStateException if window is not packed
     */
    public void focus() {
        WindowChecks.packed(this);
        glfwFocusWindow(this.windowPointer);
    }

    /**
     * @throws IllegalStateException if window is not packed
     */
    public void destroy() {
        WindowChecks.packed(this);
        glfwDestroyWindow(this.windowPointer);
    }

    /**
     * @throws IllegalStateException if window is not packed
     */
    public void freeCallbacks() {
        WindowChecks.packed(this);
        glfwFreeCallbacks(windowPointer);
    }

    public long getWindowPointer() {
        return windowPointer;
    }

    public boolean isUnpacked() {
        return !packed;
    }

    public static class WindowChecks {
        public static void packed(GLFWWindow window) {
            if(window.isUnpacked())
                throw new IllegalStateException("window is not packed");
        }
    }
}
