package com.panjohnny.pjgl.adapt.desktop;

import com.panjohnny.pjgl.core.PJGLCore;
import com.panjohnny.pjgl.core.adapters.KeyboardAdapter;
import com.panjohnny.pjgl.core.adapters.MouseAdapter;
import com.panjohnny.pjgl.core.adapters.WindowAdapter;

import javax.swing.*;

/**
 * Basically {@link JFrame}.
 *
 * @author PanJohnny
 */
public class JDWindow extends JFrame implements WindowAdapter {
    private final JDMouse mouse;
    private final JDKeyboard keyboard;

    public JDWindow(String title, int width, int height) {
        super(title);
        setSize(width, height);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        mouse = new JDMouse();
        keyboard = new JDKeyboard();
        setAutoRequestFocus(true);
    }

    @Override
    public boolean shouldClose() {
        return !isDisplayable();
    }

    @Override
    public void init(PJGLCore core) {
        if (core.getRenderer() instanceof JDRenderer renderer) {
            add(renderer);
            renderer.addMouseMotionListener(mouse);
            renderer.addMouseListener(mouse);
            renderer.addKeyListener(keyboard);
        } else {
            System.getLogger("PJGL").log(System.Logger.Level.WARNING, "SwingWindow initialized without SwingRenderer in mind. This could lead to issues. If you are making your own renderer interpretation please extend SwingRender in order to be added as Canvas.");
        }
    }

    @Override
    public MouseAdapter getMouse() {
        return mouse;
    }

    @Override
    public KeyboardAdapter getKeyboard() {
        return keyboard;
    }

    @Override
    public void setVisible(boolean b) {
        super.setVisible(b);
    }
}
