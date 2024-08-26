package com.panjohnny.pjgl.api.asset.atlas;

import com.panjohnny.pjgl.api.asset.Sprite;

/**
 * Texture atlases are used to optimize rendering.
 *
 * @apiNote Only for LWJGL
 * @author PanJohnny
 */
public class TextureAtlas extends Sprite<Integer> {
    private final int width, height;
    public TextureAtlas(String id, Integer image, int width, int height) {
        super(id, image);
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @SuppressWarnings("unused")
    public AtlasRegion defineRegion(float offsetX, float offsetY, float width, float height) {
        if (!this.of(Integer.class))
            throw new UnsupportedOperationException("You can't turn this sprite into partition, it does not have the type of Integer");

        return new AtlasRegion(String.format("%s_partition_%d_%d_%d_%d", getID(), (int) offsetX, (int) offsetY, (int) width, (int) height), this, offsetX, offsetY, width, height);
    }
}
