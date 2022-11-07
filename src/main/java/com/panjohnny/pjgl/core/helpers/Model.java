package com.panjohnny.pjgl.core.helpers;


import com.panjohnny.pjgl.api.utils.RenderUtil;
import org.lwjgl.opengl.GL15;

import static org.lwjgl.opengl.GL20.*;

@SuppressWarnings("unused")
public class Model {
    private final int drawCount;
    private final int vertexId;
    private final int textureId;
    private final int indicesId;

    public static Model rectangle() {

        float[] vertices = {
                -1, 1, 0, // top left
                1, 1, 0,
                1, -1, 0,

                -1, -1, 0
        };

        int[] indices = {
                0, 1, 2,
                2, 3, 0
        };

        float[] t = {
                0, 0, // 0
                0, 1, // 1
                1, 1, //2
                // 2
                1, 0 // 3
                // 0
        };

        return new Model(vertices, t, indices);
    }

    public Model(float[] vertices, float[] texCoordinates, int[] indices) {
        drawCount = indices.length;

        vertexId = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vertexId);
        GL15.glBufferData(GL_ARRAY_BUFFER, RenderUtil.createFloatBufferWithData(vertices), GL_STATIC_DRAW);

        textureId = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, textureId);
        glBufferData(GL_ARRAY_BUFFER, RenderUtil.createFloatBufferWithData(texCoordinates), GL_STATIC_DRAW);

        indicesId = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indicesId);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, RenderUtil.createIntBufferWithData(indices),GL_STATIC_DRAW);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    public void render() {
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glBindBuffer(GL_ARRAY_BUFFER, vertexId);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

        glBindBuffer(GL_ARRAY_BUFFER, textureId);
        glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indicesId);
        glDrawElements(GL_TRIANGLES, drawCount, GL_UNSIGNED_INT, 0);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        glDisableClientState(0);
        glDisableClientState(1);
    }

    public int getDrawCount() {
        return drawCount;
    }

    public int getVertexId() {
        return vertexId;
    }

    public int getTextureId() {
        return textureId;
    }

    public int getIndicesId() {
        return indicesId;
    }
}
