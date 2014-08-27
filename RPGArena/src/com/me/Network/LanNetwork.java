package com.me.Network;

import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;
import com.me.GameData.GameCommand;
import com.me.GameData.StatusPacket;
import com.me.LanNetworkUtils.DiscoveryPacket;
import com.me.LanNetworkUtils.NotifyLoaded;
import com.me.LanNetworkUtils.NotifyReady;
import com.me.LanNetworkUtils.PlayerStatusPacket;
import com.me.LanNetworkUtils.SetupPacket;
import com.me.LanNetworkUtils.StartGame;
import com.me.LanNetworkUtils.TimePacket;
import com.me.gameutils.GameVars;

/**
 * this class implements lan network handler
 * depending on situation this class encapsulates a lanserver or lanclient
 * @author star
 *
 */
public class LanNetwork extends Network{

	public LanClient client;
	public LanServer server;
	
	public enum Mode {
		IDLE,DISCOVERY,CLIENT,SERVER
	}
	public Mode currentmode;
	
	public LanNetwork(){
		//prepare both server and client for running
		// service will be started when required
		client=new LanClient(this);
		server=new LanServer(this);
		register(client);
		register(server);
		currentmode=Mode.IDLE;
	}
	public void register (EndPoint endPoint) {
		Kryo kryo = endPoint.getKryo();
		kryo.register(DiscoveryPacket.class);
		kryo.register(String[].class);
		kryo.register(int[].class);
		kryo.register(PlayerStatusPacket.class);
		kryo.register(SetupPacket.class);
		kryo.register(StatusPacket.class);
		kryo.register(TimePacket.class);
		kryo.register(StartGame.class);
		kryo.register(NotifyLoaded.class);
		kryo.register(NotifyReady.class);
	}

	public void setdiscoverymode(){
		if(currentmode!=Mode.DISCOVERY) setidlemode();
		currentmode=Mode.DISCOVERY;
		client.startdiscovery();
		
	}
	public void setclientmode(String name, String ipaddress){
		if(currentmode!=Mode.CLIENT) setidlemode();
		currentmode=Mode.CLIENT;
		client.startclient(name,ipaddress);
		this.id=client.getID();
	}
	public void setservermode(String name){
		if(currentmode!=Mode.SERVER) setidlemode();
		currentmode=Mode.SERVER;
		server.startserver(name);
		this.id=-1;
	}
	public void setidlemode(){
		switch(currentmode){
		case IDLE:
			break;
		case DISCOVERY:
			client.stopdiscovery();
			currentmode=Mode.IDLE;
			break;
		case CLIENT:
			client.stopclient();
			currentmode=Mode.IDLE;
			break;
		case SERVER:
			server.stopserver();
			currentmode=Mode.SERVER;
			break;
		default:
			break;
		}
	}
	public Array<DiscoveryPacket> gethostlist() {
		if(currentmode==Mode.DISCOVERY)
			return client.hostlist;
		else{
			System.out.println("Not in discovery mode");
			return null;
		}
	}
	@Override
	public PlayerStatusPacket getstatus(){
		if(currentmode==Mode.SERVER){
			return server.status;
		}else if (currentmode==Mode.CLIENT){
			return client.status;
		}else{
			System.out.println("Not in Setup Mode");
			return null;
		}
			
	}
	@Override
	public void startgame(){
		if(currentmode==Mode.SERVER){
			
			server.sendToAllTCP(new StartGame());
			new Thread(new Runnable(){

				@Override
				public void run() {
					while(!isready){
						if(server.listener.loaded()){
							synchrotime();
							server.sendToAllTCP(new NotifyReady());
							isready=true;
						}
					}
				}
				
			}).start();
			started=true;
		}else{
			System.out.println("Not in server mode");
		}
	}
	@Override
	public void notifyloaded(){

		if(currentmode==Mode.CLIENT){
			NotifyLoaded pack=new NotifyLoaded();
			client.sendTCP(pack);
		}
		if(currentmode==Mode.SERVER){
			server.listener.loadmap[0]=true;
		}else{
			System.out.println("Not in bound");
		}
	}
	
	@Override
	public void synchrotime(){
		if(currentmode==Mode.SERVER){
			TimePacket tpack=new TimePacket();
			tpack.zerotime=GameVars.Clock.currenttime();
			this.zerotime=tpack.zerotime;
			server.sendToAllTCP(tpack);
		}else{
			System.out.println("Not in server mode");
		}
	}
	@Override
	public void update(StatusPacket packet) {
		this.networklistener.update(new StatusPacket(packet));
		packet.timetag=GameVars.Clock.currenttime()-this.zerotime+LanSettings.packetdelay;
		packet.id=this.id;
		if(currentmode==Mode.CLIENT){
			client.sendTCP(packet);
		}
		else if(currentmode==Mode.SERVER){
			server.sendToAllTCP(packet);
		}
	}
	@Override
	public void execute(GameCommand command) {
		// TODO Auto-generated method stub
	}
	@Override
	public void setname(String name) {
		// TODO Auto-generated method stub
		
	}
}
