attribute vec3 a_position;
attribute vec3 a_normal;
attribute vec2 a_texCoord0;
 
uniform mat4 u_worldTrans;
uniform mat4 u_projViewTrans;

uniform vec3 u_lambient;
 
uniform vec3 u_dirlightcolor;
uniform vec3 u_dirlightdirec;

varying vec2 v_texCoord0;

varying vec3 v_normal;
varying vec3 v_ldiffuse;
 
void main() {
    v_texCoord0 = a_texCoord0;
	v_normal=a_normal;
	
	v_ldiffuse = u_lambient;
	vec3 lightDir = -u_dirlightdirec;
	float NdotL = clamp(dot(a_normal, lightDir), 0.0, 1.0);
	vec3 value = u_dirlightcolor * NdotL;
	v_ldiffuse += value;

    gl_Position = u_projViewTrans  * vec4(a_position, 1.0);
}