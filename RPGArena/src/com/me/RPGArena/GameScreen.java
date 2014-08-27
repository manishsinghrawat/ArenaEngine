package com.me.RPGArena;

//import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.me.RPGEngine.GameEngine;
import com.me.RPGEngine.TextManager;

public class GameScreen implements Screen {
	public TextManager textman;
	GameEngine gameengine;
	RPGArena game;
	
	public GameScreen(RPGArena game) {
		this.game=game;
	}
	
	@Override
	public void show() {
		//	Gdx.graphics.setDisplayMode(Gdx.graphics.getDesktopDisplayMode().width, Gdx.graphics.getDesktopDisplayMode().height, true);
		//	Gdx.graphics.setVSync(true);
			textman=new TextManager();
			gameengine=new GameEngine();
			gameengine.load("sfarena1.map");
			gameengine.setNetwork(game.getNetworkManager());
			gameengine.settextman(textman);
			game.getNetworkManager().notifyloaded();
	}

	@Override
	public void render(float delta) {
		
		//Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        
        if(game.getNetworkManager().isready){
        	textman.begin();
        	textman.add("FPS", Gdx.graphics.getFramesPerSecond());
        	gameengine.update(textman);
        	textman.end();
        }
	}
	@Override
	public void resize(int width,int height){
		gameengine.resize(width,height);
		textman.resize(width,height);
	}
	@Override
	public void dispose() {
		textman.dispose();
		gameengine.dispose();
	}


	
	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

}
