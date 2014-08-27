package com.me.LanNetworkUtils;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.List;

import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.kryonet.Client;
import com.me.Network.LanSettings;


public class ClientDiscoveryThread implements Runnable{
	List<InetAddress> lst;
	private boolean tmp;
	private Client client;
	private Array<DiscoveryPacket> hostlist;
	private boolean shutdown;
	private Thread discoverythread;
	DatagramSocket socket=null;
	
	public ClientDiscoveryThread(Client client, Array<DiscoveryPacket> hostlist) {
		super();
		this.client = client;
		this.hostlist = hostlist;
		
	}
	public void start(){
		if(discoverythread!=null){
			shutdown=true;
			try {
				discoverythread.join(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		try {
			/** 
			 * Using this will bind broadcast to specific port
			 */
		//	socket = new DatagramSocket(LanSettings.broadport);
			socket = new DatagramSocket();
		} catch (SocketException e) {
			e.printStackTrace();
		}
		discoverythread = new Thread(this, "Discovery");
		discoverythread.setDaemon(true);
		discoverythread.start();
	}
	public void stop(){
		if (shutdown) return;
		shutdown = true;
		/*
		 * required when stop needs to be blocked until socket is closed
		 * but this is not required since the port used by discoverer is different than
		 * designated tcp and udp ports
		 * try {
			discoverythread.join(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		*/
	}
	@Override
	public void run() {
		
		shutdown=false;
	//	try {
			while(!shutdown){

				lst=client.discoverHosts(socket,LanSettings.udpport, 2000);
				synchronized(hostlist){
					for(final DiscoveryPacket hst:hostlist) {
						tmp=true;
						for(final InetAddress add:lst) {
							if(add.toString().equals(hst.ipaddress)) {
								tmp=false;
								break;
							}
						}
						if(tmp)	hostlist.removeValue(hst, true);
					}
				}

			}
			
	//	} catch (SocketException e) {
	//		e.printStackTrace();
	//	} finally{
			if (socket != null) socket.close();
			System.out.println("Host discovery closed");
	//	}
	}

}
