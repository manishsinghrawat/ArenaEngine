package com.me.RPGArena;



import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.esotericsoftware.minlog.Log;
import com.me.Network.Network;
import com.me.gameutils.GameVars;
import com.me.gameutils.TimeInterface;

/**
 * The game's main class, called as application events are fired.
 */
public class RPGArena extends Game{
    // constant useful for logging
    public static final String LOG = RPGArena.class.getSimpleName();

    // whether we are in development mode
    public static final boolean DEV_MODE = false;

    // a libgdx helper class that logs the current FPS each second
    private FPSLogger fpsLogger;
    
    //private NetworkSystem network;
    
    //network manager for game
    //can be lan network or online network
    //this is configured using configuration screens
    private Network network;

    public RPGArena(TimeInterface clock) {
    	GameVars.Clock=clock;
	}
	//returns network manager
    public Network getNetworkManager(){
    	return network;
    }
    public void setNetworkManager(Network network){
    	this.network=network;
    }
    // Game-related methods
    public Screen getGameScreen() {
		// TODO Auto-generated method stub
		return new GameScreen(this);
	}

    public Screen getMenuScreen() {
    	// TODO Auto-generated method stub
    	return new MenuScreen(this);
    }
    public Screen getSplashScreen() {
    	// TODO Auto-generated method stub
    	return new SplashScreen(this);
    }
    @Override
    public void create()
    {
        Gdx.app.log( RPGArena.LOG, "Creating game on " + Gdx.app.getType() );
        // create the helper objects
    	Log.set(Log.LEVEL_DEBUG);
        fpsLogger = new FPSLogger();
  //      network=new NetworkSystem();
    }

    @Override
    public void resize(int width,int height ){
        super.resize( width, height );
      //  Gdx.app.log( RPGArena.LOG, "Resizing game to: " + width + " x " + height );
        
     // show the splash screen when the game is resized for the first time;
        // this approach avoids calling the screen's resize method repeatedly
        if( getScreen() == null ) {
                setScreen( new SplashScreen( this ) );
        }
    }

    @Override
    public void render()
    {
        super.render();
        if( DEV_MODE ) fpsLogger.log();
    }

    @Override
    public void pause()
    {
        super.pause();
      //  Gdx.app.log(RPGArena.LOG, "Pausing game" );

        // persist the profile, because we don't know if the player will come
        // back to the game
       // profileManager.persist();
    }

    @Override
    public void resume()
    {
        super.resume();
       // Gdx.app.log( RPGArena.LOG, "Resuming game" );
    }

    @Override
    public void setScreen(Screen screen )
    {
        super.setScreen( screen );
      //  Gdx.app.log(RPGArena.LOG, "Setting screen: " + screen.getClass().getSimpleName() );
    }

    @Override
    public void dispose(){
        super.dispose();
        if(this.getScreen()!=null){  
        	Gdx.app.log(RPGArena.LOG, "Disposing game" );
        	this.getScreen().dispose();
        }
        	
    }

    public Screen getMatchScreen(String name,String hostname) {
		return new MatchScreen(this,name,hostname);
	}
    public Screen getHostScreen(String name) {
		return new MatchScreen(this,name);
	}
	public Screen getJoinScreen() {
		return new JoinScreen(this);
	}
	
	
}
