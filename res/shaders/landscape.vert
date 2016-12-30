#version 330 core

layout (location = 0) in vec3 vertex_in;
layout (location = 1) in vec2 tc_in;
layout (location = 2) in vec3 normals_in;

uniform mat4 model, view, projection;

out vec3 vColor;
out vec2 vTc;
out float vHeight_fac;

void main(){
		
		
		vTc = tc_in;
		
 		vec3 color1 = vec3(120.0/255.0, 200.0/255.0, 120.0/255.0);
		vec3 color2 = vec3(149.0/255.0, 109.0/255.0, 79.0/255.0);	
		vec3 color3 = vec3(71.0/255.0, 131.0/255.0, 177.0/255.0);
		
		float p1 = 0.1;
		float p2 = 0.3;
		float p3 = 0.8;
		
		float fac1;
		float fac2;
		float fac3;
		
		float vy = vertex_in.y;
		vHeight_fac = vy/0.92426;
		
		if(vy < p1){
			fac1 = vy / p1;
			fac2 = 0;
			fac3 = 0;
		} else if (vy > p1 && vy < p2){
			fac2 = (vy-p1) / (p2-p1);
			fac1 = 1 - fac2;
			fac3 = 0;
		} else if (vy > p2){
			fac3 = (vy-p2) / (p3-p2);
			fac2 = 1 - fac3;
			fac1 = 0;
		}
			
		vec3 color1_faded = fac1 * color1;
		vec3 color2_faded = fac2 * color2;
		vec3 color3_faded = fac3 * color3;
		
		vColor =  color2_faded + color1_faded + color3_faded;
		
        gl_Position                  = projection * view * model *  vec4(vertex_in,1.0);
       
}