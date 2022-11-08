package com.panjohnny.pjgl.api.object;

/**
 * Subclass of GameObject that is not able to be rendered and serves the only purpose of receiving updates.
 */
@SuppressWarnings("unused")
public abstract class UpdateReceiver extends GameObject{
    public UpdateReceiver() {
        super(-1, -1, -1, -1, null, null);
    }

    @Override
    public abstract void update(long delta);

    @Override
    public boolean canBeRendered() {
        return false;
    }
}
