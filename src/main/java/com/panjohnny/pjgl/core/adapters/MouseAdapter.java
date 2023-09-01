package com.panjohnny.pjgl.core.adapters;

/**
 * Adapter for getting the mouse input.
 *
 * @author PanJohnny
 */
@SuppressWarnings("unused")
public interface MouseAdapter {
    int BUTTON_LEFT = 0;
    int BUTTON_MIDDLE = 1;
    int BUTTON_RIGHT = 2;

    boolean isKeyDown(int keyCode);

    default boolean isKeyUp(int keyCode) {
        return !isKeyDown(keyCode);
    }

    double getX();

    double getY();

    boolean isOnWindow();
}
