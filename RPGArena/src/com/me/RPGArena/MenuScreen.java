package com.me.RPGArena;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MenuScreen extends AbstractScreen{
	Table table;
	Label welcomeLabel;
	TextButton hostbutton;
	TextButton joinbutton;
	 TextButton onlinebutton;
	 TextButton quitbutton;
	private TextButton optionbutton;
    public MenuScreen(RPGArena game){
        super( game );
    }
    @Override
    public void show(){
    	super.show();
        // retrieve the skin (created on the AbstractScreen class)
        TextureAtlas atlas=new TextureAtlas("data/Menu/menu.atlas");
        Skin skin = super.getSkin();
        
        //add atlas to skin
        skin.addRegions(atlas);
        
        //load custom menu style
        skin.load(Gdx.files.internal("data/Menu/menuskin.json"));
        
        // create the table actor
        table=super.getTable();
        
        //set the skin for table
        table.setSkin(skin);

        // add the table to the stage and retrieve its layout
       // stage.addActor( table ); already added

    //    table.debug(); 
        
        
        // register the label "welcome"
        welcomeLabel = new Label( "RPG Arena", skin);

        /*
        hostbutton = new TextButton( "Host A Game", skin);
       hostbutton.addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x, float y){
        		Screen screen=game.getScreen();
        		game.setScreen( game.getMatchScreen() );
        		screen.dispose();
        	}
        } );
*/
       joinbutton = new TextButton( "Local Game", skin);
        joinbutton.addListener( new ClickListener() {
            @Override
        	public void clicked(InputEvent event, float x, float y){
            	Screen screen=game.getScreen();
        		game.setScreen( game.getJoinScreen() );
        		screen.dispose();
        	}
        } );

        
        onlinebutton = new TextButton( "Play Online", skin);
        onlinebutton.addListener( new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x, float y){
        		//game.setScreen( game.getGameScreen() );
        	}
        } );
        optionbutton = new TextButton( "Options", skin);
        optionbutton.addListener( new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x, float y){
        		//game.setScreen( game.getGameScreen() );
        	}
        } );
        
        quitbutton = new TextButton( "Quit", skin);
        quitbutton.addListener( new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x, float y){
        		Gdx.app.exit();
        	}
        } );
        
        table.clear();
        
        //table construction
        table.right().pad(25);
        table.add(welcomeLabel).center().height(180).padBottom(24);
        table.row();
        table.add(onlinebutton).width(420).height(72).padBottom(24);
        table.row();
        table.add(joinbutton).width(420).height(72).padBottom(24);
        table.row();
        table.add(optionbutton).width(420).height(72).padBottom(24);
        table.row();
        table.add(quitbutton).width(420).height(72).expandY().bottom().padBottom(20);
    }

    @Override
    public void resize(int width,int height){
        super.resize(width, height);
    }
    
    @Override
    public void dispose(){
        super.dispose();
    }
}
