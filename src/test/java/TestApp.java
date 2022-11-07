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
