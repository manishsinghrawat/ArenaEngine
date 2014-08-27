package com.me.RPGEngine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.utils.Disposable;
import com.me.Loaders.MapLoader;
import com.me.Network.Network;

public class GameEngine implements Disposable{

	GameCamera camera;
	Environment environment;
	GameWorld world;
	InputSystem inputsys;
	RenderSystem rendersystem;
	GameHud hud;
	GameLogic logic;
	
	public GameEngine(){
		camera=new GameCamera();
		camera.update();
		
		hud=new GameHud();

		environment=new Environment();
		environment.set(ColorAttribute.createAmbient(0.15f, 0.15f, 0.15f,1f));
		environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, 5, 15, -10));
		
		//inputsys=new InputSystem(camera);
        Gdx.input.setInputProcessor(hud);
		//Gdx.input.setInputProcessor(new GestureDetector(inputsys));
        
		rendersystem=new RenderSystem();
		rendersystem.config(camera);
		rendersystem.config(environment);
		
	}

	public void load(String mapfile){
		MapLoader loader=new MapLoader();
		loader.load(mapfile);
		world=new GameWorld(loader);
		logic=new GameLogic(camera,world, hud);
	//	logic.settextman(textman);
		rendersystem.config(world);
	}
		
	public void settextman(TextManager textman){
		logic.settextman(textman);
	}
	public void setNetwork(Network network){
		logic.load(network);
	}
	public void update(TextManager textman){
		
//		camera.POI.add(hud.touchpad.getKnobPercentX()/5.0f, hud.touchpad.getKnobPercentY()/5.0f, 0.0f);
		hud.act(Gdx.graphics.getDeltaTime());
		logic.update(Gdx.graphics.getDeltaTime());
		camera.refresh();
		rendersystem.render(textman);
		hud.draw();
	}
	
	public void close(){
		
	}
	
	@Override
	public void dispose() {
		world.dispose();
	}

	public void resize(int width, int height) {
	    hud.getViewport().update(width, height, true);
	}	
}
