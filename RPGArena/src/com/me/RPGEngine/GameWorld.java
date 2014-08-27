package com.me.RPGEngine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.me.Loaders.Doodad;
import com.me.Loaders.DoodadData;
import com.me.Loaders.GObjectData;
import com.me.Loaders.MapData;
import com.me.Loaders.MapLoader;
import com.me.Loaders.TileData;

public class GameWorld implements Disposable{
	TileData tilemodel;
	MapData mapdesc;
	DoodadData doodadmodel;
	GObjectData unitmodel;
	GridNode[][] mapgrid;

	boolean[][] pathable;
	
	Array<GameObject> units;
	
	//world transform for chunks
	float offx;
	float offy;
	public GameWorld(MapLoader loader){
		units=new Array<GameObject>(false,16);
		this.tilemodel=loader.tilemodel;
		tilemodel.load();//need to be called before usage
		this.doodadmodel=loader.doodadmodel;
		doodadmodel.load();
		this.unitmodel=loader.unitmodel;
		unitmodel.load();
		
		this.mapdesc=loader.mapdesc;
		offx=-mapdesc.size;
		offy=mapdesc.size;			
		this.doodadmodel=loader.doodadmodel;
		
		int nodesize=mapdesc.size/mapdesc.chunksize;
		int chunksize=mapdesc.chunksize;
		
		mapgrid=new GridNode[nodesize][nodesize];
		pathable=new boolean[mapdesc.size*2][mapdesc.size*2];
		
		int i,j,id;
		for(j=0;j<nodesize;j++)
			for(i=0;i<nodesize;i++)
				mapgrid[i][j]=new GridNode(this,i*chunksize*2-mapdesc.size,-j*chunksize*2+mapdesc.size,chunksize);

		boolean pmap[];
		id=0;
		for(j=0;j<mapdesc.size;j++){
			for(i=0;i<mapdesc.size;i++){
				mapgrid[i/chunksize][j/chunksize].addtile(mapdesc.terrain.get(id));
				pmap=tilemodel.models.get(mapdesc.terrain.get(id).modelid).pmap;
				pathable[i*2][j*2]=pmap[0];
				pathable[i*2][j*2+1]=pmap[1];
				pathable[i*2+1][j*2]=pmap[2];
				pathable[i*2+1][j*2+1]=pmap[3];
				id++;
			}
		}
		
		for(final Doodad doodad:mapdesc.doodads){
			final int xtile=doodad.x/2;
			final int ytile=doodad.y/2;
			final int x=xtile/chunksize;
			final int y=ytile/chunksize;
			
			mapgrid[x][y].adddoodad(doodad);
			
			pathable[doodad.x][doodad.y]=false;
			
			if(doodad.y!=0)
				pathable[doodad.x][doodad.y-1]=false;
			if(doodad.x!=0)
				pathable[doodad.x-1][doodad.y]=false;
			if(doodad.x!=0 && doodad.y!=0)
				pathable[doodad.x-1][doodad.y-1]=false;
			
		}
		/*GameObject unit=new GameObject();
		unit.world=this;
		unit.modelid=0;
		unit.load();
		unit.setposition(50, 50);
		
		unit.animate();
		*/
		for(i=0;i<nodesize;i++)
			for(j=0;j<nodesize;j++){
				mapgrid[i][j].buildtiles();
				mapgrid[i][j].builddoodads();
				mapgrid[i][j].calcbounds();
			}
			
	}

	
	//check this method error in culling maybe
	public void getnodes(Array<GridNode> renderset, float x1,float y1,float x2,float y2){
		final int mapsize=mapdesc.size/mapdesc.chunksize;
		//check
		x1=(x1+mapdesc.size)/(mapdesc.chunksize*2);
		x2=(x2+mapdesc.size)/(mapdesc.chunksize*2)+1;
		
		y1=-(y1-mapdesc.size)/(mapdesc.chunksize*2)+1; //for bottom bound
		y2=-(y2-mapdesc.size)/(mapdesc.chunksize*2);  //for top bound

		final int tx1=x1<0?0:(int)x1;
		final int tx2=x2<mapsize?(int)x2:mapsize;
		final int ty1=y2<0?0:(int)y2;
		final int ty2=y1<mapsize?(int)y1:mapsize;
		
	//	System.out.println(tx1+" "+tx2+" "+ty1+" "+ty2+" ");
		for(int i=tx1;i<tx2;i++)
			for(int j=ty1;j<ty2;j++)
				renderset.add(mapgrid[i][j]);
	}

	/**
	 * transform map coordinates to global world coordinates
	 * @param local input position vector
	 * @param target result is returned in target
	 */
	public void getglobal(Vector3 local, Vector3 target){
		 target.x=local.x-mapdesc.size;
		 target.y=-local.y+mapdesc.size;
		 target.z=local.z;
	}
	@Override
	public void dispose() {
		tilemodel.dispose();
		doodadmodel.dispose();
		unitmodel.dispose();
	}


	public void register(GameObject unit) {
		final int x=(int) (unit.position.x/(2*mapdesc.chunksize));
		final int y=(int) (unit.position.y/(2*mapdesc.chunksize));
		if(mapgrid[x][y]!=unit.parent){
			if (unit.parent!=null)
				unit.parent.removeunit(unit);
			mapgrid[x][y].addunit(unit);
			unit.parent=mapgrid[x][y];
		}
	}
	
	
	
}
