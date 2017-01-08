#version 330 core

uniform sampler2D tex0;

in vec2 tc_vt;

out vec4 color;

void main(){

	color = texture(tex0, tc_vt);
	
	if(color.w < 0.2){
		discard;
	}
	
}