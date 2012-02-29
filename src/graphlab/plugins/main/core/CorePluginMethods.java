// GraphLab Project: http://graphlab.sharif.edu
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphlab.plugins.main.core;

import graphlab.graph.atributeset.GraphAttrSet;
import graphlab.graph.graph.EdgeModel;
import graphlab.graph.graph.GraphModel;
import graphlab.graph.graph.SubGraph;
import graphlab.graph.graph.VertexModel;
import graphlab.graph.ui.GHTMLPageComponent;
import graphlab.platform.core.BlackBoard;
import graphlab.platform.core.exception.ExceptionHandler;
import graphlab.platform.plugin.PluginMethods;
import graphlab.plugins.commonplugin.undo.UndoableActionOccuredData;
import graphlab.plugins.commonplugin.undo.undo.RedoAction;
import graphlab.plugins.commonplugin.undo.undo.UndoAction;
import graphlab.plugins.main.ccp.Copy;
import graphlab.plugins.main.ccp.Cut;
import graphlab.plugins.main.core.actions.AddTab;
import graphlab.plugins.main.core.actions.CloseTab;
import graphlab.plugins.main.core.actions.ResetGraph;
import graphlab.plugins.main.core.actions.StatusBarMessage;
import graphlab.plugins.main.core.actions.edge.AddEdge;
import graphlab.plugins.main.core.actions.graph.ClearGraph;
import graphlab.plugins.main.core.actions.vertex.AddVertex;
import graphlab.plugins.main.core.actions.vertex.DeleteVertex;

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
    public void addEdge(GraphModel g, EdgeModel e) {
        AddEdge.doJob(g, e.source, e.target);
    }

    /**
     * adds e to current editing graph
     */
    public void addEdge(EdgeModel e) {
        AddEdge.doJob(getGraph(), e.source, e.target);
    }

    /**
     * create and adds a new edge from v1, v2 to g
     */
    public void addEdge(GraphModel g, VertexModel v1, VertexModel v2) {
        AddEdge.doJob(g, v1, v2);
    }

    public void addEdge(VertexModel v1, VertexModel v2) {
        AddEdge.doJob(getGraph(), v1, v2);
    }

    public void deleteEdge(GraphModel g, EdgeModel e) {
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
    public VertexModel addVertex(GraphModel g) {
        return AddVertex.addVertexToRandomPosition(g);
    }

    /**
     * add a new vertex to a random position of the current graph and returns it
     */
    public VertexModel addVertex() {
        return AddVertex.addVertexToRandomPosition(getGraph());
    }

    /**
     * adds a vertex to the given point of graph
     */
    public VertexModel addVertex(GraphModel g, int x, int y) {
        return AddVertex.doJob(g, x, y);
    }

    /**
     * adds a vertex to the given point of current graph
     */
    public VertexModel addVertex(int x, int y) {
        return AddVertex.doJob(getGraph(), x, y);
    }

    /**
     * deletes a vertex from it's coressponding graph
     */
    public void deleteVertex(GraphModel g, VertexModel v) {
        DeleteVertex.doJob(g, v);
    }

//*********************      U N D O / R E D O        *************************

    /**
     * @see graphlab.plugins.commonplugin.undo.undo.UndoAction#undo(graphlab.platform.core.BlackBoard)
     */
    public void undo() {
        UndoAction.undo(blackboard);
    }

    /**
     * @see graphlab.plugins.commonplugin.undo.undo.RedoAction#redo(graphlab.platform.core.BlackBoard)
     */
    public void redo() {
        RedoAction.redo(blackboard);
    }

    /**
     * puts data in the stack of undo/redo actions, so it will be regarded as an undoable action and will be undone by the rules of undo/redo.
     */
    public void addUndoData(UndoableActionOccuredData data) {
        blackboard.setData(UndoableActionOccuredData.EVENT_KEY, data);
    }
//*********************   TABBED EDITING      *******************************

    /**
     * @see graphlab.plugins.main.core.actions.AddTab#addTab(graphlab.platform.core.BlackBoard)
     */
    public void addTab() {
        AddTab.addTab(blackboard);
    }

    /**
     * @see graphlab.plugins.main.core.actions.AddTab#addTabNoGUI(boolean, graphlab.platform.core.BlackBoard)
     */
    public void addTabNoGUI(boolean isdirected , BlackBoard blackboard) {
        AddTab.addTabNoGUI(isdirected, blackboard);
    }
    /**
     * @see graphlab.plugins.main.core.actions.AddTab#displayGraph(graphlab.graph.graph.GraphModel,graphlab.platform.core.BlackBoard)
     */
    public void showGraph(GraphModel g) {
        AddTab.displayGraph(g, blackboard);
    }

    /**
     * @see graphlab.plugins.main.core.actions.CloseTab#dojob(graphlab.platform.core.BlackBoard)
     */
    public void closeTab() {
        CloseTab.dojob(blackboard);
    }
//*****************************

    /**
     * @see graphlab.plugins.main.core.actions.ResetGraph#ResetGraph(graphlab.platform.core.BlackBoard)
     */
    public void resetGraph() {
        ResetGraph.resetGraph(getGraph());
    }

    /**
     * @see graphlab.plugins.main.core.actions.ResetGraph#ResetGraph(graphlab.platform.core.BlackBoard)
     */
    public void resetGraph(GraphModel g) {
        ResetGraph.resetGraph(g);
    }

    /**
     * @see graphlab.plugins.main.core.actions.StatusBarMessage#setMessage(graphlab.platform.core.BlackBoard,String)
     */
    public void showStatusBarMessage(String s) {
        StatusBarMessage.setMessage(blackboard, s);
    }

    /**
     * @see graphlab.plugins.main.core.actions.StatusBarMessage#showQuickMessage(graphlab.platform.core.BlackBoard,String)
     */
    public void showQuickMessageInStatusbar(String message) {
        StatusBarMessage.showQuickMessage(blackboard, message);
    }

//******************   Cut Copy Paste  ************************

    /**
     * @see graphlab.plugins.main.ccp.Copy#copy(graphlab.graph.graph.SubGraph)
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