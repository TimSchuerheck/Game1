#version 330 core

layout (location = 0) in vec3 vertex_in;
layout (location = 1) in vec2 tc_in;
layout (location = 2) in vec3 normals_in;

out vec2 tc_vert;
out vec3 normals_vert;
out vec3 to_camera_vector;
out vec3 to_light_vector;

uniform vec3 light_position;
uniform mat4 view, projection, model;

void main(){
        tc_vert = tc_in;
       
        mat4 model_view              = view * model;
        vec3 position_in_viewspace   = vec4(model_view * vec4(vertex_in, 1.0)).xyz;
       
		mat4 normal_matrix 			= transpose(inverse(model_view));
		normals_vert 				= (normal_matrix * vec4(normals_in,0.0)).xyz;
		to_camera_vector			= - position_in_viewspace.xyz;
		
		vec4 light_position_viewspace = view * vec4(light_position,1.0);
		to_light_vector 			= (light_position_viewspace.xyz - position_in_viewspace).xyz;

        gl_Position                  = projection * vec4(position_in_viewspace,1.0);
       
}