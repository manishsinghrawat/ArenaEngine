package com.me.Network;

import java.io.IOException;

import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.kryonet.Client;
import com.me.LanNetworkUtils.ClientDiscovery;
import com.me.LanNetworkUtils.ClientDiscoveryThread;
import com.me.LanNetworkUtils.ClientListener;
import com.me.LanNetworkUtils.DiscoveryPacket;
import com.me.LanNetworkUtils.PlayerStatusPacket;

public class LanClient extends Client{
	public enum Clientmode{
		UNBOUND,SEARCH,BOUND
	}
	Clientmode curmode;
	public Array<DiscoveryPacket> hostlist;
	ClientDiscoveryThread discthread;
	public LanNetwork lanNetwork;
	
	
	PlayerStatusPacket status;
	ClientListener listener;
	
	public LanClient(LanNetwork lanNetwork){
		super();
		this.lanNetwork=lanNetwork;
		hostlist=new Array<DiscoveryPacket>();
		curmode=Clientmode.UNBOUND;

		status=new PlayerStatusPacket();
		listener=new ClientListener(this,status);
		this.addListener(listener);
		
		discthread=new ClientDiscoveryThread(this,hostlist);
		ClientDiscovery clientdiscoveryhandler=new ClientDiscovery(hostlist);
		this.setDiscoveryHandler(clientdiscoveryhandler);
	}
	
	public void startdiscovery(){
		if(curmode==Clientmode.SEARCH)
			return;
		else if(curmode==Clientmode.BOUND){
			System.out.println("Client busy");
			return;
		}
		else if(curmode==Clientmode.UNBOUND){
			curmode=Clientmode.SEARCH;
			discthread.start();
		}
	}
	public void stopdiscovery(){
		discthread.stop();
		curmode=Clientmode.UNBOUND;
	}
	
	public void startclient(String name, String ipaddress){
		if(curmode==Clientmode.SEARCH){
			System.out.println("Client busy");
			return;
		}else if(curmode==Clientmode.BOUND){
			return;
		}
		else if(curmode==Clientmode.UNBOUND){
			this.start();
			try {
				listener.setname(name);
				this.connect(2000, ipaddress, LanSettings.tcpport, LanSettings.udpport);
				status.reset();
				curmode=Clientmode.BOUND;
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	public void stopclient(){
		this.stop();
	//	status.id=null;
	//	status.name=null;
		curmode=Clientmode.UNBOUND;
	}
	
}