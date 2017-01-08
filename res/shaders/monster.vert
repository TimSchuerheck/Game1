#version 330 core

layout (location = 0) in vec3 position;

uniform mat4 model, viewproj;

void main(){
	vec4 pos = viewproj * model * vec4(position, 1.0);
	gl_PointSize = (1.0 - pos.z / pos.w) * 120000.0;
	gl_Position = pos;
	
}