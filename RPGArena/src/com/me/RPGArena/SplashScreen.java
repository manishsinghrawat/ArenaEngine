package com.me.RPGArena;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

/**
 * Shows a splash image and moves on to the next screen.
 */
public class SplashScreen extends AbstractScreen {
    private Texture splashTexture;
    Image splashImage;
    public SplashScreen(RPGArena game){
        super( game );
    }

    @Override
    public void show(){
        super.show();

        // load the texture with the splash image
        splashTexture = new Texture( "data/libgdx.png" );

        // set the linear texture filter to improve the image stretching
        splashTexture.setFilter( TextureFilter.Linear, TextureFilter.Linear );
         // let's make sure the stage is clear
        stage.clear();
        // here we create the splash image actor and set its size
        splashImage = new Image(splashTexture);
        // this is needed for the fade-in effect to work correctly; we're just
        // making the image completely transparent
        splashImage.setColor(1.0f, 1.0f, 1.0f, 0.0f);;   
        // configure the fade-in/out effect on the splash image
        splashImage.addAction(Actions.sequence(/*Actions.fadeIn(1.0f),
        		Actions.delay(1.25f), Actions.fadeOut(1.0f),*/ new Action() {
        	public boolean act(float delta) {
        		// when the image is faded out, move on to the next
        		// screen
        		Screen screen=game.getScreen();
        		game.setScreen(game.getMenuScreen());
        		screen.dispose();
        		return true; // returning true consumes the event
        	}
        }));
        // and finally we add the actors to the stage, on the correct order
        stage.addActor( splashImage );
    }

    @Override
    public void resize(int width,int height){
        super.resize( width, height);
        splashImage.setWidth(width);
        splashImage.setHeight(height);
    }

    @Override
    public void dispose(){
        super.dispose();
        splashTexture.dispose();
    }
}