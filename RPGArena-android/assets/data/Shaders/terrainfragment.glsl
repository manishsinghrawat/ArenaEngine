#ifdef GL_ES
precision mediump float;
#endif

uniform sampler2D u_diffuseTexture;

varying vec3 v_normal;
varying vec3 v_ldiffuse;
varying vec2 v_texCoord0;
 
void main() {
	vec3 normal = v_normal;
	
	vec4 diffuse=texture2D(u_diffuseTexture,v_texCoord0);
	gl_FragColor.rgb = (diffuse.rgb * v_ldiffuse);
	gl_FragColor.a=diffuse.a;
	//gl_FragColor.rgb=v_ldiffuse;
}