package com.me.RPGEngine;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

public abstract class GameObject {
	public GridNode parent;
	public GameWorld world;
	public ModelInstance model;
//	public int modelid;
	public float collisionsize;
	public Vector3 position;
	
//	public float x; //range form 0 to tilesize*2
//	public float y; //range from 0 to tilesize*2
//	public float height;
	
	public Matrix4 transform;
	
	protected AnimationController controller;
	
	private int curanim=-1;
	
	public GameObject(GameWorld world,int modelid){
		this.world=world;
		this.model=new ModelInstance(world.unitmodel.models.get(modelid).model);
		this.collisionsize=world.unitmodel.models.get(modelid).collisionsize;
		controller = new AnimationController(model);
		transform=new Matrix4().idt();
		position=new Vector3();
	}
	public void animate(int id){
		if(curanim==-1)
			controller.animate(model.animations.get(id).id, -1, null, 0.5f);
		else if(curanim!=id)
			controller.animate(model.animations.get(id).id, -1, null, 0.5f);
			//controller.animate(model.animations.get(id).id,-1, null, 100);
	}
	public void refresh(){
		world.register(this);
		position.z=parent.getheight(position.x, position.y, false);
	}
	public abstract void update(float deltatime);
	
/*	
 * public void refresh() {
		world.refreshobject(this);
		height=parent.getheight(x,y,false);
	}
	public void update(float deltatime){
		model.transform=model.transform.setToTranslation(x-world.mapdesc.size, -y+world.mapdesc.size, height*1.5f).mul(transform).rotate(1, 0, 0, 90);
		controller.update(deltatime);
	}
	*/	
	/*public void load(GameWorld gameWorld){
		this.world=gameWorld;
		this.model=new ModelInstance(world.unitmodel.models.get(modelid).model);
		controller = new AnimationController(model);
		transform=new Matrix4().idt();
		
		refresh();
		this.collisionsize=parent.world.unitmodel.models.get(modelid).collisionsize;
	}*/
}
