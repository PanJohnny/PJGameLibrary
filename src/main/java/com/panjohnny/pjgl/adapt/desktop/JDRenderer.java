package com.panjohnny.pjgl.adapt.desktop;

import com.panjohnny.pjgl.api.asset.Sprite;
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
public class JDRenderer extends Canvas implements RendererAdapter {
    @Override
    public void render(List<GameObject> objects) {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }
        Graphics2D g = (Graphics2D) bs.getDrawGraphics();

        g.clearRect(0, 0, getWidth(), getHeight());

        for (GameObject object : objects) {
            SpriteRenderer spriteRenderer = object.getComponent(SpriteRenderer.class);
            G2DRenderer g2DRenderer = object.getComponent(G2DRenderer.class);
            Position position = object.getComponent(Position.class);
            Size size = object.getComponent(Size.class);
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
}
