package com.me.Loaders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.graphics.g3d.model.MeshPart;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.JsonReader;

public class TileData implements Disposable{
	public static class Data{
		public float vertices[];
		public short indices[];
		
		//Used for loading vertices and indices
		public String nodeid;
		//Used for loading vertices and indices
		public int rotation;
		
		public boolean ramp;
		public int[] hmap;
		public boolean pmap[];
		public float[] angle; 
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
		MeshPart part=null;
		for(final Data tmod:models){
			int idx=0;
			for(Node node:refmodel.nodes){
				if(node.id.equals(tmod.nodeid)){
					part=node.parts.get(0).meshPart;
				}
			}
			if(part==null)
				System.out.println("Invalid node id : "+tmod.nodeid);
			
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
			for(int i=0;i<max;i++){
				tmp.set(vertices[idx+min], vertices[idx+min+1], vertices[idx+min+2]).rotate(tmod.rotation, 0, 0, 1);
				tmod.vertices[idx] = tmp.x;
				tmod.vertices[idx+1] = tmp.y;
				tmod.vertices[idx+2] = tmp.z;
				tmp.set(vertices[idx+min+3], vertices[idx+min+4], vertices[idx+min+5]).rotate(tmod.rotation, 0, 0, 1);
				tmod.vertices[idx+3] = tmp.x;
				tmod.vertices[idx+4] = tmp.y;
				tmod.vertices[idx+5] = tmp.z;

				tmod.vertices[idx+6] = vertices[idx+min+6];
				tmod.vertices[idx+7] = vertices[idx+min+7];

				idx += vertexsize;
			}
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

	public void get(int modelid, float[] vertices, short[] indices, int voffset, int ioffset, int xpos, int ypos, float height) {
		final Data tset=models.get(modelid);
		for (int j = 0; j <tset.vertices.length ; j++) {
			switch(j%vertexsize){
				case 0:
					vertices[j+voffset] = tset.vertices[j]+xpos;
					break;
				case 1:
					vertices[j+voffset] = tset.vertices[j]+ypos;
					break;
				case 2:
					vertices[j+voffset] = tset.vertices[j]+height;
					break;
				default:
					vertices[j+voffset] = tset.vertices[j];
					break;
			}
		}
		for (int j = 0; j < tset.indices.length; j++)
			indices[j+ioffset] =(short)( tset.indices[j]+voffset/vertexsize);
	}
}
