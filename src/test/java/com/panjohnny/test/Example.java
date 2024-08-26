package com.panjohnny.test;

import com.panjohnny.pjgl.adapt.desktop.JDInitializer;
import com.panjohnny.pjgl.api.PJGL;
import com.panjohnny.pjgl.api.asset.SpriteRegistry;
import com.panjohnny.pjgl.api.object.GameObject;
import com.panjohnny.pjgl.api.object.components.Position;
import com.panjohnny.pjgl.api.object.components.Size;
import com.panjohnny.pjgl.api.object.components.SpriteRenderer;

@SuppressWarnings("unused")
public class Example {
    public static void main(String[] args) {
        PJGL.init(new JDInitializer("Apple!"));
        PJGL pjgl = PJGL.getInstance();

        SpriteRegistry.registerImageSprite("apple", "./assets/apple.png");

        GameObject apple = new GameObject() {
            public final Position position = addComponent(new Position(this, 10, 10));
            public final Size size = addComponent(new Size(this, 100, 100));
            public final SpriteRenderer renderer = addComponent(new SpriteRenderer(this, "apple"));
        };

        pjgl.getManager().addObject(apple);
        pjgl.start();
    }
}
