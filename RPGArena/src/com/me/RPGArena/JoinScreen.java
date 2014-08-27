package com.me.RPGArena;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.me.LanNetworkUtils.DiscoveryPacket;
import com.me.Network.LanNetwork;

public class JoinScreen extends AbstractScreen{

	
	private Table table;
	private Label welcomeLabel;
	private Label nameLabel;
	private TextField namefield;
	Array<String> team1;
	Array<String> team2;
	private Label description;
	private List<DiscoveryPacket> gamelist;
	private Array<DiscoveryPacket> hostlist;
	private TextButton hostbutton;
	private TextButton joinbutton;
	private TextButton backbutton;
	private LanNetwork netman;
	
	public JoinScreen(RPGArena game) {
		super(game);
		netman=new LanNetwork();
		game.setNetworkManager(netman);
		netman.setdiscoverymode();
		hostlist=netman.gethostlist();
		
	//	hosts=new Array<Host>();
	//	Client client = new Client();
	//	client.getKryo().register(DiscoveryResponsePacket.class);
		//	client.start();
	//	finding=true;
	//	new Thread(this).start();
    	//client.connect(5000, "192.168.0.4", 54555, 54777);
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
        welcomeLabel = new Label( "Join Local Game", skin );
     
        nameLabel = new Label( "Name", skin);
        namefield=new TextField("Default Player", skin);
      
       // games=new Array<String>();
       // games.add(new String("1 : coolpuneet (2 Slots)"));
       // games.add(new String("2 : sasukeuchiha (0 Slots)"));
        
        gamelist=new List<DiscoveryPacket>(skin);
        gamelist.getSelection().setRequired(false);
	//	netman.startsearch(gamelist.getItems());
        
        //gamelist.setItems(netsys.hostlist);
        gamelist.addListener(new ClickListener(){
         	@Override
         	public void clicked(InputEvent event, float x, float y){
         		
         	}
        });
        
    
        description=new Label("",skin);
        
      joinbutton = new TextButton( "Join Game", skin);
      joinbutton.addListener(new ClickListener() {
        	@Override
        	public void clicked(InputEvent event, float x, float y){
        		
				try {
					//System.out.println(gamelist.getSelected().ipaddress);
					Screen screen=game.getScreen();
					game.setScreen(game.getMatchScreen(namefield.getText(),gamelist.getSelected().ipaddress.substring(1)));
					screen.dispose();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		
        	}
       });

       hostbutton = new TextButton( "Host Game", skin);
       hostbutton.addListener( new ClickListener() {
    	   @Override
    	   public void clicked(InputEvent event, float x, float y){
    		   	Screen screen=game.getScreen();
       			game.setScreen( game.getHostScreen(namefield.getText()));
       			screen.dispose();
    	   }
       } );

       backbutton = new TextButton( "Back", skin);
        backbutton.addListener( new ClickListener() {
            @Override
        	public void clicked(InputEvent event, float x, float y){
            	Screen screen=game.getScreen();
        		game.setScreen( game.getMenuScreen());
        		screen.dispose();
        	}
        } );

        table.clear();
        
        //table construction
        table.left().top().pad(25);
        table.row().height(72).padBottom(20);
        table.add(welcomeLabel).center().colspan(3);
        
        table.row().height(30).padBottom(2);
        table.add(nameLabel).left().colspan(3);
        
        table.row().height(30).padBottom(30);
        table.add(namefield).left().colspan(3).fill();
        
        table.row().padBottom(30);
        table.add(gamelist).height(250).colspan(3).fill();
        
        table.row().expandY();
        table.add(description).colspan(3);
        
        table.row().height(100).padBottom(10);
        table.add(joinbutton).height(40).width(150).right().padRight(20);
        table.add(hostbutton).height(40).width(150).right().padRight(20);
        table.add(backbutton).width(150).height(40).right();
    }
    
    @Override
    public void render(float delta){
    	//synchronize discovery and render thread
    	//synchronized on network system object to allow editing underlying host array on the go.
    	
    	synchronized(hostlist){
    		//check if network manager is in discovery mode
    		if(hostlist!=null)
    		gamelist.setItems(hostlist);
    		
    		super.render(delta);
    	}
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
