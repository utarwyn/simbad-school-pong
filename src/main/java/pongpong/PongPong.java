package pongpong;

import simbad.gui.Simbad;
import simbad.sim.World;

public class PongPong {

	public static void main(String[] args) {
		Environment env = new Environment();
		Simbad frame = new Simbad(env, "Pong pong", false);

		frame.getWorld().changeViewPoint(World.VIEW_FROM_TOP, null);

		frame.getSimulator().startSimulation();
		frame.getSimulator().setFramesPerSecond(30);
		frame.addKeyListener(new PongListener(env));
	}

}
