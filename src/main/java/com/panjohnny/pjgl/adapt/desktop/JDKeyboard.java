package com.panjohnny.pjgl.adapt.desktop;

import com.panjohnny.pjgl.adapt.Adaptation;
import com.panjohnny.pjgl.core.adapters.KeyboardAdapter;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

/**
 * A keyboard adapter. Uses VK key codes provided by {@link KeyEvent}.
 *
 * @see KeyEvent
 * @author PanJohnny
 */
@Adaptation("java-desktop@pjgl")
public class JDKeyboard implements KeyListener, KeyboardAdapter {
    private final ArrayList<Character> chars = new ArrayList<>();
    private final ArrayList<Integer> codes = new ArrayList<>();

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!codes.contains(e.getKeyCode())) {
            chars.add(e.getKeyChar());
            codes.add(e.getKeyCode());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        chars.remove((Character) e.getKeyChar());
        codes.remove((Integer) e.getKeyCode());
    }

    @Override
    public boolean isKeyDown(int keyCode) {
        return codes.contains(keyCode);
    }

    @Override
    public boolean isKeyDown(char character) {
        return chars.contains(character);
    }
}
