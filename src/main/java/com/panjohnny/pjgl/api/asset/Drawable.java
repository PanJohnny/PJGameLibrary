package com.panjohnny.pjgl.api.asset;

/**
 * Interface representing keypair of string id and object value. Inherited by {@link Sprite}.
 *
 * @author PanJohnny
 * @see Sprite
 */
@SuppressWarnings("unused")
public interface Drawable {
    String getID();

    Object getValue();
}
