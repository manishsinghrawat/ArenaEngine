package com.me.Network;

import com.me.GameData.GameCommand;
import com.me.GameData.StatusPacket;
import com.me.LanNetworkUtils.PlayerStatusPacket;

/**
 * Base class for all network Managers
 * @author star
 *
 */
public abstract class Network {
	public int id;
	public boolean isready=false;
	public boolean started=false;
	public boolean loaded=false;
	public NetworkListener networklistener;

	public long zerotime;
	
	public void setlistener(NetworkListener listener){
		networklistener=listener;
	}
	
	public abstract void setname(String name);
	
	public abstract void update(StatusPacket update);
	
	public abstract void execute(GameCommand command);
	
	public abstract void synchrotime();
	
	public abstract PlayerStatusPacket getstatus();

	public abstract void startgame();

	public abstract void notifyloaded() ;
}
