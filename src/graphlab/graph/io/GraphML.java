// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.graph.io;

import graphlab.graph.atributeset.EdgeAttrSet;
import graphlab.graph.atributeset.GraphAttrSet;
import graphlab.graph.atributeset.VertexAttrSet;
import graphlab.graph.graph.EdgeModel;
import graphlab.graph.graph.GraphModel;
import graphlab.graph.graph.VertexModel;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Author: @Reza Mohammadi
 */
public class GraphML {
    public static HashMap<String, String> graphMLGraphKeys = new HashMap<String, String>();
    public static HashMap<String, String> graphMLVertexKeys = new HashMap<String, String>();
    public static HashMap<String, String> graphMLEdgeKeys = new HashMap<String, String>();

    public static String vertex2GraphML(VertexModel v) {
        int id = v.getId();
        String s = "    <node id=\"" + id + "\">\n";
        for (String key : graphMLVertexKeys.keySet()) {
            VertexAttrSet _ = new VertexAttrSet(v);
            if (_.get(key) != null)
                s += "      <data key=\"n_" + key + "\">"
                        + _.get(key).toString()
                        + "</data>\n";
        }
        s += "    </node>\n";
        return s;
    }

    public static String edge2GraphML(EdgeModel e) {
        String s = "";
        String s2 = "";
        if (e.getId() != null)
            s = " id=\"" + e.getId() + "\"";
        EdgeAttrSet _ = new EdgeAttrSet(e);
// edge direction now is setted only in graph
//        if ((_.get(EdgeAttrSet.DIRECTED) != null) && (e.model.getAttributes().get(EdgeModel.DIRECTED).toString().equalsIgnoreCase("true")))
//            s += " directed=\"true\"";
        s2 = "    <edge source=\"" + e.source.getId()
                + "\" target=\"" + e.target.getId()
                + "\"" + s + ">\n";
        for (String key : graphMLEdgeKeys.keySet()) {
            if (_.get(key) != null)
                s2 += "      <data key=\"e_" + key + "\">"
                        + _.get(key).toString()
                        + "</data>\n";
        }
        s2 += "    </edge>\n";
        return s2;
    }

    public static String graph2GraphML(GraphModel g) {
        initializeGMLKeys(g);
        GraphAttrSet _ = new GraphAttrSet(g);
        String graphML = graphMLKeys();
        graphML +=
                "  <graph id=\"" + g.getLabel()
                        + "\" edgedefault=\"" + (g.isDirected() ? GraphAttrSet.EDGEDEFAULT_DIRECTED : GraphAttrSet.EDGEDEFAULT_UNDIRECTED)
                        + "\">\n";
        for (String ss : graphMLGraphKeys.keySet()) {
            if (!ss.equals(GraphAttrSet.EDGEDEFAULT))
                if (_.get(ss) != null)
                    graphML += "    <data key=\"g_" + ss + "\">"
                            + _.get(ss)
                            + "</data>\n";
        }
        for (VertexModel v : g) {
            graphML += GraphML.vertex2GraphML(v);
        }
        for (Iterator<EdgeModel> it = g.edgeIterator(); it.hasNext();) {
            EdgeModel e = it.next();
            graphML += GraphML.edge2GraphML(e);
        }
        graphML += "  </graph>\n";
        return graphML;
    }
//    public static String graphSelection2GraphML(GraphModel g, Collection<Vertex> v, Collection<Edge> e){
//        String graphML = graphMLKeys();
//        graphML +=
//                "  <graph id=\"" + g.getAttribute(Graph.ID)
//                + "\" edgedefault=\"" + (g.isDirected()?Graph.EDGEDEFAULT_DIRECTED:GraphModel.EDGEDEFAULT_UNDIRECTED)
//                + "\">\n";
//        for (String ss : Graph.graphMLGraphKeys.keySet()) {
//            if (ss!= Graph.EDGEDEFAULT)
//            if (g.getAttribute(ss) != null)
//                graphML += "    <data key=\"g_" + ss + "\">"
//                        + g.getAttribute(ss)
//                        + "</data>\n";
//        }
//        for (Vertex vv: v) {
//            graphML += GraphML.vertex2GraphML(vv);
//        }
//        for (Edge ee:e) {
//            graphML += GraphML.edge2GraphML(ee);
//        }
//        graphML += "  </graph>\n";
//        return graphML;
//
//    }

    public static String graphMLKeys() {
        String s = "";
        for (Map.Entry<String, String> e : graphMLGraphKeys.entrySet()) {
            s += "  <key id=\"g_" + e.getKey() + "\" for=\"graph\" attr.name=\""
                    + e.getKey() + "\" attr.type=\"" + e.getValue() + "\"/>\n";
        }
        for (Map.Entry<String, String> e : graphMLVertexKeys.entrySet()) {
            s += "  <key id=\"n_" + e.getKey() + "\" for=\"node\" attr.name=\""
                    + e.getKey() + "\" attr.type=\"" + e.getValue() + "\"/>\n";
        }
        for (Map.Entry<String, String> e : graphMLEdgeKeys.entrySet()) {
            s += "  <key id=\"e_" + e.getKey() + "\" for=\"edge\" attr.name=\""
                    + e.getKey() + "\" attr.type=\"" + e.getValue() + "\"/>\n";
        }
        return s;
    }

    static void initializeGMLKeys(GraphModel g) {
        graphMLEdgeKeys.clear();
        graphMLGraphKeys.clear();
        graphMLVertexKeys.clear();
        for (VertexModel v : g) {
            VertexAttrSet _ = new VertexAttrSet(v);
            Map<String, Object> atr = _.getAttrs();
            for (String name : atr.keySet()) {
                if (atr.get(name) != null)
                    graphMLVertexKeys.put(name, atr.get(name).getClass().getName());
            }
        }
        Iterator<EdgeModel> ie = g.edgeIterator();
        while (ie.hasNext()) {
            EdgeModel edge = ie.next();
            Map<String, Object> atr = new EdgeAttrSet(edge).getAttrs();
            for (String name : atr.keySet()) {
                if (atr.get(name) != null)
                    graphMLEdgeKeys.put(name, atr.get(name).getClass().getName());
            }
        }
        Map<String, Object> atr = new GraphAttrSet(g).getAttrs();
        for (String name : atr.keySet()) {
            if (atr.get(name) != null)
                graphMLGraphKeys.put(name, atr.get(name).getClass().getName());
        }
    }
}