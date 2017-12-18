package pongpong.input;

import simbad.gui.Simbad;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KeyManager implements KeyListener, Runnable {

	private Simbad simbad;

	private Map<Integer, List<KeyPress>> listeners;

	private List<Integer> keysPressed;

	public KeyManager(Simbad simbad) {
		this.simbad = simbad;

		this.listeners = new HashMap<>();
		this.keysPressed = new ArrayList<>();

		this.simbad.getWorld().getCanvas3D().addKeyListener(this);
		new Thread(this).start();
	}

	public void addKeyPressListener(int keyCode, KeyPress runnable) {
		if (!this.listeners.containsKey(keyCode))
			this.listeners.put(keyCode, new ArrayList<>());

		this.listeners.get(keyCode).add(runnable);
	}

	@Override
	public void keyTyped(KeyEvent keyEvent) {

	}

	@Override
	public void keyPressed(KeyEvent keyEvent) {
		if (!this.keysPressed.contains(keyEvent.getKeyCode()))
			this.keysPressed.add(keyEvent.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent keyEvent) {
		this.keysPressed.remove((Object) keyEvent.getKeyCode());
	}

	@Override
	public void run() {
		while (simbad.getWorld().getCanvas3D().isEnabled()) {
			for (Integer keyPressed : this.keysPressed) {
				if (this.listeners.get(keyPressed) == null)
					continue;

				for (KeyPress event : this.listeners.get(keyPressed))
					event.onPress();
			}

			try {
				Thread.sleep(16);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
