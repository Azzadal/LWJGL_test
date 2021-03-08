#version 410

layout (location = 0) in vec3 position;
layout (location = 1) in vec3 color;
layout (location = 2) in vec2 textureCoord;

out vec3 pos;
out vec3 passColor;
out vec2 passTextureCoord;

void main() {
	pos = position;
	passColor = color;
	passTextureCoord = textureCoord;
	gl_Position = vec4(position, 1.0f);
}