package com.panjohnny.pjgl.core.rendering;

import com.panjohnny.pjgl.api.object.AbstractGameObjectRenderer;
import com.panjohnny.pjgl.core.helpers.Shader;

@SuppressWarnings("unused")
public record RendererInfo(AbstractGameObjectRenderer renderer, Shader defaultShader, int matrixScale) {

    public static RendererInfoBuilder builder() {
        return new RendererInfoBuilder();
    }

    public static RendererInfo defaultRendererInfo() {
        return new RendererInfoBuilder().build();
    }


    public static class RendererInfoBuilder {
        private Shader defaultShader;
        private int matrixScale;
        private AbstractGameObjectRenderer renderer;

        RendererInfoBuilder() {
            this.matrixScale = 16;
            this.renderer = new AbstractGameObjectRenderer.SimpleGameObjectRenderer();
        }

        public RendererInfoBuilder defaultShader(Shader defaultShader) {
            this.defaultShader = defaultShader;
            return this;
        }

        public RendererInfoBuilder matrixScale(int matrixScale) {
            this.matrixScale = matrixScale;
            return this;
        }

        public RendererInfoBuilder renderer(AbstractGameObjectRenderer renderer) {
            this.renderer = renderer;
            return this;
        }

        public RendererInfo build() {
            return new RendererInfo(renderer, defaultShader, matrixScale);
        }
    }
}
