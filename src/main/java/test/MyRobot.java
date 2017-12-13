package test;

import simbad.sim.Agent;

import javax.vecmath.Vector3d;

public class MyRobot extends Agent {

	MyRobot(Vector3d position, String name) {
		super(position, name);
	}

	public void initBehavior() {
	}

	public void performBehavior() {
		// avance à 0.5 m/s
		setTranslationalVelocity(0.5);
		// changer l'angle fréquemment
		if ((getCounter() % 100) == 0)
			setRotationalVelocity(Math.PI / 2 * (0.5 - Math.random()));
	}

}