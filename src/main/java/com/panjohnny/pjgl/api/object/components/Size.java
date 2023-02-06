package com.panjohnny.pjgl.api.object.components;

import com.panjohnny.pjgl.api.object.Component;
import com.panjohnny.pjgl.api.object.GameObject;

/**
 * Made of width and height integers.
 *
 * @author PanJohnny
 */
public class Size extends Component {
    public int width, height;

    public Size(GameObject owner) {
        super(owner);
    }

    public Size(GameObject owner, int width, int height) {
        super(owner);
        this.width = width;
        this.height = height;
    }

    @Override
    public void onLoad() {

    }

    @Override
    public void update(long deltaTime) {

    }
}
