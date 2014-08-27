package com.me.RPGEngine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.utils.DefaultTextureBinder;
import com.badlogic.gdx.graphics.g3d.utils.RenderContext;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Plane;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

public class RenderSystem {
	private Camera camera=null;
	private Environment environment=null;
	private TerrainShader shader=null;
	private GameWorld world=null;
	private ModelBatch batch;
	RenderContext rendercontext;
	
	public RenderSystem() {
		rendercontext = new RenderContext(new DefaultTextureBinder(DefaultTextureBinder.WEIGHTED, 1));
		shader=new TerrainShader();
		shader.init();
		batch=new ModelBatch();
	}
	
	/**
	 * calculates renderable terrain from whole map
	 * rasterizer algorithm for rendering triangle
	 * @param renderset Renderable chunks will be returned in this array.
	 * @param textman 
	 */
	private void getrenderableterrain(Array<GridNode> renderset){
		final float projplaneheight=0.0f;
		Plane plane=new Plane(new Vector3(0.0f,0.0f,1.0f),projplaneheight);
		final Vector3 campos=camera.position;
		float minx=campos.x,miny=campos.y;
		float maxx=minx,maxy=miny;
		
		Vector3 intersection=new Vector3();
		for(int i=4;i<8;i++){
			final Vector3 point=camera.frustum.planePoints[i];
			if(point.z<-0.001)
				Intersector.intersectLinePlane(campos.x,campos.y, campos.z, point.x, point.y,point.z, plane, intersection);
			else
				intersection=point;
			
			if(intersection.x<minx)
				minx=intersection.x;
			if(intersection.y<miny)
				miny=intersection.y;
			if(intersection.x>maxx)
				maxx=intersection.x;
			if(intersection.y>maxy)
				maxy=intersection.y;
		}
		
		world.getnodes(renderset,minx, miny, maxx, maxy);
		int i=0;
		while(i<renderset.size){
			if(!camera.frustum.boundsInFrustum(renderset.get(i).bounds))
				renderset.removeIndex(i);
			else 
				i++;
		}
	}
	
	public void render(TextManager textman) {
		//array holding renderable chunksets
		rendercontext.begin();
			Array<GridNode> nodes=new Array<GridNode>(false,16);
			getrenderableterrain(nodes);
			
			for(final GridNode node:nodes){
				textman.add("Node", node.bounds);
			}
			
			shader.begin(camera, rendercontext);
			shader.config(environment);
			//	shader.worldtransform(world);
			shader.config(world.tilemodel.material);
			for(final GridNode node:nodes)
				shader.render(node.tilemesh);
			
			shader.config(world.doodadmodel.material);
			for(final GridNode node:nodes)
				if(node.doodadmesh!=null)
					shader.render(node.doodadmesh);
			
			shader.end();
		rendercontext.end();
		
			batch.begin(camera);
			for(final GridNode node:nodes)
				for(final GameObject unit:node.units){
					unit.update(Gdx.graphics.getDeltaTime());
					batch.render(unit.model,environment);
				}
			batch.end();
			
	}
	
	public void config(GameWorld world) {
		this.world=world;
	}
	public void config(Camera camera){
		this.camera=camera;
	}
	public void config(Environment environment) {
		this.environment=environment;
	}
}
