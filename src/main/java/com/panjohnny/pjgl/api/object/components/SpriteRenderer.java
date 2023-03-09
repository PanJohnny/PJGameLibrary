package com.panjohnny.pjgl.api.object.components;

import com.panjohnny.pjgl.api.PJGL;
import com.panjohnny.pjgl.api.asset.Sprite;
import com.panjohnny.pjgl.api.asset.SpriteRegistry;
import com.panjohnny.pjgl.api.object.Component;
import com.panjohnny.pjgl.api.object.GameObject;

/**
 * Used to represent sort of container for sprite used to render.
 *
 * @implSpec When creating your own renderer you need to create your own logic for this component if you want it to work. You can look into {@link com.panjohnny.pjgl.adapt.desktop.JDRenderer}.
 * @apiNote Does not render anything, just a container.
 * @author PanJohnny
 */
@SuppressWarnings("unused")
public class SpriteRenderer extends Component {
    public boolean resizeWithSize = true;
    private Sprite<?> sprite;

    /**
     * Use the new SpriteRegistry to register sprites and then constructor {@link #SpriteRenderer(GameObject, String)}
     */
    @Deprecated
    public SpriteRenderer(GameObject owner, Sprite<?> sprite) {
        super(owner);
        this.sprite = sprite;
    }

    public SpriteRenderer(GameObject owner, String spriteId) {
        super(owner);
        this.sprite = SpriteRegistry.getSprite(spriteId);
    }

    public Sprite<?> getSprite() {
        return sprite;
    }

    public SpriteRenderer setSprite(Sprite<?> sprite) {
        this.sprite = sprite;
        return this;
    }

    @Override
    public void onLoad() {

    }

    @Override
    public void update(long deltaTime) {

    }

    /**
     * Attempts to fix sprite if it has no image attached and only id. In order for this to work you need to register sprites in the {@link com.panjohnny.pjgl.api.asset.SpriteRegistry}.
     */
    public void fixSprite() {
        if (sprite != null) {
            if (sprite.getValue() == null) {
                sprite = SpriteRegistry.getSprite(sprite.getID());
                if (sprite == null) {
                    PJGL.LOGGER.log(System.Logger.Level.WARNING, "Since v0.3.7-alpha sprites should be registered using sprite registry.");
                }
            }
        }
    }
}
