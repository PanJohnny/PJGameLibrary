package com.panjohnny.pjgl.adapt.desktop;

import com.panjohnny.pjgl.core.adapters.KeyboardAdapter;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class JDKeyboard implements KeyListener, KeyboardAdapter {
    private final ArrayList<Character> chars = new ArrayList<>();
    private final ArrayList<Integer> codes = new ArrayList<>();

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        chars.add(e.getKeyChar());
        codes.add(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        chars.remove((Object) e.getKeyChar());
        codes.remove((Object) e.getKeyCode());
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
