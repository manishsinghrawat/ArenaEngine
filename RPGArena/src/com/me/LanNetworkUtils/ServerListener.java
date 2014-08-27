package com.me.LanNetworkUtils;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.me.GameData.StatusPacket;
import com.me.Network.LanServer;

public class ServerListener extends Listener{
	
	private PlayerStatusPacket status;
	private LanServer server;
	public boolean[] loadmap;
	
	public ServerListener(LanServer server,PlayerStatusPacket status){
		this.server=server;
		this.status=status;
		loadmap=new boolean[status.size];
	}

	@Override
	public void received (Connection connection, Object object) {
		if(object instanceof SetupPacket){
			synchronized(status){
				status.addplayer(((SetupPacket)object).name,connection.getID());
				
			}
			server.sendToAllTCP(status);
		}
		else if(object instanceof NotifyLoaded){
			for(int i=0;i<status.size;i++){
				if(status.id[i]==connection.getID()){
					loadmap[i]=true;
				}
			}
		}
		else if(object instanceof StatusPacket){
			server.lanNetwork.networklistener.update((StatusPacket)object);
			server.sendToAllExceptTCP(connection.getID(), object);
		}
	}
	public boolean loaded(){
		for(int i=0;i<status.size;i++){
			if (!(loadmap[i] || status.id[i]==0 )){
				return false;
			}
		//	System.out.println("Player "+i+" loaded");
		}
		return true;
	}
	@Override
	public void disconnected(Connection connection){
		synchronized(status){
			status.removeplayer(connection.getID());
		}
		server.sendToAllTCP(status);
	}
	
}
