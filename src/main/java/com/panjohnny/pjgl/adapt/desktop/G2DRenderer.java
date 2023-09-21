package com.panjohnny.pjgl.adapt.desktop;

import com.panjohnny.pjgl.adapt.Adaptation;
import com.panjohnny.pjgl.api.object.Component;
import com.panjohnny.pjgl.api.object.GameObject;

import java.awt.*;
import java.util.function.Consumer;

/**
 * Component for implementing your own graphics rendering.
 *
 * @author PanJohnny
 */
@SuppressWarnings("unused")
@Adaptation("java-desktop@pjgl")
public class G2DRenderer extends Component {
    private Consumer<Graphics2D> render;

    public G2DRenderer(GameObject owner, Consumer<Graphics2D> render) {
        super(owner);
        this.render = render;
    }

    @Override
    public void onLoad() {

    }

    @Override
    public void update(long deltaTime) {

    }

    public void render(Graphics2D g) {
        if (render != null)
            render.accept(g);
    }

    public void setRender(Consumer<Graphics2D> render) {
        this.render = render;
    }
}
