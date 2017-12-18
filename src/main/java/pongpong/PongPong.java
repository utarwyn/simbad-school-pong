package pongpong;

import pongpong.input.KeyManager;
import simbad.gui.Simbad;
import simbad.sim.World;

import java.awt.event.KeyEvent;

public class PongPong {

	public static void main(String[] args) {
		Environment env = new Environment();
		Simbad frame = new Simbad(env, "Pong pong", false);

		frame.getWorld().changeViewPoint(World.VIEW_FROM_TOP, null);

		frame.getSimulator().setFramesPerSecond(60);
		frame.getSimulator().startSimulation();

		KeyManager keyManager = new KeyManager(frame);

		keyManager.addKeyPressListener(
				KeyEvent.VK_UP,
				() -> env.getBarre1().up()
		);
		keyManager.addKeyPressListener(
				KeyEvent.VK_DOWN,
				() -> env.getBarre1().down()
		);
	}

}
