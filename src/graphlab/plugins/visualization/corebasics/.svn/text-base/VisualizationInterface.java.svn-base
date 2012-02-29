// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphlab.plugins.visualization.corebasics;

import graphlab.graph.graph.EdgeModel;
import graphlab.graph.graph.GraphModel;
import graphlab.graph.graph.VertexModel;

import java.awt.geom.Point2D;
import java.util.HashMap;

/**
 * @author Rouzbeh Ebrahimi
 * Email: ruzbehus@gmail.com
 */
public interface VisualizationInterface {
    /*
      @param g
     */
    public void setWorkingGraph(GraphModel g);

    public abstract HashMap<VertexModel, Point2D> getNewVertexPlaces();

    public abstract HashMap<EdgeModel, Point2D> getNewEdgeCurveControlPoints();
}
