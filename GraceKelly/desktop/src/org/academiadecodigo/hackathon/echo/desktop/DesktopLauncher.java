package org.academiadecodigo.hackathon.echo.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import org.academiadecodigo.hackathon.echo.GKGame;
import org.academiadecodigo.hackathon.echo.assets.GameProperties;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Grace Kelly";
		config.width = GameProperties.WIDTH;
		config.height = GameProperties.HEIGHT;
		new LwjglApplication(new GKGame(), config);
	}
}
