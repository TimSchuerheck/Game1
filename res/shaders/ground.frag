#version 330 core

out vec4 color;

in vec2 tc_vert;
in vec3 normals_vert;
in vec3 to_camera_vector;
in vec3 to_light_vector;

uniform sampler2D tex0;
uniform sampler2D tex1;
uniform vec3 light_color;
uniform float shininess;

void main(){

		vec3 surface_normal_norm 		= normalize(normals_vert);
		vec3 to_camera_vector_norm 		= normalize(to_camera_vector);
		vec3 to_light_vector_norm	 	= normalize(to_light_vector);
		vec3 ref_to_light_norm 			= normalize(reflect(-to_light_vector_norm, surface_normal_norm));
		
		
       //ambient light
       vec4 ambient_light   = vec4(light_color * 0.02f,1.0);
       
       //TODO diffuse light cube.frag
        float cos_alpha 	= max(0.0, dot(surface_normal_norm, to_light_vector_norm));    
         vec3 diffuseTerm	= texture(tex0, tc_vert).rgb * vec3(0.95, 1.05, 0.5);
        //vec3 diffuseTerm	= texture(tex0, tc_vert).rgb  * cos_alpha;
		
		vec3 horizon_blending = vec3(38.0/255.0, 19.0/255.0, 140.0/255.0) * max(0.0 ,(0.9 - cos_alpha * 1.3) * 1.1);
		
       //TODO specular light cube.frag
        float cos_beta 		= max(0.0, dot(ref_to_light_norm , to_camera_vector_norm));
        float cos_betaK 	= pow(cos_beta, shininess);
		vec3 specularTerm 	= texture(tex1, tc_vert).rgb * cos_betaK;
		
		color = vec4(horizon_blending + diffuseTerm + specularTerm * 1.5, 1.0);
        //color = vec4( diffuseTerm + specularTerm * 1.5 ,1.0) + ambient_light;

       if(color.w < 1.0)
       {
               discard;
       }
}

