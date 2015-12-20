// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.main.core;

import graphtea.graph.atributeset.GraphAttrSet;
import graphtea.graph.graph.Edge;
import graphtea.graph.graph.GraphModel;
import graphtea.graph.graph.SubGraph;
import graphtea.graph.graph.Vertex;
import graphtea.graph.ui.GHTMLPageComponent;
import graphtea.platform.core.BlackBoard;
import graphtea.platform.core.exception.ExceptionHandler;
import graphtea.platform.plugin.PluginMethods;
import graphtea.plugins.main.ccp.Copy;
import graphtea.plugins.main.ccp.Cut;
import graphtea.plugins.main.core.actions.AddTab;
import graphtea.plugins.main.core.actions.CloseTab;
import graphtea.plugins.main.core.actions.ResetGraph;
import graphtea.plugins.main.core.actions.StatusBarMessage;
import graphtea.plugins.main.core.actions.edge.AddEdge;
import graphtea.plugins.main.core.actions.graph.ClearGraph;
import graphtea.plugins.main.core.actions.vertex.AddVertex;
import graphtea.plugins.main.core.actions.vertex.DeleteVertex;

import javax.swing.*;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author azin azadi

 */
public class CorePluginMethods implements PluginMethods {
    BlackBoard blackboard;
//************************    E D G E      ********************************

    public CorePluginMethods(BlackBoard blackboard) {
        this.blackboard = blackboard;
    }

    /**
     * adds e to g
     */
    public void addEdge(GraphModel g, Edge e) {
        AddEdge.doJob(g, e.source, e.target);
    }

    /**
     * adds e to current editing graph
     */
    public void addEdge(Edge e) {
        AddEdge.doJob(getGraph(), e.source, e.target);
    }

    /**
     * create and adds a new edge from v1, v2 to g
     */
    public void addEdge(GraphModel g, Vertex v1, Vertex v2) {
        AddEdge.doJob(g, v1, v2);
    }

    public void addEdge(Vertex v1, Vertex v2) {
        AddEdge.doJob(getGraph(), v1, v2);
    }

    public void deleteEdge(GraphModel g, Edge e) {
        g.removeEdge(e);
    }

//*****************         G R A P H      *************************

    /**
     * removes all edges and vertices of g
     */
    public void clearGraph(GraphModel g) {
        ClearGraph.destroyGraph(g);
    }

    /**
     * removes all edges and vertices of current graph
     */
    public void clearGraph() {
        ClearGraph.destroyGraph(getGraph());
    }

//****************       V E R T E X      ***************************

    /**
     * adds a new vertex to a random point of the graph and returns it
     */
    public Vertex addVertex(GraphModel g) {
        return AddVertex.addVertexToRandomPosition(g);
    }

    /**
     * add a new vertex to a random position of the current graph and returns it
     */
    public Vertex addVertex() {
        return AddVertex.addVertexToRandomPosition(getGraph());
    }

    /**
     * adds a vertex to the given point of graph
     */
    public Vertex addVertex(GraphModel g, int x, int y) {
        return AddVertex.doJob(g, x, y);
    }

    /**
     * adds a vertex to the given point of current graph
     */
    public Vertex addVertex(int x, int y) {
        return AddVertex.doJob(getGraph(), x, y);
    }

    /**
     * deletes a vertex from it's coressponding graph
     */
    public void deleteVertex(GraphModel g, Vertex v) {
        DeleteVertex.doJob(g, v);
    }


//*********************   TABBED EDITING      *******************************

    /**
     * @see graphtea.plugins.main.core.actions.AddTab#addTab(graphtea.platform.core.BlackBoard)
     */
    public void addTab() {
        AddTab.addTab(blackboard);
    }

    /**
     * @see graphtea.plugins.main.core.actions.AddTab#addTabNoGUI(boolean, graphtea.platform.core.BlackBoard)
     */
    public void addTabNoGUI(boolean isdirected , BlackBoard blackboard) {
        AddTab.addTabNoGUI(isdirected, blackboard);
    }
    /**
     * @see graphtea.plugins.main.core.actions.AddTab#displayGraph(graphtea.graph.graph.GraphModel,graphtea.platform.core.BlackBoard)
     */
    public void showGraph(GraphModel g) {
        AddTab.displayGraph(g, blackboard);
    }

    /**
     * @see graphtea.plugins.main.core.actions.CloseTab#dojob(graphtea.platform.core.BlackBoard)
     */
    public void closeTab() {
        CloseTab.dojob(blackboard);
    }
//*****************************

    /**
     * @see graphtea.plugins.main.core.actions.ResetGraph#ResetGraph(graphtea.platform.core.BlackBoard)
     */
    public void resetGraph() {
        ResetGraph.resetGraph(getGraph());
    }

    /**
     * @see graphtea.plugins.main.core.actions.ResetGraph#ResetGraph(graphtea.platform.core.BlackBoard)
     */
    public void resetGraph(GraphModel g) {
        ResetGraph.resetGraph(g);
    }

    /**
     * @see graphtea.plugins.main.core.actions.StatusBarMessage#setMessage(graphtea.platform.core.BlackBoard,String)
     */
    public void showStatusBarMessage(String s) {
        StatusBarMessage.setMessage(blackboard, s);
    }

    /**
     * @see graphtea.plugins.main.core.actions.StatusBarMessage#showQuickMessage(graphtea.platform.core.BlackBoard,String)
     */
    public void showQuickMessageInStatusbar(String message) {
        StatusBarMessage.showQuickMessage(blackboard, message);
    }

//******************   Cut Copy Paste  ************************

    /**
     * @see graphtea.plugins.main.ccp.Copy#copy(graphtea.graph.graph.SubGraph)
     */
    public void copyToClipboard(SubGraph selection) {
        Copy.copy(selection);
    }

    public void pasteFromClipboard() {
        //Todo:Azin jaan een ye giri daare ke behet migam hala:D (rouzbeh)

    }

    public void cutToClipboard(SubGraph selection) {
        Cut.cut(selection, getGraph(), blackboard);

    }

    private GraphModel getGraph() {
        return blackboard.getData(GraphAttrSet.name);
    }

    public static GraphModel getGraph(BlackBoard blackboard) {
        return blackboard.getData(GraphAttrSet.name);
    }

    //***************************
    /**
     * shows the givve page in a new dialog,
     * Note that the used html viewer is GHTMLPageComponent, which is internally
     * uses a JEditorPane, but the blackboard in dialog will be a new blackboard,
     * (in the case of you want to use "bsh:" feature of GHTMLPageComponent,
     * for this use showPageInDialog(URL, blackboard).
     */
    public static void showPageInDialog(String pageUrl, String title) {
        BlackBoard blackboard = new BlackBoard();
        URL page = null;
        try {
            page = new URL(pageUrl);
        } catch (MalformedURLException e) {
            ExceptionHandler.catchException(e);
        }
        if (page != null)
            showPageInDialog(title, blackboard, page);
//        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * shows the given page in a new dialog,
     * Note that the used html viewer is GHTMLPageComponent, which is internally
     * uses a JEditorPane,
     */
    private static void showPageInDialog(String title, BlackBoard blackboard, URL page) {
        JFrame f = new JFrame(title);
        GHTMLPageComponent browserPane = new GHTMLPageComponent(blackboard);
        browserPane.setPage(page);
        f.add(new JScrollPane(browserPane));
        f.setVisible(true);
        f.setSize(500, 500);
        f.validate();
        f.setResizable(false);
    }

}