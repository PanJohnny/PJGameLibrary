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
