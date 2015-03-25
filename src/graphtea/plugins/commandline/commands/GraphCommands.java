// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.commandline.commands;

import Jama.Matrix;
import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.GraphPoint;
import graphtea.graph.graph.Vertex;
import graphtea.graph.ui.GTabbedGraphPane;
import graphtea.library.algorithms.goperators.*;
import graphtea.library.algorithms.goperators.product.*;
import graphtea.platform.core.BlackBoard;
import graphtea.platform.core.exception.ExceptionHandler;
import graphtea.platform.lang.CommandAttitude;
import graphtea.platform.parameter.Parameter;
import graphtea.plugins.graphgenerator.core.PositionGenerators;
import graphtea.plugins.main.GraphData;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

/**
 * @author Mohammad Ali Rostami
 * @email ma.rostami@yahoo.com
 */

public class GraphCommands {
    BlackBoard bb;

    public GraphCommands(BlackBoard bb) {
        this.bb = bb;
        datas = new GraphData(bb);
    }

    GraphData datas;

    @CommandAttitude(name = "current_graph", abbreviation = "_cg"
            , description = "the matrix related to the graph")
    public GraphModel getCurrentGraph() {
        return datas.getGraph();
    }


    @CommandAttitude(name = "matrix", abbreviation = "_mat"
            , description = "the matrix related to the graph")
    public Matrix graph2Matrix() {
        return datas.getGraph().getAdjacencyMatrix();
    }

    @CommandAttitude(name = "weighted_matrix", abbreviation = "_wmat"
            , description = "the weighted matrix related to the graph (with MatLab matrix format)")
    public String weightMatrix() {
        GraphModel g = datas.getGraph();
        String ret = "";
        for (Vertex v : g) {
            for (Vertex w : g) {
                Edge e = g.getEdge(v, w);
                ret += " " + (e == null ? "0" : e.getWeight()) + " ,";
            }
            ret = ret.substring(0, ret.length() - 1);
            ret += ";";
        }
        if (g.getVerticesCount() > 0)
            ret = ret.substring(0, ret.length() - 1);
        return "[" + ret + "]";
    }

    @CommandAttitude(name = "matlab_matrix", abbreviation = "_mt"
            , description = "the weighted matrix related to the graph (with MatLab matrix format)")
    public String matlabMatrix() {
        GraphModel g = datas.getGraph();
        String ret = "";
        for (Vertex v : g) {
            for (Vertex w : g) {
                Edge e = g.getEdge(v, w);
                ret += " " + (e == null ? "0" : "1") + " ,";
            }
            ret = ret.substring(0, ret.length() - 1);
            ret += ";";
        }

        if (g.getVerticesCount() > 0)
            ret = ret.substring(0, ret.length() - 1);
        return "[" + ret + "]";
    }


    @CommandAttitude(name = "add_tab", abbreviation = "_at"
            , description = "Adds a new Tab to GUI")
    public void addTab() {
        datas.core.addTab();
    }

    @CommandAttitude(name = "show_graph", abbreviation = "_sg"
            , description = "Shows the given graph in a new TAB")
    public void showGraph(@Parameter(name = "graph")GraphModel g) {
        datas.core.addTabNoGUI(g.isDirected(), bb);
        datas.getGraph().addSubGraph(g, new Rectangle (100,100, 500,500));

    }

    @CommandAttitude(name = "clear_graph", abbreviation = "_cg"
            , description = "Clears the graph")
    public void clearGraph() {
        datas.core.clearGraph();
    }

    @CommandAttitude(name = "close_tab", abbreviation = "_ct"
            , description = "closes the selected tab from GUI")
    public void closeTab() {
        datas.core.closeTab();
    }

    @CommandAttitude(name = "cut", abbreviation = "_c"
            , description = "Cuts the selected data to clipboard")
    public void cutToClipboard() throws ShellCommandException {
        if (!(datas.select.isSelectionEmpty())) {
            datas.core.cutToClipboard(datas.select.getSelected());
        } else {
            throw new ShellCommandException("Nothing has been selected.");
        }
    }

    @CommandAttitude(name = "paste", abbreviation = "_p"
            , description = "Pastes from the clipboard")
    public void pasteFromClipboard() {
        datas.core.pasteFromClipboard();
    }

    @CommandAttitude(name = "redo", abbreviation = "_r"
            , description = "Redos the last action")
    public void redo() {
        datas.core.redo();
    }

    @CommandAttitude(name = "reset_graph", abbreviation = "_rg"
            , description = "Resets the Graph")
    public void resetGraph() {
        datas.core.resetGraph();
    }

    @CommandAttitude(name = "undo", abbreviation = "_u"
            , description = "Undos the last performed action")
    public void undo() {
        datas.core.undo();
    }

//    @CommandAttitude(name = "matlab" , abbreviation = "_ml"
//            , description = "")
//    public String matlab(@Parameter(name = "matlab_command") String matlab_command) {
//        return
//    }

//    @CommandAttitude(name = "zoom_in", abbreviation = "_zi"
//            , description = "zooms the board in")
//    public void zoomIn() {
//        datas.core.zoomIn();
//    }                                \
//
//    @CommandAttitude(name = "zoom_out", abbreviation = "_zo"
//            , description = "zooms the board out")
//    public void zoomOut() {
//        datas.core.zoomOut();
//    }

    @CommandAttitude(name = "copy_selected", abbreviation = "_c"
            , description = "Copies the selected data to clipboard")
    public void copyToClipboard() throws ShellCommandException {
        if (!(datas.select.isSelectionEmpty())) {
            datas.core.copyToClipboard(datas.select.getSelected());
        } else {
            throw new ShellCommandException("Nothing has been selected.");
        }
    }

    @CommandAttitude(name = "induced", abbreviation = "_induce", description = "Vertex Induced subgraph of given vertices")
    public GraphModel induced(@Parameter(name = "graph")GraphModel g
            , @Parameter(name = "vertices")Object[] c) {
        GTabbedGraphPane gtp = bb.getData(GTabbedGraphPane.NAME);
        resetGraph();
        HashSet hs = new HashSet();

        for (Object vm : c) {
            hs.add(getVertexById((Integer) vm, g));
        }
        GraphModel gm = (GraphModel) VertexInduced.induced(g, hs);
        gm.setDirected(g.isDirected());
        gtp.addGraph(gm);
        return gm;
    }

    public Vertex getVertexById(int id, GraphModel g) {
        for (Vertex v : g)
            if (v.getId() == id)
                return v;
        return null;
    }

    @CommandAttitude(name = "edge_induced", abbreviation = "_e_induce", description = "Edge Induced subgraph of selected edges")
    public GraphModel edge_induced(@Parameter(name = "graph")GraphModel g) {
        GTabbedGraphPane gtp = bb.getData(GTabbedGraphPane.NAME);
        HashSet hs = datas.select.getSelectedEdges();
        resetGraph();
        GraphModel gm = (GraphModel) EdgeInduced.edgeInduced(g, hs);
        gm.setDirected(g.isDirected());
        gtp.addGraph(gm);
        return gm;
    }

    @CommandAttitude(name = "gjoin", abbreviation = "_jn", description = "Joins two graphs")
    public void gjoin(@Parameter(name = "first_graph")GraphModel g1,
                      @Parameter(name = "second_graph")GraphModel g2) {
        GTabbedGraphPane gtp = bb.getData(GTabbedGraphPane.NAME);
        GraphModel graphModel = (GraphModel) GraphUnion.union(g1, g2);
        graphModel.setDirected(g1.isDirected());
        gtp.addGraph(graphModel);
    }


    @CommandAttitude(name = "gunion", abbreviation = "_un", description = "Creates the union of two given graphs")
    public GraphModel gunion(@Parameter(name = "first_graph")GraphModel g1
            , @Parameter(name = "second_graph")GraphModel g2) {
        GTabbedGraphPane gtp = bb.getData(GTabbedGraphPane.NAME);
        GraphModel graphModel = (GraphModel) GraphUnion.union(g1, g2);
        graphModel.setDirected(g1.isDirected());
        gtp.addGraph(graphModel);
        return graphModel;
    }

    @CommandAttitude(name = "gcorona", abbreviation = "_gcorona", description = "Creates the union of two given graphs")
    public GraphModel gcorona(@Parameter(name = "first_graph")GraphModel g1
            , @Parameter(name = "second_graph")GraphModel g2) {
        GTabbedGraphPane gtp = bb.getData(GTabbedGraphPane.NAME);
        GraphModel graphModel = (GraphModel) GraphCorona.corona(g1,g2);
        Vertex[] varr=graphModel.getVertexArray();
        int k =0;
        System.out.println("gvc" + g1.getVerticesCount() + " " +
             g2.getVerticesCount()+ " " + graphModel.getVerticesCount());
        for(int i=g1.getVerticesCount();i< graphModel.getVerticesCount();
            i=i+g2.getVerticesCount(),k++) {
            for(int j=0;j<g2.getVerticesCount();j++) {
                int index=g1.getVerticesCount()+g2.getVerticesCount()*k + j;
                GraphPoint gp1 = varr[index].getLocation();
                GraphPoint gp2 = varr[k].getLocation();
                GraphPoint gp3 = GraphPoint.sub(gp2,gp1);
                System.out.println("div" + gp3);
                gp3= GraphPoint.div(gp3,2);
                gp3.add(gp1);
                graphModel.getVertex(varr[index].getId()).setLocation(gp3);
            }
        }
        graphModel.setDirected(g1.isDirected());
        gtp.addGraph(graphModel);
        return graphModel;
    }

    @CommandAttitude(name = "gsum", abbreviation = "_gsum",
            description = "Creates the sum of two given graphs")
    public GraphModel gsum(@Parameter(name = "first_graph")GraphModel g1
            , @Parameter(name = "second_graph")GraphModel g2) {
        GTabbedGraphPane gtp = bb.getData(GTabbedGraphPane.NAME);
        GraphModel graphModel = (GraphModel) GraphUnion.union(g1,g2);
        Vertex[] varr = graphModel.getVertexArray();
        for(int i=0; i < g1.getVerticesCount();i++) {
            for(int j=0;j<g2.getVerticesCount();j++) {
                graphModel.addEdge(new Edge(
                        varr[i],varr[g1.getVerticesCount()+j]
                ));
            }
        }
        graphModel.setDirected(g1.isDirected());
        gtp.addGraph(graphModel);
        return graphModel;
    }

    @CommandAttitude(name = "cartesian_product", abbreviation = "_cproduct", description = "Computes and shows the cartesian product of given graphs")
    public void cartesian_product(@Parameter(name = "first_graph")GraphModel g1
            , @Parameter(name = "second_graph")GraphModel g2) {
        GTabbedGraphPane gtp = bb.getData(GTabbedGraphPane.NAME);
        GCartesianProduct p = new GCartesianProduct();
        GraphModel graphModel = (GraphModel) p.multiply(g1, g2);
        graphModel.setDirected(g1.isDirected());
        int n = graphModel.getVerticesCount();
        Point ps[] = PositionGenerators.circle(250, 300, 300, n);
        int count = 0;
        for (Vertex v : graphModel) {
            v.setLocation(new GraphPoint(ps[count].x, ps[count].y));
            count++;
        }
        gtp.addGraph(graphModel);
    }

    @CommandAttitude(name = "gdisjunction",
            abbreviation = "_disj",
            description = "Computes the disjunction of graphs")
    public void disjunction(@Parameter(name = "first_graph")GraphModel g1
            , @Parameter(name = "second_graph")GraphModel g2) {
        GTabbedGraphPane gtp = bb.getData(GTabbedGraphPane.NAME);
        GDisjunction p = new GDisjunction();
        GraphModel graphModel = (GraphModel) p.multiply(g1, g2);
        graphModel.setDirected(g1.isDirected());
        int n = graphModel.getVerticesCount();
        Point ps[] = PositionGenerators.circle(200, 300, 300, n);
        int count = 0;
        for (Vertex v : graphModel) {
            v.setLocation(new GraphPoint(ps[count].x, ps[count].y));
            count++;
        }
        gtp.addGraph(graphModel);
    }

    @CommandAttitude(name = "gsymdiff",
            abbreviation = "_symdiff",
            description = "Computes the symmetric difference of graphs")
    public void symdiff(@Parameter(name = "first_graph")GraphModel g1
            , @Parameter(name = "second_graph")GraphModel g2) {
        GTabbedGraphPane gtp = bb.getData(GTabbedGraphPane.NAME);
        GSymDiff p = new GSymDiff();
        GraphModel graphModel = (GraphModel) p.multiply(g1, g2);
        graphModel.setDirected(g1.isDirected());
        int n = graphModel.getVerticesCount();
        Point ps[] = PositionGenerators.circle(200, 300, 300, n);
        int count = 0;
        for (Vertex v : graphModel) {
            v.setLocation(new GraphPoint(ps[count].x, ps[count].y));
            count++;
        }
        gtp.addGraph(graphModel);

    }

    @CommandAttitude(name = "gcomposition",
            abbreviation = "composition",
            description = "Computes the composition of graphs")
    public void composition(@Parameter(name = "first_graph")GraphModel g1
            , @Parameter(name = "second_graph")GraphModel g2) {
        GTabbedGraphPane gtp = bb.getData(GTabbedGraphPane.NAME);
        GComposition p = new GComposition();
        GraphModel graphModel = (GraphModel) p.multiply(g1, g2);
        graphModel.setDirected(g1.isDirected());
        int n = graphModel.getVerticesCount();
        Point ps[] = PositionGenerators.circle(200, 300, 300, n);
        int count = 0;
        for (Vertex v : graphModel) {
            v.setLocation(new GraphPoint(ps[count].x, ps[count].y));
            count++;
        }
        gtp.addGraph(graphModel);

    }

    @CommandAttitude(name = "load_graphml", abbreviation = "_lg"
            , description = "loads a graph from a GrapmML file")
    public void loadGraphML(@Parameter(name = "filename")String fileName) {
        try {
            datas.saveLoad.loadGraphML(new File(fileName));
        } catch (IOException e) {
            ExceptionHandler.catchException(e);
        } catch (ParserConfigurationException e) {
            ExceptionHandler.catchException(e);
        } catch (SAXException e) {
            ExceptionHandler.catchException(e);
        }
    }

    Vertex getVertexByID(String id) {
        int ID = Integer.parseInt(id);
        for (Vertex v : datas.getGraph()) {
            if (v.getId() == ID)
                return v;
        }
        return null;
    }

    Vertex getVertexByLabel(String label) {
        for (Vertex v : datas.getGraph()) {
            if (v.getLabel().equals(label))
                return v;
        }
        return null;
    }
}


