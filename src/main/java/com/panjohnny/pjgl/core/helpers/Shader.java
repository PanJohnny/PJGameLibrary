package com.panjohnny.pjgl.core.helpers;

import com.panjohnny.pjgl.api.utils.FileUtil;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;

import java.io.PrintStream;
import java.nio.FloatBuffer;
import java.util.function.BiConsumer;

import static org.lwjgl.opengl.GL20.*;

@SuppressWarnings("unused")
public class Shader {
    private final int program;
    private final int vertexShader;
    private final int fragmentShader;

    public Shader(String vertexFile, String fragmentFile) {
        program = glCreateProgram();
        vertexShader = glCreateShader(GL_VERTEX_SHADER);
        fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);

        glShaderSource(vertexShader, FileUtil.readResource(vertexFile));
        glCompileShader(vertexShader);
        if(glGetShaderi(vertexShader, GL_COMPILE_STATUS) != 1) {
            throw new ShaderError(vertexFile, glGetShaderInfoLog(vertexShader));
        }

        glShaderSource(fragmentShader, FileUtil.readResource(fragmentFile));
        glCompileShader(fragmentShader);
        if(glGetShaderi(fragmentShader, GL_COMPILE_STATUS) != 1) {
            throw new ShaderError(fragmentFile, glGetShaderInfoLog(fragmentShader));
        }

        glAttachShader(program, vertexShader);
        glAttachShader(program, fragmentShader);

        glBindAttribLocation(program, 0, "vertices");
        glBindAttribLocation(program, 1, "textures");

        glLinkProgram(program);
        if (glGetProgrami(program, GL_LINK_STATUS) != 1) {
            throw new ShaderError(glGetProgramInfoLog(program));
        }

        glValidateProgram(program);
        if (glGetProgrami(program, GL_VALIDATE_STATUS) != 1) {
            throw new ShaderError(glGetProgramInfoLog(program));
        }
    }

    public Shader(String file) {
        this(file + ".vert", file + ".frag");
    }

    public void bind() {
        glUseProgram(program);
    }

    public void setUniformInteger(String name, int value) {
        setUniform(name, value, GL20::glUniform1i);
    }
    public void setUniformMatrix4f(String name, Matrix4f value) {
        setUniform(name, value, (location, v) -> {
            FloatBuffer buffer = BufferUtils.createFloatBuffer(4*4);
            v.get(buffer);
            glUniformMatrix4fv(location, false, buffer);
        });
    }

    public <T> void setUniform(String name, T value, BiConsumer<Integer, T> uniformHandler) {
        int location = glGetUniformLocation(program, name);

        if (location != -1) {
            uniformHandler.accept(location, value);
        }
    }
    private static class ShaderError extends Error {
        private final String log;
        public ShaderError(String name, String log) {
            super("Error compiling shader %s".formatted(name));
            this.log = log;
        }

        public ShaderError(String log) {
            super("Error loading shader");
            this.log = log;
        }

        @Override
        public void printStackTrace(PrintStream s) {
            super.printStackTrace(s);
            System.err.println("\tCaused by: " + log);
        }
    }

    public int getProgram() {
        return program;
    }

    public int getVertexShader() {
        return vertexShader;
    }

    public int getFragmentShader() {
        return fragmentShader;
    }
}
