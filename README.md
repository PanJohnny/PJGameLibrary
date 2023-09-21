# PanJohnny's Game Library
[![](https://jitpack.io/v/PanJohnny/PJGameLibrary.svg)](https://jitpack.io/#PanJohnny/PJGameLibrary)
[![](https://img.shields.io/badge/Javadocs-Online-informational)](https://panjohnny.github.io/PJGameLibrary)

A game library for making simple games in java

## Example with java desktop (awt + swing)

```java
public class Example {
    public static void main(String[] args) {
        PJGL.init(new JDInitializer("Apple!"));
        PJGL pjgl = PJGL.getInstance();
        pjgl.start();

        SpriteRegistry.registerSprite("apple", "/apple.png");

        GameObject apple = new GameObject() {
            public final Position position = addComponent(new Position(this, 10, 10));
            public final Size size = addComponent(new Size(this, 100, 100));
            public final SpriteRenderer renderer = addComponent(new SpriteRenderer(this, "apple"));
        };

        pjgl.getManager().queueAddition(apple);
    }
}
```

## Adaptability

This project is free to be adapted by anyone.

Currently you can use these two:
 - Java Desktop - awt, swing
 - LWJGL

You can read more information at the wiki, or ask me directly. [@PanJohnny1](https://twitter.com/PanJohnny1)
