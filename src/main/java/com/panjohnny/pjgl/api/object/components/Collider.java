package com.panjohnny.pjgl.api.object.components;

import com.panjohnny.pjgl.api.PJGL;
import com.panjohnny.pjgl.api.object.Component;
import com.panjohnny.pjgl.api.object.GameObject;

import java.awt.*;
import java.util.ArrayList;

/**
 * Simple collision detection system. Uses {@link Rectangle} that is created from {@link Position} and {@link Size}.
 *
 * @see Rectangle
 * @see Position
 * @see Size
 *
 * @implNote Position and Size components are required to be on the object.
 * @author PanJohnny
 */
@SuppressWarnings("unused")
public class Collider extends Component {
    private final Rectangle bounds;
    private final ArrayList<GameObject> collisions = new ArrayList<>();
    public boolean checkForCollisions = true;

    public Collider(GameObject owner) {
        super(owner);
        this.bounds = new Rectangle();
    }

    @Override
    public void onLoad() {
        if (owner.getComponent(Position.class) == null || owner.getComponent(Size.class) == null) {
            PJGL.LOGGER.log(System.Logger.Level.WARNING, "Collider needs to use Position and Size in order to create bounding box, please add Position and Size components. If you have Position and Size components added, please ignore this message, or add it before this component.");
        }
    }

    @Override
    public void update(long deltaTime) {
        Position position = owner.getComponent(Position.class);
        Size size = owner.getComponent(Size.class);

        if (position != null && size != null) {
            // texture clipping
            bounds.setBounds(position.x - 1, position.y - 1, size.width + 1, size.height + 1);
        } else {
            bounds.setBounds(0, 0, 0, 0);
        }

        if (checkForCollisions) {
            checkForCollisions();
        }
    }

    public void checkForCollisions() {
        collisions.clear();

        for (GameObject object : PJGL.getInstance().getManager().getObjects()) {
            if (object != owner && collidesWith(object)) {
                collisions.add(object);
            }
        }
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public boolean collidesWith(GameObject object) {
        Collider collider = object.getComponent(Collider.class);

        return collider != null && collider.bounds.width != 0 && collider.bounds.height != 0 && collider.getBounds().intersects(bounds);
    }

    public boolean contains(int x, int y) {
        return bounds.contains(x, y);
    }

    public ArrayList<GameObject> getCollisions() {
        return collisions;
    }
}
