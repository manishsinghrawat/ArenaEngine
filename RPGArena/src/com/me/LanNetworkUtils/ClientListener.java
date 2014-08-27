package com.me.LanNetworkUtils;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.me.GameData.StatusPacket;
import com.me.Network.LanClient;
import com.me.gameutils.GameVars;


public class ClientListener extends Listener{
	PlayerStatusPacket status;
	private String name;
	private LanClient client;
	
	public ClientListener(LanClient client,PlayerStatusPacket status){
		this.client=client;
		this.status=status;
		//this.name=name;
	}
	public void setname(String name){
		this.name=name;
	}
	@Override
	public void received (Connection connection, Object object) {
	//	System.out.println(object.getClass());
		if(object instanceof PlayerStatusPacket){
			synchronized(status){
				status.copyfrom((PlayerStatusPacket)object);
			}
		}else if(object instanceof StartGame){
			client.lanNetwork.started=true;
		}else if (object instanceof NotifyReady){
			client.lanNetwork.isready=true;
		}
		else if(object instanceof TimePacket){
			client.lanNetwork.zerotime=GameVars.Clock.currenttime();
		}
		else if(object instanceof StatusPacket){
			client.lanNetwork.networklistener.update((StatusPacket)object);
		}
	}
	@Override
	public void connected(Connection connection){
		SetupPacket packet=new SetupPacket(name);
		connection.sendTCP(packet);
	}
	@Override
	public void disconnected(Connection connection){
		synchronized(status){
			status.id=null;
			status.name=null;
		}
	}
}
