// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/

package graphtea.graph.ui;

import graphtea.platform.core.BlackBoard;
import graphtea.platform.core.exception.ExceptionHandler;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.HashMap;

/**
 * provides a tabbed UI Interface for editing multiple graphs in multiple tabs
 *
 * @author Azin Azadi
 */
public class GTabbedPane {
    public static final String NAME = "GTabbedPane";
    public static final String CURRENT_COMPONENT = "GTabbedPane current component";

    public JTabbedPane jtp;
    public BlackBoard blackboard;

    public GTabbedPane(BlackBoard b) {
        blackboard = b;
        jtp = new JTabbedPane();
//        jtp.setDoubleBuffered(true);
        jtp.setBorder(null);
        jtp.setOpaque(false);
//        jtp.setBackground(Color.white);
        jtp.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                reTab();
            }
        });

        b.setData(GTabbedPane.NAME, this);
    }

    protected void reTab() {
        JComponent sc = (JComponent) jtp.getSelectedComponent();
        blackboard.setData(CURRENT_COMPONENT, sc);
    }

    public JTabbedPane getTabedPane() {
        return jtp;
    }

    /**
     * adds a tab to the tabbed pane, if addHelper=true, the added component will be a
     * GSplitedPane which have a GHTMLPageComponent on it's top as a helper
     *
     * @param title The component title
     * @param component The component
     * @param addHelper Switch for helper
     */
    public JComponent addComponent(String title, JComponent component, boolean addHelper) {
        if (addHelper)
            component = new GSplitedPane(new GHTMLPageComponent(blackboard), component);
        jtp.addTab(title, component);
        jtp.setSelectedComponent(component);
        jtp.setTabComponentAt(jtp.getTabCount() - 1, new graphtea.graph.old.ButtonTabComponent(jtp));
        return component;
    }

    public HashMap<Class, Class<? extends JComponent>> supportedType = new HashMap<>();

    public void add(Object o, String label) {
        if (supportedType.containsKey(o.getClass())) {
            try {
                JComponent c = (JComponent) supportedType.get(o.getClass()).getConstructors()[0].newInstance(o);
                jtp.addTab(label, c);
                jtp.setSelectedComponent(c);
            } catch (Exception e) {
                ExceptionHandler.catchException(e);
            }
        } else System.err.println("Error in type");
    }

    public void registerType(Class clazz, Class<? extends JComponent> jcclazz) {
        supportedType.put(clazz, jcclazz);
    }
}