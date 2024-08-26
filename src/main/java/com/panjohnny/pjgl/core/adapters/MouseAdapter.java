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

    boolean isButtonDown(int button);

    default boolean isButtonUp(int button) {
        return !isButtonDown(button);
    }

    double getX();

    double getY();

    boolean isOnWindow();
}
