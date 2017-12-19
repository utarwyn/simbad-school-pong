package pongpong.world;

import simbad.sim.EnvironmentDescription;
import simbad.sim.Wall;

import javax.vecmath.Color3f;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

public class Environment extends EnvironmentDescription {

	private Paddle paddle1;
	private Paddle paddle2;

	public Environment() {
		setWorldSize(35);
		light1SetPosition(-5, 10, 5);
		setUsePhysics(true);

		this.paddle1 = new Paddle(new Vector3d(-10, 0, 0), new Vector3f(.3f, 1, 2), new Color3f(.130f, .589f, .953f));
		this.paddle2 = new Paddle(new Vector3d( 10, 0, 0), new Vector3f(.3f, 1, 2), new Color3f(.957f, .263f, .212f));

		add(this.paddle1);
		add(this.paddle2);

		wallColor = new Color3f(0, 0, 0);
		add(new Wall(new Vector3d(0, 0, -9), 50, 2, this));
		add(new Wall(new Vector3d(0, 0, 9), 50, 2, this));

		add(new Ball());
	}

	public Paddle getPaddle1() {
		return paddle1;
	}

	public Paddle getPaddle2() {
		return paddle2;
	}

}
