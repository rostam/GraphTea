// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.graph.ui;

import graphtea.graph.JGraph;
import graphtea.graph.atributeset.GraphAttrSet;
import graphtea.graph.atributeset.GraphNotifiableAttrSet;
import graphtea.graph.event.GraphSelectData;
import graphtea.graph.graph.AbstractGraphRenderer;
import graphtea.graph.graph.FastRenderer;
import graphtea.graph.graph.GraphModel;
import graphtea.platform.attribute.AttributeListener;
import graphtea.platform.core.BlackBoard;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.HashMap;

/**
 * provides a tabbed UI Interface for editing multiple graphs in multiple tabs
 *
 * @author azin azadi
 */
public class GTabbedGraphPane extends GTabbedPane {
    public static final String NAME = "GTabbedGraphPane";

    private static int lastGraphIndex;

    public static GTabbedGraphPane getCurrentGTabbedGraphPane(BlackBoard b) {
        return b.getData(GTabbedGraphPane.NAME);
    }

    private HashMap<String, GraphModel> graphs = new HashMap<String, GraphModel>();

    public GTabbedGraphPane(BlackBoard b) {
        super(b);
        jtp.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                reTab();
            }
        });
        b.setData(GTabbedGraphPane.NAME, this);
    }

    public static Boolean defaultDirectedChoice = false;

    protected void reTab() {
        super.reTab();
        JComponent sc = (JComponent) jtp.getSelectedComponent();

        if (sc instanceof GSplitedPane)
            sc = ((GSplitedPane) sc).main;
        if (sc instanceof JGraph) {
            GraphModel graph = ((JGraph) sc).getGraph();
            AbstractGraphRenderer graphV = ((JGraph) sc).getGraphView();
            //set the graph that all actions are work with to the selected graph
            blackboard.setData(GraphAttrSet.name, graph);
            blackboard.setData(AbstractGraphRenderer.EVENT_KEY, graphV);
            GraphSelectData c = new GraphSelectData();
            c.g = graph;
            blackboard.setData(GraphSelectData.EVENT_KEY, c);
        }
    }

    public HashMap<String,GraphModel> getGraphs() {
        return graphs;
    }

    /**
     * create a tab for the given graph, the name of tab will be "G" + graph label
     *
     * @param g graphmodel
     */
    public void addGraph(GraphModel g) {
        AbstractGraphRenderer v = new FastRenderer(g, blackboard);
        final JGraph c = new JGraph(g, v);// JGraph.getNewComponent(blackboard);

        if (g.getLabel() == null)
            g.setLabel("G" + String.valueOf(lastGraphIndex++));

        final JComponent gsp = addComponent(g.getLabel(), c, true);

        graphs.put(g.getLabel(),g);

        new GraphNotifiableAttrSet(g).addAttributeListener(new AttributeListener() {
            JComponent cc = gsp;

            public void attributeUpdated(String name, Object oldVal, Object newVal) {
                if (name.equals(GraphAttrSet.LABEL))
                    updateTitle((String) newVal, cc);
            }
        });
    }
//
//    public void addComponent(String title, JComponent compoenent) {
//        jtp.add(title, new JScrollPane(compoenent));

    //    }

    private void updateTitle(String newVal, JComponent c) {
        for (int i = 0; i < jtp.getTabCount(); i++) {
            if (jtp.getComponentAt(i) == c) {
                jtp.setTitleAt(i, newVal + "");
                if (jtp.getTabComponentAt(i) instanceof ButtonTabComponent) {
                    ButtonTabComponent buttonTabComponent = (ButtonTabComponent) jtp.getTabComponentAt(i);
                    JLabel l = buttonTabComponent.label;
                    l.setText(l.getText());
                    l.validate();
                    c.validate();
                    c.doLayout();
                    jtp.doLayout();
                    jtp.validate();
                }
            }
        }
    }

    public Component getComponent(BlackBoard b) {
        b.setData(NAME, this);
        return jtp;
    }

    //-------------------------------------------------------------------------------
    /**
     * shows a message as a notification to the user, It will hide when the hideNotificationMessage is called
     * and the prv message will be shown,
     * if formatIt=true, an html formatting will be applied to the message to make it nicer, do it if you don't
     * pass a complete html message
     *
     * @param message
     * @param b
     * @param formatIt
     */
    public static void showNotificationMessage(String message, BlackBoard b, boolean formatIt) {
        if (formatIt) message = htmlFormat(message);
        GHTMLPageComponent c = getCurrentGHTMLPageComponent(b);
        if (c == null) JOptionPane.showMessageDialog(null, message);
        else
            c.showNotificationMessage(message);
    }

    /**
     * shows a message to the user, It will hide when the showNotificationMessage is called, and show again
     * when the hideNotificationMessage is called
     * if formatIt=true, an html formatting will be applied to the message to make it nicer, do it if you don't
     * pass a complete html message
     */
    public static void setMessage(String message, BlackBoard b, boolean formatIt) {
        if (formatIt) message = htmlFormat(message);
        GHTMLPageComponent c = getCurrentGHTMLPageComponent(b);
        if (c == null) JOptionPane.showMessageDialog(null, message);
        else
            getCurrentGHTMLPageComponent(b).setMessage(message);
    }

    /**
     * hides the previously showing message
     */
    public static void hideNotificationMessage(BlackBoard b) {
        GHTMLPageComponent pc = getCurrentGHTMLPageComponent(b);
        if (pc != null)
            pc.hideNotificationMessage();
    }

    /**
     * shows a message and hide it after a given time
     */
    public static void showTimeNotificationMessage(String message, final BlackBoard b, final long timeMillis, boolean formatIt) {
        showNotificationMessage(message, b, formatIt);
        new Thread() {
            public void run() {
                try {
                    Thread.sleep(timeMillis + 10000);
                    hideNotificationMessage(b);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    static String htmlFormat(String message) {
        return "<HTML><BODY><CENTER><h1>" + message + "</h1></CENTER></BODY></HTML>";
    }

    //------------------------------------------------
    /**
     * returns the current GHTMLPageComponent which is used in the top of a tab,
     * if there is not any of them returns null
     *
     * @param b
     * @return
     * @see GTabbedPane
     */
    public static GHTMLPageComponent getCurrentGHTMLPageComponent(BlackBoard b) {
        Object o = b.getData(CURRENT_COMPONENT);
        if (o instanceof GSplitedPane) {
            GSplitedPane g = (GSplitedPane) o;
            return g.helper;
        }
        return null;

    }
}

