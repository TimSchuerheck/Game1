#version 330 core

out vec4 color;

uniform sampler2D tex0;
uniform vec3 inColor;

void main(){
	
	vec4 tex = texture(tex0, gl_PointCoord);
	
	color = tex;
	
	if( color.a < 0.7 ){
		discard;
	} else {
		color = vec4(inColor, 1.0);
	}
}