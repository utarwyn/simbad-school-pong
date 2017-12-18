package pongpong;

import java.awt.event.KeyEvent;

public class PongListener implements java.awt.event.KeyListener {

	private Environment env;

	public PongListener(Environment env) {
		this.env = env;
	}

	@Override
	public void keyTyped(KeyEvent keyEvent) {

	}

	@Override
	public void keyPressed(KeyEvent keyEvent) {
		int keyCode = keyEvent.getKeyCode();

		if (keyCode == KeyEvent.VK_UP) {
			this.env.getBarre1().up();
		} else if (keyCode == KeyEvent.VK_DOWN) {
			this.env.getBarre1().down();
		}
	}

	@Override
	public void keyReleased(KeyEvent keyEvent) {

	}

}
