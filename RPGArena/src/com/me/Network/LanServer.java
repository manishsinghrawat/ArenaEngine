package com.me.Network;

import java.io.IOException;

import com.esotericsoftware.kryonet.Server;
import com.me.LanNetworkUtils.PlayerStatusPacket;
import com.me.LanNetworkUtils.ServerDiscovery;
import com.me.LanNetworkUtils.ServerListener;


public class LanServer extends Server {
	public enum ServerMode{
		DISCOVERABLE,UNDISCOVERABLE
	}
	
	ServerMode curmode;
	ServerDiscovery serverdiscoveryhandler;
	PlayerStatusPacket status;
	ServerListener listener;
	public LanNetwork lanNetwork;
	
	public LanServer(LanNetwork lanNetwork){
		super();
		this.lanNetwork=lanNetwork;
		serverdiscoveryhandler=new ServerDiscovery();
		curmode=ServerMode.DISCOVERABLE;
		serverdiscoveryhandler.enable();
		this.setDiscoveryHandler(serverdiscoveryhandler);
		
		status=new PlayerStatusPacket();
		listener=new ServerListener(this,status);
		this.addListener(listener);
	}
	
	public void startserver(String name){
		serverdiscoveryhandler.setpacket("GameHost", name);
		status.reset();
		status.addplayer(name, -1);
		this.start();
		try {
			this.bind(LanSettings.tcpport, LanSettings.udpport);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void stopserver(){
		this.stop();
	//	status.id=null;
	//	status.name=null;
	}
}
