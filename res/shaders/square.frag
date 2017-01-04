#version 330 core

out vec4 fColor;

//v in vColor because of vertex color
in vec3 vColor;

void main(){
	
    fColor = vec4(vColor,1f);
}

