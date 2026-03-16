// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU Lesser General Public License (LGPL): http://www.gnu.org/licenses/

package graphtea.extensions.actions;

import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GPoint;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.platform.parameter.Parameter;
import graphtea.platform.parameter.Parametrizable;
import graphtea.plugins.main.GraphData;
import graphtea.plugins.main.extension.GraphActionExtension;

/**
 * Shared template for TotalGraph, EdgeSemitotalGraph, and VertexSemitotalGraph.
 *
 * <p>All three subdivide each edge into k+1 segments. They differ in three
 * independent boolean features controlled by subclass overrides:
 * <ul>
 *   <li>{@link #markSubdivisionVertices()} — tag subdivision vertices with their edge</li>
 *   <li>{@link #addCurvedOriginalEdge()} — add a curved edge between original endpoints</li>
 *   <li>{@link #addSubdivisionVertexEdges()} — connect subdivision vertices of adjacent edges</li>
 * </ul>
 *
 * @author Mohammad Ali Rostami
 * @author Azin Azadi
 */
abstract class SubdividedGraphBase implements GraphActionExtension, Parametrizable {
    @Parameter
    public int k = 2;

    /** Whether to tag each subdivision vertex with its originating edge. */
    protected boolean markSubdivisionVertices() { return false; }

    /** Whether to add a curved edge between the original source and target. */
    protected boolean addCurvedOriginalEdge() { return false; }

    /** Whether to add edges between subdivision vertices of adjacent edges. */
    protected boolean addSubdivisionVertexEdges() { return false; }

    @Override
    public void action(GraphData graphData) {
        GraphModel g1 = graphData.getGraph();
        GraphModel g2 = new GraphModel(false);
        g2.setIsEdgesCurved(true);

        for (Vertex v : g1.getVertexArray()) {
            Vertex tmp = new Vertex();
            tmp.setLocation(v.getLocation());
            g2.addVertex(tmp);
        }

        for (Edge e : g1.getEdges()) {
            GPoint v1 = e.source.getLocation();
            GPoint v2 = e.target.getLocation();
            GPoint step = GPoint.div(GPoint.sub(v2, v1), k + 1);

            Vertex src = g2.getVertex(e.source.getId());
            for (int i = 0; i < k; i++) {
                Vertex tmp = new Vertex();
                if (markSubdivisionVertices()) {
                    tmp.getProp().obj = e;
                }
                GPoint pos = new GPoint(step);
                pos.multiply(i + 1);
                pos.add(v1);
                tmp.setLocation(pos);
                g2.addVertex(tmp);
                g2.addEdge(new Edge(src, tmp));
                src = tmp;
            }
            g2.addEdge(new Edge(src, g2.getVertex(e.target.getId())));

            if (addCurvedOriginalEdge()) {
                addCurvedEdge(g2, g2.getVertex(e.source.getId()), g2.getVertex(e.target.getId()));
            }
        }

        if (addSubdivisionVertexEdges()) {
            for (Vertex v1 : g2.getVertexArray()) {
                for (Vertex v2 : g2.getVertexArray()) {
                    if (v1.getProp().obj != null && v2.getProp().obj != null) {
                        Edge e1 = (Edge) v1.getProp().obj;
                        Edge e2 = (Edge) v2.getProp().obj;
                        if (edgeConnects(e1, e2)) {
                            addCurvedEdge(g2, v1, v2);
                        }
                    }
                }
            }
        }

        graphData.core.showGraph(g2);
    }

    private static void addCurvedEdge(GraphModel g, Vertex src, Vertex tgt) {
        Edge ee = new Edge(src, tgt);
        GPoint vector = GPoint.div(GPoint.sub(tgt.getLocation(), src.getLocation()),
                GPoint.sub(tgt.getLocation(), src.getLocation()).norm());
        GPoint ppVec = new GPoint(-vector.y, vector.x);
        ppVec.multiply(30.0);
        ee.setCurveControlPoint(ppVec);
        g.addEdge(ee);
    }

    private static boolean edgeConnects(Edge e1, Edge e2) {
        if (e1.source.getId() == e2.source.getId()) { return true; }
        if (e1.target.getId() == e2.source.getId()) { return true; }
        if (e1.source.getId() == e2.target.getId()) { return true; }
        return e1.target.getId() == e2.target.getId();
    }

    @Override
    public String getCategory() { return "Transformations"; }
}
