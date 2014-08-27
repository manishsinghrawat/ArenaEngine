package com.me.Loaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.model.data.ModelData;
import com.badlogic.gdx.utils.Json;

public class MapLoader {
	public MapData mapdesc;
	public TileData tilemodel;
	public DoodadData doodadmodel;
	public GObjectData unitmodel;

	public void load(String filename){
		Json json=new Json();
		mapdesc = json.fromJson(MapData.class,Gdx.files.internal("data/Maps/"+filename));
		tilemodel=json.fromJson(TileData.class,Gdx.files.internal("data/Models/"+mapdesc.tileset));
		doodadmodel=json.fromJson(DoodadData.class,Gdx.files.internal("data/Models/"+mapdesc.doodadset));
		unitmodel=json.fromJson(GObjectData.class,Gdx.files.internal("data/Models/"+mapdesc.modelset));
		
	//	tilemodel.load();
		//doodadmodel.load();
	}
	public void dispose(){
	//	tilemodel.dispose();
		//doodadmodel.dispose();
	}
}
