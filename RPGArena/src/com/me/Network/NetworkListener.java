package com.me.Network;

import com.me.GameData.GameCommand;
import com.me.GameData.StatusPacket;

public interface NetworkListener {
	
	public void update(StatusPacket packet);
	
	public void execute(GameCommand command);
	
}
