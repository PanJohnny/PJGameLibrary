#version 330

uniform sampler2D sampler;

in vec2 texCoords;

void main() {
    gl_FragColor = texture2D(sampler, texCoords);
}