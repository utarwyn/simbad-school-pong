package pongpong;

import pongpong.entity.AI;
import pongpong.entity.Entity;
import pongpong.entity.Player;
import pongpong.hud.HUD;
import pongpong.input.KeyManager;
import pongpong.world.Ball;
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

	private Ball ball;

	private Long startTime;

	private PongPong(boolean two) {
		instance = this;

		this.environment = new Environment();

		this.hud = new HUD();
		this.simbad = new Simbad(this.environment, "Pong pong", this.hud, false);

		this.initialize(two);
		this.start(two);
	}

	public HUD getHUD() {
		return this.hud;
	}

	private void initialize(boolean two) {
		this.simbad.getWorld().changeViewPoint(World.VIEW_FROM_TOP, null);

		this.entity1 = new Player(this.environment.getPaddle1());
		if (two)
			this.entity2 = new Player(this.environment.getPaddle2());
		else
			this.entity2 = new AI(this.environment.getPaddle2());

		this.ball = this.environment.getBall();
	}

	private void start(boolean two) {
		this.simbad.getSimulator().setFramesPerSecond(60);
		this.simbad.getSimulator().startSimulation();

		this.startTime = System.currentTimeMillis();

		this.simbad.getWorld().getCanvas3D().requestFocus();
		KeyManager.getInstance().initialize(this.simbad);

		((Player) this.entity1).setMovementKeys(KeyEvent.VK_Z, KeyEvent.VK_S);
		if (two) {
			((Player) this.entity2).setMovementKeys(KeyEvent.VK_UP, KeyEvent.VK_DOWN);
		}
	}

	public String getTimer() {
		if (this.startTime == null) return "00:00";
		long diff = (System.currentTimeMillis() - this.startTime) / 1000;
		int mins = (int) diff / 60;
		int secs = (int) (diff - 60 * mins);

		return String.format("%02d", mins) + ":" + String.format("%02d", secs);
	}

	public Entity getEntityBySide(int side) {
		if (side < 0) {
			if (this.entity1.getSide() < 0)
				return this.entity1;
			else
				return this.entity2;
		} else {
			if (this.entity1.getSide() > 0)
				return this.entity1;
			else
				return this.entity2;
		}
	}

	public Ball getBall() {
		return this.ball;
	}

	public void resetPaddles() {
		this.entity1.getPaddle().moveToStartPosition();
		this.entity2.getPaddle().moveToStartPosition();
	}

	public static PongPong getInstance() {
		return instance;
	}

	public static void main(String[] args) {
		new PongPong((args.length > 0 && args[0].equals("2")));
	}

}
