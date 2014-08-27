package com.me.Loaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.graphics.g3d.model.MeshPart;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonReader;

public class DoodadData {
	public static class Data{
		public float vertices[];
		public short indices[];
		
		//Used for loading vertices and indices
		public int nodeid;
		
		public boolean pathable;
		public boolean size;
	}
	public Array<Data> models;
	public String modelfile;
	
	public Material material;
	public VertexAttributes attributes;
	public int vertexsize;
	
	private Model refmodel;
	public void load(){
		G3dModelLoader loader=new G3dModelLoader(new JsonReader(), new InternalFileHandleResolver());
		refmodel=loader.loadModel(Gdx.files.internal("data/Models/"+modelfile));
		
		//support only one mesh,material and vertex attribute 
		Mesh mesh=refmodel.meshes.get(0);
		material=refmodel.materials.get(0);
		attributes=mesh.getVertexAttributes();
		
		vertexsize=mesh.getVertexAttributes().vertexSize/4;
		float[] vertices=new float[mesh.getNumVertices()*vertexsize];
		short[] indices=new short[mesh.getNumIndices()];
		mesh.getIndices(indices);
		mesh.getVertices(vertices);
		
		Vector3 tmp =new Vector3();
		for(final Data tmod:models){
			final MeshPart part=refmodel.meshParts.get(tmod.nodeid);
			tmod.indices=new short[part.numVertices];
			int min=vertices.length/vertexsize,max=0;
			for(int i=part.indexOffset;i<part.indexOffset+part.numVertices;i++){	
				if(indices[i]<min)
					min=indices[i];
				if(indices[i]>max)
					max=indices[i];
			}
			for(int i=0;i<tmod.indices.length;i++){
				tmod.indices[i]=(short)(indices[i+part.indexOffset]-min);
			}
			max=max-min+1; 		//max now stores length(number of vertices not actual size in floats)
			min*=vertexsize;	//min now stores offset in vertices array(not vertex number)
			tmod.vertices=new float[max*vertexsize];
			for(int i=0;i<tmod.vertices.length;i++)
				tmod.vertices[i]=vertices[i+min];			
		}
		//mesh.dispose();
	}
	
	public void dispose(){
		if(refmodel!=null)
			refmodel.dispose();
	}

	public int getvertexsize(int modelid) {
		return models.get(modelid).vertices.length;
	}
	public int getindexsize(int modelid) {
		return models.get(modelid).indices.length;
	}

	//convert this system to accept doodad and return
	public void get(int modelid, float[] vertices, short[] indices, int voffset, int ioffset,int rotation, int xpos, int ypos, float height) {
		final Data tset=models.get(modelid);

		Vector3 tmp =new Vector3();
		for (int j = 0; j <tset.vertices.length ; j+=vertexsize) {

				tmp.set(tset.vertices[j], tset.vertices[j+1], tset.vertices[j+2]).rotate(rotation, 0, 0, 1);
				vertices[j+voffset] = tmp.x+xpos;
				vertices[j+voffset+1] = tmp.y+ypos;
				vertices[j+voffset+2] = tmp.z+height;
				tmp.set(tset.vertices[j+3], tset.vertices[j+4], tset.vertices[j+5]).rotate(rotation, 0, 0, 1);
				vertices[j+voffset+3] = tmp.x;
				vertices[j+voffset+4] = tmp.y;
				vertices[j+voffset+5] = tmp.z;
				
				vertices[j+voffset+6] = tset.vertices[j+6];
				vertices[j+voffset+7] = tset.vertices[j+7];
			
		}
		for (int j = 0; j < tset.indices.length; j++)
			indices[j+ioffset] =(short)( tset.indices[j]+voffset/vertexsize);
	}
}
