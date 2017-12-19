/* author: Louis Hugues - created on 12 f�vr. 2005  */
package simbad.gui;

import simbad.sim.EnvironmentDescription;
import simbad.sim.Simulator;
import simbad.sim.World;

import javax.media.j3d.Canvas3D;
import java.awt.*;


/**
 * Runs simbad simulator in batch mode with no user interface (only small 3d window).
 * Using the folling scenario:
 * construct->reset->step, step ,..., step -> dispose-> System.exit
 */
public class Simbatch {
    int counter;
    Frame frame;
    World world;
    Simulator simulator;
    Canvas3D canvas3d;
    Panel panel;
    /** Construct a batch version of Simbad simulator */
    public Simbatch(EnvironmentDescription ed, boolean do3DRendering ){
        counter = 0;
        world = new World(ed, null);
        // !!!!
        // We need absolutly to show the 3d world in a window
        // otherwise it reveal a memory bug in java3d
        // see Bug ID: 4727054 
        // !!!!!
        canvas3d = world.getCanvas3D();
        frame = new Frame();
        panel = new Panel();
        panel.setLayout(new BorderLayout());
        panel.add(canvas3d);
        
        frame.add(panel);
        frame.pack();
        frame.setSize(100, 100);
        frame.setVisible(true);
         if (!do3DRendering){
           frame.hide();
           // Do not render
           canvas3d.stopRenderer();
         }
        simulator = new Simulator(null, world, ed);
    }

    /** Restart the simulation */
    public void reset(){
        simulator.resetSimulation();
        simulator.initBehaviors();
    }
    /** perform one step - call it in your main loop*/
    public void step(){
        simulator.simulateOneStep();
        }
        
    /** Dispose resource at end.**/
    public void dispose(){
       
        simulator.dispose();
        world.dispose();
        simulator = null;
        world = null;
        canvas3d = null;
      
        frame.dispose();
        System.runFinalization();
        System.gc();
    }

}
