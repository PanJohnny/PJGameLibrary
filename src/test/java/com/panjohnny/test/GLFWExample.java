package com.panjohnny.test;

import com.panjohnny.pjgl.adapt.lwjgl.GLFWKeyboard;
import com.panjohnny.pjgl.adapt.lwjgl.GLFWWindow;
import com.panjohnny.pjgl.adapt.lwjgl.LWJGLInitializer;
import com.panjohnny.pjgl.api.PJGL;
import com.panjohnny.pjgl.api.PJGLEvents;
import com.panjohnny.pjgl.api.asset.SpriteRegistry;
import com.panjohnny.pjgl.api.object.GameObject;
import com.panjohnny.pjgl.api.object.components.Position;
import com.panjohnny.pjgl.api.object.components.Size;
import com.panjohnny.pjgl.api.object.components.SpriteRenderer;
import com.panjohnny.pjgl.core.EngineOptions;
import org.lwjgl.glfw.GLFW;

@SuppressWarnings("unused")
public class GLFWExample {
    public static void main(String[] args) {
        EngineOptions.logFPS = true;
        PJGL.init(new LWJGLInitializer("Apple!", 750, 750));
        PJGL pjgl = PJGL.getInstance();

        PJGLEvents.VISIBLE.listen(() -> {
            SpriteRegistry.registerTextureSprite("apple", "./assets/apple.png");

            GameObject apple = new GameObject() {
                public final Position position = addComponent(new Position(this, 10, 10));
                public final Size size = addComponent(new Size(this, 100, 100));
                public final SpriteRenderer renderer = addComponent(new SpriteRenderer(this, "apple"));
            };

            pjgl.getManager().queueAddition(apple);
        });

        GLFWKeyboard keyboard = pjgl.getKeyboard();
        GLFWWindow window = pjgl.getWindow();

        PJGLEvents.TICK.listen(() -> {
            if (keyboard.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) {
                window.close();
            }

            if (keyboard.isKeyDown(GLFW.GLFW_KEY_LEFT)) {
                pjgl.getRenderer().getCamera().move(-1,0);
            }

            if (keyboard.isKeyDown(GLFW.GLFW_KEY_RIGHT)) {
                pjgl.getRenderer().getCamera().move(1,0);
            }

            if (keyboard.isKeyDown(GLFW.GLFW_KEY_UP)) {
                pjgl.getRenderer().getCamera().move(0,-1);
            }

            if (keyboard.isKeyDown(GLFW.GLFW_KEY_DOWN)) {
                pjgl.getRenderer().getCamera().move(0,1);
            }
        });
        pjgl.start();
    }
}
