package com.panjohnny.pjgl.adapt.desktop;

import com.panjohnny.pjgl.adapt.Adaptation;
import com.panjohnny.pjgl.api.asset.Sprite;
import com.panjohnny.pjgl.api.camera.OrthographicCamera2D;
import com.panjohnny.pjgl.api.object.GameObject;
import com.panjohnny.pjgl.api.object.components.Position;
import com.panjohnny.pjgl.api.object.components.Size;
import com.panjohnny.pjgl.api.object.components.SpriteRenderer;
import com.panjohnny.pjgl.core.adapters.RendererAdapter;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.List;

/**
 * Renderer implementing support for {@link SpriteRenderer} and {@link G2DRenderer}.
 *
 * @author PanJohnny
 */
@Adaptation("java-desktop@pjgl")
public class JDRenderer extends Canvas implements RendererAdapter {
    private final OrthographicCamera2D camera;

    /**
     * Does not render objects that are out of the camera bounds. The data to determine that is pulled from objects size and position.
     */
    public static boolean performanceMode = true;

    /**
     * Whenever to sync camera size with screen size.
     *
     * @see #syncCameraSize()
     */
    public static boolean syncCameraSizeWithScreenSize = true;

    public JDRenderer() {
        this.camera = new OrthographicCamera();
    }

    @Override
    public void render(List<GameObject> objects) {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }
        Graphics2D g = (Graphics2D) bs.getDrawGraphics();

        g.clearRect(0, 0, getWidth(), getHeight());

        if (syncCameraSizeWithScreenSize)
            syncCameraSize();

        camera.apply(g);

        for (GameObject object : objects) {
            SpriteRenderer spriteRenderer = object.getComponent(SpriteRenderer.class);
            G2DRenderer g2DRenderer = object.getComponent(G2DRenderer.class);
            Position position = object.getComponent(Position.class);
            Size size = object.getComponent(Size.class);

            if (performanceMode && position != null) {
                double screenX = position.x - camera.getTransformX();
                double screenY = position.y - camera.getTransformY();

                if (screenX < 0) {
                    if (size != null) {
                        if (screenX + size.width < 0)
                            continue;
                    } else if (screenX + 1000 < 0) {
                        // Discrete 1000 zone, create issue or pull request :)
                        continue;
                    }
                } else if (screenY < 0) {
                    if (size != null) {
                        if (screenY + size.height < 0)
                            continue;
                    } else if (screenY + 1000 < 0) {
                        // Discrete 1000 zone, create issue or pull request :)
                        continue;
                    }
                } else if (screenX > getWidth()) {
                    // I can see the issue here. PR or issue again.
                    continue;
                } else if (screenY > getHeight()) {
                    continue;
                }
            }

            if (spriteRenderer != null && position != null) {
                Sprite<?> sprite = spriteRenderer.getSprite();
                if (sprite.of(Image.class)) {
                    Image image = (Image) sprite.getImage();

                    if (size == null || !spriteRenderer.resizeWithSize) {
                        g.drawImage(image, position.x, position.y, null);
                    } else {
                        g.drawImage(image, position.x, position.y, size.width, size.height, null);
                    }
                }
            }

            if (g2DRenderer != null) {
                if (position != null) {
                    int x = position.x;
                    int y = position.y;

                    g.translate(x, y);
                    g2DRenderer.render(g);

                    // return to origin
                    g.translate(-x, -y);
                } else {
                    g2DRenderer.render(g);
                }
            }

        }

        g.dispose();
        bs.show();
    }

    @Override
    public OrthographicCamera2D getCamera() {
        return camera;
    }

    /**
     * Updates camera width and height to the camera size.
     */
    public void syncCameraSize() {
        camera.setWidth(getWidth());
        camera.setHeight(getHeight());
    }

    static class OrthographicCamera extends OrthographicCamera2D {
        @Override
        public <T> void apply(T handle) {
            if (handle instanceof Graphics2D graphics) {
                graphics.translate(-transformX, -transformY);
                graphics.scale(scaleX, scaleY);
            }
        }
    }
}
