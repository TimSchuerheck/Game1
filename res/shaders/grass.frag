#version 330 core

out vec4 color;

in vec2 tc_vert;

uniform sampler2D tex0;
uniform int daytime;

void main(){

   color = texture(tex0, tc_vert);
        
   if( daytime == 1 ){
      
      float sum = color.r + color.g + color.b;
      float q = sum/3;
      color = vec4( q , q , q + 0.03 , color.a );
      	 
    }

       if(color.w <0.5)
       {
             discard;
     	}
}

