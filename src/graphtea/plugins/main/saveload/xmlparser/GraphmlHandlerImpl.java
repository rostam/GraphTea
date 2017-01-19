// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.main.saveload.xmlparser;

import graphtea.graph.atributeset.EdgeAttrSet;
import graphtea.graph.atributeset.GraphAttrSet;
import graphtea.graph.atributeset.VertexAttrSet;
import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.Vertex;
import graphtea.platform.StaticUtils;
import graphtea.platform.core.BlackBoard;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import java.util.HashMap;

public class GraphmlHandlerImpl implements GraphmlHandler {

    public static final boolean DEBUG = false;
    public HashMap<String, Vertex> vByID = new HashMap<>();


    public GraphModel g;
    private Vertex curv = null;
    private Edge cure = null;
    public BlackBoard bb;
    private VertexAttrSet curvAS;
    private EdgeAttrSet cureAS;

    /**
     * @param graph if graph is null a new graph will be generated which can be get by getGraph() , otherwise the loaded graph
     *              will be added to the given graph
     */

    public GraphmlHandlerImpl(GraphModel graph) {
        g = graph;
    }

    /**
     * @see GraphmlHandlerImpl(GraphModel graph)
     */
    public GraphmlHandlerImpl() {
        g = null;
    }


    public GraphmlHandlerImpl(BlackBoard blackBoard) {
        bb = blackBoard;
//        g = new Graph(bb);
    }

    public GraphModel getGraph() {
        return g;
    }

    public static HashMap<String, String> graphMLGraphKeys = new HashMap<>();
    public static HashMap<String, String> graphMLVertexKeys = new HashMap<>();
    public static HashMap<String, String> graphMLEdgeKeys = new HashMap<>();

    public void handle_key(final java.lang.String data, final Attributes meta) throws SAXException {
        String s = meta.getValue("for");
        String id = meta.getValue("id");
        String attrname = meta.getValue("attr.name");
        String attrtype = meta.getValue("attr.type");
        if (s.equalsIgnoreCase("graph")) {
            graphMLGraphKeys.put(attrname, attrtype);
        } else if (s.equalsIgnoreCase("node")) {
            graphMLVertexKeys.put(attrname, attrtype);
        } else {
            graphMLEdgeKeys.put(attrname, attrtype);
        }
        if (DEBUG) System.err.println("handle_key: " + data + "," + id + "," + s);
    }

    public void start_edge(final Attributes meta) throws SAXException {
        Vertex v1 = vByID.get(meta.getValue("source"));
        Vertex v2 = vByID.get(meta.getValue("target"));

        if (DEBUG)
            System.out.println("Edge between : (" + meta.getValue(EdgeAttrSet.SOURCE) + ")" + v1 + ",(" + meta.getValue(EdgeAttrSet.TARGET) + ")" + v2);
        Edge e = new Edge(v1, v2);
        //todo: the id can not be setted (it's a fix value)
//        e.setID(meta.getValue(Edge.ID));
        g.insertEdge(e);
        cure = e;
        cureAS = new EdgeAttrSet(e);
        if (DEBUG) System.err.println("start_edge: " + meta);
    }

    public void end_edge() throws SAXException {

        if (DEBUG) System.err.println("end_edge()");
    }

    public void handle_locator(final Attributes meta) throws SAXException {

        if (DEBUG) System.err.println("handle_locator: " + meta);
    }

    public void handle_data(final java.lang.String data, final Attributes meta) throws SAXException {
        String s1 = meta.getValue("key");
        if (s1.charAt(0) == 'g') {
            String ss = graphMLGraphKeys.get(s1.substring(2));
            //it is handled on start graph
//            if (ss != Graph.EDGEDEFAULT)
            new GraphAttrSet(g).put(s1.substring(2), StaticUtils.fromString(ss, data));
        } else if (s1.charAt(0) == 'n') {
            String ss = graphMLVertexKeys.get(s1.substring(2));
            curvAS.put(s1.substring(2), StaticUtils.fromString(ss, data));
        } else {
            String ss = graphMLEdgeKeys.get(s1.substring(2));
            cureAS.put(s1.substring(2), StaticUtils.fromString(ss, data));
        }
        if (DEBUG) System.err.println("handle_data: " + data + "," + s1);
    }

    public void start_node(final Attributes meta) throws SAXException {
        String id = meta.getValue("id");
        Vertex v = new Vertex();
        vByID.put(id, v);
        curv = v;
        curvAS = new VertexAttrSet(curv);
        if (DEBUG)
            System.out.println("Vertex added : " + v);

        if (DEBUG) System.err.println("start_node: " + meta);
    }

    public void end_node() throws SAXException {
        g.insertVertex(curv);

        if (DEBUG) System.err.println("end_node()");
    }

    public void start_graph(final Attributes meta) throws SAXException {
        if (g == null) {
//todo
            g = new GraphModel(meta.getValue("edgedefault").equals("directed"));
        }
        g.setLabel(meta.getValue("id"));

        if (DEBUG) System.err.println("start_graph: " + meta);
    }

    public void end_graph() throws SAXException {

        if (DEBUG) System.err.println("end_graph()");
    }

    public void start_endpoint(final Attributes meta) throws SAXException {

        if (DEBUG) System.err.println("start_endpoint: " + meta);
    }

    public void end_endpoint() throws SAXException {

        if (DEBUG) System.err.println("end_endpoint()");
    }

    public void start_graphml(final Attributes meta) throws SAXException {

        if (DEBUG) System.err.println("start_graphml: " + meta);
    }

    public void end_graphml() throws SAXException {

        if (DEBUG) System.err.println("end_graphml()");
    }

    public void start_hyperedge(final Attributes meta) throws SAXException {

        if (DEBUG) System.err.println("start_hyperedge: " + meta);
    }

    public void end_hyperedge() throws SAXException {

        if (DEBUG) System.err.println("end_hyperedge()");
    }

    public void start_port(final Attributes meta) throws SAXException {

        if (DEBUG) System.err.println("start_port: " + meta);
    }

    public void end_port() throws SAXException {

        if (DEBUG) System.err.println("end_port()");
    }

    public void handle_desc(final java.lang.String data, final Attributes meta) throws SAXException {

        if (DEBUG) System.err.println("handle_desc: " + data);
    }
}