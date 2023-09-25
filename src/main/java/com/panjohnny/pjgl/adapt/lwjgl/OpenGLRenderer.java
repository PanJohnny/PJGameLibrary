package com.panjohnny.pjgl.adapt.lwjgl;

import com.panjohnny.pjgl.adapt.Adaptation;
import com.panjohnny.pjgl.adapt.desktop.G2DRenderer;
import com.panjohnny.pjgl.api.PJGL;
import com.panjohnny.pjgl.api.PJGLEvents;
import com.panjohnny.pjgl.api.asset.Sprite;
import com.panjohnny.pjgl.api.asset.atlas.AtlasRegion;
import com.panjohnny.pjgl.api.asset.SpriteRegistry;
import com.panjohnny.pjgl.api.camera.OrthographicCamera2D;
import com.panjohnny.pjgl.api.object.GameObject;
import com.panjohnny.pjgl.api.object.components.Position;
import com.panjohnny.pjgl.api.object.components.Size;
import com.panjohnny.pjgl.api.object.components.SpriteRenderer;
import com.panjohnny.pjgl.api.util.Pair;
import com.panjohnny.pjgl.core.adapters.RendererAdapter;
import org.lwjgl.glfw.GLFW;

import java.awt.image.BufferedImage;
import java.util.List;

import static com.panjohnny.pjgl.adapt.lwjgl.LWJGLConstants.WINDOW;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11C.GL_BLEND;
import static org.lwjgl.opengl.GL11C.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11C.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11C.glBlendFunc;
import static org.lwjgl.opengl.GL11C.glEnable;

/**
 * @author PanJohnny
 */
@Adaptation("lwjgl@pjgl")
public class OpenGLRenderer implements RendererAdapter {

    private OpenGLOrthographicCamera cam;

    public OpenGLRenderer() {
        PJGLEvents.VISIBLE.listen(() -> {
            glEnable(GL_TEXTURE_2D);
            glEnable(GL_TRIANGLES);
            glEnable(GL_BLEND);
            glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
            cam = new OpenGLOrthographicCamera();
        });
    }

    @Override
    public void render(List<GameObject> objects) {
        // Clear screen
        glClear(GL_COLOR_BUFFER_BIT);
        cam.apply(null);

        // Rendering code...
        for (GameObject object : objects) {
            Position position = object.getComponent(Position.class); // public .x .y
            Size size = object.getComponent(Size.class); // public .width .height
            G2DRenderer g2d = object.getComponent(G2DRenderer.class); // Graphics2D renderer -> compatibility


            if (position != null && size != null) {
                SpriteRenderer renderer = object.getComponent(SpriteRenderer.class);
                if (renderer != null && renderer.enabled && renderer.getSprite().of(Integer.class)) {
                    int texture = (int) renderer.getSprite().getImage();
                    glBindTexture(GL_TEXTURE_2D, texture);

                    if (renderer.getSprite() instanceof AtlasRegion reg) {
                        float x = position.x;
                        float y = position.y;
                        float width = size.width;
                        float height = size.height;

                        // Define the position and size of the region you want to render within the atlas
                        float region_x = reg.getOffsetX();  // X-coordinate of the region within the atlas
                        float region_y = reg.getOffsetY();   // Y-coordinate of the region within the atlas
                        float region_width = reg.getWidth();  // Width of the region
                        float region_height = reg.getHeight(); // Height of the region

                        // Calculate the texture coordinates for the region within the atlas
                        float tex_x1 = region_x / reg.getAtlas().getWidth();
                        float tex_x2 = (region_x + region_width) / reg.getAtlas().getWidth();
                        float tex_y1 = region_y / reg.getAtlas().getHeight();
                        float tex_y2 = (region_y + region_height) / reg.getAtlas().getHeight();

                        glBegin(GL_TRIANGLES);

                        glTexCoord2f(tex_x1, tex_y1);
                        glVertex2f(x, y);

                        glTexCoord2f(tex_x2, tex_y1);
                        glVertex2f(x + width, y);

                        glTexCoord2f(tex_x2, tex_y2);
                        glVertex2f(x + width, y + height);

                        glTexCoord2f(tex_x2, tex_y2);
                        glVertex2f(x + width, y + height);

                        glTexCoord2f(tex_x1, tex_y2);
                        glVertex2f(x, y + height);

                        glTexCoord2f(tex_x1, tex_y1);
                        glVertex2f(x, y);

                        glEnd();
                    } else {

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
                } else if (g2d != null && g2d.enabled) {
                    float width = size.width;
                    float height = size.height;
                    Sprite<?> sprite = SpriteRegistry.getSprite(object.toString());
                    if (sprite == null) {
                        BufferedImage image = new BufferedImage((int) width, (int) height, BufferedImage.TYPE_INT_ARGB); // compatibility mode
                        g2d.render(image.createGraphics());
                        image.getGraphics().dispose();
                        SpriteRegistry.createTemporaryTextureSprite(object, image);
                    }
                    glBindTexture(GL_TEXTURE_2D, (int) SpriteRegistry.getSprite(object.toString()).getValue());

                    // Set up the vertices and texture coordinates
                    float x = position.x;
                    float y = position.y;

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
        }

        // Swap the buffers to display the rendered frame
        GLFW.glfwSwapBuffers(WINDOW.get());
    }

    @Override
    public OrthographicCamera2D getCamera() {
        return cam;
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
            glTranslated(transformX, transformY, 0);
            glScaled(scaleX, scaleY, 0);
        }
    }
}
