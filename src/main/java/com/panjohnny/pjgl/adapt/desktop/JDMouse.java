package com.panjohnny.pjgl.adapt.desktop;

import com.panjohnny.pjgl.api.PJGL;
import com.panjohnny.pjgl.api.event.PJGLEvent;
import com.panjohnny.pjgl.api.object.GameObject;
import com.panjohnny.pjgl.api.object.components.Collider;
import com.panjohnny.pjgl.core.adapters.MouseAdapter;

import javax.swing.event.MouseInputListener;
import java.awt.event.MouseEvent;

public class JDMouse implements MouseInputListener, MouseAdapter {
    public final PJGLEvent<MouseClicksObjectData> onObjectClick = new PJGLEvent<>();
    private final boolean[] keys = new boolean[3];
    private boolean onWindow = false;
    private int x, y = 0;

    @Override
    public void mouseClicked(MouseEvent e) {
        if (onObjectClick.getListenerCount() > 0 && e.getButton() - 1 >= 0) {
            for (GameObject object : PJGL.getInstance().getManager().getObjects()) {
                Collider collider = object.getComponent(Collider.class);

                if (collider != null) {
                    if (collider.contains(x, y)) {
                        onObjectClick.call(new MouseClicksObjectData(e.getButton() - 1, object));
                    }
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int key = e.getButton() - 1;
        if (key >= 0) {
            keys[key] = true;
        }
        x = e.getX();
        y = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int key = e.getButton() - 1;
        if (key >= 0) {
            keys[key] = false;
        }
        x = e.getX();
        y = e.getY();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        onWindow = true;
        x = e.getX();
        y = e.getY();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        onWindow = false;
        x = e.getX();
        y = e.getY();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        x = e.getX();
        y = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        x = e.getX();
        y = e.getY();
    }

    @Override
    public boolean isKeyDown(int keyCode) {
        return keys[keyCode];
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public boolean isOnWindow() {
        return onWindow;
    }

    public record MouseClicksObjectData(int button, GameObject object) {

    }
}
