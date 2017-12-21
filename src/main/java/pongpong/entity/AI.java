package pongpong.entity;

import pongpong.PongPong;
import pongpong.entity.ai.AILevel;
import pongpong.entity.ai.AIPrediction;
import pongpong.util.Rectangle;
import pongpong.world.Ball;
import pongpong.world.Paddle;

import javax.vecmath.Point2d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

public class AI extends Entity implements Runnable {

	private AIPrediction prediction;

	private AILevel level;

	public AI(Paddle paddle) {
		super(paddle);

		this.prediction = new AIPrediction(this);
		this.level = AILevel.getDefault();

		this.adaptSpeed();

		new Thread(this).start();
	}

	public AILevel getLevel() {
		return this.level;
	}

	@Override
	public void scores() {
		super.scores();
		this.level = this.level.getNext();
		this.adaptSpeed();
	}

	@Override
	public void loses() {
		super.loses();
		this.level = this.level.getPrev();
		this.adaptSpeed();
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(16);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			Ball ball = PongPong.getInstance().getBall();
			Paddle paddle = this.getPaddle();
			Rectangle rect = this.getPaddle().get2DRepresentation();

			// On tente de lancer la prédiction
			this.predict(ball);

			// On bouge le pad suivant la prédiction précédente
			if (this.prediction.exists()) {
				if (this.prediction.getY() < (rect.getTop() + rect.getHeight()/2 - .5)) {
					paddle.up();
				} else if (this.prediction.getY() > (rect.getBottom() - rect.getHeight()/2 + .5)) {
					paddle.down();
				} else {
					paddle.resetAcceleration();
				}
			}
		}
	}

	private void predict(Ball ball) {
		Vector3d ballVel = ball.getVelocity();

		// On évite de lancer la prédiction trop souvent...
		if (this.prediction.exists() &&
				((this.prediction.getBallVelX() * ballVel.x) > 0) &&
				((this.prediction.getBallVelY() * ballVel.z) > 0) &&
				(this.prediction.youngerThan(this.level.getReaction())))
			return;

		// Définition de la zone de prédiction
		Rectangle zone = this.getPaddle().get2DRepresentation();
		Rectangle predictZone = zone.clone();

		predictZone.setY(-1000);
		predictZone.setHeight(2000);

		// Point de prédiction d'arrivée de la balle
		Point2d pt = AI.ballIntercept(ball, predictZone, ballVel.x * 10, ballVel.z * 10);

		if (pt != null) {
			// Mise à niveau du point de prédiction
			double min = Paddle.MIN_Z + ball.getRadius();
			double max = Paddle.MAX_Z + zone.getHeight() - ball.getRadius();

			while ((pt.y < min) || (pt.y > max)) {
				if (pt.y < min)
					pt.y = min + (min - pt.y);
				else if (pt.y > max)
					pt.y = min + (max - min) - (pt.y - max);
			}

			this.prediction.renew(ball);
		} else {
			this.prediction.reset();
		}

		// Application de la prédiction
		if (this.prediction.exists())
			this.prediction.setPoint(pt);
	}

	private void adaptSpeed() {
		// Adaptation de la vitesse du pad suivant le niveau de l'AI
		// et son temps de réaction face à la balle.
		float factor = 1 / (float) this.level.getReaction();

		this.getPaddle().setBaseSpeed(.05f * factor);
		this.getPaddle().setJolt(.0001f * factor);
		this.getPaddle().setMaxSpeed(.6f * factor);
	}

	private static Point2d intercept(double x1, double y1, double x2, double y2,
							  double x3, double y3, double x4, double y4) {
		double denom = ((y4 - y3) * (x2 - x1)) - ((x4 - x3) * (y2 - y1));

		if (denom != 0) {
			double ua = (((x4 - x3) * (y1 - y3)) - ((y4 - y3) * (x1 - x3))) / denom;

			if ((ua >= 0) && (ua <= 1)) {
				double ub = (((x2 - x1) * (y1 - y3)) - ((y2 - y1) * (x1 - x3))) / denom;

				if ((ub >= 0) && (ub <= 1)) {
					double x = x1 + (ua * (x2 - x1));
					double y = y1 + (ua * (y2 - y1));

					return new Point2d(x, y);
				}
			}
		}

		return null;
	}

	private static Point2d ballIntercept(Ball ball, Rectangle zone, double nx, double ny) {
		Point2d pt = null;

		Vector3f ballPos = ball.getTranslation();
		float ballRad = ball.getRadius();

		if (nx < 0) {
			pt = AI.intercept(
					ballPos.x, ballPos.z, ballPos.x + nx, ballPos.z + ny,
					zone.getRight() + ballRad, zone.getTop() - ballRad,
					zone.getRight() + ballRad, zone.getBottom() + ballRad);
		} else if (nx > 0) {
			pt = AI.intercept(
					ballPos.x, ballPos.z, ballPos.x + nx, ballPos.z + ny,
					zone.getLeft() - ballRad, zone.getTop() - ballRad,
					zone.getLeft() - ballRad, zone.getBottom() + ballRad);
		}

		if (pt == null) {
			if (ny < 0) {
				pt = AI.intercept(
						ballPos.x, ballPos.z, ballPos.x + nx, ballPos.z + ny,
						zone.getLeft() - ballRad, zone.getBottom() + ballRad,
						zone.getRight() + ballRad, zone.getBottom() + ballRad);
			} else if (ny > 0) {
				pt = AI.intercept(
						ballPos.x, ballPos.z, ballPos.x + nx, ballPos.z + ny,
						zone.getLeft() - ballRad, zone.getTop() - ballRad,
						zone.getRight() + ballRad, zone.getTop() - ballRad);
			}
		}

		return pt;
	}

}
