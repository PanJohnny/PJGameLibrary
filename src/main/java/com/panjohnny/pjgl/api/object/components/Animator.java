package com.panjohnny.pjgl.api.object.components;

import com.panjohnny.pjgl.api.PJGL;
import com.panjohnny.pjgl.api.asset.Animation;
import com.panjohnny.pjgl.api.object.Component;
import com.panjohnny.pjgl.api.object.GameObject;

import java.util.Objects;

/**
 * Component that handles animations. Requires SpriteRenderer to be present on the owner.
 *
 * @author PanJohnny
 */
@SuppressWarnings("unused")
public class Animator extends Component {
    private Animation animation;
    private boolean cancelFrame = false;
    public Animator(GameObject owner) {
        super(owner);
    }

    public Animator(GameObject owner, Animation animation) {
        super(owner);
        this.animation = animation;
    }

    @Override
    public void onLoad() {
        // Detect SpriteRenderer
    }

    @Override
    public void update(long l) {
        SpriteRenderer renderer = owner.getComponent(SpriteRenderer.class);

        if (renderer == null) {
            PJGL.LOGGER.log(System.Logger.Level.ERROR, "Animator requires SpriteRenderer to be present on the object. Animator disabled!");
            enabled = false;
            return;
        }

        if (cancelFrame) {
            cancelFrame = false;
            return;
        }

        if (animation != null && !animation.getFrame().sprite().getID().equals(renderer.getSprite().getID())) {
            renderer.setSprite(animation.getFrame().sprite());
        }
    }

    public void setAnimation(Animation animation) {
        if (Objects.equals(animation.getId(), this.animation.getId())) {
            return;
        }
        animation.reset();
        this.animation = animation;
    }

    public Animation getAnimation() {
        return animation;
    }

    public void cancelFrame() {
        cancelFrame = true;
    }

    public void toFirstFrame() {
        SpriteRenderer renderer = owner.getComponent(SpriteRenderer.class);

        if (renderer == null) {
            PJGL.LOGGER.log(System.Logger.Level.ERROR, "Animator requires SpriteRenderer to be present on the object. Animator disabled!");
            enabled = false;
            return;
        }

        if (animation != null) {
            animation.reset();
            renderer.setSprite(animation.getFrames().get(0).sprite());
        }
    }
}
