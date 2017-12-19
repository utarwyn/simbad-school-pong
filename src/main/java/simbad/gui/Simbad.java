/*
 *  Simbad - Robot Simulator
 *  Copyright (C) 2004 Louis Hugues
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful, 
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 -----------------------------------------------------------------------------
 * $Author: sioulseuguh $ 
 * $Date: 2005/08/07 12:24:56 $
 * $Revision: 1.14 $
 * $Source: /cvsroot/simbad/src/simbad/gui/Simbad.java,v $
 */

package simbad.gui;

import pongpong.hud.HUD;
import simbad.demo.DemoManager;
import simbad.sim.*;

import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
//import javax.swing.UIManager;

/**
 * This is the Simbad application mainframe.
 */
public class Simbad extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	static int SIZEX = 800;
	static int SIZEY = 700;
	JInternalFrame desktop;
	WorldWindow worldWindow = null;
	ControlWindow controlWindow = null;
	World world;
	Simulator simulator;
	Console console = null;
	AgentInspector agentInspector = null;
	boolean debugMode;

	static Simbad simbadInstance = null;


	/**
	 * Construct Simbad application with the given environement description
	 */
	public Simbad(EnvironmentDescription ed, String title, HUD hud, boolean debugMode) {
		super(title);

		simbadInstance = this;
		this.debugMode = debugMode;
		desktop = new JInternalFrame();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(SIZEX, SIZEY);
		createGUI();
		start(ed, hud);

		hud.setSimbad(this);

		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setUndecorated(true);
		setVisible(true);
	}


	/**
	 * Create the main HUD. Only called once.
	 */
	private void createGUI() {
		desktop.setFocusable(true);
		getContentPane().add(desktop);
	}

	/**
	 * Starts (or Restarts after releaseRessources) the world and simulator.
	 */
	private void start(EnvironmentDescription ed, HUD hud) {
		System.out.println("Starting environmentDescription: " + ed.getClass().getName());
		world = new World(ed, hud);
		simulator = new Simulator(desktop, world, ed);
		createInternalFrames();

		if (debugMode)
			runBackgroundMode();
	}

	/**
	 * Release all ressources.
	 */
	private void releaseRessources() {
		simulator.dispose();
		world.dispose();
		disposeInternalFrames();
	}


	/**
	 * Creates the windows as Swing InternalFrames
	 */
	private void createInternalFrames() {
		worldWindow = new WorldWindow(world);
		((BasicInternalFrameUI) worldWindow.getUI()).setNorthPane(null);
		this.add(worldWindow);

		worldWindow.show();
		worldWindow.setLocation(0, 0);
		worldWindow.setBorder(BorderFactory.createLineBorder(Color.WHITE, 5));
		worldWindow.setCursor(getToolkit().createCustomCursor(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB), new Point(), null));

		if (this.debugMode) {
			agentInspector = createAgentInspector(simulator, 20, 20);
			controlWindow = new ControlWindow(world, simulator);
			desktop.add(controlWindow);
			controlWindow.show();
			controlWindow.setLocation(300, 450);
		}
	}

	/**
	 * Dispose the windows- used before restart.
	 */
	private void disposeInternalFrames() {
		simulator.dispose();
		worldWindow.dispose();
		if (agentInspector != null) agentInspector.dispose();
		if (controlWindow != null) {
			controlWindow.dispose();
		}
	}

	/**
	 * creates agent inspector window
	 */
	private AgentInspector createAgentInspector(Simulator simulator, int x,
												int y) {
		ArrayList agents = simulator.getAgentList();
		SimpleAgent a = ((SimpleAgent) agents.get(0));

		if (a instanceof Agent) {
			AgentInspector ai = new AgentInspector((Agent) a, !debugMode, simulator);
			desktop.add(ai);
			ai.show();
			ai.setLocation(x, y);
			return ai;
		} else
			return null;
	}

	public void actionPerformed(ActionEvent event) {
		if (event.getActionCommand() == "demo") {
			releaseRessources();
			start(DemoManager.getDemoFromActionEvent(event), null);
		}
	}

	/**
	 * Runs Simbad in background mode for computation intensive application.
	 * Minimize graphic display and renderer computation.
	 */
	private void runBackgroundMode() {
		//TODO pb with collision , pb with camera in this mode.
		setTitle(this.getTitle() + " - Debug Mode");
		System.out.println("---------------------------------------------");
		System.out.println("Simbad is running in 'Debug Mode");
		System.out.println("World is rendered very rarely. UI is disabled");
		System.out.println("--------------------------------------------");
		// slow down
		//agentInspector.setFramesPerSecond(0.5f);
		// Show a small indication window
		/*JInternalFrame frame = new JInternalFrame();
		JPanel p = new JPanel();
		p.add(new JLabel("BACKGROUND MODE"));
		frame.setContentPane(p);
		frame.setClosable(false);
		frame.pack();
		frame.setLocation(SIZEX / 2, SIZEY * 3 / 4);
		desktop.add(frame);
		frame.show();*/
		world.changeViewPoint(World.VIEW_FROM_TOP, null);
		// start
		//simulator.startBackgroundMode();
	}

	/**
	 * The simbad main. Process command line arguments and launch simbad.
	 */
	public static void main(String[] args) {
		// process options
		boolean backgroundMode = false;
		for (int i = 0; i < args.length; i++) {
			if ("-bg".compareTo(args[i]) == 0) backgroundMode = true;
		}
		// Check java3d presence
		try {
			Class.forName("javax.media.j3d.VirtualUniverse");
		} catch (ClassNotFoundException e1) {
			JOptionPane.showMessageDialog(null, "Simbad requires Java 3D", "Simbad 3D", JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
		}

		//request antialising
		System.setProperty("j3d.implicitAntialiasing", "true");

		new Simbad(new simbad.demo.BaseDemo(), "Simbad Demo", null, backgroundMode);
	}

	public Simulator getSimulator() {
		return simulator;
	}

	public World getWorld() {
		return this.world;
	}

	public JInternalFrame getDesktopPane() {
		return desktop;
	}

	/////////////////////////
	// Class methods
	public static Simbad getSimbadInstance() {
		return simbadInstance;
	}


}