package com.panjohnny.pjgl.api.object.components;

import com.panjohnny.pjgl.api.asset.Sprite;
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

    public SpriteRenderer(GameObject owner, Sprite<?> sprite) {
        super(owner);
        this.sprite = sprite;
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
}
