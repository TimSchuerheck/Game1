#version 330 core

layout (location = 0) in vec3 position;
layout (location = 1) in vec3 colorIn;

out vec3 vColor;

uniform mat4 view, projection, model;

void main(){
        vColor = colorIn;

       gl_Position = projection * view * model * vec4(position, 1.0);
}