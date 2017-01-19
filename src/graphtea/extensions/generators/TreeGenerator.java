// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.extensions.generators;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.platform.lang.ArrayX;
import graphtea.platform.parameter.Parameter;
import graphtea.platform.parameter.Parametrizable;
import graphtea.plugins.graphgenerator.GraphGenerator;
import graphtea.plugins.graphgenerator.core.PositionGenerators;
import graphtea.plugins.graphgenerator.core.SimpleGeneratorInterface;
import graphtea.plugins.graphgenerator.core.extension.GraphGeneratorExtension;

import java.awt.*;

/**
 * @author azin azadi, Hoshmand Hasannia
 */
//@CommandAttitude(name = "generate_tree" , abbreviation = "_g_t"
//        ,description = "generate a tree with depth and degree")
public class TreeGenerator implements GraphGeneratorExtension, Parametrizable, SimpleGeneratorInterface {
    @Parameter(name = "Height")
    public static Integer depth = 3;
    @Parameter(name = "Degree")
    public static Integer degree = 3;
    @Parameter(name = "Positioning Method")
    public static ArrayX<String> m =
            new ArrayX<>("Circular", "Backward", "UpDown");

//    String positioning;

    int n;
    GraphModel g;

    public void setWorkingGraph(GraphModel g) {
        this.g = g;
    }

/*    public NotifiableAttributeSet getParameters() {
        PortableNotifiableAttributeSetImpl a=new PortableNotifiableAttributeSetImpl();
        a.put ("Degree",3);
        a.put ("Height",3);
        a.put("Positioning Method",new ArrayX<String>("Circular", "Backward" , "UpDown"));
        return a;
    }

    public void setParameters(NotifiableAttributeSet parameters) {
        d = Integer.parseInt(""+parameters.getAttributes().get("Degree"));
        h = Integer.parseInt(""+parameters.getAttributes().get("Height"));
        positioning = ((ArrayX)parameters.getAttributes().get("Positioning Method")).getValue().toString();
        n = (int) ((Math.pow(d,h+1)-1)/(d-1));
    }*/

    public String getName() {
        return "Complete Tree";
    }

    public String getDescription() {
        return "Generates a complete tree";
    }

    Vertex[] v;

    public Vertex[] getVertices() {
        n = (int) ((Math.pow(degree, depth + 1) - 1) / (degree - 1));
        Vertex[] ret = new Vertex[n];
        for (int i = 0; i < n; i++)
            ret[i] = new Vertex();
        v = ret;
        return ret;
    }

    public Edge[] getEdges() {
        n = (int) ((Math.pow(degree, depth + 1) - 1) / (degree - 1));
        Edge[] ret = new Edge[n - 1];
        for (int i = 0; i < n - 1; i++) {
            ret[i] = new Edge(v[i + 1], v[i / degree]);
        }
        return ret;
    }

    public Point[] getVertexPositionsBackward() {
        Point[] ret = new Point[n];
        ret[0] = new Point(20000 / 2, 20000 / 2);
        int last = 1;
        int ww = 20000;
        int hh = 20000;
        int rad0 = Math.min(ww, hh) / 2;
        for (int i = 0; i < n - Math.pow(degree, depth); i++) {
            int h = (int) ((Math.log((i + 1) * (degree - 1))) / Math.log(degree));
            int rad = (i == 0 ? rad0 : (int) (rad0 / Math.pow(2.5, h)));
            Point _p[] = PositionGenerators.circle(rad, ret[i].x, ret[i].y, degree);
            System.arraycopy(_p, 0, ret, last, degree);
            last += degree;
        }
        return ret;
    }

    public Point[] getVertexPositions() {
        String positioning = m.getValue();
        n = (int) ((Math.pow(degree, depth + 1) - 1) / (degree - 1));
        if (positioning.equals("Backward")) {
            return getVertexPositionsBackward();
        } else if (positioning.equals("UpDown")) {
            return getVertexPositionUpdown();
        }
        return getVertexPositionsCircular();
    }


    private Point[] getVertexPositionUpdown() {
        Point[] ret = new Point[n];
        double vwidth = 200;
        double vheight = 200;
        double yratio = vheight / depth;
        for (int i = depth; i >= 0; i--) {
            int vertexnInRow = (int) Math.pow(degree, i);
            double xratio = vwidth / (vertexnInRow + 1);
            int firstInRow = 0;
            for (int j = 0; j <= i - 1; j++) {
                firstInRow += Math.pow(degree, j);
            }
            firstInRow++;

            for (int j = 1; j <= vertexnInRow; j++) {
                double x = j * xratio;
                ret[firstInRow + j - 2] = new Point((int) x, (int) yratio * i);
            }
        }
        return ret;
    }

    public Point[] getVertexPositionsCircular() {
        Point[] ret = new Point[n];
        ret[0] = new Point(0, 0);
        int last = 1;
        for (int h = 1; h <= depth; h++) {
            int n = (int) Math.pow(degree, h);
            Point p[] = PositionGenerators.circle(30 * h * h, 0, 0, n);
            System.arraycopy(p, 0, ret, last, n);
            last += n;
        }
        return ret;
    }

    public String checkParameters() {
    	if(depth<0 || degree<0)return" Both depth & degree must be positive!";
    	else
    		return null;
    }

    public GraphModel generateGraph() {
        return GraphGenerator.getGraph(false, this);
    }

    /**
     * generates a Complete Tree with given parameters
     */
    public static GraphModel generateTree(int depth, int degree) {
        TreeGenerator.depth = depth;
        TreeGenerator.degree = degree;
        return GraphGenerator.getGraph(false, new TreeGenerator());
    }

    @Override
    public String getCategory() {
        return "Trees";
    }
}
