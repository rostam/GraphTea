// GraphTea Project: http://github.com/graphtheorysoftware/GraphTea
// Copyright (C) 2012 Graph Theory Software Foundation: http://GraphTheorySoftware.com
// Copyright (C) 2008 Mathematical Science Department of Sharif University of Technology
// Distributed under the terms of the GNU General Public License (GPL): http://www.gnu.org/licenses/
package graphtea.plugins.main.core;

import graphtea.graph.graph.GraphColoring;
import graphtea.graph.graph.SubGraph;
import graphtea.graph.old.ArrowHandler;
import graphtea.graph.old.GShape;
import graphtea.graph.old.GStroke;
import graphtea.graph.old.PolygonArrow;
import graphtea.platform.Application;
import graphtea.platform.StaticUtils;
import graphtea.platform.core.BlackBoard;
import graphtea.platform.core.Listener;
import graphtea.platform.core.exception.ExceptionHandler;
import graphtea.platform.preferences.lastsettings.StorableOnExit;
import graphtea.plugins.main.ui.*;
import graphtea.ui.UI;
import graphtea.ui.UIUtils;
import graphtea.ui.components.GFrame;
import graphtea.ui.components.gpropertyeditor.editors.inplace.GSimpleComboEditor;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;

/**
 * @author Reza Mohammadi, azin
 */
public class Init implements graphtea.platform.plugin.PluginInterface, StorableOnExit {

    public static Class uiClass = null;

    public void init(final BlackBoard blackboard) {
        try {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                ExceptionHandler.catchException(e);
            }
            UI ui = new UI(blackboard, false);
            blackboard.addListener(Application.POST_INIT_EVENT, new Listener() {
                public void keyChanged(String key, Object value) {
                    GFrame frame = UIUtils.getGFrame(blackboard);
                    URL resource = getClass().getResource("/images/Icon.ICO");
                    if (resource != null) {
                        ImageIcon icon = new ImageIcon(resource);
                        frame.setIconImage(icon.getImage());
                    }
                    frame.validate();
                    UIUtils.getGFrame(blackboard).setVisible(true);
                }
            });
            ui.loadXML("/graphtea/plugins/main/core/SampleUI.xml", getClass());
            UIUtils.registerRenderer(PolygonArrow.class, new ArrowRenderer());
            UIUtils.registerEditor(PolygonArrow.class, new ArrowEditor());
            UIUtils.registerRenderer(GShape.class, new GShapeRenderer());
            UIUtils.registerRenderer(GStroke.class, new GStrokeRenderer());
            UIUtils.registerEditor(GStroke.class, new GStrokeEditor());
            UIUtils.registerEditor(GShape.class, new GSimpleComboEditor(new GShapeRenderer()));
            UIUtils.registerRenderer(SubGraph.class, new SubGraphRenderer());
            UIUtils.registerRenderer(GraphColoring.class, new GraphColoringRenderer());

            StaticUtils.setFromStringProvider(PolygonArrow.class.getName(), new ArrowHandler());
            graphtea.ui.components.GFrame gFrame = UIUtils.getGFrame(blackboard);
            gFrame.setTitle("GraphTea "+Application.VERSION_NAME+" "+Application.VERSION);
            gFrame.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    if (JOptionPane.showConfirmDialog(null, "Do you want to exit?",
                            "Application Exiting...", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        try {
                            SETTINGS.saveSettings();
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                        System.exit(0);
                    }
                }
            });


        } catch (Exception e) {
            ExceptionHandler.catchException(e);
        }
    }
}