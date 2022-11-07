package com.panjohnny.pjgl.api.utils;


import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class RenderUtil {
    public static FloatBuffer createFloatBufferWithData(float[] data) {
        FloatBuffer buffer = org.lwjgl.BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    public static IntBuffer createIntBufferWithData(int[] data) {
        IntBuffer buffer = org.lwjgl.BufferUtils.createIntBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }
}
