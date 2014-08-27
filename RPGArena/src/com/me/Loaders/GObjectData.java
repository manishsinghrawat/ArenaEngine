package com.me.Loaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonReader;

public class GObjectData {
	public static class Data{
		public Model model;
		
		//Used for loading model
		public String modelfile;
		
		public float collisionsize;
	}
	public Array<Data> models;
	
	public void load(){
		G3dModelLoader loader=new G3dModelLoader(new JsonReader(), new InternalFileHandleResolver());
		
		for(final Data data:models)
			data.model=loader.loadModel(Gdx.files.internal("data/Models/"+data.modelfile));
	//	models.get(0).model.materials.get(0).set(new BlendingAttribute(true,0.5f) );
	}

	public void dispose() {
		for(final Data data:models)
			data.model.dispose();
	}


}
