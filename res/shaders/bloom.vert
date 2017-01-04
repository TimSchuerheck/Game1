#version 330 core

layout (location = 0) in vec3 vertex_in;
layout (location = 1) in vec2 tc_in;

out vec2 tc_vert;

void main(){
        tc_vert = tc_in;
       
        gl_Position                  = vec4(vertex_in,1.0);
       
}