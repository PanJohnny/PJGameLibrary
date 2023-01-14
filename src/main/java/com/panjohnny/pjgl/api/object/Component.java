package com.panjohnny.pjgl.api.object;

public abstract class Component {
    protected final GameObject owner;
    public boolean enabled = true;

    public Component(GameObject owner) {
        this.owner = owner;
        onLoad();
    }

    public abstract void onLoad();

    public abstract void update(long deltaTime);
}
