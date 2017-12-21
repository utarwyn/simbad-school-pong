package pongpong.input;

import simbad.gui.Simbad;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class KeyManager implements KeyListener, Runnable {

	private static KeyManager instance;

	private Simbad simbad;

	private List<CustomKeyEvent> pressListeners;

	private List<CustomKeyEvent> releaseListeners;

	private ConcurrentLinkedQueue<Integer> keysPressed;

	private KeyManager() {
		this.pressListeners = new ArrayList<>();
		this.releaseListeners = new ArrayList<>();
		this.keysPressed = new ConcurrentLinkedQueue<>();
	}

	public void initialize(Simbad simbad) {
		this.simbad = simbad;

		this.simbad.getWorld().getCanvas3D().addKeyListener(this);
		new Thread(this).start();
	}

	public void addKeyPressListener(CustomKeyEvent runnable) {
		this.pressListeners.add(runnable);
	}

	public void addKeyReleaseListener(CustomKeyEvent runnable) {
		this.releaseListeners.add(runnable);
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
		int keyReleased = keyEvent.getKeyCode();

		this.keysPressed.remove(keyReleased);

		for (CustomKeyEvent event : this.releaseListeners)
			event.run(keyReleased);
	}

	@Override
	public void run() {
		while (simbad.getWorld().getCanvas3D().isEnabled()) {
			Iterator<Integer> it = this.keysPressed.iterator();

			while (it.hasNext()) {
				Integer keyPressed = it.next();

				for (CustomKeyEvent event : this.pressListeners)
					event.run(keyPressed);
			}

			try {
				Thread.sleep(16);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static KeyManager getInstance() {
		if (instance != null)
			return instance;

		return instance = new KeyManager();
	}

}
