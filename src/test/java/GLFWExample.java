import com.panjohnny.pjgl.adapt.lwjgl.GLFWKeyboard;
import com.panjohnny.pjgl.adapt.lwjgl.GLFWWindow;
import com.panjohnny.pjgl.adapt.lwjgl.LWJGLInitializer;
import com.panjohnny.pjgl.api.PJGL;
import com.panjohnny.pjgl.api.PJGLEvents;
import com.panjohnny.pjgl.api.asset.SpriteRegistry;
import com.panjohnny.pjgl.api.object.GameObject;
import com.panjohnny.pjgl.api.object.components.Position;
import com.panjohnny.pjgl.api.object.components.Size;
import com.panjohnny.pjgl.api.object.components.SpriteRenderer;
import org.lwjgl.glfw.GLFW;

@SuppressWarnings("unused")
public class GLFWExample {
    public static void main(String[] args) {
        PJGL.init(new LWJGLInitializer("Apple!", 750, 750));
        PJGL pjgl = PJGL.getInstance();

        PJGLEvents.VISIBLE.listen(() -> {
            SpriteRegistry.registerTextureSprite("apple", "./apple.png");

            GameObject apple = new GameObject() {
                public final Position position = addComponent(new Position(this, 10, 10));
                public final Size size = addComponent(new Size(this, 100, 100));
                public final SpriteRenderer renderer = addComponent(new SpriteRenderer(this, "apple"));
            };

            pjgl.getManager().queueAddition(apple);
        });

        GLFWKeyboard keyboard = pjgl.getKeyboard();
        GLFWWindow window = pjgl.getWindow();

        PJGLEvents.TICK.listen(() -> {
            if (keyboard.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) {
                window.close();
            }
        });
        pjgl.start();
    }
}
