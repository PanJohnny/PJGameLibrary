package com.panjohnny.pjgl.api.util;

import com.panjohnny.pjgl.api.object.GameObject;
import com.panjohnny.pjgl.api.object.components.SpriteRenderer;

import java.io.*;
import java.util.Base64;

@SuppressWarnings("unused")
public final class SerializationUtil {
    /**
     * Deserializes the object from Base64 string.
     */
    public static Object deserialize(String s) throws IOException,
            ClassNotFoundException {
        byte[] data = Base64.getDecoder().decode(s);
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
        Object o = ois.readObject();
        ois.close();

        if (o instanceof GameObject g) {
            SpriteRenderer renderer = g.getComponent(SpriteRenderer.class);
            if (renderer != null) {
                renderer.fixSprite();
            }
        }

        return o;
    }

    /**
     * Writes the object to a Base64 string.
     */
    public static String serialize(Serializable o) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(o);
        oos.close();
        return Base64.getEncoder().encodeToString(baos.toByteArray());
    }
}
