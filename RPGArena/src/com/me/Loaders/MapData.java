package com.me.Loaders;

import com.badlogic.gdx.utils.Array;

public class MapData {
	public int size;
	public int chunksize;
	public Array<Tile> terrain;
	public Array<Doodad> doodads;
	
	//fields for loading
	public String tileset;
	public String doodadset;
	public String modelset;
}
