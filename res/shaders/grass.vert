#version 330 core

layout (location = 0) in vec3 vertex_in;
layout (location = 1) in vec2 tc_in;
layout (location = 2) in vec3 normals_in;

out vec2 tc_vert;

uniform mat4 view, projection, model;

void main(){
        tc_vert = tc_in;
       
        mat4 model_view              = view * model;
        vec3 position_in_viewspace   = vec4(model_view * vec4(vertex_in, 1.0)).xyz;

        gl_Position                  = projection * vec4(position_in_viewspace,1.0);
       
}