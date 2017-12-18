package pongpong;

import simbad.sim.Box;
import simbad.sim.EnvironmentDescription;

import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

public class Barre extends Box {

	public Barre(Vector3d pos, Vector3f extent, EnvironmentDescription wd) {
		super(pos, extent, wd);
	}

	public void up() {
		Vector3f vec = this.getTranslation();

		this.translateTo(new Vector3d(vec.x - 1, vec.y, vec.z));
	}

	public void down() {

	}

}
