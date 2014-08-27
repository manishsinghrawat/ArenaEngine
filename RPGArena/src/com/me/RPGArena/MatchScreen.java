package com.me.RPGArena;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.me.LanNetworkUtils.PlayerStatusPacket;
import com.me.Network.LanNetwork;

public class MatchScreen extends AbstractScreen{

	private Table table;
	private Label welcomeLabel;
	private TextButton startbutton;
	private TextButton backbutton;
	private TextButton team2button;
	private TextButton team1button;
	Array<String> team1;
	Array<String> team2;
	private Label description;
	private boolean host;
	private List<String> list1;
	private List<String> list2;
	private LanNetwork netman;
	private PlayerStatusPacket status;
	
	//Server server;
	//Client client;

	/**
	 * constructor to create a local server
	 * @param game
	 * @param name
	 */
	public MatchScreen(RPGArena game,String name) {
		super(game);
		this.host=true;
		netman=(LanNetwork) game.getNetworkManager();
	//	status=new PlayerStatusPacket();
	//	status.addplayer(name, -1);
	    netman.setservermode(name);
	    status=netman.getstatus();
	}
	
	/**
	 * Constructor to create a client 
	 * @param game
	 * @param name
	 * @param string address of host
	 */
	public MatchScreen(RPGArena game,String name,String ipaddress) {
		super(game);
		this.host=false;
		netman=(LanNetwork) game.getNetworkManager();
		//status=new PlayerStatusPacket();
		netman.setclientmode(name,ipaddress);
	    status=netman.getstatus();
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

       // table.debug(); 
        
        // register the label "welcome"
        welcomeLabel = new Label( "Local Game", skin );
        
        /*   
        nameLabel = new Label( "Name", skin);
        namefield=new TextField("", skin);
        refreshbutton=new TextButton("Refresh", skin);
        refreshbutton.addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x, float y){
        	//	game.setScreen( game.getGameScreen() );
        	}
        } );
       */ 
        
        team1=new Array<String>();
        team1.add(new String("1 : Waiting for player"));
        team1.add(new String("2 : Waiting for player"));
        
        team2=new Array<String>();
        team2.add(new String("3 : Waiting for player"));
        team2.add(new String("4 : Waiting for player"));
        
        team1button = new TextButton( "Team1", skin);
        team1button.addListener(new ClickListener() {
         	@Override
         	public void clicked(InputEvent event, float x, float y){
         	//	game.setScreen( game.getGameScreen() );
         	}
         } );
        
        list1=new List<String>(skin);
        list1.getSelection().setRequired(false);
        list1.setItems(team1);

        list1.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                list2.setSelectedIndex(-1);
                return true;
            }
        });
        
        team2button = new TextButton( "Team2", skin);
        team2button.addListener(new ClickListener() {
         	@Override
         	public void clicked(InputEvent event, float x, float y){
         	//	game.setScreen( game.getGameScreen() );
         	}
         } );
        list2=new List<String>(skin);
        list2.getSelection().setRequired(false);
        list2.setItems(team2);
       
       list2.addListener(new InputListener() {
           public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
               list1.setSelectedIndex(-1);
               return true;
           }
       });
       
        description=new Label("",skin);
        
       startbutton = new TextButton( "Start Game", skin);
       startbutton.addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x, float y){
        		netman.startgame();
        	//	Screen screen=game.getScreen();
        	//	game.setScreen(game.getGameScreen());
        	//	screen.dispose();
        	}
        } );

       backbutton = new TextButton( "Back", skin);
       backbutton.addListener( new ClickListener() {
    	   @Override
    	   public void clicked(InputEvent event, float x, float y){
    		 //  if(host)
    		//	   netsys.server.close();
    		 //  else
    		//	   netsys.client.close();
    		  
    		   Screen screen=game.getScreen();
    		   game.setScreen( game.getJoinScreen() );
    		   screen.dispose();
    	   }
       } );
       if(!host){
    	   list1.setTouchable(Touchable.disabled);
    	   list2.setTouchable(Touchable.disabled);
    	   startbutton.setDisabled(true);
       }
       table.clear();

        //table construction
        table.left().top().pad(25);
        table.row().height(72).padBottom(20);
        table.add(welcomeLabel).center().colspan(3);
        table.row().height(100).padBottom(30);
        table.add(team1button).width(70).padRight(15);
        table.add(list1).width(550);
        table.row().height(100).padBottom(30);
        table.add(team2button).width(70).padRight(15);
        table.add(list2).width(550);
        table.row().expandY();
        table.add(description).colspan(3);
        table.row().height(100).padBottom(15);
        table.add(startbutton).height(40).width(150).right().colspan(3);
        table.row().height(100);
        table.add(backbutton).width(150).height(40).right().colspan(3);
        
    }
    
    @Override
    public void resize(int width,int height){
        super.resize(width, height);
    }
    
    @Override
    public void render(float delta){
    	synchronized(status){
    		if(status.id==null){
    			//netman.setdiscoverymode();
    			Screen screen=game.getScreen();
    			game.setScreen(game.getJoinScreen());
    			screen.dispose();
    		}
    		else
    		{
    			list1.setItems("1 : "+status.name[0],"2 : "+status.name[1]);
    			list2.setItems("3 : "+status.name[2],"4 : "+status.name[3]);
    			super.render(delta);
    		}
    	}
    	if(netman.started){
			Screen screen=game.getScreen();
			game.setScreen(game.getGameScreen());
			screen.dispose();
    	}
    }
    
    @Override
    public void dispose(){
        super.dispose();
    }
}
