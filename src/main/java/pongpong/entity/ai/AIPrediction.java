package pongpong.entity.ai;

import pongpong.PongPong;
import pongpong.entity.AI;
import pongpong.util.Rectangle;
import pongpong.world.Ball;

import javax.vecmath.Point2d;

public class AIPrediction {

	private AI ai;

	private boolean exists;

	private long since;

	private double ballVelX;

	private double ballVelY;

	private Point2d prediction;

	private Point2d exactPrediction;

	public AIPrediction(AI ai) {
		this.ai = ai;
	}

	public double getBallVelX() {
		return ballVelX;
	}

	public double getBallVelY() {
		return ballVelY;
	}

	public double getX() {
		return this.prediction.getX();
	}

	public double getY() {
		return this.prediction.getY();
	}

	public boolean youngerThan(double time) {
		return System.currentTimeMillis() - this.since < time * 1000;
	}

	public boolean exists() {
		return exists;
	}

	public void setPoint(Point2d prediction) {
		this.exactPrediction = new Point2d(prediction.x, prediction.y);
		this.prediction = new Point2d(prediction.x, prediction.y);

		// Add AI error
		Ball ball = PongPong.getInstance().getBall();
		Rectangle paddleRect = this.ai.getPaddle().get2DRepresentation();
		double ballX = ball.getTranslation().x;

		double closeness = (ball.getVelocity().x < 0 ? ballX - paddleRect.getRight() : paddleRect.getLeft() - ballX);
		double error = this.ai.getLevel().getError() * closeness;

		this.prediction.setY(this.prediction.getY() + ((Math.random() * error * 2) - error));
	}

	public void renew(Ball ball) {
		this.exists = true;
		this.since = System.currentTimeMillis();

		this.ballVelX = ball.getVelocity().x;
		this.ballVelY = ball.getVelocity().z;
	}

	public void reset() {
		this.exists = false;
	}

}
