#version 330 core

layout (location = 0) in vec3 vertex_in;
layout (location = 1) in vec2 tc_in;
layout (location = 2) in vec3 normals_in;

uniform mat4 view, projection, model;

out vec2 tc_vt;

void main(){

	tc_vt = tc_in;

	gl_Position = projection * view * model * vec4(vertex_in, 1.0);

}