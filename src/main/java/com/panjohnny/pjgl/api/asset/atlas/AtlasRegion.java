package com.panjohnny.pjgl.api.asset.atlas;

import com.panjohnny.pjgl.api.asset.Sprite;

/**
 * @author PanJohnny
 */
public class AtlasRegion extends Sprite<Integer> {
    private final float offsetX;
    private final float offsetY;
    private final float width;
    private final float height;
    private final TextureAtlas atlas;
    public AtlasRegion(String id, TextureAtlas atlas, float offsetX, float offsetY, float width, float height) {
        super(id, atlas.getImage());

        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.width = width;
        this.height = height;

        this.atlas = atlas;
    }

    public float getOffsetX() {
        return offsetX;
    }

    public float getOffsetY() {
        return offsetY;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public TextureAtlas getAtlas() {
        return atlas;
    }
}
