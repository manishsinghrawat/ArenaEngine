package com.me.RPGEngine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Vector3;

public class GameCamera extends PerspectiveCamera{
	public Vector3 POI;
	public float angle;
	public float zoom;
	public float height;
	public float distance;
	
	public GameCamera(){
		super(67,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		POI=new Vector3(0,0,0);
		reset();
		refresh();
	}
	public void reset(){
		angle=0.0f;
		height=12.0f;
		distance=6.0f;
	}
	public void refresh(){
		final double angr=(angle*Math.PI)/180;
		final float x=distance*(float) Math.sin(angr);
		final float y=distance*(float) Math.cos(angr);
		
		position.set(x,-y,height).add(POI);
		direction.set(-x,y,-height).nor();
		up.set(-x,y,height).nor();
		near=1;
		far=50;
		super.update();
	}
}
