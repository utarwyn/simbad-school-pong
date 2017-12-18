package pongpong.input;

import simbad.gui.Simbad;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class KeyManager implements KeyListener, Runnable {

	private Simbad simbad;

	private Map<Integer, List<KeyPress>> listeners;

	private ConcurrentLinkedQueue<Integer> keysPressed;

	public KeyManager(Simbad simbad) {
		this.simbad = simbad;

		this.listeners = new HashMap<>();
		this.keysPressed = new ConcurrentLinkedQueue<>();

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
		int keyCode = keyEvent.getKeyCode();

		if (!this.keysPressed.contains(keyCode))
			this.keysPressed.add(keyCode);
	}

	@Override
	public void keyReleased(KeyEvent keyEvent) {
		this.keysPressed.remove((Object) keyEvent.getKeyCode());
	}

	@Override
	public void run() {
		while (simbad.getWorld().getCanvas3D().isEnabled()) {
			Iterator<Integer> it = this.keysPressed.iterator();

			while (it.hasNext()) {
				Integer keyPressed = it.next();

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
