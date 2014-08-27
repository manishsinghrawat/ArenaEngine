package com.me.RPGEngine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.utils.Array;
import com.me.Loaders.Doodad;
import com.me.Loaders.DoodadData;
import com.me.Loaders.Tile;
import com.me.Loaders.TileData;
import com.me.Loaders.TileData.Data;

public class GridNode {
	Mesh tilemesh;
	Mesh watermesh;
	Mesh doodadmesh;
	
	
	Array<Tile> tiles;
	Array<Doodad> doodads;
	Array<GameObject> units;
	
	private int chunksize;
	private int x; //top left corner in global coordinates
	private int y; //top left corner in global coordinates
	GameWorld world;
	private	float minheight;
	private	float maxheight;
	public BoundingBox bounds;
	
	public GridNode(GameWorld parent,int x,int y,int chunksize){
		this.world=parent;
		this.tiles=new Array<Tile>();
		this.doodads=new Array<Doodad>();
		this.units=new Array<GameObject>(false,6);
		
		this.chunksize=chunksize;
		this.x=x;
		this.y=y;
		
		minheight=100;
		maxheight=-100;
	}
	public void addtile(Tile tile){
		tiles.add(tile);
	}
	public void adddoodad(Doodad doodad){
		doodads.add(doodad);
	}
	public void buildtiles(){

		final TileData tilemodel=world.tilemodel;
		
		//add an error handling when given model specified is not found in tiledatabase 
		int p,q,tilemeshvsize=0,tilemeshisize=0;
		for(final Tile tile:tiles){
			tilemeshvsize+=tilemodel.getvertexsize(tile.modelid);
			tilemeshisize+=tilemodel.getindexsize(tile.modelid);
		}
		
		final float vertices[] = new float[tilemeshvsize];
		final short indices[] = new short[tilemeshisize];

		int voffset = 0;
		int ioffset = 0;
		int idx=0;
		for(q=0;q<chunksize;q++){
			for(p=0;p<chunksize;p++){
				final Tile tile=tiles.get(idx);
				if(tile.height<minheight)
					minheight=tile.height*1.5f;
				if(tile.height>maxheight)
					maxheight=tile.height*2;
				tilemodel.get(tile.modelid,vertices,indices,voffset,ioffset,p*2+1+x,-q*2-1+y,tile.height*1.5f);
				voffset+=tilemodel.getvertexsize(tile.modelid);
				ioffset+=tilemodel.getindexsize(tile.modelid);
				idx++;
			}
		}
		maxheight+=1;
		
		tilemesh = new Mesh(true, vertices.length/tilemodel.vertexsize, indices.length,tilemodel.attributes);
		tilemesh.setVertices(vertices);
		tilemesh.setIndices(indices);
	}
	public void builddoodads(){
		if(doodads.size==0)
			return;
		final DoodadData doodadmodel=world.doodadmodel;
		//add an error handling when given model specified is not found in tiledatabase 
		int doodadmeshvsize=0,doodadmeshisize=0;
		for(final Doodad doodad:doodads){
			doodadmeshvsize+=doodadmodel.getvertexsize(doodad.modelid);
			doodadmeshisize+=doodadmodel.getindexsize(doodad.modelid);
		}
		final float vertices[] = new float[doodadmeshvsize];
		final short indices[] = new short[doodadmeshisize];

		int voffset = 0;
		int ioffset = 0;
		for(final Doodad doodad:doodads){
			final int modelid=doodad.modelid;
			final int localx=doodad.x%(chunksize*2);
			final int localy=doodad.y%(chunksize*2);
			final int height=tiles.get((localx/2)+(localy/2)*chunksize).height;
		//	System.out.println(height);
			doodadmodel.get(modelid, vertices,indices,voffset,ioffset,doodad.rotation,localx+x,-localy+y,height*1.5f);
			voffset+=doodadmodel.getvertexsize(modelid);
			ioffset+=doodadmodel.getindexsize(modelid);
		}
		doodadmesh = new Mesh(true, vertices.length/doodadmodel.vertexsize, indices.length,doodadmodel.attributes);
		doodadmesh.setVertices(vertices);
		doodadmesh.setIndices(indices);
	}
	public void addunit(GameObject unit){
		this.units.add(unit);
	}
	public void removeunit(GameObject unit){
		this.units.removeValue(unit, true);
	}
	public float getheight(float xcor,float ycor,boolean airunit){
		final int localx=(int)xcor%(chunksize*2);
		final int localy=(int)ycor%(chunksize*2);
		final Tile  tile=tiles.get((localx/2)+(localy/2)*chunksize);
		int[] hmap=world.tilemodel.models.get(tile.modelid).hmap;
		xcor=xcor/2-(float) (Math.floor(xcor/2));
		ycor=ycor/2-(float) (Math.floor(ycor/2));
		if(world.tilemodel.models.get(tile.modelid).ramp || airunit){
			
			final float y1=(1-ycor)*hmap[0]+(ycor)*hmap[1];
			final float y2=(1-ycor)*hmap[2]+(ycor)*hmap[3];
			return ((1-xcor)*y1+(xcor)*y2)/2+tile.height;
		}
		else if(xcor<0.5)
			if(ycor<0.5)
				return hmap[0]/2+tile.height;
			else
				return hmap[1]/2+tile.height;
		else
			if(ycor<0.5)
				return hmap[2]/2+tile.height;
			else
				return hmap[3]/2+tile.height;
	
	//	System.out.println((xcor*y1+(1-xcor)*y2)/2+tiles.get((localx/2)+(localy/2)*chunksize).height);
		
	}
	public void calcbounds(){
		bounds=new BoundingBox(new Vector3(x,y,minheight),	new Vector3(x+chunksize*2,y-chunksize*2,maxheight));
		bounds.getCorners();
	}
}
