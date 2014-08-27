package com.me.RPGEngine;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class GameHud extends Stage{
	Skin skin;
	Touchpad touchpad;
	Button power1;
	public GameHud(){
		super();
		//this.camera=camera;
		TextureAtlas atlas=new TextureAtlas(Gdx.files.internal("data/Hud/Hud.atlas"));
		skin = new Skin(Gdx.files.internal("data/Hud/hudskin.json"),atlas);

		touchpad=new Touchpad(10,skin);
        touchpad.setBounds(10, 10, 200, 200); //fix later for variable size
        //touchpad.addListener(logicsystem);
		this.addActor(touchpad);
		
		power1=new Button(skin);
		
	    power1.setBounds(Gdx.graphics.getWidth()  - 200f, 200f, 50, 50);
	    
	   // power1.addListener(logicsystem);
	        
	    this.addActor(power1);
	}
	
	public void act(float deltatime){
		super.act(deltatime);
	}
	public void dispose(){
		skin.dispose();
	}
}
