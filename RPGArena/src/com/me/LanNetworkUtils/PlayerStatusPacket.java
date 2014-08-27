package com.me.LanNetworkUtils;

public class PlayerStatusPacket {
	public int size;
	public String[] name;
	public int[] id;
	public PlayerStatusPacket(){
		size=4;
		name=new String[size];
		id=new int[size];
		reset();
	}
	public void addplayer(String name, int id) {
		for(int i=0;i<size;i++){
			if(this.name[i].equals("")){
				this.name[i]=name;
				this.id[i]=id;
				break;
			}

		}
	}
	public void copyfrom(PlayerStatusPacket object) {
		this.size=object.size;
		this.id=object.id;
		this.name=object.name;
	}
	public void removeplayer(int id2) {
		for(int i=0;i<size;i++){
			if(this.id[i]==id2){
				this.name[i]="";
			}
		}
	}
	public void reset() {
		for(int i=0;i<size;i++){
			name[i]="";
			id[i]=0;
		}
	}
}
