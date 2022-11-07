package com.panjohnny.pjgl.core.helpers;

import org.checkerframework.common.value.qual.IntRange;

import static org.lwjgl.opengl.GL13.*;

@SuppressWarnings("unused")
public class Texture extends Wrapper<Integer> {

    public Texture(int texture) {
        super(texture);
    }

    public void bind(@IntRange(from = 0, to = 31) int sampler) {
        glActiveTexture(GL_TEXTURE0 + sampler);
        glBindTexture(GL_TEXTURE_2D, value);
    }

    public int getTexture() {
        return value;
    }
}
