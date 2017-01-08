#version 330 core

out vec4 color;

in vec2 tc_vert;
in vec3 normals_vert;
in vec3 to_camera_vector;
in vec3 to_light_vector;

in vec3 mdlspc_normals;

uniform float shininess;
uniform samplerCube tex0;
uniform sampler2D tex1;

void main(){
		
		vec3 surface_normal_norm 		= normalize(normals_vert);
		vec3 to_camera_vector_norm 		= normalize(to_camera_vector);
		vec3 to_light_vector_norm	 	= normalize(to_light_vector);
		vec3 ref_to_light_norm 			= normalize(reflect(-to_light_vector_norm, surface_normal_norm));
		
		
		vec4 diffuse_texture = texture(tex1, tc_vert);
		
		vec3 fellFarbe = vec3(1f,1f,1f);
		
		vec4 environment = texture(tex0, mdlspc_normals);
		
       vec3 ambient = vec3(1.0,1.0,1.0);
       
       //TODO diffuse light cube.frag
        float cos_alpha 	= max(0.0, dot(surface_normal_norm, to_light_vector_norm));    
        //vec3 diffuseTerm	= diffuse_texture.xyz * cos_alpha;
      	
       //TODO specular light cube.frag
        float cos_beta 		= max(0.0, dot(ref_to_light_norm , to_camera_vector_norm));
        float cos_betaK 	= pow(cos_beta, shininess);
		vec3 specularTerm 	= fellFarbe * cos_betaK;
		
        //color = environment * 0.2 + vec4(1.0, 1.0, 1.0, 1.0) * 0.4;
        color = vec4( diffuse_texture.xyz * 0.8 + specularTerm * 0.0   + environment.xyz * 0.20 , 1.0);


      // if(color.w < 1.0)
      // {
      //         discard;
      // }
}

