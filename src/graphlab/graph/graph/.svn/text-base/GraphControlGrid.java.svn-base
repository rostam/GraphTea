// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphlab.graph.graph;

import graphlab.graph.event.GraphModelListener;
import graphlab.library.util.Pair;

import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;

//todo: it can not handle the cas hat vertex positions are changed. unusable
/**
 * This class puts graph elements in a grid (for example in a 10*10 grid) so that searching for the nearest
 * element to a point of graph performs faster, larger grid will have faster searches,
 * it does not guaranteed to have a fast search at every situation, but overally in a grid of size n (a n*n rectangle) the
 * search for the vertex on the given point will finish in V(G)/(n*n) steps. for an edge it will be some thing near E(G)/(n*n)
 * (it can be larger up to E(G)/(n) because edges aare lines and can fill more than one cell in the grid.)
 * In the worst case the search for the vertex will be run in V(G) steps and for the Edge in E(g) steps.
 * <p/>
 * Adding and removing element to/from graph has some cost here. but the cost will not appear until a search for an element
 * in graph is performed. the cost is small (in average case) when the graph bounds doesn't become larger, but when it become larger the cost is
 * V(G) (or E(G))
 *
 * @author Azin Azadi
 */
public class GraphControlGrid implements GraphModelListener {
    /**
     * a flag to recreate Grids
     */
    private boolean refresh;

    private GraphModel g;

    private VertexModel fakeVertex;
    private EdgeModel fakeEdge;

    VertexModel[] verticesGrid[][];


    EdgeModel[] edgesGrid[][];

    int planeDivisions = 10;

    Rectangle2D.Double gbounds;


    public GraphControlGrid(GraphModel g) {
        g.addGraphListener(this);
        this.g = g;
        refresh = true;
        fakeVertex = new VertexModel();
        VertexModel _fakeVertex = new VertexModel();
        fakeVertex.setLocation(new GraphPoint(Double.MAX_VALUE, Double.MAX_VALUE));
        _fakeVertex.setLocation(new GraphPoint(Double.MAX_VALUE, Double.MAX_VALUE));
        fakeEdge = new EdgeModel(fakeVertex, _fakeVertex);
    }

    public Pair mindiste(GraphModel g, GraphPoint p) {
        if (refresh) {
            refresh();
            refresh = false;
        }
        if (p.x < gbounds.x || p.y < gbounds.y || p.x > gbounds.x + gbounds.width || p.y > gbounds.y + gbounds.height)
            return new Pair<EdgeModel, Double>(null, 100000d);
        int ix = (int) ((p.x - gbounds.x) / gbounds.width * planeDivisions);
        int iy = (int) ((p.y - gbounds.y) / gbounds.height * planeDivisions);

        if (p.x < gbounds.x)
            ix = 0;
        if (p.y < gbounds.y)
            iy = 0;
        if (p.x > gbounds.x + gbounds.width)
            ix = planeDivisions - 1;
        if (p.y > gbounds.y + gbounds.height)
            iy = planeDivisions - 1;

        double min = 100000;
        EdgeModel mine = null;
        EdgeModel[] ei = edgesGrid[ix][iy];
        for (EdgeModel e : ei) {
            Line2D.Double l = new Line2D.Double(e.source.getLocation().x, e.source.getLocation().y, e.target.getLocation().x, e.target.getLocation().y);
            double dist = l.ptLineDistSq(p);
            if (min > dist) {
                min = dist;
                mine = e;
            }
        }
        return new Pair(mine, min);
    }

    public Pair<VertexModel, Double> mindistv(GraphPoint p) {
        if (refresh) {
            refresh();
            refresh = false;
        }

        int ix = (int) ((p.x - gbounds.x) / gbounds.width * planeDivisions);
        int iy = (int) ((p.y - gbounds.y) / gbounds.height * planeDivisions);

        if (p.x < gbounds.x)
            ix = 0;
        if (p.y < gbounds.y)
            iy = 0;
        if (p.x > gbounds.x + gbounds.width)
            ix = planeDivisions - 1;
        if (p.y > gbounds.y + gbounds.height)
            iy = planeDivisions - 1;

        double min = 100000;
        VertexModel minv = null;
        VertexModel[] vv = verticesGrid[ix][iy];
        for (VertexModel v : vv) {
            double dist = Math.pow(v.getLocation().x - p.x, 2) + Math.pow(v.getLocation().y - p.y, 2);
            if (min > dist) {
                min = dist;
                minv = v;
            }
        }
        if (minv == null)
            System.out.println("minv: null");
        else
            System.out.println("minv: " + minv.toString());
        return new Pair<VertexModel, Double>(minv, min);
    }

    private void refresh() {
        verticesGrid = new VertexModel[planeDivisions][planeDivisions][0];
        gbounds = g.getZoomedBounds();
        if (gbounds.width == 0)
            gbounds.width = 1;
        if (gbounds.height == 0)
            gbounds.height = 1;
        for (VertexModel v : g) {
            addVertexToGrid(v);
        }

        edgesGrid = new EdgeModel[planeDivisions][planeDivisions][0];
        Iterator<EdgeModel> ie = g.edgeIterator();
        while (ie.hasNext()) {
            EdgeModel e = ie.next();
            addEdgeToGrid(e);
        }

    }


    private void addVertexToGrid(VertexModel v) {
        GraphPoint loc = v.getLocation();
        int ix = (int) ((loc.x - gbounds.x) / gbounds.width * planeDivisions);
        int iy = (int) ((loc.y - gbounds.y) / gbounds.height * planeDivisions);
        addVertexToGrid(ix, iy, v);
    }

    private void removeVertexFromGrid(VertexModel v) {
        GraphPoint loc = v.getLocation();
        int ix = (int) ((loc.x - gbounds.x) / gbounds.width * planeDivisions);
        int iy = (int) ((loc.y - gbounds.y) / gbounds.height * planeDivisions);
        VertexModel[] s = verticesGrid[ix][iy];
        for (int i = 0; i < s.length; i++) {
            if (s[i] == v) {
                s[i] = fakeVertex;
                return;
            }
        }
    }

    private void addEdgeToGrid(EdgeModel e) {
        GraphPoint loc1 = e.source.getLocation();
        GraphPoint loc2 = e.source.getLocation();
        int ix1 = (int) ((loc1.x - gbounds.x) / gbounds.width * planeDivisions);
        int iy1 = (int) ((loc1.y - gbounds.y) / gbounds.height * planeDivisions);
        int ix2 = (int) ((loc2.x - gbounds.x) / gbounds.width * planeDivisions);
        int iy2 = (int) ((loc2.y - gbounds.y) / gbounds.height * planeDivisions);

        int dy = iy2 - iy1;
        int dx = ix2 - ix1;
        int maxCells = Math.abs(dx) + Math.abs(dy);
        addEdgeToGrid(ix1, iy1, e);
        int prvx = ix1, prvy = iy1;
        for (int i = 0; i < maxCells; i++) {           //go through the line from 1 to 2 and put the edge on the grid
            double alpha = i / (double) maxCells;
            int x = (int) (alpha * ix2 + (1 - alpha) * ix1);
            int y = (int) (alpha * iy2 + (1 - alpha) * iy1);
            if (x != prvx || y != prvy) {
                addEdgeToGrid(x, y, e);
                prvx = x;
                prvy = y;
            }
        }
    }

    private void addEdgeToGrid(int ix, int iy, EdgeModel e) {
        EdgeModel[] s = edgesGrid[ix][iy];
        if (s == null)
            s = new EdgeModel[0];
        boolean found = false;
        for (int i = 0; i < s.length; i++) {
            if (s[i] == fakeEdge) {
                s[i] = e;
                found = true;
                break;
            }
        }
        if (!found) {      //there were no fake edges so make the array larger
            EdgeModel _[] = new EdgeModel[s.length + 2];
            System.arraycopy(s, 0, _, 0, s.length);
            _[s.length] = e;
            _[s.length + 1] = fakeEdge;         //make it a little more larger for better performance, similar to Vectors
            edgesGrid[ix][iy] = _;
        }
    }

    private void addVertexToGrid(int ix, int iy, VertexModel v) {
        VertexModel[] s = verticesGrid[ix][iy];
        if (s == null)
            s = new VertexModel[0];
        boolean found = false;
        for (int i = 0; i < s.length; i++) {
            if (s[i] == fakeVertex) {
                s[i] = v;
                found = true;
                break;
            }
        }
        if (!found) {      //there were no fake edges so make the array larger
            VertexModel[] _ = new VertexModel[s.length + 2];
            System.arraycopy(s, 0, _, 0, s.length);
            _[s.length] = v;
            _[s.length + 1] = fakeVertex;         //make it a little more larger for better performance, similar to Vectors
            verticesGrid[ix][iy] = _;
        }
    }

    private void removeEdgeFromGrid(EdgeModel e) {
        GraphPoint loc1 = e.source.getLocation();
        GraphPoint loc2 = e.source.getLocation();
        int ix1 = (int) ((loc1.x - gbounds.x) / gbounds.width * planeDivisions);
        int iy1 = (int) ((loc1.y - gbounds.y) / gbounds.height * planeDivisions);
        int ix2 = (int) ((loc2.x - gbounds.x) / gbounds.width * planeDivisions);
        int iy2 = (int) ((loc2.y - gbounds.y) / gbounds.height * planeDivisions);

        int dy = iy2 - iy1;
        int dx = ix2 - ix1;
        int maxCells = Math.abs(dx) + Math.abs(dy);
        removeEdge(edgesGrid[ix1][iy1], e);
        int prvx = ix1, prvy = iy1;
        for (int i = 0; i < maxCells; i++) {           //go through the line from 1 to 2 and put the edge on the grid
            double alpha = i / (double) maxCells;
            int x = (int) (alpha * ix2 + (1 - alpha) * ix1);
            int y = (int) (alpha * iy2 + (1 - alpha) * iy1);
            if (x != prvx || y != prvy) {
                EdgeModel[] s = edgesGrid[x][y];
                removeEdge(s, e);
                prvx = x;
                prvy = y;
            }
        }
    }

    private void removeEdge(EdgeModel[] s, EdgeModel e) {
        for (int ii = 0; ii < s.length; ii++) {
            if (s[ii] == e) {
                s[ii] = fakeEdge;
                break;
            }
        }
    }


    public void vertexAdded(VertexModel v) {
        Rectangle2D.Double tbounds = g.getZoomedBounds();
        if (tbounds.x < gbounds.x || tbounds.y < gbounds.y || tbounds.width > gbounds.width || tbounds.height > gbounds.height)
            refresh = true;
        else {
            addVertexToGrid(v);
        }

    }

    public void vertexRemoved(VertexModel v) {
        removeVertexFromGrid(v);
    }

    public void edgeAdded(EdgeModel e) {
        Rectangle2D.Double tbounds = g.getZoomedBounds();
        if (tbounds.x < gbounds.x || tbounds.y < gbounds.y || tbounds.width > gbounds.width || tbounds.height > gbounds.height)
            refresh = true;
        else {
            addEdgeToGrid(e);
        }
    }

    public void edgeRemoved(EdgeModel e) {
        removeEdgeFromGrid(e);
    }

    public void graphCleared() {
        refresh = true;
    }

    public void repaintGraph() {
        refresh = true;
    }

}