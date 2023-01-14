package com.panjohnny.pjgl.api.object.components;

import com.panjohnny.pjgl.api.asset.Sprite;
import com.panjohnny.pjgl.api.object.Component;
import com.panjohnny.pjgl.api.object.GameObject;

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
