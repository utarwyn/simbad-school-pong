package pongpong.entity;

import pongpong.world.Paddle;

public abstract class Entity {

	private int side;

	private int score;

	private Paddle paddle;

	Entity(Paddle paddle) {
		this.paddle = paddle;
		this.side = paddle.getTranslation().getX() < 0 ? -1 : 1;
	}

	public void resetScore() {
		this.score = 0;
	}

	public void scores() {
		this.score++;
	}

	public void loses() {

	}

	public Paddle getPaddle() {
		return this.paddle;
	}

	public int getSide() {
		return this.side;
	}

	public int getScore() {
		return this.score;
	}

}
