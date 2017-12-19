package pongpong;

import pongpong.input.KeyManager;
import pongpong.world.Environment;
import simbad.gui.Simbad;
import simbad.sim.World;

import java.awt.event.KeyEvent;

public class PongPong {

	private static PongPong instance;

	private Environment environment;

	private Simbad simbad;

	private PongPong() {
		instance = this;

		this.environment = new Environment();
		this.simbad = new Simbad(this.environment, "Pong pong", false);

		this.initialize();
	}

	public Environment getEnvironment() {
		return environment;
	}

	private void initialize() {
		this.simbad.getWorld().changeViewPoint(World.VIEW_FROM_TOP, null);

		this.simbad.getSimulator().setFramesPerSecond(60);
		this.simbad.getSimulator().startSimulation();

		this.simbad.getWorld().getCanvas3D().requestFocus();

		KeyManager keyManager = new KeyManager(this.simbad);

		keyManager.addKeyPressListener((keyCode) -> {
			switch (keyCode) {
				case KeyEvent.VK_UP:
					this.environment.getPaddle2().up();
					break;
				case KeyEvent.VK_DOWN:
					this.environment.getPaddle2().down();
					break;

				case KeyEvent.VK_Z:
					this.environment.getPaddle1().up();
					break;
				case KeyEvent.VK_S:
					this.environment.getPaddle1().down();
					break;
			}
		});

		keyManager.addKeyReleaseListener((keyCode) -> {
			switch (keyCode) {
				case KeyEvent.VK_UP:
				case KeyEvent.VK_DOWN:
					this.environment.getPaddle2().resetAcceleration();
					break;
				case KeyEvent.VK_Z:
				case KeyEvent.VK_S:
					this.environment.getPaddle1().resetAcceleration();
					break;
			}
		});
	}

	public static PongPong getInstance() {
		return instance;
	}

	public static void main(String[] args) {
		new PongPong();
	}

}
