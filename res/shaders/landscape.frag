#version 330 core

out vec4 color;

in vec3 vColor;
in vec2 vTc;
in float vHeight_fac;

uniform sampler2D tex0;

void main(){

       vec3 diffuseTerm = vColor;
      
        color = vec4( diffuseTerm , 1.0) + vec4 ((texture(tex0, vTc*5).xyz * vHeight_fac), 1.0);

       if(color.w < 1.0)
       {
               discard;
       }
}