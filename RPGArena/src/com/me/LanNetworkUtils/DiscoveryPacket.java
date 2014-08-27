package com.me.LanNetworkUtils;


public class DiscoveryPacket {
	public String ipaddress;
	public String gameName;
	public String playerName;

	@Override
	public String toString(){
		return playerName+"("+ipaddress+")";
	}

	public void update(DiscoveryPacket packet) {
		this.ipaddress=packet.ipaddress;
		this.gameName=packet.gameName;
		this.playerName=packet.playerName;
	}
}
