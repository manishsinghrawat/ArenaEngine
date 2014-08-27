package com.me.RPGEngine;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;
/**
 * 
 * @author MAnish
 * @since 5/6/2014
 * 
 * Shader class for rendering
 *
 */
//TODO cache uniform ids to reduce string matching for uniforms
public class TerrainShader implements Disposable{

	private Camera camera;
	private RenderContext context;
	ShaderProgram program;

	public void dispose() {
		program.dispose();
	}
	public void init() {
		String vert = Gdx.files.internal("data/Shaders/terrainvertex.glsl").readString();
		String frag = Gdx.files.internal("data/Shaders/terrainfragment.glsl").readString();
		program = new ShaderProgram(vert, frag);
		if (!program.isCompiled())
			throw new GdxRuntimeException(program.getLog());
	}

	public void begin(Camera camera, RenderContext context) {
		this.camera = camera;
        this.context = context;
        program.begin();
       // System.out.println(camera.direction);
        program.setUniformMatrix("u_projViewTrans", camera.combined);
        context.setDepthTest(GL20.GL_LEQUAL);
        context.setCullFace(GL20.GL_BACK);
        context.setBlending(true, GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
	}
	public void worldtransform(Matrix4 mat){
		program.setUniformMatrix("u_worldTrans",mat);
	}
	public void render(GridNode chunk) {
        chunk.tilemesh.render(program, GL20.GL_TRIANGLES);
	}
	public void render(Mesh mesh) {
		mesh.render(program, GL20.GL_TRIANGLES);
	}
		
	public void end() {
		program.end();
	}
	
	public void config(Environment environment) {
		Color color;
		if(environment.directionalLights.size>0){
			color=environment.directionalLights.get(0).color;
			program.setUniformf("u_dirlightcolor",color.r,color.g,color.b);
			program.setUniformf("u_dirlightdirec", environment.directionalLights.get(0).direction);
		}
		
		if(environment.has(ColorAttribute.Ambient)){
			color=((ColorAttribute)environment.get(ColorAttribute.Ambient)).color;
			program.setUniformf("u_lambient",color.r,color.g,color.b);
		}
	}
	public void config(Material material) {
		if(material.has(TextureAttribute.Diffuse)){
			int id=context.textureBinder.bind(((TextureAttribute)(material.get(TextureAttribute.Diffuse))).textureDescription);
			program.setUniformi("u_diffuseTexture", id);
		//	System.out.println(id);
		}
		/*if(material.has(ColorAttribute.Diffuse)){
			program.setUniformf("u_worldTrans", ((ColorAttribute)material.get(ColorAttribute.Diffuse)).color);
		}
		*/
	}

}
