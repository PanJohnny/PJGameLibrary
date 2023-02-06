# PanJohnny's Game Library
[![](https://jitpack.io/v/PanJohnny/PJGameLibrary.svg)](https://jitpack.io/#PanJohnny/PJGameLibrary)
A game library for making simple games in java

## Example with java desktop (awt + swing)

```java
public class Example {
    public static void main(String[] args) {
        PJGL.init(new JDInitializer("Apple!"));
        PJGL pjgl = PJGL.getInstance();
        pjgl.start();

        Sprite<Image> appleSprite = SpriteUtil.createImageSprite("apple", "/apple.png");
        GameObject apple = new GameObject() {
            public final Position position = addComponent(new Position(this, 10, 10));
            public final Size size = addComponent(new Size(this, 100, 100));
            public final SpriteRenderer renderer = addComponent(new SpriteRenderer(this, appleSprite));
        };

        pjgl.getManager().addObject(apple);
    }
}
```

## Adaptability

This project is free to be adapted by anyone. I stepped down from handling graphics using lwjgl's openGL, so I only implemented the java desktop adapter. I created this in a way that makes it possible to plug in lwjgl.

You can read more information at the wiki, or ask me directly. [@PanJohnny1](https://twitter.com/PanJohnny1)
