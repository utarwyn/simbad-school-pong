package pongpong.world;

import simbad.sim.BallAgent;

import javax.vecmath.Color3f;
import javax.vecmath.Vector3d;

public class Ball extends BallAgent {

	Ball() {
		super(new Vector3d(0, 0, 0), "Ball", new Color3f(1, 1, 1), .3f, .1f);
	}

	public void initBehavior() {
		this.linearVelocity = new Vector3d((int) (Math.random() * 20) - 10, 0, (int) (Math.random() * 12) - 6);
		//this.linearVelocity = new Vector3d(0, 0, 5);
	}

	public void performBehavior() {
		if (this.collisionDetected()) {
			this.linearVelocity.setX(-this.linearVelocity.getX()*1.02f);
			this.linearVelocity.setZ(-this.linearVelocity.getZ());
			return;
		}

		if (this.linearVelocity.getX() > 20)
			this.linearVelocity.setX(20);
		if (this.linearVelocity.getX() < -20)
			this.linearVelocity.setX(-20);
	}

}
