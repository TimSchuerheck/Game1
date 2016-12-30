#version 330 core

in vec3 tc_vert;

out vec4 color;

uniform samplerCube tex;

void main(){
	color = texture(tex, tc_vert);
}