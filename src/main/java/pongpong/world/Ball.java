package pongpong.world;

import simbad.sim.BallAgent;
import simbad.sim.StaticObject;
import simbad.sim.Wall;

import javax.vecmath.Color3f;
import javax.vecmath.Vector3d;

public class Ball extends BallAgent {

	private static final int MAX_ANGLE = 60;

	private static final int SPEED = 6;

	Ball() {
		super(new Vector3d(0, 0, 0), "Ball", new Color3f(1, 1, 1), .3f, .1f);
	}

	public void initBehavior() {
		this.linearVelocity = new Vector3d((int) (Math.random() * 20) - 10, 0, (int) (Math.random() * 12) - 6);
		//this.linearVelocity = new Vector3d(0, 0, 5);
	}

	public void performBehavior() {
		if (this.collisionDetected()) {
			StaticObject object = this.getObjectCollided();

			if (object instanceof Paddle) {
				this.bounceOnPaddle((Paddle) object);
				this.linearVelocity.setX(-this.linearVelocity.getX());
			}

			if (object instanceof Wall)
				this.linearVelocity.setZ(-this.linearVelocity.getZ());

			return;
		}

		if (this.linearVelocity.getX() > 20)
			this.linearVelocity.setX(20);
		if (this.linearVelocity.getX() < -20)
			this.linearVelocity.setX(-20);
	}

	private void bounceOnPaddle(Paddle paddle) {
		float dist = this.getTranslation().getZ() - paddle.getTranslation().getZ();
		float midLength = paddle.getLength() / 2;

		double perc = ((dist + midLength) / (double) (midLength * 2));
		double angle = Math.toRadians((MAX_ANGLE * 2.0) * perc - MAX_ANGLE);

		int dirFactor = this.linearVelocity.getX() < 0 ? -1 : 1;
		float speed = SPEED + SPEED * paddle.getAcceleration() * 4;

		double vX = speed * Math.cos(angle);
		double vZ = speed * Math.sin(angle);

		this.linearVelocity.set(dirFactor * vX, 0, vZ);
	}

}
