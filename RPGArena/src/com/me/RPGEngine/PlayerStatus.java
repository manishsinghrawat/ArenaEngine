package com.me.RPGEngine;

import java.util.LinkedList;
import java.util.Queue;

import com.me.GameData.StatusPacket;

public class PlayerStatus {
	public StatusPacket currentpacket;
	public Queue<StatusPacket> messagequeue;
	public String name;
	public int id;
	public GameHero hero;
	
	public PlayerStatus(){
		currentpacket=null;
		messagequeue=new LinkedList<StatusPacket>();
	}

	public PlayerStatus(String string, int i,GameHero gameHero) {
		currentpacket=null;
		messagequeue=new LinkedList<StatusPacket>();
		name=string;
		id=i;
		this.hero=gameHero;
		hero.moveto(50,50);
		hero.refresh();
		hero.animate(2);
	}
}
