package com.me.RPGArena;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;
import com.me.gameutils.TimeInterface;

public class Main {
	public static void main(String[] args) {
		Settings settings=new Settings();
		settings.maxWidth=512;
		settings.maxHeight=512;
		
		
		TexturePacker.process(settings,"../RPGArena-android/assets/data/Hud/images","../RPGArena-android/assets/data/Hud","Hud");
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "RPGArena";
		cfg.width = 1280;
		cfg.height = 720;
		
		new LwjglApplication(new RPGArena(new TimeInterface(){

			@Override
			public long currenttime() {
				return System.nanoTime()/1000000;
			}
			
		}), cfg);
	}
}
