package com.academiadecodigo.hackathon.echo.desktop;

import com.academiadecodigo.hackathon.echo.assets.GameProperties;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.academiadecodigo.hackathon.echo.GKGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Grace Kelly";
		config.width = GameProperties.WIDTH;
		config.height = GameProperties.HEIGHT;
		new LwjglApplication(new GKGame(), config);
	}
}
