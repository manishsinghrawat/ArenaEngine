package com.me.RPGArena;

import android.os.Bundle;
import android.os.SystemClock;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.me.gameutils.TimeInterface;

public class MainActivity extends AndroidApplication implements TimeInterface {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        
        initialize(new RPGArena(this), cfg);
    }

	@Override
	public long currenttime() {
		return SystemClock.elapsedRealtime();
	}
}