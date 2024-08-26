package com.panjohnny.pjgl.adapt.lwjgl;

import com.panjohnny.pjgl.adapt.Adaptation;
import com.panjohnny.pjgl.api.util.Pair;
import com.panjohnny.pjgl.core.PJGLCore;
import com.panjohnny.pjgl.core.adapters.KeyboardAdapter;
import com.panjohnny.pjgl.core.adapters.MouseAdapter;
import com.panjohnny.pjgl.core.adapters.WindowAdapter;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static com.panjohnny.pjgl.adapt.lwjgl.LWJGLConstants.WINDOW;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * Implementation for LWJGL GLFW window.
 *
 * @author PanJohnny
 */
@Adaptation("lwjgl@pjgl")
public class GLFWWindow implements WindowAdapter {

    private final String title;
    private final int width, height;

    private final GLFWKeyboard keyboard;
    private final GLFWMouse mouse;

    public GLFWWindow(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;

        keyboard = new GLFWKeyboard();
        mouse = new GLFWMouse();
    }

    @Override
    public void setVisible(boolean visible) {
        if (visible) {
            // Make the window visible
            glfwShowWindow(WINDOW.get());
        } else {
            glfwHideWindow(WINDOW.get());
        }
    }

    @Override
    public boolean shouldClose() {
        return glfwWindowShouldClose(WINDOW.get());
    }

    @Override
    public void init(PJGLCore core) {
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if ( !glfwInit() )
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

        // Create the window
        WINDOW.set(glfwCreateWindow(width, height, title, NULL, NULL));
        if ( WINDOW.get() == NULL )
            throw new RuntimeException("Failed to create the GLFW window");

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        try ( GLFWKeyCallback keyCallback = GLFWKeyCallback.create(keyboard) ) {
            glfwSetKeyCallback(WINDOW.get(), keyCallback);
        }

        // Get the thread stack and push a new frame
        try ( MemoryStack stack = stackPush() ) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(WINDOW.get(), pWidth, pHeight);

            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            if (vidmode == null)
                throw new RuntimeException("Can't get video mode for primary monitor.");

            // Center the window
            glfwSetWindowPos(
                    WINDOW.get(),
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        } // the stack frame is popped automatically

        // Make the OpenGL context current
        glfwMakeContextCurrent(WINDOW.get());
        // Enable v-sync
        glfwSwapInterval(core.fpsSetting);

        GL.createCapabilities();
    }

    /**
     * Gets the window size using {@link org.lwjgl.glfw.GLFW#glfwGetWindowSize(long, IntBuffer, IntBuffer)}
     * @return pair of integers - width and height
     */
    public Pair<Integer, Integer> getSize() {
        try ( MemoryStack stack = stackPush() ) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(WINDOW.get(), pWidth, pHeight);

            return new Pair<>(pWidth.get(), pHeight.get());
        }
    }

    public void close() {
        glfwSetWindowShouldClose(WINDOW.get(), true);
    }

    @Override
    public MouseAdapter getMouse() {
        return mouse;
    }

    @Override
    public KeyboardAdapter getKeyboard() {
        return keyboard;
    }
}
