#version 330 core

out vec4 color;

in vec2 tc_vert;

uniform sampler2D tex0;

void main(){

        color = texture(tex0, tc_vert);

       if(color.w <0.5)
       {
             discard;
     	}
}

