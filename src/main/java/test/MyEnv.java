package test;

import simbad.sim.Arch;
import simbad.sim.EnvironmentDescription;

import javax.vecmath.Vector3d;

class MyEnv extends EnvironmentDescription {

	MyEnv() {
		add(new Arch(new Vector3d(3,0,-3),this));
		add(new MyRobot(new Vector3d(0, 0, 0),"mon robot"));
	}

}