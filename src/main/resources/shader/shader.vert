#version 330

attribute vec3 verticies;
attribute vec2 textures;

out vec2 texCoords;

uniform mat4 projection;

void main() {
    texCoords = textures;
    gl_Position = projection * vec4(verticies, 1);
}