#version 330 core

out vec4 color;

in vec2 tc_vert;

uniform sampler2D tex0;
uniform sampler2D tex1;
uniform mat4 rotation;
uniform int width, height;

vec3 depth(){
   float v = pow(texture(tex1, vec2(gl_FragCoord.x/width, gl_FragCoord.y/height)).r , 256);
	return vec3(v,v,v);
}

void main(){

   vec4 sum = vec4(0);
   vec2 texcoord = vec2(gl_FragCoord.x/width, gl_FragCoord.y/height);
   int j;
   int i;
	

	float threshhold1 = 0.3;
	float threshhold2 = 0.5;
	
	float fac1 = 0.000;
	float fac2 = 0.01;
	float fac3 = 0.005;
	
	float color_intensity = texture(tex0, texcoord).r + texture(tex0, texcoord).g + texture(tex0, texcoord).b;
	color_intensity / 3;
	
   for( i= -4 ;i < 4; i++)
   {
        for (j = -3; j < 3; j++)
        {
            sum += texture(tex0, texcoord + vec2(j, i)*0.004) * 0.25;
        }
   }
       if (color_intensity < threshhold1)
    {
       color = sum*sum*fac1 + texture(tex0, texcoord);
    }
    else
    {
        if (color_intensity < threshhold2)
        {
            color = sum*sum*fac2 + texture(tex0, texcoord);
        }
        else
        {
            color = sum*sum*fac3 + texture(tex0, texcoord);
        }
    }
    

}

