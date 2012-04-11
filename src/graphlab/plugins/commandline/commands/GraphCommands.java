// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.commandline.commands;

import Jama.Matrix;
import graphlab.graph.graph.Edge;
import graphlab.graph.graph.GraphModel;
import graphlab.graph.graph.GraphPoint;
import graphlab.graph.graph.Vertex;
import graphlab.graph.ui.GTabbedGraphPane;
import graphlab.library.algorithms.goperators.EdgeInduced;
import graphlab.library.algorithms.goperators.GraphUnion;
import graphlab.library.algorithms.goperators.VertexInduced;
import graphlab.library.algorithms.goperators.product.GCartesianProduct;
import graphlab.library.algorithms.goperators.product.GPopularProduct;
import graphlab.platform.core.BlackBoard;
import graphlab.platform.core.exception.ExceptionHandler;
import graphlab.platform.lang.CommandAttitude;
import graphlab.platform.parameter.Parameter;
import graphlab.plugins.graphgenerator.core.PositionGenerators;
import graphlab.plugins.main.GraphData;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.File;
import java.io.IOException;
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

//    @CommandAttitude(name = "matlab" , abbreviation = "_ml"
//            , description = "")
//    public String matlab(String command) {
//        String parsed;
////        return ConnectorReportExtension(parsed);
//    }

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

//    @CommandAttitude(name = "gcomplement", abbreviation = "_gc", description = "complement")
//    public GraphModel gcomplement(@Parameter(name = "graph_name") GraphModel g1_name) {
//        GTabbedGraphPane gtp = bb.getData(GTabbedGraphPane.NAME);
//        GraphModel g1 = null;
//        for (Component component : gtp.getTabedPane().getComponents()) {
//            if (!(component instanceof JGraph))
//                continue;
//
//            GraphModel graph = ((JGraph) component).getGraph();
//            if (graph.getLabel().equals(g1_name))
//                g1 = graph;
//        }
//        g1 = g1_name;
////        GraphModel graphModel = GComplement.complement((GraphModel)g1);
////        graphModel.setDirected(g1.isDirected());
////        gtp.addGraph(graphModel);
//        return g1;
//    }

//    @CommandAttitude(name = "help_window", abbreviation = "_hw"
//            , description = "Shows the help window")
//    public void showHelpWindow() {
//        datas.help.showHelpWindow();
//    }

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

    @CommandAttitude(name = "product", abbreviation = "_product", description = "Computes and shows Popular product of given graphs")
    public void product(@Parameter(name = "first_graph")GraphModel g1
            , @Parameter(name = "second_graph")GraphModel g2) {
        GTabbedGraphPane gtp = bb.getData(GTabbedGraphPane.NAME);
        GPopularProduct p = new GPopularProduct();
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

//    @CommandAttitude(name = "preview_file", abbreviation = "_pf"
//            , description = "Previews the given filename")
//    public void showPreview(@Parameter(name = "filename:") String fileName) throws ShellCommandException {
//        try {
//            datas.preview.showPreview(fileName);
//        } catch (Exception e) {
//            throw new ShellCommandException("File does not exist of corrupted");
//        }
//
//    }

    //

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


