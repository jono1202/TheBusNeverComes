package busstop.pheonix_studios.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.phoenixstudios.busstop.the_bus_never_comes;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = Math.round(720/2f);
		config.height = Math.round(1280/2f);
		new LwjglApplication(new the_bus_never_comes(), config);
	}
}
