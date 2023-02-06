package com.panjohnny.pjgl.core.adapters;

/**
 * Adapter for keyboards. Note that different systems use other keycodes!
 * You may use different keyboard utilities.
 *
 * @author PanJohnny
 */
@SuppressWarnings("unused")
public interface KeyboardAdapter {
    boolean isKeyDown(int keyCode);

    default boolean isKeyUp(int keycode) {
        return !isKeyDown(keycode);
    }

    boolean isKeyDown(char character);

    default boolean isKeyUp(char character) {
        return !isKeyDown(character);
    }
}
