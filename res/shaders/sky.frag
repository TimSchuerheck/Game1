#version 330 core

in vec3 tc_vert;

out vec4 color;

uniform samplerCube tex;
uniform int daytime;

void main(){
	color = texture(tex, tc_vert);
	
	if( daytime == 1 ){
      
      float sum = color.r + color.g + color.b;
      float q = sum/3;
      color = vec4( q , q , q + 0.03 , 1.0);
      	 
    }
}