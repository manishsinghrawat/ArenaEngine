package com.me.LanNetworkUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

import com.esotericsoftware.kryonet.Serialization;
import com.esotericsoftware.kryonet.ServerDiscoveryHandler;
import com.esotericsoftware.kryonet.UdpConnection;
import com.me.LanNetworkUtils.DiscoveryPacket;

public class ServerDiscovery implements ServerDiscoveryHandler{
	public DiscoveryPacket packet;
	private boolean disabled;
	public ServerDiscovery(){
		this.disabled=false;
		packet= new DiscoveryPacket();
		//packet.ipaddress = fromAddress.getAddress().toString();
		packet.gameName = "defaultgame";
		packet.playerName = "defaultname";
	}
	public void setpacket(String hostname,String gamename){
		this.packet.gameName=gamename;
		this.packet.playerName=hostname;
	}
	public void disable(){
		disabled=true;
	}
	public void enable(){
		disabled=false;
	}
	@Override
	public boolean onDiscoverHost(UdpConnection udp,
			InetSocketAddress fromAddress, Serialization serialization)
					throws IOException {
		if(!disabled){
		//	packet.ipaddress = fromAddress.getAddress().toString();
		//	packet.ipaddress = udp.datagramChannel.socket().getLocalAddress().toString();
			packet.ipaddress="";
			ByteBuffer buffer = ByteBuffer.allocate(256);
			serialization.write(null, buffer, packet);
			buffer.flip();

			udp.datagramChannel.send(buffer, fromAddress);
		}
		return true;
	}

}
