package com.me.RPGEngine;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.me.GameData.GameCommand;
import com.me.GameData.StatusPacket;
import com.me.LanNetworkUtils.PlayerStatusPacket;
import com.me.Network.Network;
import com.me.Network.NetworkListener;
import com.me.gameutils.GameVars;

public class GameLogic implements NetworkListener{
	GameCamera camera;
	GameWorld world;
	GameHud hud;
	GameHero main;
	Network network;
	static public int logicframetime=1000/20;
	//TODO replace with map
	Array<PlayerStatus> gamesetup;
	
	PlayerStatus thisplayer;
	float speed=0.005f; //0-1
	
	public long lastupdate;
	private StatusPacket thisstatus;
	
	private TextManager textman;
	
	/* variables to avoid unnecessary garbage collection
	 *  tmp for update function
	 * tmp1 for refrsh function
	 * 
	 */
	private Vector2 tmp,tmp1;
	
	public GameLogic(GameCamera camera,GameWorld world, GameHud hud) {
		this.camera=camera;
		this.world=world;
		this.hud=hud;
		lastupdate=0;

		tmp=new Vector2();
		tmp1=new Vector2();
			//	main=new GameHero(world,0);
	//	main.moveto(50, 50);
	//	main.refresh();
	//	world.getglobal(main.position,camera.POI);
	//	main.animate(2);
	}
	
	public void update(float deltatime){
		
		textman.add("time", GameVars.Clock.currenttime()-network.zerotime);
		
		//TODO implement turnrate
		if(lastupdate==0 || GameVars.Clock.currenttime()-lastupdate>logicframetime){
			
			thisstatus.xpercent=hud.touchpad.getKnobPercentX();
			thisstatus.ypercent=-hud.touchpad.getKnobPercentY();
			
			tmp.set(thisstatus.xpercent,thisstatus.ypercent);
			
			refresh(tmp,thisstatus.xpos,thisstatus.ypos,thisplayer.hero.collisionsize);
			
			

			thisstatus.xpos+=speed*tmp.x*(GameVars.Clock.currenttime()-lastupdate);
			thisstatus.ypos+=speed*tmp.y*(GameVars.Clock.currenttime()-lastupdate);
			
			network.update(thisstatus);
			lastupdate=GameVars.Clock.currenttime();
		}
		
			textman.add("direction",tmp);
		
		//TODO smoothen transition and try to remove spikes
		synchronized(gamesetup){
			for(final PlayerStatus player:gamesetup ){
				if(!player.messagequeue.isEmpty()){
					//add code to handle situation if packets arrive out of order
					if(GameVars.Clock.currenttime()>network.zerotime+player.messagequeue.peek().timetag){
						player.currentpacket=player.messagequeue.remove();
					}		
				}
				if(player.currentpacket!=null){		
					//System.out.println(player.currentpacket.xpercent +" "+player.currentpacket.ypercent);
					if(Math.abs(player.currentpacket.xpercent)<0.001f && Math.abs(player.currentpacket.ypercent)<0.001f){
						player.hero.animate(0);
					}
					else{
						player.hero.transform.idt().rotate(new Vector3(0,0,1),(float) ((180/Math.PI)*Math.atan2(-player.currentpacket.ypercent,player.currentpacket.xpercent)));
						player.hero.animate(2);
					}
					//System.out.println(System.currentTimeMillis()-network.zerotime-player.currentpacket.timetag);
					
					tmp.set(player.currentpacket.xpercent, player.currentpacket.ypercent);
					refresh(tmp,player.currentpacket.xpos,player.currentpacket.ypos,player.hero.collisionsize);
					player.hero.moveto(player.currentpacket.xpos+speed*tmp.x*(GameVars.Clock.currenttime()-network.zerotime-player.currentpacket.timetag), player.currentpacket.ypos+speed*tmp.y*(GameVars.Clock.currenttime()-network.zerotime-player.currentpacket.timetag));
					
					player.hero.refresh();

				}
			}
			world.getglobal(thisplayer.hero.position, camera.POI);
		}
		//		if(hud.power1.isPressed()){

		//		network.send("yo");
		//}
	}
	public void settextman(TextManager textman){
		this.textman=textman;
	}
	public void load(Network network) {
		this.network=network;
		gamesetup=new Array<PlayerStatus>();
		PlayerStatusPacket status = network.getstatus();
		thisstatus=new StatusPacket();
		thisstatus.id=network.id;
		for(int i=0;i<status.size;i++){
			if(status.id[i]!=0){
				gamesetup.add(new PlayerStatus(status.name[i],status.id[i],new GameHero(world,0)));
			}
			if(status.id[i]==network.id){
				thisplayer=gamesetup.get(gamesetup.size-1);
				thisstatus.xpos=thisplayer.hero.position.x;
				thisstatus.ypos=thisplayer.hero.position.y;	
			}
		}
		network.setlistener(this);
	}

	@Override
	public void update(StatusPacket packet) {
		synchronized(gamesetup){
			for(final PlayerStatus status:gamesetup){
				if(status.id==packet.id){
					status.messagequeue.add(packet);
				}
			}
		}
	}

	@Override
	public void execute(GameCommand command) {
		// TODO Auto-generated method stub
		
	}
	
	
	//TODO  return vector with direction for movement
	//reuse twice for self as well as reciever
	public void refresh(Vector2 dir,float x,float y,float size){
		final int xx=(int) x;
		final int yy=(int) y;
		//System.out.println(size);
		if(dir.x>-0.001f && dir.y>-0.001f){
			if(world.pathable[xx+1][yy] && world.pathable[xx][yy+1]){
				if((1+xx-x<size && 1+yy-y<size) && !world.pathable[xx+1][yy+1]){
					tmp1.set(1+xx-x, 1+yy-y).nor();
					dir.mulAdd(tmp1, -dir.dot(tmp1));
				}
				return;
			}
			if(1+xx-x<size && !world.pathable[xx+1][yy]){
				dir.x=0.0f;
			}
			if(1+yy-y<size && !world.pathable[xx][yy+1]){
				dir.y=0.0f;
			}
			
			
		}else if(dir.x<0.001f && dir.y>-0.001f){
			if(world.pathable[xx-1][yy] && world.pathable[xx][yy+1]){
				if((x-xx<size && 1+yy-y<size) && !world.pathable[xx-1][yy+1]){
					tmp1.set(x-xx, 1+yy-y).nor();
					dir.mulAdd(tmp1, -dir.dot(tmp1));
				}
				return;
			}
			if(x-xx<size && !world.pathable[xx-1][yy]){
				dir.x=0.0f;
			}
			if(1+yy-y<size && !world.pathable[xx][yy+1]){
				dir.y=0.0f;
			}
		}else if(dir.x<0.001f && dir.y<0.001f){
			if(world.pathable[xx-1][yy] && world.pathable[xx][yy-1]){
				if((x-xx<size && y-yy<size) && !world.pathable[xx-1][yy-1]){
					tmp1.set(x-xx, y-yy).nor();
					dir.mulAdd(tmp1, -dir.dot(tmp1));
				}
				return;
			}
			if(x-xx<size && !world.pathable[xx-1][yy]){
				dir.x=0.0f;
			}
			if(y-yy<size && !world.pathable[xx][yy-1]){
				dir.y=0.0f;
			}
		}else if(dir.x>-0.001f && dir.y<0.001f){
			if(world.pathable[xx+1][yy] && world.pathable[xx][yy-1]){
				if((1+xx-x<size && y-yy<size) && !world.pathable[xx+1][yy-1]){
					tmp1.set(1+xx-x,y-yy).nor();
					dir.mulAdd(tmp1, -dir.dot(tmp1));
				}
				return;
			}
			if(1+xx-x<size && !world.pathable[xx+1][yy]){
				dir.x=0.0f;
			}
			if(y-yy<size && !world.pathable[xx][yy-1]){
				dir.y=0.0f;
			}
			
		}
	}
}
