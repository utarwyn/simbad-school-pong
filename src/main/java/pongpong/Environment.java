package pongpong;

import simbad.sim.EnvironmentDescription;

import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

public class Environment extends EnvironmentDescription {

	private Barre barre1;
	private Barre barre2;

	public Environment() {
		setWorldSize(35);
		light1SetPosition(-5, 10, 5);
		setUsePhysics(true);

		this.barre1 = new Barre(new Vector3d(-10, 0, 0), new Vector3f(1, 1, 4));
		this.barre2 = new Barre(new Vector3d( 10, 0, 0), new Vector3f(1, 1, 4));

		add(this.barre1);
		add(this.barre2);
		add(new Balle());
	}

	public Barre getBarre1() {
		return barre1;
	}

	public Barre getBarre2() {
		return barre2;
	}

}
