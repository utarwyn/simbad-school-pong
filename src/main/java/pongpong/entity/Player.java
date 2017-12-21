package pongpong.entity;

import pongpong.input.KeyManager;
import pongpong.world.Paddle;

public class Player extends Entity {

	private int keyUp;

	private int keyDown;

	public Player(Paddle paddle) {
		super(paddle);
	}

	public void setMovementKeys(int keyUp, int keyDown) {
		this.keyUp = keyUp;
		this.keyDown = keyDown;

		KeyManager.getInstance().addKeyPressListener((keyCode) -> {
			if (keyCode == this.keyUp)
				this.getPaddle().up();
			if (keyCode == this.keyDown)
				this.getPaddle().down();
		});

		KeyManager.getInstance().addKeyReleaseListener((keyCode) -> {
			if (keyCode == this.keyUp || keyCode == this.keyDown)
				this.getPaddle().resetAcceleration();
		});
	}

}
