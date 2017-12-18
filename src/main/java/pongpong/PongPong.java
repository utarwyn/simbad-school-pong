package pongpong;

import pongpong.input.KeyManager;
import pongpong.world.Environment;
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

		frame.getWorld().getCanvas3D().requestFocus();

		KeyManager keyManager = new KeyManager(frame);

		keyManager.addKeyPressListener(
				KeyEvent.VK_UP,
				() -> env.getPaddle1().up()
		);
		keyManager.addKeyPressListener(
				KeyEvent.VK_DOWN,
				() -> env.getPaddle1().down()
		);

		keyManager.addKeyPressListener(
				KeyEvent.VK_Z,
				() -> env.getPaddle2().up()
		);
		keyManager.addKeyPressListener(
				KeyEvent.VK_S,
				() -> env.getPaddle2().down()
		);
	}

}
