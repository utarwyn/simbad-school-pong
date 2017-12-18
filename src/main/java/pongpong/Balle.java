package pongpong;

import simbad.sim.BallAgent;

import javax.vecmath.Vector3d;

public class Balle extends BallAgent {

	public Balle() {
		super(new Vector3d(0, 0, 0), "Balle", .5f);
	}

	public void initBehavior() {

	}

	public void performBehavior() {
		if (this.collisionDetected()) {
			rotateY(Math.PI);
			return;
		}


		//setTranslationalVelocity(8);
		//setMotorsAcceleration(8);
	}

}
