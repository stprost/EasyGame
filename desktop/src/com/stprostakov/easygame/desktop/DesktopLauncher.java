package com.stprostakov.easygame.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.stprostakov.easygame.EasyGame;
import com.stprostakov.easygame.Zachet;

public class DesktopLauncher {

	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "EasyGame";
		config.width = 800;
		config.height = 480;
		new LwjglApplication(new Zachet(), config);
	}
}
