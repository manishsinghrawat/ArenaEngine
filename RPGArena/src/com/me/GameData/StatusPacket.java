package com.me.GameData;

public class StatusPacket {

	public long timetag;
	public int id;
	public float xpos;
	public float ypos;
	public float xpercent;
	public float ypercent;
	public StatusPacket(){
		
	}
	public StatusPacket(StatusPacket packet) {
		timetag=packet.timetag;
		id=packet.id;
		xpos=packet.xpos;
		ypos=packet.ypos;
		xpercent=packet.xpercent;
		ypercent=packet.ypercent;
	}
}
