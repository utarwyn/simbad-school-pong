package pongpong;

import simbad.sim.BallAgent;

import javax.vecmath.Color3f;
import javax.vecmath.Vector3d;

public class Balle extends BallAgent {

	public Balle() {
		super(new Vector3d(0, 0, 0), "Balle", new Color3f(0, 1, 0), .5f, .1f);
	}

	public void initBehavior() {
		this.linearVelocity = new Vector3d(2, 0, 0);
	}

	public void performBehavior() {
		if (this.collisionDetected()) {
			this.linearVelocity.setX(-this.linearVelocity.getX()*1.5f);
			return;
		}
	}

}
