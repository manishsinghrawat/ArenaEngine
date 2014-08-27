package com.me.RPGEngine;

import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;

public class InputSystem implements GestureListener{

	GameCamera camera;
	
	public InputSystem(GameCamera camera){
		this.camera=camera;
	}
	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		// TODO Auto-generated method stub
		
		return false;
	}

	@Override
	public boolean longPress(float x, float y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		// TODO Auto-generated method stub
		/*
		final float angf=camera.angle-deltaX/10;
		final float heif=camera.height+deltaY/30;
		
		if (angf<90.0 && angf>-90.0)
			camera.angle=angf;
		if (heif<15.0 && heif>3.0)
			camera.height=heif;
		 *
		 */
		camera.POI.sub(deltaX/30,-deltaY/30 , 0);
		return false;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
			Vector2 pointer1, Vector2 pointer2) {
		// TODO Auto-generated method stub
		return false;
	}
	public void update() {
		// TODO Auto-generated method stub
		
	}

}
