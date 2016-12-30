#version 330 core

layout(location = 0) in vec3 position_in;

out vec3 tc_vert;

uniform mat4 model, view, projection;

void main(){

tc_vert = position_in;
gl_Position = projection * view * model * vec4(position_in, 1.0);

}