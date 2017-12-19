package pongpong;

import pongpong.entity.Entity;
import pongpong.entity.Player;
import pongpong.hud.HUD;
import pongpong.input.KeyManager;
import pongpong.world.Environment;
import simbad.gui.Simbad;
import simbad.sim.World;

import java.awt.event.KeyEvent;

public class PongPong {

	private static PongPong instance;

	private HUD hud;

	private Environment environment;

	private Simbad simbad;

	private Entity entity1, entity2;

	private Long start;

	private PongPong() {
		instance = this;

		this.environment = new Environment();

		this.hud = new HUD();
		this.simbad = new Simbad(this.environment, "Pong pong", this.hud, false);

		this.initialize();
	}

	public HUD getHUD() {
		return this.hud;
	}

	private void initialize() {
		this.simbad.getWorld().changeViewPoint(World.VIEW_FROM_TOP, null);

		this.simbad.getSimulator().setFramesPerSecond(60);
		this.simbad.getSimulator().startSimulation();

		this.simbad.getWorld().getCanvas3D().requestFocus();

		this.entity1 = new Player(-1);
		this.entity2 = new Player( 1);

		this.start = System.currentTimeMillis();

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

	public String getTimer() {
		if (this.start == null) return "00:00";
		long diff = (System.currentTimeMillis() - this.start) / 1000;
		int mins = (int) diff / 60;
		int secs = (int) (diff - 60 * mins);

		return String.format("%02d", mins) + ":" + String.format("%02d", secs);
	}

	public Entity getEntityBySide(int side) {
		return this.entity1.getSide() < 0 && side < 0 ? this.entity1 : this.entity2;
	}

	public static PongPong getInstance() {
		return instance;
	}

	public static void main(String[] args) {
		new PongPong();
	}

}
