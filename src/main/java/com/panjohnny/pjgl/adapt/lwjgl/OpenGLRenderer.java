package com.panjohnny.pjgl.adapt.lwjgl;

import com.panjohnny.pjgl.api.PJGL;
import com.panjohnny.pjgl.api.camera.OrthographicCamera2D;
import com.panjohnny.pjgl.api.object.GameObject;
import com.panjohnny.pjgl.api.object.components.Position;
import com.panjohnny.pjgl.api.object.components.Size;
import com.panjohnny.pjgl.api.object.components.SpriteRenderer;
import com.panjohnny.pjgl.api.util.Pair;
import com.panjohnny.pjgl.core.adapters.RendererAdapter;
import org.lwjgl.glfw.GLFW;

import java.util.List;

import static com.panjohnny.pjgl.adapt.lwjgl.LWJGLConstants.WINDOW;
import static org.lwjgl.opengl.GL11.*;

/**
 *
 *
 * @author PanJohnny
 */
public class OpenGLRenderer implements RendererAdapter {

    public OpenGLRenderer() {

    }

    @Override
    public void render(List<GameObject> objects) {
        // Clear screen
        glClear(GL_COLOR_BUFFER_BIT);
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_TRIANGLES);
        getCamera().apply(null);

        // Rendering code...
        for (GameObject object : objects) {
            Position position = object.getComponent(Position.class); // public .x .y
            Size size = object.getComponent(Size.class); // public .width .height
            SpriteRenderer renderer = object.getComponent(SpriteRenderer.class);

            if (position != null && size != null && renderer != null && renderer.enabled && renderer.getSprite().of(Integer.class)) {
                int texture = (int) renderer.getSprite().getImage();
                glBindTexture(GL_TEXTURE_2D, texture);

                // Set up the vertices and texture coordinates
                float x = position.x;
                float y = position.y;
                float width = size.width;
                float height = size.height;

                // Render the object using appropriate OpenGL calls
                glBegin(GL_TRIANGLES);

                glTexCoord2f(0.0f, 0.0f);
                glVertex2f(x, y);

                glTexCoord2f(1.0f, 0.0f);
                glVertex2f(x + width, y);

                glTexCoord2f(1.0f, 1.0f);
                glVertex2f(x + width, y + height);

                glTexCoord2f(1.0f, 1.0f);
                glVertex2f(x + width, y + height);

                glTexCoord2f(0.0f, 1.0f);
                glVertex2f(x, y + height);

                glTexCoord2f(0.0f, 0.0f);
                glVertex2f(x, y);

                glEnd();
            }
        }

        // Swap the buffers to display the rendered frame
        GLFW.glfwSwapBuffers(WINDOW.get());
    }

    @Override
    public OrthographicCamera2D getCamera() {
        return new OpenGLOrthographicCamera();
    }

    public static class OpenGLOrthographicCamera extends OrthographicCamera2D {

        @Override
        public <T> void apply(T handle) {
            GLFWWindow window = PJGL.getInstance().getWindow();
            Pair<Integer, Integer> size = window.getSize();
            glMatrixMode(GL_PROJECTION);
            glLoadIdentity();
            glViewport(0, 0, size.a(), size.b());
            glOrtho(0.0f, size.a(), size.b(), 0.0f, 0.0f, 1.0f);
        }
    }
}
