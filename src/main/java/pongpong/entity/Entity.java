package pongpong.entity;

public abstract class Entity {

	private int side;

	private int score;

	Entity(int side) {
		this.side = side;
	}

	public void resetScore() {
		this.score = 0;
	}

	public void scores() {
		this.score++;
	}

	public int getSide() {
		return this.side;
	}

	public int getScore() {
		return this.score;
	}

}
