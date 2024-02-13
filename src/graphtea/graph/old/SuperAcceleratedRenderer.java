// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.graph.old;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.platform.core.BlackBoard;

import java.awt.*;

/**
 * a super accelerated renderer, not working completely yet.
 */
public class SuperAcceleratedRenderer extends AcceleratedRenderer {
    public SuperAcceleratedRenderer(GraphModel g, BlackBoard blackboard) {
        super(g, blackboard);
//        t = new Thread(this);
//        t.setPriority(Thread.MIN_PRIORITY);
        isRunning = true;
    }

    private static final int FRAME_DELAY = 1000; // 20ms. implies 50fps (1000/20) = 50

    boolean isRunning;
    //    JComponent gui;
    long cycleTime;

    public void paint(Graphics2D g) {


    }


    public void run() {
        cycleTime = System.currentTimeMillis();
//todo: should extend canvas for the following lines
//        createBufferStrategy(2);
//        BufferStrategy strategy = getBufferStrategy();

        // Game Loop
        while (isRunning) {
//            updateGUI(strategy);
            synchFramerate();
        }
    }


    private void synchFramerate() {
        cycleTime = cycleTime + FRAME_DELAY;
        long difference = cycleTime - System.currentTimeMillis();
        try {
            Thread.sleep(Math.max(0, difference));
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void vertexAdded(Vertex v) {
        v.setLabel(v.getId() + "");
        v.setVertexListener(this);
    }

    public void edgeAdded(Edge e) {
        e.setLabel(e.getId());
        e.setEdgeListener(this);
//        e.updateBounds();
    }
}
