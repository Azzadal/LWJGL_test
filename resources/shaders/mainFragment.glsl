#version 410

in vec3 passColor;
in vec2 passTextureCoord;
layout (location = 0) out vec4 outColor;
//layout (location = 0) out vec4 textureColor;
vec4 textureColor;
in vec3 position;

uniform sampler2D u_TextureSampler;
uniform vec4 u_Colour;

void main() {
//	outColor = vec4(passColor, 1.0f);
	outColor = texture(u_TextureSampler, passTextureCoord);
//	textureColor = texture(tex, passTextureCoord);
//	outColor = vec4(passColor, 1.0) * textureColor;
//	outColor = u_Colour;
}