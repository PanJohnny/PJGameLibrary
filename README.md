# PanJohnny's Game Library
Small game library build on top of LWJGL.

## Principles

 - Static access
 - Events
 - Ability to modify stuff
 - Beginner friendly

## Example

### Code


```java
import com.panjohnny.pjgl.api.GameApplication;
import com.panjohnny.pjgl.api.PJGL;
import com.panjohnny.pjgl.api.PJGLRegistries;
import com.panjohnny.pjgl.core.rendering.GLFWWindow;
import com.panjohnny.pjgl.core.rendering.OrthographicCamera;

public class TestApp extends GameApplication {
    /**
     * Method where everything should be initialized and your data loaded. Called before {@link #modifyWindow(GLFWWindow)}
     * @apiNote Beware that you should not do any GL operations here. That includes loading models and shaders!
     */
    @Override
    public void init() {
        System.out.println("Hello PJGL world!");
    }
    /**
     * Method called to modify the window from its default state. Should be used to introduce {@link com.panjohnny.pjgl.api.io} listeners.
     * @param window GLFWWindow to modify
     * @see GLFWWindow
     */
    @Override
    public void modifyWindow(GLFWWindow window) {
        System.out.println("Modify window");
        window.setTitle("Heya!");
        window.resize(1080, 720);
    }
    /**
     * Place where you should register objects and modify rendering (OpenGL) aspects if you want to. Called after {@link #modifyWindow(GLFWWindow)}
     */
    @Override
    public void load() {
        PJGLRegistries.GAME_OBJECT_MANAGER_REGISTRY.getDefaultManager().add(new ImageStuff());
        ((OrthographicCamera)PJGL.getCamera()).changeSize(1080, 720);
    }
    public static void main(String[] args) {
        PJGL.registerApplication(new TestApp());
        PJGL.run();
    }
}
```

```java
import com.panjohnny.pjgl.api.object.GameObject;
import com.panjohnny.pjgl.api.utils.TextureLoader;
import com.panjohnny.pjgl.core.helpers.Model;

public class ImageStuff extends GameObject {
    public ImageStuff() {
        super(0, 0, 100, 100, Model.rectangle(), TextureLoader.loadTexture(TextureLoader.loadImageAndHandleException("/beautiful.png")));
    }

    @Override
    public void update(long delta) {

    }
}
```

---

### Result
![Result of the code above. An window with image being rendered.](https://media.discordapp.net/attachments/789950149395742740/1039280923004375061/image.png?width=1248&height=676)