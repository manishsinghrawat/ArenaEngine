package com.me.RPGEngine;

public class GameHero extends GameObject {

	public float angle;
	
	public GameHero(GameWorld world, int modelid) {
		super(world, modelid);
		
	}

	public void moveto(float x,float y){
		position.x=x;
		position.y=y;
	}
	

	@Override
	public void update(float deltatime) {
		model.transform=model.transform.setToTranslation(position.x-world.mapdesc.size, -position.y+world.mapdesc.size, position.z*1.5f).mul(transform).rotate(1, 0, 0, 90);
		controller.update(deltatime);
	}

	
}
